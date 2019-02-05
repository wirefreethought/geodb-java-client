package com.wirefreethought.geodb.client.model;

import lombok.Getter;

public enum GeoDbDistanceUnit implements GeoDbEnum
{
    KILOMETERS("km"),
    MILES("mi");

    @Getter
    private String tag;

    GeoDbDistanceUnit(String tag)
    {
        this.tag = tag;
    }
}