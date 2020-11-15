package unit.io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.poller.CitiesConfigurationParseException;
import io.github.nadjannn.weather.poller.CitiesConfigurationReader;
import io.github.nadjannn.weather.data.CityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class CitiesConfigurationReaderTest {

    private static final String ESPOO_CITY = "{\"name\":\"Espoo\", \"upperLimit\": 20, \"lowerLimit\": -5.5}";

    private static final String TURKU_CITY = "{\"name\":\"TURKU\", \"upperLimit\": 10, \"lowerLimit\": 0}";

    private static final String VALUE_NAME = "cities";

    private CitiesConfigurationReader citiesConfigurationReader = new CitiesConfigurationReader();

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenCityHasDuplication_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[" + ESPOO_CITY + "," + ESPOO_CITY + "]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenConfigurationIsEmptyList_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test
    public void whenConfigurationContainsOneCity_shouldReturnListWithOneCity() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[" + ESPOO_CITY + "]");
        assertEquals(1, citiesConfigurationReader.getCitiesConfigurations().size());
    }

    @Test
    public void whenConfigurationContainsMoreThanOneCity_shouldReturnListWithSameSize() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[" + ESPOO_CITY + "," + TURKU_CITY + "]");
        assertEquals(2, citiesConfigurationReader.getCitiesConfigurations().size());
    }

    @Test
    public void whenAllFieldsAreDefined_configurationShouldContainAllFields() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[" + ESPOO_CITY + "]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals("Espoo", cityConfiguration.getName());
        assertEquals(new Double(20), cityConfiguration.getUpperLimit());
        assertEquals(new Double(-5.5), cityConfiguration.getLowerLimit());
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenBrokenJson_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\" \"upperLimit\": 20 \"lowerLimit\": -5.5}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenExtraFieldIsDefined_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\", \"upperLimit\": 20, \"lowerLimit\": -5.5, \"something\": 10}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenNameFieldIsUndefined_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"upperLimit\": 20, \"lowerLimit\": -5.5}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenNameFieldIsEmpty_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"\", \"upperLimit\": 20, \"lowerLimit\": -5.5}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenUpperLimitFieldIsUndefined_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"lowerLimit\": -5.5}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenUpperLimitFieldIsEmpty_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": , \"lowerLimit\": -5.5}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenLowerLimitFieldIsUndefined_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 20}]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test(expected = CitiesConfigurationParseException.class)
    public void whenLowerLimitFieldIsEmpty_shouldThrowException() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 20, \"lowerLimit\": }]");
        citiesConfigurationReader.getCitiesConfigurations();
    }

    @Test
    public void whenUpperLimitFieldIsZero_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 0, \"lowerLimit\": -5.5}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(0), cityConfiguration.getUpperLimit());
    }

    @Test
    public void whenUpperLimitFieldIsPositive_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 10, \"lowerLimit\": -5.5}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(10), cityConfiguration.getUpperLimit());
    }

    @Test
    public void whenUpperLimitFieldIsNegative_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": -10, \"lowerLimit\": -5.5}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(-10), cityConfiguration.getUpperLimit());
    }

    @Test
    public void whenLowerLimitFieldIsZero_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 10, \"lowerLimit\": 0}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(0), cityConfiguration.getLowerLimit());
    }

    @Test
    public void whenLowerLimitFieldIsPositive_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 10, \"lowerLimit\": 5.5}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(5.5), cityConfiguration.getLowerLimit());
    }

    @Test
    public void whenLowerLimitFieldIsNegative_shouldBeAcceptable() {
        ReflectionTestUtils.setField(citiesConfigurationReader, VALUE_NAME, "[{\"name\":\"Espoo\",\"upperLimit\": 10, \"lowerLimit\": -5.5}]");
        CityConfiguration cityConfiguration = citiesConfigurationReader.getCitiesConfigurations().get(0);
        assertEquals(new Double(-5.5), cityConfiguration.getLowerLimit());
    }

}
