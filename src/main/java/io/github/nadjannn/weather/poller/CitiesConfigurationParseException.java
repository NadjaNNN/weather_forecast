package io.github.nadjannn.weather.poller;

public class CitiesConfigurationParseException extends RuntimeException {

    public CitiesConfigurationParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CitiesConfigurationParseException(String message) {
        super(message);
    }

}
