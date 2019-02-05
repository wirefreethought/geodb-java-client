package com.wirefreethought.geodb.client.request;

import java.util.Set;

import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesNearLocationRequest
{
    private Boolean asciiMode;
    private IncludeDeletedMode includeDeleted;
    private String languageCode;
    private Integer limit;
    private Integer minPopulation;
    private GeoDbLocationConstraint nearLocation;
    private Integer offset;
    private GeoDbSort sort;
    private Set<CityRequestType> types;
}
