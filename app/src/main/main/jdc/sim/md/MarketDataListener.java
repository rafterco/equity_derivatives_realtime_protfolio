package jdc.sim.md;

import jdc.sim.position.PortfolioResultSubscriber;

//import javax.annotation.Nonnull;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Responsible for dequeing market data and notifying the {@link PortfolioResultSubscriber}.
 *
 * @author Jorge De Castro
 */
public final class MarketDataListener implements Consumer<Stock>, Runnable {
    private final Queue<Stock> queue;
    private final PortfolioResultSubscriber portfolioResultSubscriber;
    private volatile boolean stopped;

    public MarketDataListener( final Queue<Stock> queue,  final PortfolioResultSubscriber portfolioResultSubscriber) {
        this.queue = queue;
        this.portfolioResultSubscriber = portfolioResultSubscriber;
    }

    @Override
    public void run() {
        while (!stopped) {
            Stock stock = queue.poll();
            if (stock != null) {
                accept(stock);
            }
        }
    }

    public void stop() {
        stopped = true;
    }

    @Override
    public void accept( final Stock stock) {
        portfolioResultSubscriber.accept(stock);
    }
}
