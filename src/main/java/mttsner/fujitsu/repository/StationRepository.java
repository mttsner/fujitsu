package mttsner.fujitsu.repository;

import mttsner.fujitsu.model.City;
import mttsner.fujitsu.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StationRepository extends  JpaRepository<Station, Integer>{
    @Query("SELECT station FROM Station station " +
            "WHERE station.name = :name " +
            "ORDER BY station.observedAt " +
            "LIMIT 1")
    Station findStationByName(String name);
}