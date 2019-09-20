package com.leo.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Leo Liu
 * @create 19/09/2019
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
  private String host;
  private Integer port;
  private Integer timeout;
  private List<String> clusterNodes;
  @Value("${spring.redis.lettuce.pool.max-active}")
  private Integer lettucePoolMaxActive;
  @Value("${spring.redis.lettuce.pool.max-wait}")
  private Integer lettucePoolMaxWait;
  @Value("${spring.redis.lettuce.pool.max-idle}")
  private Integer lettucePoolMaxIdle;
  @Value("${spring.redis.lettuce.pool.min-idle}")
  private Integer lettucePoolMinIdle;
}