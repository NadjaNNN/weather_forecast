package io.github.nadjannn.weather.poller.provider.calculation;

import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import io.github.nadjannn.weather.data.CityConfiguration;

import java.util.Optional;

public interface ForecastCalculator {

    Optional<Forecast> calculateExceededTemperatureForecast(ForecastValues forecast, CityConfiguration cityConfiguration);

}
