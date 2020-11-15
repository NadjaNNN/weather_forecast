package io.github.nadjannn.weather;

import io.github.nadjannn.weather.poller.WeatherForecastPoller;
import io.github.nadjannn.weather.poller.WeatherForecastPollerService;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherForecastControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherForecastPoller<OpenWeatherDayTemperature> poller;

    @Autowired
    private WeatherForecastPollerService<OpenWeatherDayTemperature> pollerService;

    @Test
    public void whenCityIsConfigured_shouldReturnCity() throws Exception {
        pollerService.pollForecasts();
        mockMvc.perform(get("/forecasts/Espoo,fi")).andExpect(status().isOk());
    }

    @Test
    public void whenCityIsNotConfigured_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/forecasts/Joensuu,fi")).andExpect(status().isBadRequest());
    }

}
