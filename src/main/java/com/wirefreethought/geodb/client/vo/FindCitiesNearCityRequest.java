package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesNearCityRequest
{
    private Integer cityId;
    private GeoDbDistanceUnit distanceUnit;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer minPopulation;
    private Integer offset;
    private int radius;
    private GeoDbSort sort;
}
