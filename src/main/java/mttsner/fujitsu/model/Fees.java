package mttsner.fujitsu.model;

import java.math.BigDecimal;
import java.util.Map;

public class Fees {
    public Map<City, Map<Vehicle, BigDecimal>> RBF;

    public Map<Temperature, BigDecimal> ATEF;

    public Map<WindSpeed, BigDecimal> WSEF;

    public Map<Phenomenon, BigDecimal> WPEF;
}

