package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCountryRequest
{
    private Boolean asciiMode;
    private String countryId;
    private String languageCode;
}
