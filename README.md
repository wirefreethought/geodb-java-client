# GeoDB Java Client SDK
Simplify your life when calling the [GeoDB](https://geodb-cities-api.wirefreethought.com) service form a Java or Android app by using this library.

## Setup
Add the following compile-time dependency to your Maven pom.xml:
 ```
<dependency>
    <groupId>com.wirefreethought.geodb</groupId>
    <artifactId>geodb-java-client</artifactId>
    <version>1.3.4</version>
</dependency>
```

If you're using the FREE service:
```
GeoDbApiClient apiClient = new GeoDbApiClient(GeoDbInstanceType.FREE);
```

If you're using the PRO service:
1. Create an account on [RapidAPI](https://rapidapi.com). As part of account creation, Rapid asks for credit-card info. As long as you stay within the free usage limits of the Basic plan, your credit card will not be charged.
2. [Select](https://rapidapi.com/wirefreethought/api/GeoDB%20Cities/pricing) a GeoDB plan.
3. Instantiate the client.
    ```
    GeoDbApiClient apiClient = new GeoDbApiClient(GeoDbInstanceType.PRO);
    apiClient.setApiKey(YOUR_MASHAPE_KEY);
    ```

## Usage
```
GeoDbApi geoDbApi = new GeoDbApi(apiClient);

// Execute service calls. (See below for examples.)
geoDbApi.findCities(FindCitiesRequest.builder()...build());

```

## Cookbook

Find all cities in the US starting with **San** and having a minimum population of 100,000.
```
PopulatedPlacesResponse placesResponse = geoDbApi.findPlaces(
    FindPlacesRequest.builder()
        .countryIds("US")
        .namePrefix("San")
        .minPopulation(100000)
        .types(Collections.singleton(PlaceRequestType.CITY))
        .build()
);
```

Find all cities and towns in the Los Angeles area and having a minimum population of 50,000 - sorting results by population, high to low.
```
// Get the location for Los Angeles.
PopulatedPlaceResponse placeResponse = geoDbApi.findPlaceById("Q65");
GeoLocation placeLocation = placeResponse.getData().getLocation();

// Find all cities/towns within 50 miles of this location.
PopulatedPlacesResponse placesResponse = geoDbApi.findPlaces(
    FindPlacesRequest.builder()
        .nearLocation(
            NearLocationRequest.builder()
                .latitude(placeLocation.getLatitude())
                .longitude(placeLocation.getLongitude())
                .radius(50)
                .radiusUnit(LocationRadiusUnit.MILES)
                .build())
        .minPopulation(50000)
        .types(Collections.singleton(PlaceRequestType.CITY))
        .sort(
            GeoDbSort.builder()
                .fields(new SortField[] {
                    new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                })
                .build())
        .build()
)
```

Find all cities in California having a minimum population of 100,000 - sorting results by population, low to high.
```
PopulatedPlacesResponse placesResponse = geoDbApi.findRegionPlaces(
    FindRegionPlacesRequest.builder()
        .countryId("US")
        .regionCode("CA")
        .minPopulation(100000)
        .types(Collections.singleton(PlaceRequestType.CITY))
        .sort(
            GeoDbSort.builder()
                .fields(new SortField[] {
                    new SortField(PlaceSortFields.FindPlaces.POPULATION, false)
                })
                .build())
        .build()
);
```

Find all states in the US.
```
RegionsResponse regionsResponse = geoDbApi.findRegions(
    FindRegionsRequest.builder()
        .countryId("US")
        .build()
);
```

Find the distance between Los Angeles, CA and Dallas, TX.
```
DistanceResponse distanceResponse = geoDbApi.getCityDistance(
    DistanceResponse.builder()
        .fromCityId("Q65")
        .toCityId("Q16557")
        .distanceUnit(DistanceUnit.MILES)
        .build()
);
```


## API Docs
For detailed REST docs, [go here](http://geodb-cities-api.wirefreethought.com/docs/api-reference/rest-api).





