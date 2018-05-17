package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCurrenciesRequest
{
    private String countryId;
    private Integer limit;
    private Integer offset;
}
