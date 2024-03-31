package mttsner.fujitsu.model;

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
