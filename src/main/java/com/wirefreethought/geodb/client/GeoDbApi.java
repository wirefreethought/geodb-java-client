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
import com.wirefreethought.geodb.client.request.FindAdminDivisionsRequest;
import com.wirefreethought.geodb.client.request.FindCountriesRequest;
import com.wirefreethought.geodb.client.request.FindCountryRequest;
import com.wirefreethought.geodb.client.request.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.request.FindDivisionsNearPlaceRequest;
import com.wirefreethought.geodb.client.request.GetPlaceRequest;
import com.wirefreethought.geodb.client.request.FindPlacesNearLocationRequest;
import com.wirefreethought.geodb.client.request.FindPlacesNearPlaceRequest;
import com.wirefreethought.geodb.client.request.FindPlacesRequest;
import com.wirefreethought.geodb.client.request.FindRegionDivisionsRequest;
import com.wirefreethought.geodb.client.request.FindRegionPlacesRequest;
import com.wirefreethought.geodb.client.request.FindRegionRequest;
import com.wirefreethought.geodb.client.request.FindRegionsRequest;
import com.wirefreethought.geodb.client.request.GetPlaceDistanceRequest;
import com.wirefreethought.geodb.client.request.PlaceRequestType;

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
                .map(f -> toString(f))
                .collect(Collectors.joining(","));
        }

        return sortString;
    }

    private static String toString(GeoDbSort.SortField field)
    {
        return String.format("%s%s", field.isReverse() ? "-" : "+", field.getName());
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
            location,
            locationRadius,
            distanceUnit,
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public PopulatedPlacesResponse findAdminDivisions(FindDivisionsNearPlaceRequest request)
    {
        return geoApi.findCitiesNearCityUsingGET(
            request.getPlaceId(),
            request.getRadius(),
            toString(request.getDistanceUnit()),
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            toString(PlaceRequestType.ADMIN_DIVISION_2),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public CountriesResponse findAllCountries(boolean asciiMode, String languageCode, Integer limit, Integer offset, GeoDbSort.SortField sort)
    {
        return geoApi.getCountriesUsingGET(null, null, null, asciiMode, false, languageCode, limit, offset, toString(sort));
    }

    public CurrenciesResponse findAllCurrencies(Integer limit, Integer offset)
    {
        return localeApi.getCurrenciesUsingGET(null, false, limit, offset);
    }

    public LanguagesResponse findAllLanguages(Integer limit, Integer offset)
    {
        return localeApi.getLanguagesUsingGET(false, limit, offset);
    }

    public LocalesResponse findAllLocales(Integer limit, Integer offset)
    {
        return localeApi.getLocalesUsingGET(false, limit, offset);
    }

    public TimeZonesResponse findAllTimezones(Integer limit, Integer offset)
    {
        return localeApi.getTimezonesUsingGET(false, limit, offset);
    }

    public CountriesResponse findCountries(FindCountriesRequest request)
    {
        return geoApi.getCountriesUsingGET(
            request.getCurrencyCode(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            request.getSort() != null
                ? toString(request.getSort())
                : null);
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
            false,
            request.getLimit(),
            request.getOffset());
    }

    public PopulatedPlacesResponse findPlaces(FindPlacesNearLocationRequest request)
    {
        return geoApi.findPlacesNearLocationUsingGET(
            toLocationId(request.getNearLocation()),
            request.getNearLocation().getRadius(),
            toString(request.getNearLocation().getDistanceUnit()),
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public PopulatedPlacesResponse findPlaces(FindPlacesNearPlaceRequest request)
    {
        return geoApi.findPlacesNearPlaceUsingGET(
            request.getPlaceId(),
            request.getRadius(),
            toString(request.getDistanceUnit()),
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
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

        return geoApi.findPlacesUsingGET(
            location,
            locationRadius,
            distanceUnit,
            toStringFromStringSet(request.getCountryIds()),
            toStringFromStringSet(request.getExcludedCountryIds()),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public RegionResponse findRegion(FindRegionRequest request)
    {
        return this.geoApi.getRegionUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public PopulatedPlacesResponse findRegionDivisions(FindRegionDivisionsRequest request)
    {
        return geoApi.findRegionDivisionsUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()),
            toString(request.getIncludeDeleted()));
    }

    public PopulatedPlacesResponse findRegionPlaces(FindRegionPlacesRequest request)
    {
        return geoApi.findRegionPlacesUsingGET(
            request.getCountryId(),
            request.getRegionCode(),
            request.getMinPopulation(),
            request.getMaxPopulation(),
            request.getNamePrefix(),
            request.getNamePrefixDefaultLangResults(),
            toStringFromStringSet(request.getTimeZoneIds()),
            toStringFromEnumSet(request.getTypes()),
            request.getAsciiMode(),
            false,
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
            request.getNamePrefixDefaultLangResults(),
            request.getAsciiMode(),
            false,
            request.getLanguageCode(),
            request.getLimit(),
            request.getOffset(),
            request.getSort() != null
                ? toString(request.getSort())
                : null);
    }

    public PopulatedPlaceResponse getPlace(GetPlaceRequest request)
    {
        return this.geoApi.getPlaceUsingGET(
            request.getPlaceId(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public PopulatedPlaceResponse getPlaceAdminRegion(GetPlaceRequest request)
    {
        return this.geoApi.getPlaceLocatedInUsingGET(
            request.getPlaceId(),
            request.getAsciiMode(),
            request.getLanguageCode());
    }

    public DateTimeResponse getPlaceDateTime(String cityId)
    {
        return this.geoApi.getPlaceDateTimeUsingGET(cityId);
    }

    public DistanceResponse getPlaceDistance(GetPlaceDistanceRequest request)
    {
        return this.geoApi.getPlaceDistanceUsingGET(
            request.getToPlaceId(),
            request.getFromPlaceId(),
            request.getDistanceUnit().getTag());
    }

    public TimeResponse getPlaceTime(String cityId)
    {
        return this.geoApi.getPlaceTimeUsingGET(cityId);
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
