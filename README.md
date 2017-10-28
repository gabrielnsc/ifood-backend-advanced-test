# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is above 15 and below 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success,it must be prepared to be fault tolerant,responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to. 

Also, briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Fork this repository and submit your code.


## About the Project
This challenge's implementation was made by Gabriel Nascimento using Spring Boot Framework.

The project was divided into three layers, where the requested data flows from the top to the bottom and right back to the top again
 * Controller: our API gateway, responsible for getting the incoming requests
 * Service: our business layer, responsible for handling our business rules
 * Network: our communication layer, responsible for calling external APIs to our suggestion engine
 
Hope you guys enjoy it :)

### Required tools
 * JDK 8.0 (or higher)
 * Maven 3.5.0 (or higher)
 * Eclipse Oxygen (Optional)
 
### How to install
 * Clone the code from https://github.com/gabrielnsc/ifood-backend-advanced-test.git
 * Go to the project's folder and install the maven dependencies with: `mvn package`
 * Run the project with: `mvn spring-boot:run`
 
### API Calls examples

 * http://localhost:8080/api/tracks/city/Campinas
 * http://localhost:8080/api/tracks/city/Belo+Horizonte
 * http://localhost:8080/api/tracks/city/Cupertino
 * http://localhost:8080/api/tracks/location?lat=-22&lon=-47 (Campinas' coordinates)
 
### API's Documentation
The API's documentation was made using Swagger. 

Once the server is up and running you should simply open your browser and type http://localhost:8080/doc on the navigation bar.

### Unit tests
To run the unit tests you should simply:
 * Go to the project's folder and install the maven dependencies with: mvn package
 * Run the tests using the command: `mvn test`

There are currently 37 unit tests from our controller, service and network layers + our Utilities classes.
