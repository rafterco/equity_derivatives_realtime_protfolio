package jdc.sim.md;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jorge De Castro
 */
public class GeometricBrownianMotionTest {
    private static final double PREVIOUS_PRICE = 110.00;

    private GeometricBrownianMotion underTest;

    @Test
    public void newPriceMovesUp() {
        underTest = new GeometricBrownianMotion(() -> 1.3172379672869043);

        double newPrice = underTest.calculate(
                PREVIOUS_PRICE,
                0.7,
                0.30,
                2
        );

        assertEquals(0, Double.compare(110.02284022203358, newPrice));
    }

    @Test
    public void newPriceMovesDown() {
        underTest = new GeometricBrownianMotion(() -> -0.45634720383297755);

        double newPrice = underTest.calculate(
                PREVIOUS_PRICE,
                0.7,
                0.30,
                2
        );

        assertEquals(0, Double.compare(109.99211574690887, newPrice));
    }
}