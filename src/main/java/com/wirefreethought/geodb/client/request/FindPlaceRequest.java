package com.wirefreethought.geodb.client.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindPlaceRequest
{
    private Boolean asciiMode;
    private String languageCode;
    private String placeId;
}
