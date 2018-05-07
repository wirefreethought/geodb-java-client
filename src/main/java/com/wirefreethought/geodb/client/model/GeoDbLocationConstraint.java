package com.wirefreethought.geodb.client.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeoDbLocationConstraint
{
    private double latitude;
    private double longitude;
    private int radius;
    private GeoDbDistanceUnit distanceUnit;
}
