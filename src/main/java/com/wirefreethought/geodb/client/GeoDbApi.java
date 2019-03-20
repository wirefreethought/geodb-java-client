package com.wirefreethought.geodb.client;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.wirefreethought.geodb.client.model.CountriesResponse;
import com.wirefreethought.geodb.client.model.CountryResponse;
import com.wirefreethought.geodb.client.model.CurrenciesResponse;
import com.wirefreethought.geodb.client.model.DateTimeResponse;
import com.wirefreethought.geodb.client.model.DistanceResponse;
import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbEnum;
import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;
import com.wirefreethought.geodb.client.model.LanguagesResponse;
import com.wirefreethought.geodb.client.model.LocalesResponse;
import com.wirefreethought.geodb.client.model.PopulatedPlaceResponse;
import com.wirefreethought.geodb.client.model.PopulatedPlacesResponse;
import com.wirefreethought.geodb.client.model.RegionResponse;
import com.wirefreethought.geodb.client.model.RegionsResponse;
import com.wirefreethought.geodb.client.model.TimeResponse;
import com.wirefreethought.geodb.client.model.TimeZonesResponse;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.request.PlaceRequestType;
import com.wirefreethought.geodb.client.request.FindAdminDivisionsRequest;
import com.wirefreethought.geodb.client.request.FindPlacesNearPlaceRequest;
import com.wirefreethought.geodb.client.request.FindPlacesNearLocationRequest;
import com.wirefreethought.geodb.client.request.FindPlacesRequest;
import com.wirefreethought.geodb.client.request.FindPlaceRequest;
import com.wirefreethought.geodb.client.request.FindCountriesRequest;
import com.wirefreethought.geodb.client.request.FindCountryRequest;
import com.wirefreethought.geodb.client.request.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.request.FindDivisionsNearPlaceRequest;
import com.wirefreethought.geodb.client.request.FindRegionPlacesRequest;
import com.wirefreethought.geodb.client.request.FindRegionRequest;
import com.wirefreethought.geodb.client.request.FindRegionsRequest;
import com.wirefreethought.geodb.client.request.GetPlaceDistanceRequest;

public class GeoDbApi
{
    private final static IncludeDeletedMode DEFAULT_INCLUDE_DELETED_MODE = IncludeDeletedMode.NONE;
    private final static GeoDbDistanceUnit DEFAULT_RADIUS_UNIT = GeoDbDistanceUnit.MILES;

    private static String toLocationId(GeoDbLocationConstraint nearLocationRequest)
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

    private static String toString(GeoDbDistanceUnit radiusUnit)
    {
        return toString(radiusUnit, DEFAULT_RADIUS_UNIT);
    }

    private static String toString(GeoDbEnum enumValue, GeoDbEnum defaultEnumValue)
    {
        return enumValue != null
            ? enumValue.getTag()
            : (defaultEnumValue != null
                ? defaultEnumValue.getTag()
                : null);
    }

    private static String toString(GeoDbSort sort)
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

    private static String toString(IncludeDeletedMode mode)
    {
        return toString(mode, DEFAULT_INCLUDE_DELETED_MODE);
    }

    private static <T extends GeoDbEnum> String toStringFromEnumSet(Set<T> values)
    {
        return values != null && !values.isEmpty()
            ? values.stream()
                .map(GeoDbEnum::getTag)
                .collect(Collectors.joining(","))
            : null;
    }

    private static String toStringFromStringSet(Set<String> values)
    {
        return values != null && !values.isEmpty()
            ? values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","))
            : null;
    }

    private GeoApi geoApi;

    private LocaleApi localeApi;

    public GeoDbApi(ApiClient client)
    {
        this.geoApi = new GeoApi(client);
        this.localeApi = new LocaleApi(client);
    }

    public PopulatedPlacesResponse findAdminDivisions(FindAdminDivisionsRequest request)
    {
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

        return geoApi.findAdminDivisionsUsingGET(
            request.getNamePrefix(),
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            location,
            locationRadius,
            distanceUnit,
            toStringFromStringSet(request.getTimeZoneIds()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public PopulatedPlacesResponse findAdminDivisions(FindDivisionsNearPlaceRequest request)
    {
        return geoApi.findCitiesNearCityUsingGET(
            request.getPlaceId(),
            request.getMinPopulation(),
            toString(PlaceRequestType.ADMIN_DIVISION_2),
            request.getRadius(),
            toString(request.getDistanceUnit()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public CountriesResponse findAllCountries(boolean asciiMode, String languageCode, Integer limit, Integer offset)
    {
        return geoApi.getCountriesUsingGET(null, null, asciiMode, languageCode, limit, offset, false);
    }

    public CurrenciesResponse findAllCurrencies(Integer limit, Integer offset)
    {
        return localeApi.getCurrenciesUsingGET(null, limit, offset, false);
    }

    public LanguagesResponse findAllLanguages(Integer limit, Integer offset)
    {
        return localeApi.getLanguagesUsingGET(limit, offset, false);
    }

    public LocalesResponse findAllLocales(Integer limit, Integer offset)
    {
        return localeApi.getLocalesUsingGET(limit, offset, false);
    }

    public TimeZonesResponse findAllTimezones(Integer limit, Integer offset)
    {
        return localeApi.getTimezonesUsingGET(limit, offset, false);
    }

    public CountriesResponse findCountries(FindCountriesRequest request)
    {
        return geoApi.getCountriesUsingGET(
            request.getNamePrefix(),
            request.getCurrencyCode(),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            false);
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
            request.getOffset(),
            false);
    }

    public PopulatedPlaceResponse findPlace(FindPlaceRequest request)
    {
        return this.geoApi.getCityUsingGET(
            request.getPlaceId(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public PopulatedPlacesResponse findPlaces(FindPlacesNearLocationRequest request)
    {
        return geoApi.findCitiesNearLocationUsingGET(
            toLocationId(request.getNearLocation()),
            request.getMinPopulation(),
            toStringFromEnumSet(request.getTypes()),
            request.getNearLocation().getRadius(),
            toString(request.getNearLocation().getDistanceUnit()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public PopulatedPlacesResponse findPlaces(FindPlacesNearPlaceRequest request)
    {
        return geoApi.findCitiesNearCityUsingGET(
            request.getPlaceId(),
            request.getMinPopulation(),
            toStringFromEnumSet(request.getTypes()),
            request.getRadius(),
            toString(request.getDistanceUnit()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public PopulatedPlacesResponse findPlaces(FindPlacesRequest request)
    {
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
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            location,
            locationRadius,
            distanceUnit,
            toStringFromStringSet(request.getTimeZoneIds()),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public RegionResponse findRegion(FindRegionRequest request)
    {
        return this.geoApi.getRegionUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public PopulatedPlacesResponse findRegionPlaces(FindRegionPlacesRequest request)
    {
        return geoApi.findRegionCitiesUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getMinPopulation(),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()),
            false);
    }

    public RegionsResponse findRegions(FindRegionsRequest request)
    {
        return geoApi.getRegionsUsingGET(
            request.getCountryId(),
            request.getNamePrefix(),
            request.getAsciiMode(),
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            false);
    }

    public DateTimeResponse getPlaceDateTime(String cityId)
    {
        return this.geoApi.getCityDateTimeUsingGET(cityId);
    }

    public DistanceResponse getPlaceDistance(GetPlaceDistanceRequest request)
    {
        return this.geoApi.getCityDistanceUsingGET(
            request.getToPlaceId(),
            request.getFromPlaceId(),
            request.getDistanceUnit().getTag());
    }

    public TimeResponse getPlaceTime(String cityId)
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

    private String toString(GeoDbEnum geoDbEnum)
    {
        return geoDbEnum.getTag();
    }
}
