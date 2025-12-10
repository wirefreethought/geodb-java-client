package com.wirefreethought.geodb.client.model;

import lombok.Getter;

@Getter
public enum GeoDbInstanceType
{
    FREE("https://geodb-free-service.wirefreethought.com/v1"),
    PRO("https://wft-geo-db.p.rapidapi.com/v1");

    private String instanceUri;

    GeoDbInstanceType(String uri)
    {
        this.instanceUri = uri;
    }
}
