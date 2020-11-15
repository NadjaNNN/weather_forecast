package io.github.nadjannn.weather.poller.provider;

import java.util.List;

public interface DataReader<T> {

    List<T> readForecast(String cityName);
}
