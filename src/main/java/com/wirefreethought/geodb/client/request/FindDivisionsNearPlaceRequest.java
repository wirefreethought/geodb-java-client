package com.wirefreethought.geodb.client.request;

import java.util.Set;

import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindDivisionsNearPlaceRequest
{
    private Boolean asciiMode;
    private Set<String> countryIds;
    private GeoDbDistanceUnit distanceUnit;
    private Set<String> excludedCountryIds;
    private IncludeDeletedMode includeDeleted;
    private String languageCode;
    private Integer limit;
    private Integer minPopulation;
    private Integer offset;
    private String placeId;
    private int radius;
    private GeoDbSort sort;
}
