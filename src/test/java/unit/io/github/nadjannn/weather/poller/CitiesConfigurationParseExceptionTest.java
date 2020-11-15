package unit.io.github.nadjannn.weather.poller;

import io.github.nadjannn.weather.poller.CitiesConfigurationParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class CitiesConfigurationParseExceptionTest {

    private static final String MESSAGE = "Message";

    @Test
    public void whenMessageIsInConstructor_shouldKeepMessage() {
        CitiesConfigurationParseException exception = new CitiesConfigurationParseException(MESSAGE);
        assertEquals(MESSAGE, exception.getMessage());
    }

    @Test
    public void whenMessageAndCauseAreInConstructor_shouldKeepMessageAndCause() {
        Throwable cause = new Throwable();
        CitiesConfigurationParseException exception = new CitiesConfigurationParseException(MESSAGE, cause);
        assertEquals(MESSAGE, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
