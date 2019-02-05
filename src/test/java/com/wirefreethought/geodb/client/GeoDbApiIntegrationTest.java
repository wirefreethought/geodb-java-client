package com.wirefreethought.geodb.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wirefreethought.geodb.client.model.CitiesResponse;
import com.wirefreethought.geodb.client.model.CitySortFields;
import com.wirefreethought.geodb.client.model.CitySummary;
import com.wirefreethought.geodb.client.model.CountriesResponse;
import com.wirefreethought.geodb.client.model.CountrySummary;
import com.wirefreethought.geodb.client.model.CurrenciesResponse;
import com.wirefreethought.geodb.client.model.CurrencyDescriptor;
import com.wirefreethought.geodb.client.model.DateTimeResponse;
import com.wirefreethought.geodb.client.model.DistanceResponse;
import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbInstanceType;
import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.GeoDbSort.SortField;
import com.wirefreethought.geodb.client.model.RegionSummary;
import com.wirefreethought.geodb.client.model.RegionsResponse;
import com.wirefreethought.geodb.client.model.TimeResponse;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.net.ApiException;
import com.wirefreethought.geodb.client.net.GeoDbApiClient;
import com.wirefreethought.geodb.client.request.CityRequestType;
import com.wirefreethought.geodb.client.request.FindCitiesNearCityRequest;
import com.wirefreethought.geodb.client.request.FindCitiesRequest;
import com.wirefreethought.geodb.client.request.FindCountriesRequest;
import com.wirefreethought.geodb.client.request.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.request.FindRegionCitiesRequest;
import com.wirefreethought.geodb.client.request.FindRegionsRequest;
import com.wirefreethought.geodb.client.request.GetCityDistanceRequest;

import lombok.extern.slf4j.Slf4j;

@RunWith(JUnit4.class)
@Slf4j
public class GeoDbApiIntegrationTest
{
    private GeoDbApi api;
    private ApiClient apiClient;

    public GeoDbApiIntegrationTest()
    {
        ApiClient client = new GeoDbApiClient(GeoDbInstanceType.PRO);
        client.setApiKey(TestProperties.getApiKey());

        this.apiClient = client;
    }

    @Before
    public void setup()
    {
        this.api = new GeoDbApi(apiClient);
    }

    @Test
    public void testFindCities_namePrefix()
    {
        try
        {
            testFindCities(
                FindCitiesRequest.builder()
                    .namePrefix("Los")
                    .minPopulation(100000)
                    .types(Collections.singleton(CityRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(CitySortFields.FindCities.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testFindCities_nearLocation()
    {
        try
        {
            testFindCities(
                FindCitiesRequest.builder()
                    .nearLocation(
                        GeoDbLocationConstraint.builder()
                            .latitude(33.831965)
                            .longitude(-118.376601)
                            .radius(100)
                            .distanceUnit(GeoDbDistanceUnit.MILES)
                            .build())
                    .minPopulation(100000)
                    .types(Collections.singleton(CityRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(CitySortFields.FindCities.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testFindCities_timeZoneIds()
    {
        try
        {
            testFindCities(
                FindCitiesRequest.builder()
                    .minPopulation(100000)
                    .timeZoneIds(Collections.singleton("America__Los_Angeles"))
                    .types(Collections.singleton(CityRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(CitySortFields.FindCities.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testFindCitiesNearCity()
    {
        try
        {
            testFindCitiesNearCity(
                FindCitiesNearCityRequest.builder()
                    .cityId("1000")
                    .minPopulation(100000)
                    .radius(100)
                    .distanceUnit(GeoDbDistanceUnit.MILES)
                    .types(Collections.singleton(CityRequestType.CITY))
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(CitySortFields.FindCities.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testFindCountries_currencyCode()
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
    public void testFindCurrencies_countryCode()
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
    public void testFindRegionCities()
    {
        try
        {
            testFindRegionCities(
                FindRegionCitiesRequest.builder()
                    .countryId("US")
                    .regionCode("CA")
                    .minPopulation(100000)
                    .sort(
                        GeoDbSort.builder()
                            .fields(new SortField[] {
                                new SortField(CitySortFields.FindCities.POPULATION, true)
                            })
                            .build())
                    .build());
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testFindRegions()
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
    public void testGetCityDistance()
    {
        try
        {
            GetCityDistanceRequest request = GetCityDistanceRequest.builder()
                .distanceUnit(GeoDbDistanceUnit.MILES)
                .fromCityId("5315")
                .toCityId("100327")
                .build();

            DistanceResponse response = this.api.getCityDistance(request);

            assertNotNull(response);
            assertNotNull(response.getData());
            assertNull(response.getErrors());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testGetTimeZoneDateTime()
    {
        try
        {
            DateTimeResponse response = this.api.getTimeZoneDateTime("America__Los_Angeles");

            assertNotNull(response);
            assertNotNull(response.getData());
            assertNull(response.getErrors());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    @Test
    public void testGetTimeZoneTime()
    {
        try
        {
            TimeResponse response = this.api.getTimeZoneTime("America__Los_Angeles");

            assertNotNull(response);
            assertNotNull(response.getData());
            assertNull(response.getErrors());

            log(response);
        } catch (ApiException e)
        {
            handle(e);
        }
    }

    private void assertValid(CitiesResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getErrors());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(c -> {
            assertValid(c);
        });
    }

    private void assertValid(CitySummary city)
    {
        assertNotNull(city.getId());
        assertTrue(StringUtils.isNotBlank(city.getCity()));
    }

    private void assertValid(CountriesResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getErrors());
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
        assertNull(response.getErrors());
        assertFalse(response.getData().isEmpty());

        response.getData().forEach(c -> {
            assertValid(c);
        });
    }

    private void assertValid(CurrencyDescriptor currency)
    {
        assertTrue(StringUtils.isNotBlank(currency.getCode()));
        assertNotNull(currency.getCountryCodes());
        assertFalse(currency.getCountryCodes().isEmpty());
    }

    private void assertValid(RegionsResponse response)
    {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertNull(response.getErrors());
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

    private void log(CitiesResponse response)
    {
        response.getData().forEach(c -> {
            log.info("City: {}", c);
        });

        long totalCount = response.getData().size();

        if (response.getMetadata() != null)
        {
            totalCount = response.getMetadata().getTotalCount();
        }

        log.info("Total results: {}", totalCount);
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
        log.info("distance: {}", response.getData());
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

    private void testFindCities(FindCitiesRequest request)
    {
        CitiesResponse response = this.api.findCities(request);

        assertValid(response);

        log(response);
    }

    private void testFindCitiesNearCity(FindCitiesNearCityRequest request)
    {
        CitiesResponse response = this.api.findCities(request);

        assertValid(response);

        log(response);
    }

    private void testFindCountries(FindCountriesRequest request)
    {
        CountriesResponse response = this.api.findCountries(request);

        assertValid(response);

        log(response);
    }

    private void testFindCurrencies(FindCurrenciesRequest request)
    {
        CurrenciesResponse response = this.api.findCurrencies(request);

        assertValid(response);

        log(response);
    }

    private void testFindRegionCities(FindRegionCitiesRequest request)
    {
        CitiesResponse response = this.api.findRegionCities(request);

        assertValid(response);

        log(response);
    }

    private void testFindRegions(FindRegionsRequest request)
    {
        RegionsResponse response = this.api.findRegions(request);

        assertValid(response);

        log(response);
    }
}
