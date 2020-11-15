package unit.io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.CityService;
import io.github.nadjannn.weather.data.WeatherForecastDataService;
import io.github.nadjannn.weather.data.dao.City;
import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WeatherForecastDataServiceTest {

    private static final String CITY_NAME = "Espoo,fi";

    private static final String DATE_TIME = "2020-11-04 18:00:00";

    @InjectMocks
    private WeatherForecastDataService weatherForecastDataService = new WeatherForecastDataService();

    @Mock
    private CityService cityService;

    private City city;

    @Before
    public void setUp() {
        city = new City(CITY_NAME);
    }

    @Test
    public void whenCityIsNotPresent_shouldBeSaved() {
        mockUndefinedCity();
        weatherForecastDataService.saveCityForecast(CITY_NAME, createForecasts());
        verify(cityService, times(1)).saveCity(city);
    }

    @Test
    public void whenCityIsPresent_shouldBeSaved() {
       mockExistingCity();
       weatherForecastDataService.saveCityForecast(CITY_NAME, createForecasts());
       verify(cityService, times(1)).saveCity(city);
    }

    @Test
    public void whenCityIsNotPresent_shouldAssignForecasts() {
        mockUndefinedCity();
        weatherForecastDataService.saveCityForecast(CITY_NAME, createForecasts());
        assertTrue(city.getForecasts().size() > 0);
    }

    @Test
    public void whenCityIsPresent_shouldAssignForecasts() {
        mockExistingCity();
        weatherForecastDataService.saveCityForecast(CITY_NAME, createForecasts());
        assertTrue(city.getForecasts().size() > 0);
    }

    private void mockExistingCity() {
        when(cityService.fetchCity(CITY_NAME)).thenReturn(Optional.of(city));
    }

    private void mockUndefinedCity() {
        when(cityService.fetchCity(CITY_NAME)).thenReturn(Optional.empty());
        when(cityService.createCity(CITY_NAME)).thenReturn(city);
    }

    private List<Forecast> createForecasts() {
        return Arrays.asList(
                new Forecast(new ForecastValues(new Double(5), DATE_TIME), true, true),
                new Forecast(new ForecastValues(new Double(15), DATE_TIME), true, true)
        );
    }

}
