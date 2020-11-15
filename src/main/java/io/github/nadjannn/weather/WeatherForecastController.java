package io.github.nadjannn.weather;

import io.github.nadjannn.weather.data.CityService;
import io.github.nadjannn.weather.data.dao.City;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CommonsLog
public class WeatherForecastController {

    @Autowired
    private CityService cityService;

    @GetMapping(path = "/forecasts/{cityName}")
    public City fetchCityForecasts(@PathVariable String cityName) {
        log.info("Fetch forecast for " + cityName);
        return cityService.fetchCity(cityName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Forecast for the city is not present. Check configuration."));
    }

}
