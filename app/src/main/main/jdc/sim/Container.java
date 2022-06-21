package jdc.sim;

import jdc.sim.md.*;
import jdc.sim.position.OptionParams;
import jdc.sim.position.PortfolioResultSubscriber;
import jdc.sim.position.ProductPosition;

//import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Bootstrapper responsible for assembling the (publisher, listener) parts and managing their lifecycle.
 *
 * @author Jorge De Castro
 */
public final class Container {
    private final ExecutorService producer;
    private final ExecutorService consumer;
    private final MarketDataPublisher publisher;
    private final MarketDataListener listener;

    public Container( final MarketDataPublisher publisher,
                      final MarketDataListener listener) {

        this.producer = Executors.newSingleThreadExecutor();
        this.consumer = Executors.newSingleThreadExecutor();
        this.publisher = publisher;
        this.listener = listener;
    }

    public void start() {
        Thread onExit = new Thread(this::stop);
        Runtime.getRuntime().addShutdownHook(onExit);

        consumer.execute(listener);
        producer.execute(publisher);

      /*  producer.shutdown();
        consumer.shutdown();*/
    }

    public void stop() {
        publisher.stop();
        listener.stop();
        producer.shutdownNow();
        consumer.shutdownNow();

        System.out.println("Market data publisher and position subscriber container stopped.");
    }

    public static void main( final String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Usage: <path to stock params file> <path to option params file> <path to positions file>");
        }

        Queue<Stock> marketData = new ConcurrentLinkedDeque<>();
        Random random = new Random();
        Supplier<Double> gaussianSuppplier = random::nextGaussian;

        Map<String, StockParams> stockParamsBySymbol = StockParams.from(args[0]);

        MarketDataPublisher publisher = new MarketDataPublisher(
                marketData,
                stockParamsBySymbol,
                new GeometricBrownianMotion(gaussianSuppplier)
        );

        Map<String, OptionParams> optionParamsBySymbol = OptionParams.from(args[1], stockParamsBySymbol);

        List<ProductPosition> positions = ProductPosition.from(args[2], optionParamsBySymbol);

        PortfolioResultSubscriber subscriber = new PortfolioResultSubscriber(positions);

        MarketDataListener marketDataListener = new MarketDataListener(marketData, subscriber);

        Container container = new Container(publisher, marketDataListener);

        container.start();
    }
}
