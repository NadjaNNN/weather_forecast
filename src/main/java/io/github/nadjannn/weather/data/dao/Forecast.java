package io.github.nadjannn.weather.data.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "city_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private City city;

    @Column(name = "exceeded_lower_limit")
    private boolean exceededLowerLimit;

    @Column(name = "exceeded_upper_limit")
    private boolean exceededUpperLimit;

    @Embedded
    private ForecastValues forecastValues;

    public Forecast(ForecastValues forecastValues, boolean exceededLowerLimit, boolean exceededUpperLimit) {
        this.forecastValues = forecastValues;
        this.exceededLowerLimit = exceededLowerLimit;
        this.exceededUpperLimit = exceededUpperLimit;
    }
}
