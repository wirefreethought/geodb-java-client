package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbEnum;

import lombok.Getter;

public enum CityRequestType implements GeoDbEnum
{
    ADMIN_DIVISION_2("ADM2"),
    CITY("CITY");

    @Getter
    private String tag;

    CityRequestType(String tag)
    {
        this.tag = tag;
    }
}
