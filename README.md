# historical_trend
The project finds points of interest in the stock data and see if it correlates with the news. 


# Running
## Cassandra
1. Create a directory on the host where Cassandra container will store its data, e.g. `/data/cassandra`
1. Run Cassandra container with: 
```bash
docker run -p 9042:9042 --rm --name cassandra -d cassandra:3.11
```

You can check if Cassandra is running by executing Cassandra shell in the container:
```bash
docker exec -it cassandra cqlsh
``` 

## HistoricalTrend
Make sure Cassandra is running! 

Before you run the program, modify 12th line:
```scala
val projectPath = "C:/Projects/historical_trend"
```
And put there a path from your own machine. It's used to create proper paths to csv files.

During first execution `stock` keyspace should be created and csv files should be loaded into Cassandra in the form of 4 tables: `dow30`,
`historical_prices`, `nasdaq` and `sp500`.

To run the either use Intellj `Run`.