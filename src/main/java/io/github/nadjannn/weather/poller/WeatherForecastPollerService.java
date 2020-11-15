package io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.data.CityConfiguration;
import io.github.nadjannn.weather.data.WeatherForecastDataService;
import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.poller.provider.DataConverter;
import io.github.nadjannn.weather.poller.provider.DataReader;
import io.github.nadjannn.weather.poller.provider.calculation.ForecastCalculator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CommonsLog
public class WeatherForecastPollerService<T> {

    @Autowired
    private WeatherForecastDataService weatherForecastDataService;

    @Autowired
    private DataReader<T> dataReader;

    @Autowired
    private DataConverter<T> dataConverter;

    @Autowired
    private ForecastCalculator forecastCalculator;

    @Autowired
    private CitiesConfigurationReader citiesConfigurationReader;

    public void pollForecasts() {
        List<CityConfiguration> cityConfigurations = citiesConfigurationReader.getCitiesConfigurations();
        cityConfigurations.forEach(city -> processCity(city));
    }

    private void processCity(CityConfiguration cityConfiguration) {
        try {
            log.info("Process city " + cityConfiguration.getName());
            List<T> rawForecasts = dataReader.readForecast(cityConfiguration.getName());
            List<Forecast> forecasts = rawForecasts.stream()
                    .map(dataConverter::convert)
                    .map(f -> forecastCalculator.calculateExceededTemperatureForecast(f, cityConfiguration))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            weatherForecastDataService.saveCityForecast(cityConfiguration.getName(), forecasts);
        } catch (Exception e) {
            log.error("Error process city " + cityConfiguration.getName(), e);
        }
    }

}
