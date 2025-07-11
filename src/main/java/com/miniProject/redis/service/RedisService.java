package com.miniProject.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

  private final RedisTemplate redisTemplate;

  public <T> T get(String key, Class<T> weatherVOClass) {
    try {
      Object obj = redisTemplate.opsForValue().get(key);
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(obj.toString(), weatherVOClass);
    } catch (Exception e) {
      log.error("Exception {}", e.getMessage());
      return null;
    }
  }

  public void set(String key, Object obj, Long time) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(obj);
      redisTemplate.opsForValue().set(key, json, time, TimeUnit.SECONDS);
    } catch (Exception e) {
      log.error("Exception occured {}", e.getMessage());
    }
  }

}
