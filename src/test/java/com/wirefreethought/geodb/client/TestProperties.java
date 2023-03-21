package com.wirefreethought.geodb.client;

public class TestProperties
{
    public static String getApiKey()
    {
        return System.getenv("RAPID_API_KEY");
    }
}
