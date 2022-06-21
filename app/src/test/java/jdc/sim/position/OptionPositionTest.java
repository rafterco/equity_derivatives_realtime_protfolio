package jdc.sim.position;

import jdc.sim.md.Stock;
import jdc.sim.md.StockParams;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Calibrated by the examples given in the exercise.
 *
 * @author Jorge De Castro
 */
public class OptionPositionTest {
    private OptionParams params;
    private OptionPosition underTest;
    private StockParams stockParams;

    @Test
    public void aaplAt110Call() {
        stockParams = new StockParams("AAPL", 110.00, 0.34, 0.241);
        params = new OptionParams("AAPL-OCT-2020-110-C", stockParams, 110.0, 0.25);

        underTest = new CallPosition(params, -20_000);

        underTest.onMarketData(new Stock("AAPL", 110.00));

        assertEquals(0, Double.compare(5.55, underTest.price()));
        assertEquals(0, Double.compare(-111_000.00, underTest.marketValue()));
    }

    @Test
    public void aaplAt110Put() {
        stockParams = new StockParams("AAPL", 110.00, 0.34, 0.0343);
        params = new OptionParams("AAPL-OCT-2020-110-P", stockParams, 110.0, 0.34);

        underTest = new PutPosition(params, 20_000);

        underTest.onMarketData(new Stock("AAPL", 110.00));

        assertEquals(0, Double.compare(0.55, underTest.price()));
        assertEquals(0, Double.compare(11_000.00, underTest.marketValue()));
    }
}