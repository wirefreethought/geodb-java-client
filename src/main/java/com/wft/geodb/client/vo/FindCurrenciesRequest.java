package com.wft.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCurrenciesRequest
{
    private String countryCode;
    private Integer limit;
    private Integer offset;
}
