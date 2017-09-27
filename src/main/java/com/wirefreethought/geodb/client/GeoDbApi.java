package com.wirefreethought.geodb.client;

import org.apache.commons.lang3.StringUtils;

import com.wirefreethought.geodb.client.GeoApi;
import com.wirefreethought.geodb.client.LocaleApi;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.vo.CitiesResponse;
import com.wirefreethought.geodb.client.vo.CityResponse;
import com.wirefreethought.geodb.client.vo.CountriesResponse;
import com.wirefreethought.geodb.client.vo.CountryResponse;
import com.wirefreethought.geodb.client.vo.CurrenciesResponse;
import com.wirefreethought.geodb.client.vo.FindCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindCountriesRequest;
import com.wirefreethought.geodb.client.vo.FindCurrenciesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionCitiesRequest;
import com.wirefreethought.geodb.client.vo.FindRegionRequest;
import com.wirefreethought.geodb.client.vo.FindRegionsRequest;
import com.wirefreethought.geodb.client.vo.IncludeDeletedMode;
import com.wirefreethought.geodb.client.vo.LocalesResponse;
import com.wirefreethought.geodb.client.vo.LocationRadiusUnit;
import com.wirefreethought.geodb.client.vo.NearLocationRequest;
import com.wirefreethought.geodb.client.vo.RegionResponse;
import com.wirefreethought.geodb.client.vo.RegionsResponse;

public class GeoDbApi
{
    private final static IncludeDeletedMode DEFAULT_INCLUDE_DELETED_MODE = IncludeDeletedMode.NONE;
    private final static LocationRadiusUnit DEFAULT_RADIUS_UNIT = LocationRadiusUnit.MILES;

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

    public CitiesResponse findCities(FindCitiesRequest request)
    {
        String countryCodes = request.getCountryCodes() != null && !request.getCountryCodes().isEmpty()
            ? StringUtils.join(request.getCountryCodes(), ", ")
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
            request.getMinPopulation(),
            nearLocation,
            nearLocationRadius,
            nearLocationRadiusUnit,
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

    public CurrenciesResponse findCurrencies(FindCurrenciesRequest request)
    {
        return localeApi.getCurrenciesUsingGET(
            request.getCountryCode(),
            request.getLimit(),
            request.getOffset());
    }

    public CountryResponse findCountryByCode(String code)
    {
        return this.geoApi.getCountryUsingGET(code);
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

    private String toString(LocationRadiusUnit radiusUnit)
    {
        if (radiusUnit == null)
        {
            radiusUnit = DEFAULT_RADIUS_UNIT;
        }

        return radiusUnit.getTag();
    }
}
