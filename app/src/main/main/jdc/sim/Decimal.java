package jdc.sim;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Jorge De Castro
 */
public final class Decimal {
    public static double toTwoDecimalPlaces(final double price) {
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.DOWN).doubleValue();
    }
}
