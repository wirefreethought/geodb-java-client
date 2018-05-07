package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetCityDistanceRequest
{
    private Integer fromCityId;
    private Integer toCityId;
    private GeoDbDistanceUnit distanceUnit;
}
