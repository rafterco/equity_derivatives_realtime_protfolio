package jdc.sim.md;

import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

/**
 * Responsible for enqueing market data generated via {@link GeometricBrownianMotion}.
 *
 */
public final class MarketDataPublisher implements Runnable {
    private final Queue<Stock> store;
    private final Map<String, Double> priceBySymbol;
    private final Map<String, StockParams> paramsBySymbol;
    private final GeometricBrownianMotion gbm;
    private final Random random;
    private volatile boolean stopped;

    public MarketDataPublisher( final Queue<Stock> store,
                                final Map<String, StockParams> paramsBySymbol,
                                final GeometricBrownianMotion gbm) {

        if (paramsBySymbol.entrySet().stream().anyMatch(entry -> entry.getValue().price < 0)) {
            throw new IllegalArgumentException("Stock price cannot be < 0");
        }

        this.store = store;
        this.paramsBySymbol = paramsBySymbol;
        this.priceBySymbol = paramsBySymbol.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().price));
        this.gbm = gbm;
        this.random = new Random();
    }

    void publish(final double t) {
        paramsBySymbol
                .entrySet()
                .stream()
                .filter(e -> priceBySymbol.containsKey(e.getKey()))
                .forEach(entry -> {

                    String symbol = entry.getKey();
                    StockParams params = entry.getValue();

                    double lastPrice = priceBySymbol.get(symbol);
                    double newPrice = gbm.calculate(
                            lastPrice,
                            params.mu,
                            params.sigma,
                            t
                    );

                    Stock stock = new Stock(symbol, newPrice);
                    priceBySymbol.put(symbol, newPrice);

                    store.add(stock);
                });
    }

    @Override
    public void run() {
        while (!stopped) {
            double t = random.nextDouble() * (2 - 0.5) + 0.5;

            publish(t);

            long millis = Math.round(t * 1_000);
            try {
                sleep(millis);
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void stop() {
        stopped = true;
    }
}
