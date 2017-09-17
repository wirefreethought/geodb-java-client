package com.wft.geodb.client.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindCountriesRequest
{
    private String currencyCode;
    private Integer limit;
    private Integer offset;
}
