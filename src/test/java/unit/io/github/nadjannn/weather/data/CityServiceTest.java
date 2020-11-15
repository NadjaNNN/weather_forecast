package unit.io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.CityRepository;
import io.github.nadjannn.weather.data.CityService;
import io.github.nadjannn.weather.data.dao.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class CityServiceTest {

    private static final String CITY_NAME = "Moscow";

    @InjectMocks
    private CityService cityService = new CityService();

    @Mock
    private CityRepository cityRepository;

    @Test
    public void fetchCity_whenCityIsUndefined_shouldReturnEmpty() {
        when(cityRepository.findByName(CITY_NAME)).thenReturn(null);
        Optional<City> city = cityService.fetchCity(CITY_NAME);
        assertFalse(city.isPresent());
    }

    @Test
    public void fetchCity_whenCityIsDefined_shouldReturnOptionalWithValue() {
        when(cityRepository.findByName(CITY_NAME)).thenReturn(new City(CITY_NAME));
        Optional<City> city = cityService.fetchCity(CITY_NAME);
        assertTrue(city.isPresent());
        assertEquals(CITY_NAME, city.get().getName());
    }

    @Test
    public void createCity_shouldCreateCityObject() {
        City city = cityService.createCity(CITY_NAME);
        assertNotNull(city);
        assertEquals(CITY_NAME, city.getName());
    }

    @Test
    public void saveCity_shouldCallSaveInRepository() {
        City city = new City(CITY_NAME);
        cityService.saveCity(city);
        verify(cityRepository, times(1)).save(city);
    }

}
