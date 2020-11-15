package io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.dao.City;
import io.github.nadjannn.weather.data.dao.Forecast;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CommonsLog
public class WeatherForecastDataService {

    @Autowired
    private CityService cityService;

    @Transactional
    public void saveCityForecast(String cityName, List<Forecast> forecasts) {
        log.info("Saving city forecast: " + cityName);
        // Wrap forecasts delete and insert operations into one transaction.
        // Also city field updated should be updated at the same time during city saving.
        City city = fetchOrCreateCity(cityName);
        setupForecasts(city, forecasts);
        cityService.saveCity(city);
    }

    private City fetchOrCreateCity(String cityName) {
        return cityService.fetchCity(cityName)
                .orElse(cityService.createCity(cityName));
    }

    private void setupForecasts(City city, List<Forecast> forecasts) {
        city.getForecasts().clear();
        forecasts.forEach(f -> f.setCity(city));
        city.getForecasts().addAll(forecasts);
    }

}
