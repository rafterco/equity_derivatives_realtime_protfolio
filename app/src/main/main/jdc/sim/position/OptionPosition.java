package jdc.sim.position;

import jdc.sim.Decimal;
import jdc.sim.md.Stock;

//import javax.annotation.Nonnull;

/**
 * @author Jorge De Castro
 */
public abstract class OptionPosition implements ProductPosition {
    private final String symbol;
    private final double quantity;
    protected final String underlier;
    protected final double sigma;
    protected final double k;
    protected final double r;
    protected final double t;
    protected final BlackScholes bs;
    protected double price;

    public OptionPosition(
             final String symbol,
             final String underlier,
            final double sigma,
            final double k,
            final double r,
            final double t,
            final double quantity) {

        this.symbol = symbol;
        this.underlier = underlier;
        this.k = k;
        this.sigma = sigma;
        this.r = r;
        this.t = t;
        this.quantity = quantity;
        this.bs = new BlackScholes();
    }

    @Override
    public abstract void onMarketData( final Stock stock);

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
        return Decimal.toTwoDecimalPlaces(quantity);
    }
}
