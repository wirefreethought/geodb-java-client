package com.wirefreethought.geodb.client.request;

import java.util.Set;

import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRegionDivisionsRequest
{
    private Boolean asciiMode;
    private String countryId;
    private IncludeDeletedMode includeDeleted;
    private String languageCode;
    private Integer limit;
    private Integer minPopulation;
    private Integer offset;
    private String regionCode;
    private GeoDbSort sort;
}
