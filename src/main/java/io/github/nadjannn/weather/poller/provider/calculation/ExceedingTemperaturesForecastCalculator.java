package io.github.nadjannn.weather.poller.provider.calculation;

import io.github.nadjannn.weather.data.dao.Forecast;
import io.github.nadjannn.weather.data.dao.ForecastValues;
import io.github.nadjannn.weather.data.CityConfiguration;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExceedingTemperaturesForecastCalculator implements ForecastCalculator {

    public Optional<Forecast> calculateExceededTemperatureForecast(ForecastValues forecastValues,
                                                                   CityConfiguration cityConfiguration){
        boolean exceededLowerLimit = forecastValues.getTemperature().doubleValue() < cityConfiguration.getLowerLimit().doubleValue();
        boolean exceededUpperLimit = forecastValues.getTemperature().doubleValue() > cityConfiguration.getUpperLimit().doubleValue();
        if (exceededLowerLimit || exceededUpperLimit) {
            return Optional.of(new Forecast(forecastValues, exceededLowerLimit, exceededUpperLimit));
        } else {
            return Optional.empty();
        }
    }

}
