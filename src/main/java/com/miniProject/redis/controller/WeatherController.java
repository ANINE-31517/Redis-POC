package com.miniProject.redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniProject.redis.VO.WeatherVO;
import com.miniProject.redis.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping("/get-weather/{city}")
  public ResponseEntity<WeatherVO> getWeather(@PathVariable String city)
      throws JsonProcessingException {
    WeatherVO response = weatherService.getWeather(city);
    return ResponseEntity.ok(response);
  }
}
