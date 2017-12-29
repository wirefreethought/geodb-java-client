package com.wirefreethought.geodb.client.net;

import java.util.List;
import java.util.Map;

import com.squareup.okhttp.Call;
import com.wirefreethought.geodb.client.net.ApiClient;
import com.wirefreethought.geodb.client.net.ApiException;
import com.wirefreethought.geodb.client.net.Pair;
import com.wirefreethought.geodb.client.net.ProgressRequestBody.ProgressRequestListener;

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
        super();

        setBasePath(type.getInstanceUri());
    }

    @Override
    public Call buildCall(String path, String method, List<Pair> queryParams, List<Pair> collectionQueryParams, Object body, Map<String, String> headerParams,
        Map<String, Object> formParams, String[] authNames, ProgressRequestListener progressRequestListener) throws ApiException
    {
        return super.buildCall(path, method, queryParams, collectionQueryParams, body, headerParams, formParams, AUTH_NAMES, progressRequestListener);
    }
}
