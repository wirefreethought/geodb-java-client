package com.wirefreethought.geodb.client.vo;

import lombok.Getter;

public enum LocationRadiusUnit
{
    KILOMETERS("km"), MILES("mi");

    @Getter
    private String tag;

    LocationRadiusUnit(String tag)
    {
        this.tag = tag;
    }
}