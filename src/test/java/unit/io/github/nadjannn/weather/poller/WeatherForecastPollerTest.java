package unit.io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.poller.WeatherForecastPoller;
import io.github.nadjannn.weather.poller.WeatherForecastPollerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class WeatherForecastPollerTest<T> {

    @InjectMocks
    private WeatherForecastPoller<T> poller = new WeatherForecastPoller<>();

    @Mock
    private WeatherForecastPollerService<T> weatherForecastPollerService;

    @Test(expected = Test.None.class)
    public void forecastsPollingThrowsException_shouldHandleException() {
        doThrow(new RuntimeException()).when(weatherForecastPollerService).pollForecasts();
        poller.pollWeatherForecast();
    }

    @Test
    public void pollerShouldCallService() {
        poller.pollWeatherForecast();
        verify(weatherForecastPollerService, times(1)).pollForecasts();
    }

}
