package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRegionRequest
{
    private String countryCode;
    private String regionCode;
}
