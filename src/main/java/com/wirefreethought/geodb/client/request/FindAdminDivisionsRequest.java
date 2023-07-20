package com.wirefreethought.geodb.client.request;

import java.util.Set;

import com.wirefreethought.geodb.client.model.GeoDbLocationConstraint;
import com.wirefreethought.geodb.client.model.GeoDbSort;
import com.wirefreethought.geodb.client.model.IncludeDeletedMode;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindAdminDivisionsRequest
{
    private Boolean asciiMode;
    private Set<String> countryIds;
    private Set<String> excludedCountryIds;
    private IncludeDeletedMode includeDeleted;
    private String languageCode;
    private Integer limit;
    private Integer minPopulation;
    private Integer maxPopulation;
    private String namePrefix;
    private Boolean namePrefixDefaultLangResults;
    private GeoDbLocationConstraint nearLocation;
    private Integer offset;
    private GeoDbSort sort;
    private Set<String> timeZoneIds;
}
