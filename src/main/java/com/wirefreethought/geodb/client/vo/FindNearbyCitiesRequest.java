package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindNearbyCitiesRequest
{
    private Integer cityId;
    private IncludeDeletedMode includeDeleted;
    private Integer limit;
    private Integer minPopulation;
    private int nearLocationRadius;
    private LocationRadiusUnit nearLocationRadiusUnit;
    private Integer offset;
}
