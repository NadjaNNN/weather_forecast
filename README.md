# Weather forecast application.
Weather forecast application, which receives forecast for predefined city from third party service.

# How to start application
First of all make a build my maven.
 - Install Java 8 or higher.
 - Install maven version 3.6.3 or higher https://maven.apache.org/install.html
 - Call following command in the command line under the root folder:
 `mvn clean package`
 - Start service by following command under the root folder:
 `java -jar target/weatherforecast-1.0.jar`
 - Type CTRL + C to stop service when it is needed.

# How to test application
- Start application by guid above.
- Call API by the following command from command line:
`curl http://localhost:8080/forecasts/Espoo,fi`
Processing cities are specified in configuration file \src\main\resources\application.yml.
See setting `cities`. Cities configuration is in JSON format. Default configuration contains only two cities in Finland: Espoo and Turku.

End point returns exceeding forecasts only.
Forecast temperature will be skip if it does not exceed upper or lower limit, because user does not interested in such forecast.  
Response has JSON format.
```
{
   "id":1,
   "name":"Espoo,fi",
   "forecasts":[
      {
         "id":2,
         "exceededLowerLimit":false,
         "exceededUpperLimit":true,
         "forecastValues":{
            "temperature":5.53,
            "dateTime":"2020-11-11 09:00:00"
         }
      },
      {
         "id":3,
         "exceededLowerLimit":false,
         "exceededUpperLimit":true,
         "forecastValues":{
            "temperature":5.58,
            "dateTime":"2020-11-11 12:00:00"
         }
      }
   ]
}
```
List `forecasts` can be empty when there are not exceeding forecasts.

# Development
Project uses Lombok library. Make following steps if you are using IntelliJ IDEA:
1. Install Lombok Plugin.
 - Open Preferences -> Plugins and install a Lombok plugin.
2. Enable annotation processing.
 - Preferences -> Build, Execution, Deployment -> Compiler -> Annotation Processors
 - Select Enable annotation processing   

## Database
H2 in memory database is used in this project. Database can be seen by URL
`http://localhost:8080/h2-console` when application is running. Connection details are in \src\main\resources\application.yml.
 
