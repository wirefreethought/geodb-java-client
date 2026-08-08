package com.wirefreethought.geodb.client.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class GeoDbSort
{
    @AllArgsConstructor
    @Getter
    public static class SortField
    {
        private String name;
        private boolean reverse;
    }

    @Builder
    public GeoDbSort(SortField... fields)
    {
        this.fields = List.of(fields);
    }

    @Getter
    private List<SortField> fields;
}
