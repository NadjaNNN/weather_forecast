package unit.io.github.nadjannn.weather.data;

import io.github.nadjannn.weather.data.CityConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
public class CityConfigurationTest {

    private static final String NAME = "name";

    private static final Double VALUE = new Double(10);

    @Test
    public void whenNameIsSet_shouldGetSameName() {
        CityConfiguration cityConfiguration = new CityConfiguration();
        cityConfiguration.setName(NAME);
        assertEquals(NAME, cityConfiguration.getName());
    }

    @Test
    public void whenLowerLimitIsSet_shouldGetSameValue() {
        CityConfiguration cityConfiguration = new CityConfiguration();
        cityConfiguration.setLowerLimit(VALUE);
        assertEquals(VALUE, cityConfiguration.getLowerLimit());
    }

    @Test
    public void whenUpperLimitIsSet_shouldGetSameValue() {
        CityConfiguration cityConfiguration = new CityConfiguration();
        cityConfiguration.setUpperLimit(VALUE);
        assertEquals(VALUE, cityConfiguration.getUpperLimit());
    }

    @Test
    public void whenDefaultConstructorIsUsed_allFieldsShouldBeNull() {
        CityConfiguration cityConfiguration = new CityConfiguration();
        assertNull(cityConfiguration.getName());
        assertNull(cityConfiguration.getLowerLimit());
        assertNull(cityConfiguration.getUpperLimit());
    }

}
