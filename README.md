# currency-exchange project
Searchmetrics challenge


## To run project type:
mvn spring-boot:run
or open the project with any IDE and just Run As Java Appliction class CurrencyExchangeApplication.java


## Project specs
Java8
Springboot
H2 Database embebed
YahooFinance library to get currency exchange information, if the service does not response I use a mocked result.


## Notes
Regarding The check period has to be configurable
In application.properties I set fixed.delay, a better way could be a jmx parameter
or any attribute in memory or database that could be updated through a REST controller,
thus we don't need to restart the application in order to changes take effect.


## Examples endpoints

### 1) Get latest rate
http://localhost:8080/currency-exchange/v1/latestrate

### 2) Get historical rates from startDate to endDate
http://localhost:8080/currency-exchange/v1/historicalrates?startdate=2017-07-25&enddate=2017-09-01

### 3) Get all (just for test)
http://localhost:8080/currency-exchange/v1/all


## API documetation (with Swagger)
You can check API Documentation at this URL:
http://localhost:8080/swagger-ui.html

## Database console
I'm using H2 Database embebed
You can explore and perform queries at this URL:
http://localhost:8080/console