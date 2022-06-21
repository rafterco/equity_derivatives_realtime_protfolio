### Description

Publisher/subscriber system that implements requirements 1-6** to show the real-time value of portfolio products (Stocks, Call and Put options) customizable via files under `app/src/main/resources/`.
By default, the following files are used:

 * stock_params.txt - CSV of stocks "security definitions"
 * option_params.txt - CSV of options "security definitions"
 * positions.txt - CSV of positions file, consisting of tickers and number of shares/contracts


### Usage
```shell script
./gradlew build test
./gradlew run --args="<path to stock security definitions file> <path to option security definitions file> <path to positions file>", e.g.:

./gradlew run --args="src/main/resources/stock_params.txt src/main/resources/option_params.txt src/main/resources/positions.txt"
```

### Design and implementation considerations

Testable, tested, composable component that provides a simple, elegant solution to the interview exercise -typically open ended and unrealistic.

The market data publisher MD uses a discrete time geometric Brownian motion -1st time implemented from scratch in a decade+ career as a low latency developer, as MD off the critical path would
typically be consumed from alternative/deferred data sources (persisted memory maps, replay feeds, etc), or "hardware carbon copies" (Endaces, Corvills, ...)

Implemented the Black-Scholes formula for options pricing from scratch.

Unsure why libraries such as Protobuf (!) are suggested as options -the use cases of Protobufs make it a poor choice for the problem given.

We are told to "assume that the market data publisher described run as a separate thread in the same program", suggesting producer and consumer threads on a single process.

The messaging implementation could be trivially changed to a Chronicle queue -indeed, the API could be generified to support Disruptor, Aeron, or any other messaging library.

The implementation can also be trivially split into separate producer and consumer processes.


### Appendix **

Step 2), get the security definitions from an embedded database, was replaced with flat files because:

1. elsewhere, the exercise suggests better and/or clearer solution(s) could be used
2. RDMS are suboptimal for storage of security definitions -secdefs are typically small volume of non-relational data (and suitable for versioning too)
3. assuming we are asked for an embedded DB just to ascertain some degree of SQL knowledge, I leave potential schemas below:

```
CREATE TABLE ProductType (
     id INTEGER PRIMARY KEY,
     name TEXT NOT NULL
);

CREATE TABLE Product (
    id INTEGER PRIMARY KEY,
    symbol TEXT NOT NULL,
    product_type INTEGER REFERENCES ProductType
);

Create Table StockParams {
    id INTEGER PRIMARY KEY,
    mu REAL,
    std REAL,
    product INTEGER REFERENCES Product
};

Create Table OptionParams {
    id INTEGER PRIMARY KEY,
    rate REAL,
    maturity REAL,
    underlier_params INTEGER REFERENCES StockParams
    product INTEGER REFERENCES Product
};
```

This, of course, could be normalized further with tables joining stocks and stock params keys, and options and options params keys -the data however seems adequately represented by column-based storage, simpler structured formats such as JSON or YAML, or flat files.