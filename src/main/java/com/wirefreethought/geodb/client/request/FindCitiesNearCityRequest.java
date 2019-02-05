package com.wirefreethought.geodb.client.request;

import java.util.Set;

import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesNearCityRequest
{
    private Boolean asciiMode;
    private String cityId;
    private GeoDbDistanceUnit distanceUnit;
    private IncludeDeletedMode includeDeleted;
    private String languageCode;
    private Integer limit;
    private Integer minPopulation;
    private Integer offset;
    private int radius;
    private GeoDbSort sort;
    private Set<CityRequestType> types;
}
