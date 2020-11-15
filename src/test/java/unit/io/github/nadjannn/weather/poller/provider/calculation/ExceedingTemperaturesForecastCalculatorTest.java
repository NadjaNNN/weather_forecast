package unit.io.github.nadjannn.weather.poller.provider.calculation;

import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import io.github.nadjannn.weather.data.CityConfiguration;
import io.github.nadjannn.weather.poller.provider.calculation.ExceedingTemperaturesForecastCalculator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class ExceedingTemperaturesForecastCalculatorTest {

    private static final String CITY_NAME = "Norilsk";

    private static final String DATE_TIME = "2020-11-1 19:00:00";

    private ExceedingTemperaturesForecastCalculator calculator = new ExceedingTemperaturesForecastCalculator();

    private CityConfiguration cityConfiguration = new CityConfiguration();

    @Before
    public void setUp() {
        cityConfiguration = new CityConfiguration();
        cityConfiguration.setName(CITY_NAME);
        cityConfiguration.setLowerLimit(new Double(5));
        cityConfiguration.setUpperLimit(new Double(10));
    }

    @Test
    public void whenBothLimitsNotExceeded_shouldReturnEmpty() {
        Optional<Forecast> forecast = calculator.calculateExceededTemperatureForecast(createNotExceededForecastValues(), cityConfiguration);
        assertFalse(forecast.isPresent());
    }

    @Test
    public void whenLowerLimitExceeded_shouldReturnExceededForecast() {
        ForecastValues forecastValues = createExceededLowerLimitForecastValues();
        Optional<Forecast> forecast = calculator.calculateExceededTemperatureForecast(forecastValues, cityConfiguration);
        assertTrue(forecast.isPresent());
        assertEquals(forecastValues, forecast.get().getForecastValues());
        assertTrue(forecast.get().isExceededLowerLimit());
        assertFalse(forecast.get().isExceededUpperLimit());
    }

    @Test
    public void whenUpperLimitExceeded_shouldReturnExceededForecast() {
        ForecastValues forecastValues = createExceededUpperLimitForecastValues();
        Optional<Forecast> forecast = calculator.calculateExceededTemperatureForecast(forecastValues, cityConfiguration);
        assertTrue(forecast.isPresent());
        assertEquals(forecastValues, forecast.get().getForecastValues());
        assertFalse(forecast.get().isExceededLowerLimit());
        assertTrue(forecast.get().isExceededUpperLimit());
    }

    private ForecastValues createNotExceededForecastValues() {
        Double temperature = (cityConfiguration.getLowerLimit().doubleValue() + cityConfiguration.getUpperLimit().doubleValue()) / 2;
        return new ForecastValues(temperature, DATE_TIME);
    }

    private ForecastValues createExceededLowerLimitForecastValues() {
        Double temperature = cityConfiguration.getLowerLimit().doubleValue() - 1;
        return new ForecastValues(temperature, DATE_TIME);
    }

    private ForecastValues createExceededUpperLimitForecastValues() {
        Double temperature = cityConfiguration.getUpperLimit().doubleValue() + 1;
        return new ForecastValues(temperature, DATE_TIME);
    }

}
