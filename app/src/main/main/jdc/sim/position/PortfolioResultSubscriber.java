package jdc.sim.position;

import jdc.sim.Decimal;
import jdc.sim.md.Stock;

//mport javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Jorge De Castro
 */
public final class PortfolioResultSubscriber implements Consumer<Stock> {
    private long counter;
    private final List<ProductPosition> positions;

    public PortfolioResultSubscriber( final List<ProductPosition> positions) {
        this.positions = positions;
        this.counter = 1L;
    }

    @Override
    public void accept( final Stock stock) {
        System.out.printf("%n## %s Market Data Update%n", counter);
        System.out.printf("%s change to %.2f%n", stock.symbol, Decimal.toTwoDecimalPlaces(stock.price));
        System.out.println(System.lineSeparator());
        System.out.println("## Portfolio");

        System.out.printf("%-30s %30s %30s %30s %n", "symbol", "price", "qty", "value");
        positions.forEach(position -> {
            position.onMarketData(stock);

            System.out.printf("%-30s %30.2f %30.2f %30.2f%n",
                    position.symbol(),
                    Decimal.toTwoDecimalPlaces(position.price()),
                    position.qty(),
                    Decimal.toTwoDecimalPlaces(position.marketValue())
            );
        });
        System.out.println(System.lineSeparator());
        System.out.printf("#Total portfolio %105.2f%n", Decimal.toTwoDecimalPlaces(positions.stream().mapToDouble(ProductPosition::marketValue).sum()));

        counter++;
    }
}
