package unit.io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDataReader;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherForecasts;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherTemperature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class OpenWeatherDataReaderTest {

    private static final String DATE_TIME = "2020-11-1 18:05:15";

    private static final String CITY_NAME = "Espoo";

    private static final String URL = "http://api.openweathermap.org/data/2.5/forecast?units=metric";

    private static final String APP_ID = "32223";

    @InjectMocks
    private OpenWeatherDataReader openWeatherDataReader = new OpenWeatherDataReader();

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(openWeatherDataReader, "openWeatherUrl", URL);
        ReflectionTestUtils.setField(openWeatherDataReader, "openWeatherAppId", APP_ID);
    }

    @Test
    public void whenOpenWeatherApiReturnsData_shouldReturnData() {
        OpenWeatherForecasts data = createOpenWeatherForecasts();
        when(restTemplate.getForObject(createCompleteUrl(), OpenWeatherForecasts.class))
                .thenReturn(data);
        assertEquals(data.getDayTemperatures(), openWeatherDataReader.readForecast(CITY_NAME));
    }

    @Test(expected = RestClientException.class)
    public void whenOpenWeatherApiReturnsException_shouldThrowException() {
        when(restTemplate.getForObject(createCompleteUrl(), OpenWeatherForecasts.class))
                .thenThrow(new RestClientException("Message"));
        openWeatherDataReader.readForecast(CITY_NAME);
    }

    private String createCompleteUrl() {
        return URL + "&q=" + CITY_NAME + "&appid=" + APP_ID;
    }

    private OpenWeatherForecasts createOpenWeatherForecasts() {
        OpenWeatherTemperature temperature = new OpenWeatherTemperature(new Double(5));
        OpenWeatherDayTemperature dayTemperature = new OpenWeatherDayTemperature(DATE_TIME, temperature);
        List<OpenWeatherDayTemperature> list = Arrays.asList(dayTemperature);
        return new OpenWeatherForecasts(list);
    }
}
