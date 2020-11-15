package io.github.nadjannn.weather.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityConfiguration {

    @NotEmpty(message = "Field name must not be empty")
    private String name;

    @NotNull(message = "Field lowerLimit must not be null")
    private Double lowerLimit;

    @NotNull(message = "Field upperLimit must not be null")
    private Double upperLimit;

}
