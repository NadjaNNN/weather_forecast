package io.github.nadjannn.weather.poller.provider;

import io.github.nadjannn.weather.data.dao.ForecastValues;

public interface DataConverter<T> {

    ForecastValues convert(T dayTemperature);
}
