package jdc.sim.md;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

/**
 * @author Jorge De Castro
 */
public class MarketDataPublisherTest {
    private Queue<Stock> queue;
    private Map<String, StockParams> paramsBySymbol;
    private MarketDataPublisher underTest;

    @Before
    public void setUp() {
        queue = new LinkedList<>();
        StockParams aaplParams = new StockParams("AAPL", 100.00, 0.4, 0.5);
        StockParams telsaParams = new StockParams("TELSA", 400.00, 0.7, 0.8);

        paramsBySymbol = new HashMap<>();
        paramsBySymbol.put("AAPL", aaplParams);
        paramsBySymbol.put("TELSA", telsaParams);

        underTest = new MarketDataPublisher(
                queue,
                paramsBySymbol,
                new GeometricBrownianMotion(() -> 1.8909297211618126)
        );
    }

    @Test
    public void emptyQueueIfNothingPublished() {
        assertEquals(0, queue.size());
    }

    @Test
    public void publishedSymbolsAreEnqueued() {
        underTest.publish(1);

        assertEquals(2, queue.size());
    }

    @Test
    public void publishedSymbolsEnqueueCorrespondingStock() {
        underTest.publish(1);

        Stock s1 = queue.poll();
        Stock s2 = queue.poll();

        assertEquals(new Stock("AAPL", 100.03510080491154), s1);
        assertEquals(new Stock("TELSA", 400.2246484583122), s2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenStockPriceCannotBeLessThanZero() {
        StockParams aaplParams = new StockParams("AAPL", 100.00, 0.4, 0.5);
        StockParams telsaParams = new StockParams("TELSA", -4.00, 0.7, 0.8);

        paramsBySymbol = new HashMap<>();
        paramsBySymbol.put("AAPL", aaplParams);
        paramsBySymbol.put("TELSA", telsaParams);

        underTest = new MarketDataPublisher(
                queue,
                paramsBySymbol,
                new GeometricBrownianMotion(() -> 1.8909297211618126)
        );
    }

    @Test
    public void enqueuedStockPriceIsNotLessThanZero() {
        StockParams aaplParams = new StockParams("AAPL", 0.00, 0.4, 0.5);

        paramsBySymbol = new HashMap<>();
        paramsBySymbol.put("AAPL", aaplParams);

        underTest = new MarketDataPublisher(
                queue,
                paramsBySymbol,
                new GeometricBrownianMotion(() -> -1.8909297211618126)
        );

        underTest.publish(1);

        assertEquals(1, queue.size());
        Stock actual = queue.poll();

        assertEquals(new Stock("AAPL", 0), actual);
    }
}