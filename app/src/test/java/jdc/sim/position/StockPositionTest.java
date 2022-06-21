package jdc.sim.position;

import jdc.sim.md.Stock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Calibrated by the examples given in the exercise.
 *
 * @author Jorge De Castro
 */
public class StockPositionTest {
    private StockPosition underTest;

    @Before
    public void setUp() {
        underTest = new StockPosition("AAPL", 1000);
    }

    @Test
    public void aaplAt100() {
        underTest.onMarketData(new Stock("AAPL", 100.00));

        assertEquals(0, Double.compare(100_000.00, underTest.marketValue()));
    }

    @Test
    public void aaplAt110() {
        underTest.onMarketData(new Stock("AAPL", 110.00));

        assertEquals(0, Double.compare(110_000.00, underTest.marketValue()));
    }

    @Test
    public void aaplAt109() {
        underTest.onMarketData(new Stock("AAPL", 109.00));

        assertEquals(0, Double.compare(109_000.00, underTest.marketValue()));
    }
}