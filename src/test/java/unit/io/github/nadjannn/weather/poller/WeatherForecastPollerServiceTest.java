package unit.io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.data.CityConfiguration;
import io.github.nadjannn.weather.data.WeatherForecastDataService;
import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import io.github.nadjannn.weather.poller.CitiesConfigurationReader;
import io.github.nadjannn.weather.poller.WeatherForecastPollerService;
import io.github.nadjannn.weather.poller.provider.DataConverter;
import io.github.nadjannn.weather.poller.provider.DataReader;
import io.github.nadjannn.weather.poller.provider.calculation.ForecastCalculator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WeatherForecastPollerServiceTest<T> {

    private static final String CITY_NAME = "Joensuu,fi";

    private static final String DATE_TIME = "2020-11-05 18:00:00";

    @InjectMocks
    private WeatherForecastPollerService weatherForecastPollerService = new WeatherForecastPollerService();

    @Mock
    private WeatherForecastDataService weatherForecastDataService;

    @Mock
    private DataReader<T> dataReader;

    @Mock
    private DataConverter<T> dataConverter;

    @Mock
    private ForecastCalculator forecastCalculator;

    @Mock
    private CitiesConfigurationReader citiesConfigurationReader;

    private CityConfiguration cityConfiguration;

    @Before
    public void setUp() {
        cityConfiguration = new CityConfiguration(CITY_NAME, new Double(5), new Double(10));
        when(citiesConfigurationReader.getCitiesConfigurations()).thenReturn(Arrays.asList(cityConfiguration));
    }

    @Test(expected = RuntimeException.class)
    public void whenConfigurationReadingThrowsException_shouldThrowException() {
        when(citiesConfigurationReader.getCitiesConfigurations()).thenThrow(new RuntimeException("Message"));
        mock(Arrays.asList(new ForecastMapping()));
        weatherForecastPollerService.pollForecasts();
    }

    @Test
    public void configurationReadingShouldBeCalled() {
        mock(Arrays.asList(new ForecastMapping()));
        weatherForecastPollerService.pollForecasts();
        verify(citiesConfigurationReader, times(1)).getCitiesConfigurations();
    }

    @Test(expected = Test.None.class)
    public void whenDataReaderThrowsException_shouldNotThrowException() {
        mock(Arrays.asList(new ForecastMapping()));
        when(dataReader.readForecast(CITY_NAME)).thenThrow(RuntimeException.class);
        weatherForecastPollerService.pollForecasts();
    }

    @Test(expected = Test.None.class)
    public void whenDataConverterThrowsException_shouldNotThrowException() {
        ForecastMapping forecastMapping = new ForecastMapping();
        mock(Arrays.asList(forecastMapping));
        when(dataConverter.convert((T) forecastMapping.rawForecast)).thenThrow(RuntimeException.class);
        weatherForecastPollerService.pollForecasts();
    }

    @Test(expected = Test.None.class)
    public void whenCalculatorThrowsException_shouldNotThrowException() {
        ForecastMapping forecastMapping = new ForecastMapping();
        mock(Arrays.asList(forecastMapping));
        when(forecastCalculator.calculateExceededTemperatureForecast(forecastMapping.forecastValues, cityConfiguration))
                .thenThrow(RuntimeException.class);
        weatherForecastPollerService.pollForecasts();
    }

    @Test
    public void whenOnlyNotExceededForecastPresent_shouldSkipIt() {
        mock(Arrays.asList(new ForecastMapping()));
        weatherForecastPollerService.pollForecasts();
        verify(weatherForecastDataService, times(1)).saveCityForecast(CITY_NAME, Collections.emptyList());
    }

    @Test
    public void whenExceededLowerLimitForecastPresent_shouldKeepExceededOnly() {
        verifyExceededLimitFiltering(true, false);
    }

    @Test
    public void whenExceededUpperLimitForecastPresent_shouldKeepExceededOnly() {
        verifyExceededLimitFiltering(false, true);
    }

    @Test
    public void whenBothExceededLimitsPresent_shouldKeepExceededOnly() {
        verifyExceededLimitFiltering(true, true);
    }

    private void verifyExceededLimitFiltering(boolean exceededLowerLimit, boolean exceededUpperLimit) {
        List<ForecastMapping> list = Arrays.asList(new ForecastMapping(), createExceededForecastMapping(exceededUpperLimit, exceededLowerLimit));
        mock(list);
        weatherForecastPollerService.pollForecasts();
        verify(weatherForecastDataService, times(1)).saveCityForecast(CITY_NAME, fetchFilteredList(list));
    }

    private List<Forecast> fetchFilteredList(List<ForecastMapping> list) {
        return list.stream().filter(m -> m.forecast != null).map(ForecastMapping::getForecast).collect(Collectors.toList());
    }

    private void mock(List<ForecastMapping> list) {
        mockDataReader(list);
        mockDataConverter(list);
        mockCalculator(list);
    }

    private void mockCalculator(List<ForecastMapping> forecastMappings) {
        forecastMappings.forEach(m ->
                when(forecastCalculator.calculateExceededTemperatureForecast(m.forecastValues, cityConfiguration))
                        .thenReturn(Optional.ofNullable(m.forecast)));
    }

    private void mockDataConverter(List<ForecastMapping> forecastMappings) {
        forecastMappings.forEach(m ->
                when(dataConverter.convert((T) m.rawForecast)).thenReturn(m.forecastValues));
    }

    private ForecastMapping createExceededForecastMapping(boolean exceededLowerLimit, boolean exceededUpperLimit) {
        ForecastValues exceededForecastValues = new ForecastValues(new Double(4), DATE_TIME);
        return new ForecastMapping(new Forecast(exceededForecastValues, exceededLowerLimit, exceededUpperLimit));
    }

    private void mockDataReader(List<ForecastMapping> forecastMappings) {
        List<T> rawForecasts = forecastMappings.stream()
                .map(ForecastMapping::getRawForecast)
                .map(m -> (T) m)
                .collect(Collectors.toList());
        when(dataReader.readForecast(CITY_NAME)).thenReturn(rawForecasts);
    }

    @Data
    @NoArgsConstructor
    private static class ForecastMapping {
        private Object rawForecast = new Object();
        private ForecastValues forecastValues = new ForecastValues(new Double(Math.random()), DATE_TIME);
        private Forecast forecast;

        public ForecastMapping(Forecast forecast) {
            this.forecast = forecast;
        }
    }
}
