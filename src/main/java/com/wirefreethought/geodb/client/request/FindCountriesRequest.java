package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCountriesRequest
{
    private String currencyCode;
    private Integer limit;
    private String namePrefix;
    private Integer offset;
}
