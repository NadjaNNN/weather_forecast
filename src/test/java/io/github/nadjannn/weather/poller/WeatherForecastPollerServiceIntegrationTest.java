package io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.data.CityRepository;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherForecastPollerServiceIntegrationTest {

    @Autowired
    private WeatherForecastPollerService<OpenWeatherDayTemperature> pollerService;

    @MockBean
    private WeatherForecastPoller<OpenWeatherDayTemperature> poller;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void pollingShouldSaveData() {
        pollerService.pollForecasts();
        assertNotNull(cityRepository.findByName("Espoo,fi"));
        assertNotNull(cityRepository.findByName("Turku,fi"));
    }

}
