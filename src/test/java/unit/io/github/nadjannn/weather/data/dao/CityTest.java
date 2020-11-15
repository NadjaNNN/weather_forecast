package unit.io.github.nadjannn.weather.data.dao;

import io.github.nadjannn.weather.data.dao.City;
import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class CityTest {

    private static final String CITY_NAME = "Moscow";

    private static final String DATE_TIME = "2020-11-04 19:00:00";

    @Test
    public void emptyConstructorShouldBeAvailable() {
        City city = new City();
        assertNotNull(city);
    }

    @Test
    public void constructorWitNameShouldDefineName() {
        City city = new City(CITY_NAME);
        assertEquals(CITY_NAME, city.getName());
    }

    @Test
    public void constructorWitNameAndForecastShouldDefineFields() {
        City city = new City(CITY_NAME, createForecasts());
        assertEquals(CITY_NAME, city.getName());
        assertTrue(city.getForecasts().size() > 0);
    }

    @Test
    public void whenIdIsSetShouldGetSameValue() {
        City city = new City();
        city.setId(5);
        assertEquals(5, city.getId().intValue());
    }

    @Test
    public void whenNameIsSetShouldGetSameValue() {
        City city = new City();
        city.setName(CITY_NAME);
        assertEquals(CITY_NAME, city.getName());
    }

    @Test
    public void whenForecastsListIsSetShouldGetSameValue() {
        City city = new City();
        List<Forecast> forecasts = createForecasts();
        city.setForecasts(forecasts);
        assertEquals(forecasts, city.getForecasts());
    }

    private List<Forecast> createForecasts() {
        return Arrays.asList(
                new Forecast(new ForecastValues(new Double(10), DATE_TIME), false, true)
        );
    }
}
