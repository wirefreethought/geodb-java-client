# GeoDB Java Client SDK
Simplify your life when calling the [GeoDB](https://rapidapi.com/user/wirefreethought/package/GeoDB) service form a Java or Android app by using this library.

## Setup
1. Add the following compile-time dependency to your Maven pom.xml:
    ```
    <dependency>
      <groupId>com.wirefreethought.geodb</groupId>
      <artifactId>geodb-java-client</artifactId>
      <version>1.0.5</version>
    </dependency>
    ```
2. Create an account on [RapidAPI](https://rapidapi.com). As part of account creation, Rapid asks for credit-card info. As long as you stay within the free usage limits of the Basic plan, your credit card will not be charged.
3. [Select](https://rapidapi.com/user/wirefreethought/package/GeoDB/pricing) a GeoDB plan.
4. In the project root, copy **build.properties.sample** to **build.properties**.
5. In **build.properties**, substitute YOUR_MASHAPE_KEY for the key assigned to you by Rapid.
6. From the project root, run: ```mvn test```

## Usage
```
// Setup
GeoDbApiClient apiClient = new GeoDbApiClient();
apiClient.setApiKey(YOUR_MASHAPE_KEY);

GeoDbApi geoDbApi = new GeoDbApi(apiClient);

// Execute service calls. (See below for examples.)
geoDbApi.findCities(FindCitiesRequest.builder()...build());

```

## Cookbook

Find all cities in the US starting with **San** and having a minimum population of 100,000.
```
CitiesResponse citiesResponse = geoDbApi.findCities(
    FindCitiesRequest.builder()
        .countryCodes("US")
        .namePrefix("San")
        .minPopulation(100000)
        .build()
);
```

Find all cities and towns in the Los Angeles area and having a minimum population of 50,000.
```
// Get the location for Los Angeles.
CityResponse cityResponse = geoDbApi.findCityById(98364);
GeoLocation cityLocation = cityResponse.getData().getLocation();

// Find all cities/towns within 50 miles of this location.
CitiesResponse citiesResponse = geoDbApi.findCities(
    FindCitiesRequest.builder()
        .nearLocation(
            NearLocationRequest.builder()
                .latitude(cityLocation.getLatitude())
                .longitude(cityLocation.getLongitude())
                .radius(50)
                .radiusUnit(LocationRadiusUnit.MILES)
                .build())
        .minPopulation(50000)
        .build()
)
```

Find all cities in California having a minimum population of 100,000.
```
CitiesResponse citiesResponse = geoDbApi.findRegionCities(
    FindRegionCitiesRequest.builder()
        .countryCode("US")
        .regionCode("CA")
        .minPopulation(100000)
        .build()
);
```

Find all states in the US.
```
RegionsResponse regionsResponse = geoDbApi.findRegions(
    FindRegionsRequest.builder()
        .countryCode("US")
        .build()
);
```

Find the distance between Los Angeles, CA and Dallas, TX.
```
DistanceResponse distanceResponse = geoDbApi.getCityDistance(
    DistanceResponse.builder()
        .fromCityId(93831)
        .toCityId(65474)
        .distanceUnit(DistanceUnit.MILES)
        .build()
);
```


## API Docs
For detailed REST docs, including all supported endpoints as well as request/response format per endpoint, [go here](https://wirefreethought.github.io/geo-db-docs/).





