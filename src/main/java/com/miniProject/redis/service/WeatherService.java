package com.miniProject.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miniProject.redis.CO.WeatherAppCO;
import com.miniProject.redis.VO.WeatherVO;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final RestTemplate restTemplate = new RestTemplate();
  private final RedisService redisService;

  @Value("${weather.api.key}")
  private String apiKey;

  @Value("${weather.api.url}")
  private String apiUrl;

  public WeatherVO getWeather(String city) throws JsonProcessingException {
    WeatherVO weatherVO = redisService.get(city, WeatherVO.class);
    if (weatherVO != null) {
      return weatherVO;
    } else {
      String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
      WeatherAppCO response = restTemplate.getForObject(url, WeatherAppCO.class);

      Map<String, Object> main = response.getMain();
      String formattedTime = Instant.ofEpochSecond(response.getDt())
          .atZone(ZoneId.of("Asia/Kolkata"))
          .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

      WeatherVO weatherVO1 = WeatherVO.builder()
          .city(response.getName())
          .temperature((Double) main.get("temp"))
          .feelsLike((Double) main.get("feels_like"))
          .weather(response.getWeather().getFirst().get("main"))
          .time(formattedTime)
          .build();
      if (weatherVO1 != null) {
        redisService.set(city, weatherVO1, 300L);
      }
      return weatherVO1;
    }
  }

}
