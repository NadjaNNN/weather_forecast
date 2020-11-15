package io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.dao.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {

    City findByName(String name);
}
