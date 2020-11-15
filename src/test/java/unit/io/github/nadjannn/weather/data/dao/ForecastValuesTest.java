package unit.io.github.nadjannn.weather.data.dao;

import io.github.nadjannn.weather.data.dao.ForecastValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
public class ForecastValuesTest {

    private static final Double TEMPERATURE = new Double(5);

    private static final String DATE_TIME = "2020-11-04 19:00:00";

    @Test
    public void emptyConstructorShouldBeAvailable() {
        ForecastValues forecastValues = new ForecastValues();
        assertNotNull(forecastValues);
    }

    @Test
    public void constructorWithTemperatureAndDateShouldAssignValues() {
        ForecastValues forecastValues = new ForecastValues(TEMPERATURE, DATE_TIME);
        assertEquals(DATE_TIME, forecastValues.getDateTime());
        assertEquals(TEMPERATURE, forecastValues.getTemperature());
    }

    @Test
    public void whenSetTemperatureShouldGetSameValue() {
        ForecastValues forecastValues = new ForecastValues();
        forecastValues.setTemperature(TEMPERATURE);
        assertEquals(TEMPERATURE, forecastValues.getTemperature());
    }

    @Test
    public void whenSetDateTimeShouldGetSameValue() {
        ForecastValues forecastValues = new ForecastValues();
        forecastValues.setDateTime(DATE_TIME);
        assertEquals(DATE_TIME, forecastValues.getDateTime());
    }
}
