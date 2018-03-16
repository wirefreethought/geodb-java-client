package com.wirefreethought.geodb.client.vo;

import lombok.Getter;

public enum GeoDbDistanceUnit
{
    KILOMETERS("km"), MILES("mi");

    @Getter
    private String tag;

    GeoDbDistanceUnit(String tag)
    {
        this.tag = tag;
    }
}