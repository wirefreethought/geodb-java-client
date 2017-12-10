package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NearLocationRequest
{
    private double latitude;
    private double longitude;
    private int radius;
    private DistanceUnit radiusUnit;
}
