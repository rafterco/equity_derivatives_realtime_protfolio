package jdc.sim.md;

import jdc.sim.Decimal;

//import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author Jorge De Castro
 */
public final class Stock {
    public final String symbol;
    public final double price;

    public Stock( final String symbol, final double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be < 0");
        }
        this.symbol = symbol;
        this.price = Decimal.toTwoDecimalPlaces(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Double.compare(stock.price, price) == 0 && symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, price);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
