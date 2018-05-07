package com.wirefreethought.geodb.client.request;

import java.util.List;

import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesRequest
{
    private List<String> countryCodes;
    private List<String> excludedCountryCodes;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer minPopulation;
    private String namePrefix;
    private GeoDbLocationConstraint nearLocation;
    private Integer offset;
    private GeoDbSort sort;
    private List<String> timeZoneIds;
}
