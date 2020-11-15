package io.github.nadjannn.weather.poller.provider.openweather;

import io.github.nadjannn.weather.data.dao.ForecastValues;
import io.github.nadjannn.weather.poller.provider.DataConverter;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherDataConverter implements DataConverter<OpenWeatherDayTemperature> {

    public ForecastValues convert(OpenWeatherDayTemperature dayTemperature) {
        String dateTime = dayTemperature.getDateTime();
        Double temperature = dayTemperature.getTemperature().getTemperatureValue();
        return new ForecastValues(temperature, dateTime);
    }

}
