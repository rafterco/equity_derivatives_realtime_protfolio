package jdc.sim.position;

import jdc.sim.Decimal;
import jdc.sim.md.Stock;

//import javax.annotation.Nonnull;

/**
 * @author Jorge De Castro
 */
public final class StockPosition implements ProductPosition {
    private final String symbol;
    private double price;
    private final double quantity;

    public StockPosition( final String symbol, final double quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    @Override
    public void onMarketData( final Stock stock) {
        if (symbol.equals(stock.symbol)) {
            this.price = stock.price;
        }
    }

    @Override
    public String symbol() {
        return symbol;
    }

    @Override
    public double price() {
        return Decimal.toTwoDecimalPlaces(price);
    }

    @Override
    public double qty() {
        return quantity;
    }
}
