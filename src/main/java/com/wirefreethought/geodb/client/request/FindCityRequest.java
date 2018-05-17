package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCityRequest
{
    private Boolean asciiMode;
    private String cityId;
    private String languageCode;
}
