package com.wirefreethought.geodb.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wirefreethought.geodb.client.model.CountriesResponse;
import com.wirefreethought.geodb.client.model.CountrySummary;
import com.wirefreethought.geodb.client.model.CurrenciesResponse;
import com.wirefreethought.geodb.client.model.Currency;
import com.wirefreethought.geodb.client.model.DateTimeResponse;
import com.wirefreethought.geodb.client.model.DistanceResponse;
import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbInstanceType;
import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.GeoDbSort.SortField;
import com.wirefreethought.geodb.client.model.PlaceSortFields;
import com.wirefreethought.geodb.client.model.PopulatedPlaceResponse;
import com.wirefreethought.geodb.client.model.PopulatedPlaceSummary;
import com.wirefreethought.geodb.client.model.PopulatedPlacesResponse;
import com.wirefreethought.geodb.client.model.RegionSummary;
import com.wirefreethought.geodb.client.model.RegionsResponse;
import com.wirefreethought.geodb.client.model.TimeResponse;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.net.ApiException;
import com.wirefreethought.geodb.client.net.GeoDbApiClient;
import com.wirefreethought.geodb.client.request.FindAdminDivisionsRequest;
import com.wirefreethought.geodb.client.request.FindCountriesRequest;
import com.wirefreethought.geodb.client.request.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.request.FindDivisionsNearPlaceRequest;
import com.wirefreethought.geodb.client.request.FindPlacesNearPlaceRequest;
import com.wirefreethought.geodb.client.request.FindPlacesRequest;
import com.wirefreethought.geodb.client.request.FindRegionDivisionsRequest;
import com.wirefreethought.geodb.client.request.FindRegionPlacesRequest;
import com.wirefreethought.geodb.client.request.FindRegionsRequest;
import com.wirefreethought.geodb.client.request.GetPlaceDistanceRequest;
import com.wirefreethought.geodb.client.request.GetPlaceRequest;
import com.wirefreethought.geodb.client.request.PlaceRequestType;

import lombok.extern.slf4j.Slf4j;

@RunWith(JUnit4.class)
@Slf4j
class GeoDbApiIntegrationTest
{
    private GeoDbApi api;
    private ApiClient apiClient;

    public GeoDbApiIntegrationTest()
    {
        ApiClient client = new GeoDbApiClient(GeoDbInstanceType.FREE);
        client.setApiKey(TestProperties.getApiKey());

        this.apiClient = client;
    }

    @Before
    public void setup()
    {
        api = new GeoDbApi(apiClient);
    }

    @Test
    void findAdminDivisions_namePrefix()
    {
        try
        {
            testFindAdminDivisions(
                FindAdminDivisionsRequest.builder()
                    .namePrefix("Los")
                    .minPopulation(100000)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findAdminDivisions_nearLocation()
    {
        try
        {
            testFindAdminDivisions(
                FindAdminDivisionsRequest.builder()
                    .nearLocation(
                        GeoDbLocationConstraint.builder()
                            .latitude(33.831965)
                            .longitude(-118.376601)
                            .radius(100)
                            .distanceUnit(GeoDbDistanceUnit.MILES)
                            .build())
                    .minPopulation(100000)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findAdminDivisions_nearPlace()
    {
        try
        {
            testFindDivisionsNearCity(
                FindDivisionsNearPlaceRequest.builder()
                    .placeId("Q90")
                    .minPopulation(100000)
                    .radius(100)
                    .distanceUnit(GeoDbDistanceUnit.MILES)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findAdminDivisions_timeZoneIds()
    {
        try
        {
            testFindAdminDivisions(
                FindAdminDivisionsRequest.builder()
                    .minPopulation(100000)
                    .timeZoneIds(Collections.singleton("America__Los_Angeles"))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCities_namePrefix()
    {
        try
        {
            testFindCities(
                FindPlacesRequest.builder()
                    .namePrefix("Los")
                    .minPopulation(100000)
                    .types(Collections.singleton(PlaceRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCities_nearLocation()
    {
        try
        {
            testFindCities(
                FindPlacesRequest.builder()
                    .nearLocation(
                        GeoDbLocationConstraint.builder()
                            .latitude(33.831965)
                            .longitude(-118.376601)
                            .radius(100)
                            .distanceUnit(GeoDbDistanceUnit.MILES)
                            .build())
                    .minPopulation(100000)
                    .types(Collections.singleton(PlaceRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCities_nearPlace()
    {
        try
        {
            testFindCitiesNearCity(
                FindPlacesNearPlaceRequest.builder()
                    .placeId("Q60")
                    .minPopulation(50000)
                    .radius(100)
                    .distanceUnit(GeoDbDistanceUnit.MILES)
                    .types(Collections.singleton(PlaceRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCities_timeZoneIds()
    {
        try
        {
            testFindCities(
                FindPlacesRequest.builder()
                    .minPopulation(100000)
                    .timeZoneIds(Collections.singleton("America__Los_Angeles"))
                    .types(Collections.singleton(PlaceRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCountries_currencyCode()
    {
        try
        {
            testFindCountries(
                FindCountriesRequest.builder()
                    .currencyCode("USD")
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findCurrencies_countryId()
    {
        try
        {
            testFindCurrencies(
                FindCurrenciesRequest.builder()
                    .countryId("US")
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findRegionDivisions()
    {
        try
        {
            testFindRegionDivisions(
                FindRegionDivisionsRequest.builder()
                    .countryId("US")
                    .regionCode("CA")
                    .minPopulation(100000)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findRegionPlaces()
    {
        try
        {
            testFindRegionPlaces(
                FindRegionPlacesRequest.builder()
                    .countryId("US")
                    .regionCode("CA")
                    .minPopulation(100000)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(PlaceSortFields.FindPlaces.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void findRegions()
    {
        try
        {
            testFindRegions(
                FindRegionsRequest.builder()
                    .countryId("US")
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void getPlaceAdminRegion()
    {
        try
        {
            GetPlaceRequest request = GetPlaceRequest.builder()
                .placeId("Q65")
                .build();

            PopulatedPlaceResponse response = api.getPlaceAdminRegion(request);

            assertNotNull(response);
            assertNotNull(response.getData());
            assertTrue(response.getErrors().isEmpty());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void getPlaceDistance()
    {
        try
        {
            GetPlaceDistanceRequest request = GetPlaceDistanceRequest.builder()
                .distanceUnit(GeoDbDistanceUnit.MILES)
                .fromPlaceId("Q60")
                .toPlaceId("Q90")
                .build();

            DistanceResponse response = api.getPlaceDistance(request);

            assertNotNull(response);
            assertNotNull(response.getData());
            assertTrue(response.getErrors().isEmpty());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void getTimeZoneDateTime()
    {
        try
        {
            DateTimeResponse response = api.getTimeZoneDateTime("America__Los_Angeles");

            assertNotNull(response);
            assertNotNull(response.getData());
            assertTrue(response.getErrors().isEmpty());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    void getTimeZoneTime()
    {
        try
        {
            TimeResponse response = api.getTimeZoneTime("America__Los_Angeles");

            assertNotNull(response);
            assertNotNull(response.getData());
            assertTrue(response.getErrors().isEmpty());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    private void assertValid(CountriesResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getErrors().isEmpty());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(c -> {
            assertValid(c);
        });
    }

    private void assertValid(CountrySummary country)
    {
        assertTrue(StringUtils.isNotBlank(country.getCode()));
        assertTrue(!country.getCurrencyCodes().isEmpty());
        assertTrue(StringUtils.isNotBlank(country.getName()));
    }

    private void assertValid(CurrenciesResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getErrors().isEmpty());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(c -> {
            assertValid(c);
        });
    }

    private void assertValid(Currency currency)
    {
        assertTrue(StringUtils.isNotBlank(currency.getCode()));
        assertNotNull(currency.getCountryCodes());
        assertFalse(currency.getCountryCodes().isEmpty());
    }

    private void assertValid(PopulatedPlacesResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getErrors().isEmpty());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(c -> {
            assertValid(c);
        });
    }

    private void assertValid(PopulatedPlaceSummary place)
    {
        assertNotNull(place.getId());
        assertTrue(StringUtils.isNotBlank(place.getName()));
    }

    private void assertValid(RegionsResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getErrors().isEmpty());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(r -> {
            assertValid(r);
        });
    }

    private void assertValid(RegionSummary region)
    {
        assertTrue(StringUtils.isNotBlank(region.getCountryCode()));
        assertTrue(StringUtils.isNotBlank(region.getName()));
    }

    private void handle(ApiException e)
    {
        log.error(e.getResponseBody());
        fail(e.getResponseBody());
    }

    private void log(CountriesResponse response)
    {
        response.getData().forEach(c -> {
            log.info("Country: {}", c);
        });

        long totalCount = response.getData().size();

        if (response.getMetadata() != null)
        {
            totalCount = response.getMetadata().getTotalCount();
        }

        log.info("Total resuls: {}", totalCount);
    }

    private void log(CurrenciesResponse response)
    {
        response.getData().forEach(c -> {
            log.info("Currency: {}", c);
        });

        long totalCount = response.getData().size();

        if (response.getMetadata() != null)
        {
            totalCount = response.getMetadata().getTotalCount();
        }

        log.info("Total resuls: {}", totalCount);
    }

    private void log(DateTimeResponse response)
    {
        log.info("date-time: {}", response.getData().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    private void log(DistanceResponse response)
    {
        log.info("Distance: {}", response.getData());
    }

    private void log(PopulatedPlaceResponse response)
    {
        log.info("name: {}", response.getData().getName());
    }

    private void log(PopulatedPlacesResponse response)
    {
        response.getData().forEach(c -> {
            log.info("Place: {}", c);
        });

        long totalCount = response.getData().size();

        if (response.getMetadata() != null)
        {
            totalCount = response.getMetadata().getTotalCount();
        }

        log.info("Total results: {}", totalCount);
    }

    private void log(RegionsResponse response)
    {
        response.getData().forEach(c -> {
            log.info("Region: {}", c);
        });

        long totalCount = response.getData().size();

        if (response.getMetadata() != null)
        {
            totalCount = response.getMetadata().getTotalCount();
        }

        log.info("Total resuls: {}", totalCount);
    }

    private void log(TimeResponse response)
    {
        String isoTime = response.getData();

        log.info("time: {}", isoTime);
    }

    private void testFindAdminDivisions(FindAdminDivisionsRequest request)
    {
        PopulatedPlacesResponse response = api.findAdminDivisions(request);

        assertValid(response);

        log(response);
    }

    private void testFindCities(FindPlacesRequest request)
    {
        PopulatedPlacesResponse response = api.findPlaces(request);

        assertValid(response);

        log(response);
    }

    private void testFindCitiesNearCity(FindPlacesNearPlaceRequest request)
    {
        PopulatedPlacesResponse response = api.findPlaces(request);

        assertValid(response);

        log(response);
    }

    private void testFindCountries(FindCountriesRequest request)
    {
        CountriesResponse response = api.findCountries(request);

        assertValid(response);

        log(response);
    }

    private void testFindCurrencies(FindCurrenciesRequest request)
    {
        CurrenciesResponse response = api.findCurrencies(request);

        assertValid(response);

        log(response);
    }

    private void testFindDivisionsNearCity(FindDivisionsNearPlaceRequest request)
    {
        PopulatedPlacesResponse response = api.findAdminDivisions(request);

        assertValid(response);

        log(response);
    }

    private void testFindRegionDivisions(FindRegionDivisionsRequest request)
    {
        PopulatedPlacesResponse response = api.findRegionDivisions(request);

        assertValid(response);

        log(response);
    }

    private void testFindRegionPlaces(FindRegionPlacesRequest request)
    {
        PopulatedPlacesResponse response = api.findRegionPlaces(request);

        assertValid(response);

        log(response);
    }

    private void testFindRegions(FindRegionsRequest request)
    {
        RegionsResponse response = api.findRegions(request);

        assertValid(response);

        log(response);
    }
}
