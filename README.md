# Historical Trends
The project finds points of interest in the stock data and see if it correlates with the news. 


# Running
## Cassandra
1. Create a directory on the host where Cassandra container will store its data, e.g. `/data/cassandra`
1. Run Cassandra container with: 
```bash
docker run -p 9042:9042 --rm --name cassandra -v PATH_TO_CASSANDRA_DIR_CREATED:/var/lib/cassandra -d cassandra:3.11
```

You can check if Cassandra is running by executing Cassandra shell in the container:
```bash
docker exec -it cassandra cqlsh
``` 

## CsvCassandraImporter
Make sure Cassandra is running! 

Before you run the program, modify 12th line:
```scala
val projectPath = "C:/Projects/historical_trend"
```
And put there a path from your own machine. It's used to create proper paths to csv files.

During first execution `stock` keyspace should be created and csv files should be loaded into Cassandra in the form of 4 tables: `dow30`,
`historical_prices`, `nasdaq` and `sp500`.

To run the either use Intellj `Run`.

## NewYorkTimesImporter
Make sure Cassandra is running!

This job will import data about articles from NYT API. You can specify number
of months back from current day to download data for. 
```scala
val MONTHS_BACK_NUM = 12
```

During this job, Cassandra may be throwing exceptions from time to time, I don't know why.
After some time I managed to download over 45k of articles from last year.

# Running frontend
In order to run frontend, make sure that mongodb is installed and it is running in the background.
In the folder ```Frontend```, there is ```requirements.txt``` which defines all the requirements needed for frontend application to run.

Once all the prerequisites are installed. Follow the instructions to run the flask application.

Go to the terminal and go to the frontend folder. Run the following commands :

```python3 jsonTomongo.py```

```python3 app.py```

This will run the server. Follow the link the terminal which will redirect you to the web page.

