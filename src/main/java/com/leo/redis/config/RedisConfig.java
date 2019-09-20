package com.leo.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Leo Liu
 * @create 19/09/2019
 *     <p>自定义的redisConfig的配置，不配置的，也有默认的spring redis配置
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  @Autowired private RedisProperties redisProperties;

  @Bean
  @Profile({"single"})
  public LettuceConnectionFactory singleConnectionFactory(
      GenericObjectPoolConfig genericObjectPoolConfig) {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(redisProperties.getHost());
    redisStandaloneConfiguration.setPort(redisProperties.getPort());
    LettuceClientConfiguration configuration =
        LettucePoolingClientConfiguration.builder()
            .poolConfig(genericObjectPoolConfig)
            .commandTimeout(Duration.ofMillis(redisProperties.getTimeout()))
            .build();
    return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
  }

  @Bean
  @Profile({"cluster"})
  public LettuceConnectionFactory clusterConnectionFactory(
      GenericObjectPoolConfig genericObjectPoolConfig) {
    RedisClusterConfiguration redisClusterConfiguration =
        new RedisClusterConfiguration(redisProperties.getClusterNodes());
    LettucePoolingClientConfiguration configuration =
        LettucePoolingClientConfiguration.builder()
            .poolConfig(genericObjectPoolConfig)
            .commandTimeout(Duration.ofMillis(redisProperties.getTimeout()))
            .build();
    return new LettuceConnectionFactory(redisClusterConfiguration, configuration);
  }

  @Bean
  public GenericObjectPoolConfig genericObjectPoolConfig() {
    GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig<>();
    genericObjectPoolConfig.setMaxIdle(redisProperties.getLettucePoolMaxIdle());
    genericObjectPoolConfig.setMinIdle(redisProperties.getLettucePoolMinIdle());
    genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getLettucePoolMaxWait());
    genericObjectPoolConfig.setMaxTotal(redisProperties.getLettucePoolMaxActive());
    return genericObjectPoolConfig;
  }

  /**
   * 配置自定义的RedisTemplate， 不配置的话 会用默认的序列化方式， ① string: StringRedisSerializer ② object:
   * JdkSerializationRedisSerializer
   *
   * @param connectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);
    redisTemplate.setEnableTransactionSupport(true);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
