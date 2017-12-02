package com.wirefreethought.geodb.client.vo;

import java.util.List;

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
    private NearLocationRequest nearLocation;
    private Integer offset;
    private List<String> timeZoneIds;
}
