package com.wirefreethought.geodb.client.net;

import java.util.List;
import java.util.Map;

import com.wirefreethought.geodb.client.model.GeoDbInstanceType;

import okhttp3.Call;

/**
 * Use this client in place of the swagger-codegen-generated ApiClient. It implements a workaround for an authorization issue in that class.
 *
 * @author mmogley
 */
public class GeoDbApiClient extends ApiClient
{
    private static final String[] AUTH_NAMES = new String[] {
        "UserSecurity"
    };

    public GeoDbApiClient(GeoDbInstanceType type)
    {
        this(type.getInstanceUri());
    }

    public GeoDbApiClient(String instanceUri)
    {

        super();

        setBasePath(instanceUri);
    }

    @Override
    public Call buildCall(String baseUrl, String path, String method, List<Pair> queryParams, List<Pair> collectionQueryParams, Object body, Map<String, String> headerParams,
        Map<String, String> cookieParams, Map<String, Object> formParams, String[] authNames, ApiCallback callback) throws ApiException
    {
        return super.buildCall(baseUrl, path, method, queryParams, collectionQueryParams, body, headerParams, cookieParams, formParams, AUTH_NAMES, callback);
    }
}
