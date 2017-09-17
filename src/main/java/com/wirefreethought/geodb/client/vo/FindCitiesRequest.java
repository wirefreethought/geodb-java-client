package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCitiesRequest
{
    private String namePrefix;
    private String countryCode;
    private Integer minPopulation;
    private NearLocationRequest nearLocation;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer offset;
}
