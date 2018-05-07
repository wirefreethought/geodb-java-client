package com.wirefreethought.geodb.client.model;

public interface CitySortFields
{
    public static interface Common
    {
        String ELEVATION = "elevation";
        String NAME = "name";
        String POPULATION = "population";
    }

    public static interface FindCities extends Common
    {
        String COUNTRY_CODE = "countryCode";
    }

    public static interface FindRegionCities extends Common
    {
    }
}
