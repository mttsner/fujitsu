package mttsner.fujitsu.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mttsner.fujitsu.model.Fees;
import mttsner.fujitsu.model.Station;
import mttsner.fujitsu.repository.StationRepository;
import mttsner.fujitsu.model.City;
import mttsner.fujitsu.service.DeliveryFeeException;
import mttsner.fujitsu.service.DeliveryFeeService;
import mttsner.fujitsu.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;


@RestController
public class DeliveryFeeController {
    @Autowired
    private StationRepository stationRepository;

    private static final Fees fees;
    // Get fee data from json file
    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("./src/main/resources/fees.json");
            fees = objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/deliveryfee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful response, returns the fee in euros",
                    content = @Content(examples = @ExampleObject(value = "3.5"))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request, returns an error message",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Generic internal server error",
                    content = @Content)
    })
    public String index(@RequestParam City city, @RequestParam Vehicle vehicle) {
        // Retrieve the weather station data for the provided city
        String stationName = city.getStationName();
        Station station = stationRepository.findStationByName(stationName);
        // Instantiate the delivery fee service
        DeliveryFeeService deliveryFeeService = new DeliveryFeeService(station, city, vehicle, fees);
        // Attempt to calculate the delivery fee
        BigDecimal deliveryFee;
        try {
            deliveryFee = deliveryFeeService.calculateDeliveryFee();
        } catch (DeliveryFeeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        // Return BigDecimal number as a string
        return deliveryFee.toPlainString();
    }
}
