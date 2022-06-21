package jdc.sim.md;

//import com.google.common.base.Strings;
import jdc.sim.Decimal;

//import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Jorge De Castro
 */
public final class StockParams {
    public final String symbol;
    public final double price;
    public final double mu;
    public final double sigma;

    public StockParams( final String symbol, final double price, final double mu, final double sigma) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be < 0");
        }
        if (mu < 0 || mu > 1) {
            throw new IllegalArgumentException("Expected return (mu) must be between 0..1");
        }
        if (sigma < 0 || sigma > 1) {
            throw new IllegalArgumentException("Annualized standard deviation (sigma) must be between 0..1");
        }
        this.symbol = symbol;
        this.price = Decimal.toTwoDecimalPlaces(price);
        this.mu = mu;
        this.sigma = sigma;
    }

    public static Map<String, StockParams> from( final String path) {
        Map<String, StockParams> paramsBySymbol = new LinkedHashMap<>();

        // In the real world this would be a testable method with unit tests asserting comments are properly ignored etc
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream
                    //.filter(l -> !Strings.isNullOrEmpty(l))
                    .filter(l -> !l.startsWith("//"))
                    .map(String::toUpperCase)
                    .forEach(line -> {
                        String[] tokens = line.split(",");
                        if (tokens.length < 4) {
                            throw new IllegalArgumentException("Invalid stock parameters tokens in CSV line: '" + line + "'");
                        }

                        String symbol = tokens[0].trim();
                        // Skipping some validation for conciseness
                        paramsBySymbol.put(
                                symbol,
                                new StockParams(
                                        tokens[0].trim(),
                                        Double.parseDouble(tokens[1]),
                                        Double.parseDouble(tokens[2]),
                                        Double.parseDouble(tokens[3])
                                )
                        );
                    });
        } catch (IOException ioe) {
            throw new IllegalStateException("Unable to read file '" + path + "'", ioe);
        }

        return paramsBySymbol;
    }

    @Override
    public String toString() {
        return "StockParams{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", mu=" + mu +
                ", sigma=" + sigma +
                '}';
    }
}
