package com.wirefreethought.geodb.client.model;

public interface PlaceSortFields
{
    public interface Common
    {
        String ELEVATION = "elevation";
        String NAME = "name";
        String POPULATION = "population";
    }

    public interface FindCountryPlaces extends Common
    {
    }

    public interface FindPlaces extends Common
    {
        String COUNTRY_CODE = "countryCode";
    }

    public interface FindRegionPlaces extends Common
    {
    }
}
