package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCountriesRequest
{
    private Boolean asciiMode;
    private String currencyCode;
    private String languageCode;
    private Integer limit;
    private String namePrefix;
    private Boolean namePrefixDefaultLangResults;
    private Integer offset;
}
