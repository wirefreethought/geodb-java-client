package com.wirefreethought.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRegionRequest
{
    private String countryCode;
    private String regionCode;
}
