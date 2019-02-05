package com.wirefreethought.geodb.client.model;

import lombok.Getter;

public enum IncludeDeletedMode implements GeoDbEnum
{
    ALL("all"),
    NONE("none"),
    SINCE_LAST_WEEK("since_last_week"),
    SINCE_YESTERDAY("since_yesterday");

    @Getter
    private String tag;

    IncludeDeletedMode(String tag) {
        this.tag = tag;
    }
}
