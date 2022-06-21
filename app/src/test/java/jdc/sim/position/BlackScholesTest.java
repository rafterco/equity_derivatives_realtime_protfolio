package jdc.sim.position;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Calibrated against Wolfram Alpha.
 *
 * @author Jorge De Castro
 * @see <a href="https://www.wolframalpha.com/input/?i=black+scholes">https://www.wolframalpha.com/input/?i=black+scholes</a>
 */
public class BlackScholesTest {
    private static final double EPSILON = 0.01d;
    private BlackScholes underTest;

    @Before
    public void setUp() {
        underTest = new BlackScholes();
    }

    @Test
    public void call() {
        assertEquals(6.85, underTest.call(
                        150.0,
                        149.0,
                        0.02,
                        0.25,
                        0.20
                ), EPSILON
        );
    }

    @Test
    public void put() {
        assertEquals(6.07, underTest.put(
                        149.0,
                        150.0,
                        0.02,
                        0.25,
                        0.20
                ), EPSILON
        );
    }
}