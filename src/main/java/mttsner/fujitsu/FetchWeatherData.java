package mttsner.fujitsu;

import jakarta.annotation.PostConstruct;
import mttsner.fujitsu.model.Station;
import mttsner.fujitsu.repository.StationRepository;
import mttsner.fujitsu.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchWeatherData {
    @Autowired
    private StationRepository stationRepository;

    public void fetchWeatherData() throws Exception {
        WeatherDataService weatherData = new WeatherDataService();
        // Fetch weather data from weather API
        List<Station> stations = weatherData.fetchWeatherData();
        // Save weather data to repository
        stationRepository.saveAll(stations);
    }

    // Fetch fresh weather data every hour
    @Scheduled(cron = "0 15 * * * *")
    public void cron() throws Exception {
        fetchWeatherData();
    }
    // Fetch weather data on application startup
    @PostConstruct
    public void init() throws Exception {
        fetchWeatherData();
    }
}