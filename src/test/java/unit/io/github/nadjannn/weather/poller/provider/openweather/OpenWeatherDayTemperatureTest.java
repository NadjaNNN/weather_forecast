package unit.io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherTemperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
public class OpenWeatherDayTemperatureTest {

    private static final String DATE_TIME = "2020-11-1 18:05:15";

    @Test
    public void whenTemperatureFieldIsSet_shouldGetSameValue() {
        OpenWeatherTemperature temperature = new OpenWeatherTemperature();
        OpenWeatherDayTemperature dayTemperature = new OpenWeatherDayTemperature();
        dayTemperature.setTemperature(temperature);
        assertEquals(temperature, dayTemperature.getTemperature());
    }

    @Test
    public void whenDateTimeFieldIsSet_shouldGetSameValue() {
        OpenWeatherDayTemperature dayTemperature = new OpenWeatherDayTemperature();
        dayTemperature.setDateTime(DATE_TIME);
        assertEquals(DATE_TIME, dayTemperature.getDateTime());
    }

    @Test
    public void whenDefaultConstructorIsUsed_allFieldsShouldBeNull() {
        OpenWeatherDayTemperature dayTemperature = new OpenWeatherDayTemperature();
        assertNull(dayTemperature.getDateTime());
        assertNull(dayTemperature.getTemperature());
    }
}
