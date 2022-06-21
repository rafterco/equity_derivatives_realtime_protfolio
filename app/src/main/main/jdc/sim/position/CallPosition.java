package jdc.sim.position;

import jdc.sim.md.Stock;


/**
 * @author Jorge De Castro
 */
public final class CallPosition extends jdc.sim.position.OptionPosition {

    public CallPosition( final OptionParams optionParams, final double quantity) {

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
            price = bs.call(
                    stock.price,
                    k,
                    r,
                    t,
                    sigma
            );
        }
    }
}
