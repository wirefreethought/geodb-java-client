package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetCityDistanceRequest
{
    private Integer fromCityId;
    private Integer toCityId;
    private DistanceUnit distanceUnit;
}
