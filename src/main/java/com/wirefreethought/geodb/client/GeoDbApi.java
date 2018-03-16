package com.wirefreethought.geodb.client;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.vo.CitiesResponse;
import com.wirefreethought.geodb.client.vo.CityResponse;
import com.wirefreethought.geodb.client.vo.CountriesResponse;
import com.wirefreethought.geodb.client.vo.CountryResponse;
import com.wirefreethought.geodb.client.vo.CurrenciesResponse;
import com.wirefreethought.geodb.client.vo.DateTimeResponse;
import com.wirefreethought.geodb.client.vo.DistanceResponse;
import com.wirefreethought.geodb.client.vo.FindCitiesNearCityRequest;
import com.wirefreethought.geodb.client.vo.FindCitiesNearLocationRequest;
import com.wirefreethought.geodb.client.vo.FindCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindCountriesRequest;
import com.wirefreethought.geodb.client.vo.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionRequest;
import com.wirefreethought.geodb.client.vo.FindRegionsRequest;
import com.wirefreethought.geodb.client.vo.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.vo.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.vo.GeoDbSort;
import com.wirefreethought.geodb.client.vo.GetCityDistanceRequest;
import com.wirefreethought.geodb.client.vo.IncludeDeletedMode;
import com.wirefreethought.geodb.client.vo.LocalesResponse;
import com.wirefreethought.geodb.client.vo.RegionResponse;
import com.wirefreethought.geodb.client.vo.RegionsResponse;
import com.wirefreethought.geodb.client.vo.TimeResponse;
import com.wirefreethought.geodb.client.vo.TimeZonesResponse;

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

    public CountriesResponse findAllCountries(Integer limit, Integer offset)
    {
        return geoApi.getCountriesUsingGET(null, null, limit, offset);
    }

    public CurrenciesResponse findAllCurrencies(Integer limit, Integer offset)
    {
        return localeApi.getCurrenciesUsingGET(null, limit, offset);
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
            toString(request.getIncludeDeleted()),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()));
    }

    public CitiesResponse findCities(FindCitiesNearLocationRequest request)
    {
        return geoApi.findCitiesNearLocationUsingGET(
            toLocationId(request.getNearLocation()),
            request.getMinPopulation(),
            request.getNamePrefix(),
            request.getNearLocation().getRadius(),
            toString(request.getNearLocation().getDistanceUnit()),
            toString(request.getIncludeDeleted()),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()));
    }

    public CitiesResponse findCities(FindCitiesRequest request)
    {
        String countryCodes = request.getCountryCodes() != null && !request.getCountryCodes().isEmpty()
            ? StringUtils.join(request.getCountryCodes(), ", ")
            : null;

        String excludedCountryCode = request.getExcludedCountryCodes() != null && !request.getExcludedCountryCodes().isEmpty()
            ? StringUtils.join(request.getExcludedCountryCodes(), ", ")
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
            countryCodes,
            excludedCountryCode,
            request.getMinPopulation(),
            location,
            locationRadius,
            distanceUnit,
            timeZoneIds,
            toString(request.getIncludeDeleted()),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()));
    }

    public CityResponse findCityById(Integer id)
    {
        return this.geoApi.getCityUsingGET(id);
    }

    public CountriesResponse findCountries(FindCountriesRequest request)
    {
        return geoApi.getCountriesUsingGET(
            request.getNamePrefix(),
            request.getCurrencyCode(),
            request.getLimit(),
            request.getOffset());
    }

    public CountryResponse findCountryByCode(String code)
    {
        return this.geoApi.getCountryUsingGET(code);
    }

    public CurrenciesResponse findCurrencies(FindCurrenciesRequest request)
    {
        return localeApi.getCurrenciesUsingGET(
            request.getCountryCode(),
            request.getLimit(),
            request.getOffset());
    }

    public RegionResponse findRegion(FindRegionRequest request)
    {
        return this.geoApi.getRegionUsingGET(request.getCountryCode(), request.getRegionCode());
    }

    public CitiesResponse findRegionCities(FindRegionCitiesRequest request)
    {
        return geoApi.findRegionCitiesUsingGET(
            request.getCountryCode(),
            request.getRegionCode(),
            request.getMinPopulation(),
            toString(request.getIncludeDeleted()),
            request.getLimit(),
            request.getOffset(),
            toString(request.getSort()));
    }

    public RegionsResponse findRegions(FindRegionsRequest request)
    {
        return geoApi.getRegionsUsingGET(
            request.getCountryCode(),
            request.getNamePrefix(),
            request.getLimit(),
            request.getOffset());
    }

    public DateTimeResponse getCityDateTime(Integer cityId)
    {
        return this.geoApi.getCityDateTimeUsingGET(cityId);
    }

    public DistanceResponse getCityDistance(GetCityDistanceRequest request)
    {
        return this.geoApi.getCityDistanceUsingGET(request.getToCityId(), request.getFromCityId(), request.getDistanceUnit().getTag());
    }

    public TimeResponse getCityTime(Integer cityId)
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
