package unit.io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherTemperature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
public class OpenWeatherTemperatureTest {

    private static final Double TEMPERATURE = new Double(5);

    @Test
    public void whenTemperatureFieldIsSet_shouldReturnSameValue() {
        OpenWeatherTemperature temperature = new OpenWeatherTemperature();
        temperature.setTemperatureValue(TEMPERATURE);
        assertEquals(TEMPERATURE, temperature.getTemperatureValue());
    }

    @Test
    public void whenDefaultConstructorIsUsed_temperatureShouldBeNull() {
        OpenWeatherTemperature temperature = new OpenWeatherTemperature();
        assertNull(temperature.getTemperatureValue());
    }
}
