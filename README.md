# GeoDB Java Client SDK
Simplify your life when calling the [GeoDB](https://rapidapi.com/user/wirefreethought/package/GeoDB) service form a Java or Android app by using this library.

## Setup
1. Add the following compile-time dependencies to your Maven pom.xml:
    ```
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.8.1</version>
    </dependency>

    <dependency>
      <groupId>com.squareup.okhttp</groupId>
      <artifactId>okhttp</artifactId>
      <version>2.7.5</version>
    </dependency>

    <dependency>
      <groupId>com.squareup.okhttp</groupId>
      <artifactId>logging-interceptor</artifactId>
      <version>2.7.5</version>
    </dependency>

    <dependency>
      <groupId>io.swagger</groupId>
      <artifactId>swagger-annotations</artifactId>
      <version>1.5.16</version>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.18</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.25</version>
    </dependency>
    ```
2. For Android, please [go here](https://projectlombok.org/setup/android) for Lombok-specific instructions.
3. Create an account on [RapidAPI](https://rapidapi.com). As part of account creation, Rapid asks for credit-card info. As long as you stay within the free usage limits of the Basic plan, your credit card will not be charged.
4. [Select](https://rapidapi.com/user/wirefreethought/package/GeoDB/pricing) a GeoDB plan.
5. In the project root, copy **build.properties.sample** to **build.properties**.
6. In **build.properties**, substitute YOUR_MASHAPE_KEY for the key assigned to you by Rapid.
7. From the project root, run: ```mvn test```

## Usage
```
// Setup
GeoDbApiClient apiClient = new GeoDbApiClient();
apiClient.setApiKey(YOUR_MASHAPE_KEY);

GeoDbApi geoDbApi = new GeoDbApi(apiClient);

// Execute service calls. (See Cookbook section for examples.)
geoDbApi.findCities(FindCitiesRequest.builder()...build());

```

## Cookbook

### Find all cities in the US starting with 'San' and having a minimum population of 100,000.

```
geoDbApi.findCities(
    FindCitiesRequest.builder()
        .countryCode("US")
        .namePrefix("San")
        .minPopulation(100000)
        .build());
);
```





