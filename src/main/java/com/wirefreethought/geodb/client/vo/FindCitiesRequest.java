package com.wirefreethought.geodb.client.vo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesRequest
{
    private String namePrefix;
    private List<String> countryCodes;
    private List<String> excludedCountryCodes;
    private Integer minPopulation;
    private NearLocationRequest nearLocation;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer offset;
}
