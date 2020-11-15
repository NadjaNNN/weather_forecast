package io.github.nadjannn.weather;

import io.github.nadjannn.weather.poller.WeatherForecastPoller;
import io.github.nadjannn.weather.poller.WeatherForecastPollerService;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class WeatherForecastApplicationConfiguration {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WeatherForecastPoller<OpenWeatherDayTemperature> getWeatherForecastPoller() {
        return new WeatherForecastPoller<>();
    }

    @Bean
    public WeatherForecastPollerService<OpenWeatherDayTemperature> getWeatherForecastPollerService() {
        return new WeatherForecastPollerService<>();
    }

}
