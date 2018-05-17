package com.wirefreethought.geodb.client.model;

public interface CitySortFields
{
    public interface Common
    {
        String ELEVATION = "elevation";
        String NAME = "name";
        String POPULATION = "population";
    }

    public interface FindCities extends Common
    {
        String COUNTRY_CODE = "countryCode";
    }

    public interface FindRegionCities extends Common
    {
    }
}
