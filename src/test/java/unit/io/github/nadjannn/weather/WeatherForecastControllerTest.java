package unit.io.github.nadjannn.weather;

import io.github.nadjannn.weather.WeatherForecastController;
import io.github.nadjannn.weather.data.CityService;
import io.github.nadjannn.weather.data.dao.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WeatherForecastControllerTest {

    private static final String CITY_NAME = "Espoo";

    @InjectMocks
    private WeatherForecastController weatherForecastController = new WeatherForecastController();

    @Mock
    private CityService cityService;

    @Test
    public void whenCityIsPresent_shouldReturnCity() {
        City city = new City(CITY_NAME);
        when(cityService.fetchCity(CITY_NAME)).thenReturn(Optional.of(city));
        assertEquals(city, weatherForecastController.fetchCityForecasts(CITY_NAME));
    }

    @Test(expected = ResponseStatusException.class)
    public void whenCityIsNotPresent_shouldThrowException() {
        when(cityService.fetchCity(CITY_NAME)).thenReturn(Optional.empty());
        weatherForecastController.fetchCityForecasts(CITY_NAME);
    }

    @Test(expected = RuntimeException.class)
    public void whenCityReadingThrowsException_shouldThrowException() {
        when(cityService.fetchCity(CITY_NAME)).thenThrow(new RuntimeException());
        weatherForecastController.fetchCityForecasts(CITY_NAME);
    }

}
