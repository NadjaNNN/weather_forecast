package io.github.nadjannn.weather.data.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "city")
    private List<Forecast> forecasts = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public City(String name, List<Forecast> forecasts) {
        this(name);
        this.forecasts = forecasts;
    }
}
