package unit.io.github.nadjannn.weather;

import io.github.nadjannn.weather.WeatherForecastApplicationConfiguration;
import io.github.nadjannn.weather.poller.WeatherForecastPoller;
import io.github.nadjannn.weather.poller.WeatherForecastPollerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class WeatherForecastApplicationConfigurationTest {

    private WeatherForecastApplicationConfiguration configuration = new WeatherForecastApplicationConfiguration();

    @Test
    public void shouldGetRestTemplate() {
        assertTrue(configuration.getRestTemplate() instanceof RestTemplate);
    }

    @Test
    public void shouldGetWeatherForecastPoller() {
        assertTrue(configuration.getWeatherForecastPoller() instanceof WeatherForecastPoller);
    }

    @Test
    public void shouldGetWeatherForecastPollerService() {
        assertTrue(configuration.getWeatherForecastPollerService() instanceof WeatherForecastPollerService);
    }

}
