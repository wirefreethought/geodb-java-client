package com.wirefreethought.geodb.client;

import org.apache.commons.lang3.StringUtils;

import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.vo.CitiesResponse;
import com.wirefreethought.geodb.client.vo.CityResponse;
import com.wirefreethought.geodb.client.vo.CountriesResponse;
import com.wirefreethought.geodb.client.vo.CountryResponse;
import com.wirefreethought.geodb.client.vo.CurrenciesResponse;
import com.wirefreethought.geodb.client.vo.DateTimeResponse;
import com.wirefreethought.geodb.client.vo.DistanceResponse;
import com.wirefreethought.geodb.client.vo.FindCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindCountriesRequest;
import com.wirefreethought.geodb.client.vo.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.vo.FindNearbyCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionRequest;
import com.wirefreethought.geodb.client.vo.FindRegionsRequest;
import com.wirefreethought.geodb.client.vo.GetCityDistanceRequest;
import com.wirefreethought.geodb.client.vo.IncludeDeletedMode;
import com.wirefreethought.geodb.client.vo.LocalesResponse;
import com.wirefreethought.geodb.client.vo.DistanceUnit;
import com.wirefreethought.geodb.client.vo.NearLocationRequest;
import com.wirefreethought.geodb.client.vo.RegionResponse;
import com.wirefreethought.geodb.client.vo.RegionsResponse;
import com.wirefreethought.geodb.client.vo.TimeResponse;
import com.wirefreethought.geodb.client.vo.TimeZonesResponse;

public class GeoDbApi
{
    private final static IncludeDeletedMode DEFAULT_INCLUDE_DELETED_MODE = IncludeDeletedMode.NONE;
    private final static DistanceUnit DEFAULT_RADIUS_UNIT = DistanceUnit.MILES;

    private GeoApi geoApi;
    private LocaleApi localeApi;

    public GeoDbApi(ApiClient client)
    {
        this.geoApi = new GeoApi(client);
        this.localeApi = new LocaleApi(client);
    }

    public CountriesResponse findAllCountries(Integer limit, Integer offset)
    {
        return geoApi.getCountriesUsingGET(null, limit, offset);
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

        String nearLocation = null;
        Integer nearLocationRadius = null;
        String nearLocationRadiusUnit = null;

        if (request.getNearLocation() != null)
        {
            NearLocationRequest nearLocationRequest = request.getNearLocation();

            nearLocation = toLocationString(nearLocationRequest);
            nearLocationRadius = nearLocationRequest.getRadius();
            nearLocationRadiusUnit = toString(nearLocationRequest.getRadiusUnit());
        }

        return geoApi.findCitiesUsingGET(
            request.getNamePrefix(),
            countryCodes,
            excludedCountryCode,
            request.getMinPopulation(),
            nearLocation,
            nearLocationRadius,
            nearLocationRadiusUnit,
            timeZoneIds,
            toString(request.getIncludeDeleted()),
            request.getLimit(),
            request.getOffset());
    }

    public CityResponse findCityById(Integer id)
    {
        return this.geoApi.getCityUsingGET(id);
    }

    public CountriesResponse findCountries(FindCountriesRequest request)
    {
        return geoApi.getCountriesUsingGET(
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

    public CitiesResponse findNearbyCities(FindNearbyCitiesRequest request)
    {
        return geoApi.findNearbyCitiesUsingGET(
            request.getCityId(),
            request.getMinPopulation(),
            request.getNearLocationRadius(),
            toString(request.getNearLocationRadiusUnit()),
            toString(request.getIncludeDeleted()),
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
            request.getOffset());
    }

    public RegionsResponse findRegions(FindRegionsRequest request)
    {
        return geoApi.getRegionsUsingGET(
            request.getCountryCode(),
            request.getLimit(),
            request.getOffset());
    }

    public DateTimeResponse getCityDateTime(Integer cityId)
    {
        return this.geoApi.getCityDateTimeUsingGET(cityId);
    }

    public DistanceResponse getCityDistance(GetCityDistanceRequest request)
    {
        return this.geoApi.getCityDistanceUsingGET(request.getFromCityId(), request.getToCityId(), request.getDistanceUnit().getTag());
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

    private String toLocationString(NearLocationRequest nearLocationRequest)
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

    private String toString(IncludeDeletedMode mode)
    {
        if (mode == null)
        {
            mode = DEFAULT_INCLUDE_DELETED_MODE;
        }

        return mode.getTag();
    }

    private String toString(DistanceUnit radiusUnit)
    {
        if (radiusUnit == null)
        {
            radiusUnit = DEFAULT_RADIUS_UNIT;
        }

        return radiusUnit.getTag();
    }
}
