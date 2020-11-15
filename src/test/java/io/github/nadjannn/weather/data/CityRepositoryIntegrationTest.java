package io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.dao.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryIntegrationTest {

    private static final String NAME = "Espoo,fi";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CityRepository repository;

    @Before
    public void setUp() {
        createCity();
    }

    @Test
    public void findByName_whenExistingCity_shouldFetchCity() {
        City city = repository.findByName(NAME);
        assertEquals(NAME, city.getName());
    }

    @Test
    public void findByName_whenNotPresentCity_shouldReturnNull() {
        City city = repository.findByName("Turku");
        assertNull(city);
    }

    private void createCity() {
        entityManager.persist(new City(NAME));
    }

}
