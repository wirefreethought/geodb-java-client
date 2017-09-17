package com.wft.geodb.client;

import com.wft.geodb.client.net.ApiClient;
import com.wft.geodb.client.vo.CitiesResponse;
import com.wft.geodb.client.vo.CityResponse;
import com.wft.geodb.client.vo.CountriesResponse;
import com.wft.geodb.client.vo.CountryResponse;
import com.wft.geodb.client.vo.CurrenciesResponse;
import com.wft.geodb.client.vo.FindCitiesRequest;
import com.wft.geodb.client.vo.FindCountriesRequest;
import com.wft.geodb.client.vo.FindCurrenciesRequest;
import com.wft.geodb.client.vo.FindRegionCitiesRequest;
import com.wft.geodb.client.vo.FindRegionRequest;
import com.wft.geodb.client.vo.FindRegionsRequest;
import com.wft.geodb.client.vo.IncludeDeletedMode;
import com.wft.geodb.client.vo.LocalesResponse;
import com.wft.geodb.client.vo.LocationRadiusUnit;
import com.wft.geodb.client.vo.NearLocationRequest;
import com.wft.geodb.client.vo.RegionResponse;
import com.wft.geodb.client.vo.RegionsResponse;

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
            request.getCountryCode(),
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
