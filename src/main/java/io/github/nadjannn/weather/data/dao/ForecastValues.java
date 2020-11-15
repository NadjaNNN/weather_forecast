package io.github.nadjannn.weather.data.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Data
public class ForecastValues {

    private Double temperature;

    @Column(name = "date_time")
    private String dateTime;

    public ForecastValues(Double temperature, String dateTime) {
        this.temperature = temperature;
        this.dateTime = dateTime;
    }
}
