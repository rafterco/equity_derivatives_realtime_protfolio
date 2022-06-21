package jdc.sim.position;

//import com.google.common.base.Strings;
import jdc.sim.md.Stock;

//import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents the baseline product position contract.
 *
 * @author Jorge De Castro
 */
public interface ProductPosition {

    void onMarketData(Stock stock);

    String symbol();

    double price();

    double qty();

    default double marketValue() {
        return price() * qty();
    }

    static List<ProductPosition> from( final String path,
                                       final Map<String, OptionParams> optionParamsBySymbol) {
        List<ProductPosition> positions = new ArrayList<>();

        // In the real world this would be a testable method with unit tests asserting comments are properly ignored etc
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream
                    //.filter(l -> !Strings.isNullOrEmpty(l))
                    .filter(l -> !l.startsWith("//"))
                    .map(String::toUpperCase)
                    .forEach(line -> {
                        String[] tokens = line.split(",");
                        if (tokens.length < 2) {
                            throw new IllegalArgumentException("Invalid position tokens in CSV line: '" + line + "'");
                        }

                        String productToken = tokens[0].trim();
                        String positionToken = tokens[1].trim();

                        if (productToken.contains("-")) {
                            if (!optionParamsBySymbol.containsKey(productToken)) {
                                throw new IllegalArgumentException("Missing option parameters (security definitions) for '" + productToken + "'");
                            }

                            String[] items = productToken.split("-");
                            if (items.length < 5) {
                                throw new IllegalArgumentException("Invalid option symbol from CSV line: '" + line + "'");
                            }

                            if ("C".equalsIgnoreCase(items[4])) {
                                positions.add(new CallPosition(optionParamsBySymbol.get(productToken), Double.parseDouble(positionToken)));
                            }
                            if ("P".equalsIgnoreCase(items[4])) {
                                positions.add(new PutPosition(optionParamsBySymbol.get(productToken), Double.parseDouble(positionToken)));
                            }
                        } else {
                            positions.add(new StockPosition(productToken, Double.parseDouble(positionToken)));
                        }
                    });
        } catch (IOException ioe) {
            throw new IllegalStateException("Unable to read file '" + path + "'", ioe);
        }

        return positions;
    }
}
