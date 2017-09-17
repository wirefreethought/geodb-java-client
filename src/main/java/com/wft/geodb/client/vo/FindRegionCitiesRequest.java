package com.wft.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRegionCitiesRequest
{
    private String countryCode;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer minPopulation;
    private Integer offset;
    private String regionCode;
}
