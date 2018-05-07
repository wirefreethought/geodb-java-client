package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesNearLocationRequest
{
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer minPopulation;
    private String namePrefix;
    private GeoDbLocationConstraint nearLocation;
    private Integer offset;
    private GeoDbSort sort;
}
