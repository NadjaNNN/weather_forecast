package io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.DataReader;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@CommonsLog
public class OpenWeatherDataReader implements DataReader<OpenWeatherDayTemperature> {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openWeather.url}")
    private String openWeatherUrl;

    @Value("${openWeather.appId}")
    private String openWeatherAppId;

    public List<OpenWeatherDayTemperature> readForecast(String cityName) {
        log.info("Read forecast for " + cityName);
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(openWeatherUrl)
                .queryParam("q", cityName)
                .queryParam("appid", openWeatherAppId)
                .build();
        return restTemplate.getForObject(uriComponents.toUriString(), OpenWeatherForecasts.class).getDayTemperatures();
    }

}
