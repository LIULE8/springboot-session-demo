package com.leo.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author Leo Liu
 * @create 20/09/2019
 */
@Configuration
@EnableRedisHttpSession(
    redisNamespace = "leo.session",
    maxInactiveIntervalInSeconds = 8 * 60 * 60)
public class SessionConfig {

  /** 使用header的X-Auth-Token作为token的载体 */
  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }
}
