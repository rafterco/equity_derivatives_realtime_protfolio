package jdc.sim.position;

//import com.google.common.base.Strings;
import jdc.sim.md.StockParams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Jorge De Castro
 */
public final class OptionParams {
    public final String symbol;
    public final StockParams stockParams;
    public final double strike;
    public final double riskFree;
    public final double maturity;

    public OptionParams( final String symbol,  final StockParams stockParams, final double strike, final double maturity) {
        this(symbol, stockParams, strike, 0.02, maturity);
    }

    public OptionParams( final String symbol,  final StockParams stockParams, final double strike, final double riskFree, final double maturity) {
        this.symbol = symbol;
        this.stockParams = stockParams;
        this.strike = strike;
        this.riskFree = riskFree;
        this.maturity = maturity;
    }

    public static Map<String, OptionParams> from( final String path,  final Map<String, StockParams> stockParamsBySymbol) {
        Map<String, OptionParams> paramsBySymbol = new LinkedHashMap<>();


        // In the real world this would be a testable method with unit tests asserting comments are properly ignored etc
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream
                    //.filter(l -> !Strings.isNullOrEmpty(l))
                    .filter(l -> !l.startsWith("//"))
                    .map(String::toUpperCase)
                    .forEach(line -> {
                        String[] tokens = line.split(",");
                        if (tokens.length < 5) {
                            throw new IllegalArgumentException("Invalid stock parameters tokens in CSV line: '" + line + "'");
                        }

                        String symbol = tokens[0].trim();
                        String underlier = tokens[1].trim();

                        if (stockParamsBySymbol.containsKey(underlier)) {
                            // Skipping further validation for conciseness
                            paramsBySymbol.put(
                                    symbol,
                                    new OptionParams(
                                            symbol,
                                            stockParamsBySymbol.get(underlier),
                                            Double.parseDouble(tokens[2]),
                                            Double.parseDouble(tokens[3]),
                                            Double.parseDouble(tokens[4])
                                    )
                            );
                        }
                    });
        } catch (IOException ioe) {
            throw new IllegalStateException("Unable to read file '" + path + "'", ioe);
        }

        return paramsBySymbol;
    }

    @Override
    public String toString() {
        return "OptionParams{" +
                "symbol='" + symbol + '\'' +
                ", stockParams=" + stockParams +
                ", strike=" + strike +
                ", riskFree=" + riskFree +
                ", maturity=" + maturity +
                '}';
    }
}
