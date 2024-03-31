package mttsner.fujitsu.service;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import mttsner.fujitsu.model.Station;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Getter
class Observation {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    List<Station> station;

    private Instant timestamp;
}

public class WeatherDataService {
    // Names of required weather stations
    private static final List<String> stationNameFilter = Arrays.asList("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    // Fetch Weather Data from the ilmateenistus.ee observations API
    private static final String WeatherApiUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    // Fetch weather data from the weather api
    public List<Station> fetchWeatherData() throws Exception {
        try {
            // Fetch weather data from the api
            RestTemplate restTemplate = new RestTemplate();
            Observation observation = restTemplate.getForObject(WeatherApiUrl, Observation.class);
            // Check if API return any data
            if (observation == null) {
                throw new Exception("Failed to get weather data from Api!");
            }
            // Filter out needed observation station data
            return filterObservationData(observation);
        } catch (RestClientException ex) {
            throw new Exception("Failed to reach weather Api!");
        }
    }

    // Filter out the stations of interest
    private List<Station> filterObservationData(Observation observation) {
        return observation.station.stream()
                .filter(station -> stationNameFilter.contains(station.getName()))
                .peek(station -> station.setObservedAt(observation.getTimestamp()))
                .toList();
    }
}
