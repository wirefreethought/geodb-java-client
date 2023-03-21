package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbEnum;

import lombok.Getter;

public enum PlaceRequestType implements GeoDbEnum
{
    ADMIN_DIVISION_2("ADM2"),
    CITY("CITY"),
    ISLAND("ISLAND");

    @Getter
    private String tag;

    PlaceRequestType(String tag)
    {
        this.tag = tag;
    }
}
