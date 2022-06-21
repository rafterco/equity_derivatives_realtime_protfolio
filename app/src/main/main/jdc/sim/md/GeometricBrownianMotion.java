package jdc.sim.md;

//import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static java.lang.Math.sqrt;

/**
 * Variables used:
 * <p>
 * s = price of stock
 * mu = expected return, between 0..1
 * sigma = annualized standard deviation, between 0..1
 * epsilon = random variable drawn from a standardized normal distribution
 * t = time in seconds
 *
 */
public final class GeometricBrownianMotion {
    private final Supplier<Double> gaussianSupplier;

    public GeometricBrownianMotion( final Supplier<Double> gaussianSupplier) {
        this.gaussianSupplier = gaussianSupplier;
    }

    public double calculate(final double s,
                            final double mu,
                            final double sigma,
                            final double t) {

        return calculate(s, mu, sigma, gaussianSupplier.get(), t);
    }

    private double calculate(final double s,
                             final double mu,
                             final double sigma,
                             final double epsilon,
                             final double t) {

        double deltat = t / 7257600;
        double ds = s * ((mu * deltat) + (sigma * epsilon * sqrt(deltat)));
        return s + ds;
    }
}
