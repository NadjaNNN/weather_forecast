package io.github.nadjannn.weather.poller.provider.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeatherForecasts {

    @JsonProperty(value = "list")
    private List<OpenWeatherDayTemperature> dayTemperatures;
}
