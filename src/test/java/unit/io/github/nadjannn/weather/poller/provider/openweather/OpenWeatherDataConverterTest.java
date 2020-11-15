package unit.io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDataConverter;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherTemperature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class OpenWeatherDataConverterTest {

    private static final String DATE_TIME = "2020-11-1 18:00:00";

    private static final Double TEMPERATURE = new Double(-5.5);

    private OpenWeatherDataConverter converter = new OpenWeatherDataConverter();

    private OpenWeatherDayTemperature dayTemperature;

    @Before
    public void setUp() {
        dayTemperature = new OpenWeatherDayTemperature();
        dayTemperature.setDateTime(DATE_TIME);
        OpenWeatherTemperature temperature = new OpenWeatherTemperature();
        temperature.setTemperatureValue(TEMPERATURE);
        dayTemperature.setTemperature(temperature);
    }

    @Test
    public void whenDateTimeIsDefined_shouldConvertToSameValue() {
        assertEquals(DATE_TIME, converter.convert(dayTemperature).getDateTime());
    }

    @Test
    public void whenTemperatureIsDefined_shouldConvertToSameValue() {
        assertEquals(TEMPERATURE, converter.convert(dayTemperature).getTemperature());
    }

}
