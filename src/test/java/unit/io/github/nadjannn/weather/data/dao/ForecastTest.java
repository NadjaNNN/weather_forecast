package unit.io.github.nadjannn.weather.data.dao;

import io.github.nadjannn.weather.data.dao.City;
import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ForecastTest {

    private static final Double TEMPERATURE = new Double(15);

    private static final String DATE_TIME = "2020-11-04 20:00:00";

    @Test
    public void emptyConstructorShouldBeAvailable() {
        Forecast forecast = new Forecast();
        assertNotNull(forecast);
    }

    @Test
    public void constructorWithValuesShouldAssignAllOfThem() {
        ForecastValues forecastValues = new ForecastValues(TEMPERATURE, DATE_TIME);
        Forecast forecast = new Forecast(forecastValues, Boolean.TRUE, Boolean.TRUE);
        assertEquals(forecastValues, forecast.getForecastValues());
        assertTrue(forecast.isExceededLowerLimit());
        assertTrue(forecast.isExceededUpperLimit());
    }

    @Test
    public void whenSetIdShouldGetSameValue() {
        Forecast forecast = new Forecast();
        forecast.setId(60);
        assertEquals(60, forecast.getId().intValue());
    }

    @Test
    public void whenSetExceededLowerLimitShouldGetSameValue() {
        Forecast forecast = new Forecast();
        forecast.setExceededLowerLimit(true);
        assertTrue(forecast.isExceededLowerLimit());
    }

    @Test
    public void whenSetExceededUpperLimitShouldGetSameValue() {
        Forecast forecast = new Forecast();
        forecast.setExceededUpperLimit(true);
        assertTrue(forecast.isExceededUpperLimit());
    }

    @Test
    public void whenSetCityShouldGetSameValue() {
        Forecast forecast = new Forecast();
        City city = new City();
        forecast.setCity(city);
        assertEquals(city, forecast.getCity());
    }

    @Test
    public void whenSetForecastValuesShouldGetSameValue() {
        Forecast forecast = new Forecast();
        ForecastValues values = new ForecastValues(TEMPERATURE, DATE_TIME);
        forecast.setForecastValues(values);
        assertEquals(values, forecast.getForecastValues());
    }
}
