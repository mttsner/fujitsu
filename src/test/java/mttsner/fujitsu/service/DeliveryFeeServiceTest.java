package mttsner.fujitsu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mttsner.fujitsu.model.Fees;
import mttsner.fujitsu.model.Station;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static mttsner.fujitsu.model.City.Tartu;
import static mttsner.fujitsu.model.Vehicle.Bike;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryFeeServiceTest {
    // Empty mock station
    private static final Station station = new Station();
    // Fee values used in calculations
    private static final Fees fees;
    // Get fee data from json file
    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("./src/main/resources/fees.json");
            fees = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCalculateDeliveryFreeWithTaskExample() {
        // Mock station data
        Station station = new Station();
        station.setAirtemperature(-2.1f);
        station.setWindspeed(4.7f);
        station.setPhenomenon("Light snow shower");
        // Instantiate delivery fee service with mock station, city and vehicle
        DeliveryFeeService deliveryFeeService = new DeliveryFeeService(station, Tartu, Bike, fees);
        // Input parameters: TARTU and BIKE -> RBF = 2,5 €
        assertEquals(new BigDecimal("2.5"), deliveryFeeService.calculateRBF());
        // Air temperature = -2,1̊ C -> ATEF = 0,5 €
        assertEquals(new BigDecimal("0.5"), deliveryFeeService.calculateATEF());
        // Wind speed = 4,7 m/s -> WSEF = 0 €
        assertEquals(new BigDecimal("0"), deliveryFeeService.calculateWSEF());
        // Weather phenomenon = Light snow shower -> WPEF = 1 €
        assertEquals(new BigDecimal("1"), deliveryFeeService.calculateWPEF());
        // Total delivery fee = RBF + ATEF + WSEF + WPEF = 2,5 + 0,5 + 0 + 1 = 4 €
        assertEquals(new BigDecimal("4.0"), deliveryFeeService.calculateDeliveryFee());
    }
}