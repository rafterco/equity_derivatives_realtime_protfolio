package jdc.sim.position;

import jdc.sim.md.Stock;

//import javax.annotation.Nonnull;

/**
 * @author Jorge De Castro
 */
public final class PutPosition extends OptionPosition {

    public PutPosition( final OptionParams optionParams, final double quantity) {

        super(
                optionParams.symbol,
                optionParams.stockParams.symbol,
                optionParams.stockParams.sigma,
                optionParams.strike,
                optionParams.riskFree,
                optionParams.maturity,
                quantity
        );
    }

    @Override
    public void onMarketData( final Stock stock) {
        if (underlier.equals(stock.symbol)) {
            price = bs.put(
                    stock.price,
                    k,
                    r,
                    t,
                    sigma
            );
        }
    }
}
