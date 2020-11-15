package io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.dao.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Optional<City> fetchCity(String cityName) {
        return Optional.ofNullable(cityRepository.findByName(cityName));
    }

    public City createCity(String cityName) {
        return new City(cityName);
    }

    public void saveCity(City city) {
        cityRepository.save(city);
    }

}
