package mttsner.fujitsu.service;

import mttsner.fujitsu.model.*;

import java.math.BigDecimal;

import static mttsner.fujitsu.model.Vehicle.*;

public class DeliveryFeeService {

    private final Station station;
    private final City city;
    private final Vehicle vehicle;

    private final Fees fees;

    public DeliveryFeeService(Station station, City city, Vehicle vehicle, Fees fees) {
        this.station = station;
        this.city = city;
        this.vehicle = vehicle;
        this.fees = fees;
    }

    public BigDecimal calculateRBF() {
        return fees.RBF.get(city).get(vehicle);
    }

    public BigDecimal calculateATEF() {
        // Extra fee based on air temperature (ATEF) is paid when vehicle = Scooter or Bike
        if (vehicle != Scooter && vehicle != Bike) {
            return new BigDecimal("0");
        }
        // Air temperature is less than -10 Celsius
        if (station.getAirtemperature() < -10) {
            return fees.ATEF.get(Temperature.Freezing);
        }
        // Air temperature is between -10 Celsius and 0 Celsius
        if (station.getAirtemperature() < 0) {
            return fees.ATEF.get(Temperature.Cold);
        }
        // No fee
        return new BigDecimal("0");
    }

    public BigDecimal calculateWSEF() throws DeliveryFeeException {
        // Extra fee based on wind speed (WSEF) in a specific city is paid in case Vehicle type = Bike
        if (vehicle != Bike) {
            return new BigDecimal("0");
        }
        // Wind speed is greater than 20 m/s
        if (station.getWindspeed() > 20) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
        }
        // Wind speed is between 10 m/s and 20 m/s
        if (station.getWindspeed() > 10) {
            return fees.WSEF.get(WindSpeed.High);
        }
        // No fee
        return new BigDecimal("0");
    }

    public BigDecimal calculateWPEF() throws DeliveryFeeException {
        //Extra fee based on weather phenomenon (WPEF) in a specific city is paid in case Vehicle
        //type = Scooter or Bike
        if (vehicle != Scooter && vehicle != Bike) {
            return new BigDecimal("0");
        }
        String phenomenon = station.getPhenomenon();
        // Weather phenomenon is related to snow or sleet
        if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
            return fees.WPEF.get(Phenomenon.Snowing);
        }
        // Weather phenomenon is related to rain
        if (phenomenon.contains("rain")) {
            return fees.WPEF.get(Phenomenon.Raining);
        }
        // In case the weather phenomenon is glaze, hail, or thunder, then the error message
        // “Usage of selected vehicle type is forbidden” has to be given
        if (phenomenon.equals("Glaze") || phenomenon.equals("Hail") || phenomenon.equals("Thunder")) {
            throw new DeliveryFeeException("Usage of selected vehicle type is forbidden");
        }
        // No fee
        return new BigDecimal("0");
    }

    public BigDecimal calculateDeliveryFee() throws DeliveryFeeException {
        return calculateRBF()
                .add(calculateATEF())
                .add(calculateWPEF())
                .add(calculateWSEF());
    }
}
