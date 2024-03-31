package mttsner.fujitsu.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum City {
    Tallinn("Tallinn-Harku"),
    Tartu("Tartu-Tõravere"),
    Parnu("Pärnu");

    @Getter
    private final String stationName;

    City(String stationName) {
        this.stationName = stationName;
    }
}
