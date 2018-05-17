package com.wirefreethought.geodb.client;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.wirefreethought.geodb.client.model.CitiesResponse;
import com.wirefreethought.geodb.client.model.CityResponse;
import com.wirefreethought.geodb.client.model.CountriesResponse;
import com.wirefreethought.geodb.client.model.CountryResponse;
import com.wirefreethought.geodb.client.model.CurrenciesResponse;
import com.wirefreethought.geodb.client.model.DateTimeResponse;
import com.wirefreethought.geodb.client.model.DistanceResponse;
import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;
import com.wirefreethought.geodb.client.model.LanguagesResponse;
import com.wirefreethought.geodb.client.model.LocalesResponse;
import com.wirefreethought.geodb.client.model.RegionResponse;
import com.wirefreethought.geodb.client.model.RegionsResponse;
import com.wirefreethought.geodb.client.model.TimeResponse;
import com.wirefreethought.geodb.client.model.TimeZonesResponse;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.request.FindCitiesNearCityRequest;
import com.wirefreethought.geodb.client.request.FindCitiesNearLocationRequest;
import com.wirefreethought.geodb.client.request.FindCitiesRequest;
import com.wirefreethought.geodb.client.request.FindCityRequest;
import com.wirefreethought.geodb.client.request.FindCountriesRequest;
import com.wirefreethought.geodb.client.request.FindCountryRequest;
import com.wirefreethought.geodb.client.request.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.request.FindRegionCitiesRequest;
import com.wirefreethought.geodb.client.request.FindRegionRequest;
import com.wirefreethought.geodb.client.request.FindRegionsRequest;
import com.wirefreethought.geodb.client.request.GetCityDistanceRequest;

public class GeoDbApi
{
    private final static IncludeDeletedMode DEFAULT_INCLUDE_DELETED_MODE = IncludeDeletedMode.NONE;
    private final static GeoDbDistanceUnit DEFAULT_RADIUS_UNIT = GeoDbDistanceUnit.MILES;

    private GeoApi geoApi;
    private LocaleApi localeApi;

    public GeoDbApi(ApiClient client)
    {
        this.geoApi = new GeoApi(client);
        this.localeApi = new LocaleApi(client);
    }

    public CountriesResponse findAllCountries(boolean asciiMode, String languageCode, Integer limit, Integer offset)
    {
        return geoApi.getCountriesUsingGET(null, null, asciiMode, languageCode, limit, offset);
    }

    public CurrenciesResponse findAllCurrencies(Integer limit, Integer offset)
    {
        return localeApi.getCurrenciesUsingGET(null, limit, offset);
    }

    public LanguagesResponse findAllLanguages(Integer limit, Integer offset) {
        return localeApi.getLanguagesUsingGET(limit, offset);
    }

    public LocalesResponse findAllLocales(Integer limit, Integer offset)
    {
        return localeApi.getLocalesUsingGET(limit, offset);
    }

    public TimeZonesResponse findAllTimezones(Integer limit, Integer offset)
    {
        return localeApi.getTimezonesUsingGET(limit, offset);
    }

    public CitiesResponse findCities(FindCitiesNearCityRequest request)
    {
        return geoApi.findCitiesNearCityUsingGET(
            request.getCityId(),
            request.getMinPopulation(),
            request.getRadius(),
            toString(request.getDistanceUnit()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public CitiesResponse findCities(FindCitiesNearLocationRequest request)
    {
        return geoApi.findCitiesNearLocationUsingGET(
            toLocationId(request.getNearLocation()),
            request.getMinPopulation(),
            request.getNearLocation().getRadius(),
            toString(request.getNearLocation().getDistanceUnit()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public CitiesResponse findCities(FindCitiesRequest request)
    {
        String countryIds = request.getCountryIds() != null && !request.getCountryIds().isEmpty()
            ? StringUtils.join(request.getCountryIds(), ", ")
            : null;

        String excludedCountryIds = request.getExcludedCountryIds() != null && !request.getExcludedCountryIds().isEmpty()
            ? StringUtils.join(request.getExcludedCountryIds(), ", ")
            : null;

        String timeZoneIds = request.getTimeZoneIds() != null && !request.getTimeZoneIds().isEmpty()
            ? StringUtils.join(request.getTimeZoneIds(), ", ")
            : null;

        String location = null;
        Integer locationRadius = null;
        String distanceUnit = null;

        if (request.getNearLocation() != null)
        {
            GeoDbLocationConstraint nearLocation = request.getNearLocation();

            location = toLocationId(nearLocation);
            locationRadius = nearLocation.getRadius();
            distanceUnit = toString(nearLocation.getDistanceUnit());
        }

        return geoApi.findCitiesUsingGET(
            request.getNamePrefix(),
            countryIds,
            excludedCountryIds,
            request.getMinPopulation(),
            location,
            locationRadius,
            distanceUnit,
            timeZoneIds,
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public CityResponse findCity(FindCityRequest request)
    {
        return this.geoApi.getCityUsingGET(
            request.getCityId(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public CountriesResponse findCountries(FindCountriesRequest request)
    {
        return geoApi.getCountriesUsingGET(
            request.getNamePrefix(),
            request.getCurrencyCode(),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset());
    }

    public CountryResponse findCountry(FindCountryRequest request)
    {
        return this.geoApi.getCountryUsingGET(
            request.getCountryId(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public CurrenciesResponse findCurrencies(FindCurrenciesRequest request)
    {
        return localeApi.getCurrenciesUsingGET(
            request.getCountryId(),
            request.getLimit(),
            request.getOffset());
    }

    public RegionResponse findRegion(FindRegionRequest request)
    {
        return this.geoApi.getRegionUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public CitiesResponse findRegionCities(FindRegionCitiesRequest request)
    {
        return geoApi.findRegionCitiesUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getMinPopulation(),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public RegionsResponse findRegions(FindRegionsRequest request)
    {
        return geoApi.getRegionsUsingGET(
            request.getCountryId(),
            request.getNamePrefix(),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset());
    }

    public DateTimeResponse getCityDateTime(String cityId)
    {
        return this.geoApi.getCityDateTimeUsingGET(cityId);
    }

    public DistanceResponse getCityDistance(GetCityDistanceRequest request)
    {
        return this.geoApi.getCityDistanceUsingGET(
            request.getToCityId(),
            request.getFromCityId(),
            request.getDistanceUnit().getTag());
    }

    public TimeResponse getCityTime(String cityId)
    {
        return this.geoApi.getCityTimeUsingGET(cityId);
    }

    public DateTimeResponse getTimeZoneDateTime(String zoneId)
    {
        return localeApi.getTimeZoneDateTimeUsingGET(zoneId);
    }

    public TimeResponse getTimeZoneTime(String zoneId)
    {
        return localeApi.getTimeZoneTimeUsingGET(zoneId);
    }

    private String toLocationId(GeoDbLocationConstraint nearLocationRequest)
    {
        String locationStringFormat = "%s%s";

        if (nearLocationRequest.getLatitude() >= 0)
        {
            locationStringFormat = "+" + locationStringFormat;
        }

        if (nearLocationRequest.getLongitude() >= 0)
        {
            locationStringFormat = "%s+%s";
        }

        return String.format(locationStringFormat, "" + nearLocationRequest.getLatitude(), "" + nearLocationRequest.getLongitude());
    }

    private String toString(GeoDbDistanceUnit radiusUnit)
    {
        if (radiusUnit == null)
        {
            radiusUnit = DEFAULT_RADIUS_UNIT;
        }

        return radiusUnit.getTag();
    }

    private String toString(GeoDbSort sort)
    {
        String sortString = StringUtils.EMPTY;

        if (sort != null)
        {
            sortString = sort.getFields().stream()
                .map(f -> String.format("%s%s", f.isReverse() ? "-" : "+", f.getName()))
                .collect(Collectors.joining(","));
        }

        return sortString;
    }

    private String toString(IncludeDeletedMode mode)
    {
        if (mode == null)
        {
            mode = DEFAULT_INCLUDE_DELETED_MODE;
        }

        return mode.getTag();
    }
}
