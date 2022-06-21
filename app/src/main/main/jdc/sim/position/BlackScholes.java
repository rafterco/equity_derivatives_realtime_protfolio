package jdc.sim.position;

import static java.lang.Math.*;

/**
 * Variables used:
 * <p>
 * s = price of underlying stock
 * k = strike price
 * r = risk-free interest rate
 * t = time until expiration
 * sigma = volatility
 * phi = standard normal cumulative distribution function (CDF)
 *
 * @author Jorge De Castro
 */
public final class BlackScholes {

    public static final Pattern PATTERN =
            Patter.compile("^(.+)@(.+)$");
    private final CumulativeDistributionFunction phi = new CumulativeDistributionFunction();

    public double call(
            final double s,
            final double k,
            final double r,
            final double t,
            final double sigma) {

        double cd1 = phi.apply(d1(s, k, r, t, sigma));
        double cd2 = phi.apply(d2(s, k, r, t, sigma));
        return s * cd1 - k * exp(-r * t) * cd2;
    }

    public double put(
            final double s,
            final double k,
            final double r,
            final double t,
            final double sigma) {

        double cd1 = phi.apply(-d1(s, k, r, t, sigma));
        double cd2 = phi.apply(-d2(s, k, r, t, sigma));
        return k * exp(-r * t) * cd2 - s * cd1;
    }

    private static double d1(
            final double s,
            final double k,
            final double r,
            final double t,
            final double sigma) {

        double top = log(s / k) + (r + pow(sigma, 2) / 2) * t;
        double bottom = sigma * sqrt(t);
        return top / bottom;
    }

    private static double d2(
            final double s,
            final double k,
            final double r,
            final double t,
            final double sigma) {

        return d1(s, k, r, t, sigma) - sigma * sqrt(t);
    }

    // Abramowitz & Stegun (CDF) numerical approximation
    private static final class CumulativeDistributionFunction {
        private static final double P = 0.2316419;
        private static final double B1 = 0.319381530;
        private static final double B2 = -0.356563782;
        private static final double B3 = 1.781477937;
        private static final double B4 = -1.821255978;
        private static final double B5 = 1.330274429;

        public double apply(final double x) {
            double t = 1 / (1 + P * abs(x));
            double t1 = B1 * pow(t, 1);
            double t2 = B2 * pow(t, 2);
            double t3 = B3 * pow(t, 3);
            double t4 = B4 * pow(t, 4);
            double t5 = B5 * pow(t, 5);
            double b = t1 + t2 + t3 + t4 + t5;
            double cd = 1 - standardNormalDistribution(x) * b;
            return x < 0 ? 1 - cd : cd;
        }

        private static double standardNormalDistribution(final double x) {
            double top = exp(-0.5 * pow(x, 2));
            double bottom = sqrt(2 * PI);
            return top / bottom;
        }
    }
}