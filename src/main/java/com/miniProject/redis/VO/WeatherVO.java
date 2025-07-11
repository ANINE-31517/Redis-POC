package com.miniProject.redis.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherVO {
  private String city;
  private String weather;
  private Double temperature;
  private Double feelsLike;
  private String time;
}
