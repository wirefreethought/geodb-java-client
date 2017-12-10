package com.wirefreethought.geodb.client.vo;

import lombok.Getter;

public enum DistanceUnit
{
    KILOMETERS("km"), MILES("mi");

    @Getter
    private String tag;

    DistanceUnit(String tag)
    {
        this.tag = tag;
    }
}