package unit.io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherDayTemperature;
import io.github.nadjannn.weather.poller.provider.openweather.OpenWeatherForecasts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
public class OpenWeatherForecastsTest {

    @Test
    public void whenDayTemperaturesListIsSet_shouldGetSameList() {
        List<OpenWeatherDayTemperature> list = Collections.emptyList();
        OpenWeatherForecasts forecasts = new OpenWeatherForecasts();
        forecasts.setDayTemperatures(list);
        assertEquals(list, forecasts.getDayTemperatures());
    }

    @Test
    public void whenDefaultConstructorIsUsed_dayTemperaturesShouldBeNull() {
        OpenWeatherForecasts forecasts = new OpenWeatherForecasts();
        assertNull(forecasts.getDayTemperatures());
    }

}
