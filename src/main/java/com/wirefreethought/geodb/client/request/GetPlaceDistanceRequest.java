package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbDistanceUnit;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetPlaceDistanceRequest
{
    private GeoDbDistanceUnit distanceUnit;
    private String fromPlaceId;
    private String toPlaceId;
}
