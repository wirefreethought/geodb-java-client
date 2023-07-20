package com.wirefreethought.geodb.client.request;

import com.wirefreethought.geodb.client.model.GeoDbSort;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindRegionsRequest
{
    private Boolean asciiMode;
    private String countryId;
    private String languageCode;
    private Integer limit;
    private String namePrefix;
    private Boolean namePrefixDefaultLangResults;
    private Integer offset;
    private GeoDbSort.SortField sort;
}
