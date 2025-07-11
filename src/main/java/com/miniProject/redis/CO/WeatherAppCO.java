package com.miniProject.redis.CO;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class WeatherAppCO {
  private Map<String, Object> main;
  private List<Map<String, String>> weather;
  private long dt;
  private String name;
}
