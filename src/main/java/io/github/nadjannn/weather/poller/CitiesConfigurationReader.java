package io.github.nadjannn.weather.poller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nadjannn.weather.data.CityConfiguration;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@CommonsLog
public class CitiesConfigurationReader {

    private static final String NON_UNIQUE_NAMES_MESSAGE = "Cities have to be unique in configuration";

    private static final String DELIMITER = ",";

    @Value("${cities}")
    private String cities;

    private Optional<List<CityConfiguration>> cityConfigurations = Optional.empty();

    /**
     * Fetches cities configuration from application configuration file.
     *
     * @return List of CityConfiguration, which contains cities definition and limits of temperatures.
     */
    public List<CityConfiguration> getCitiesConfigurations() {
        if (!cityConfigurations.isPresent()) {
            List<CityConfiguration> configurations = convertConfigurationsFromJson();
            validate(configurations);
            cityConfigurations = Optional.of(configurations);
        }
        return cityConfigurations.get();
    }

    private void validate(List<CityConfiguration> configurations) {
        if (CollectionUtils.isEmpty(configurations)) {
            throw new CitiesConfigurationParseException("Undefined cities configuration.");
        }
        Optional<String> violations = fetchViolations(configurations);
        if (violations.isPresent()) {
            throw new CitiesConfigurationParseException("Cities configuration has problems:" + violations.get());
        }
    }

    private List<CityConfiguration> convertConfigurationsFromJson() {
        try {
            log.debug("Convert cities configuration:" + cities);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.reader()
                    .forType(new TypeReference<List<CityConfiguration>>() {})
                    .readValue(cities);
        } catch (Exception e) {
            throw new CitiesConfigurationParseException("Cannot parse cities configuration", e);
        }
    }

    private Optional<String> fetchViolations(List<CityConfiguration> configurations) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        List<String> violationMassages = configurations.stream()
                .map(configuration -> validator.validate(configuration))
                .filter(set -> !set.isEmpty())
                .flatMap(set -> set.stream().map(ConstraintViolation::getMessage))
                .collect(Collectors.toList());
        // Add name duplications verification, it is also a violation for configuration.
        hasNameDuplications(configurations).ifPresent(value -> violationMassages.add(value));
        return violationMassages.isEmpty() ? Optional.empty() : Optional.of(String.join(DELIMITER, violationMassages));
    }

    private Optional<String> hasNameDuplications(List<CityConfiguration> configurations) {
        Set<String> names = new HashSet<>();
        boolean hasUniqueNames = configurations.stream()
                .map(c -> names.add(c.getName()))
                .filter(v -> !v)
                .findFirst()
                .orElse(Boolean.TRUE);
        return hasUniqueNames ? Optional.empty() : Optional.of(NON_UNIQUE_NAMES_MESSAGE);
    }

}
