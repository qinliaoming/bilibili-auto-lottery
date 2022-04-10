package org.carcinus.tools.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static HttpClient httpClient;
    static {
        httpClient = HttpClientBuilder
                .create()
                .build();
        httpClient = HttpClients.createDefault();
    }

    private static boolean judgeStatus(HttpResponse response) {
        return !(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK);
    }

    public static String doGet(String uri, List<Header> headers) throws Exception {
        HttpGet httpGet = new HttpGet(uri);
        if (headers != null) headers.forEach(httpGet::addHeader);
        HttpResponse response = httpClient.execute(httpGet);
        if (judgeStatus(response)) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }


    private static List<BasicNameValuePair> buildParams(Map<String, String> params) {
        ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<>(params.size());
        params.forEach((k, v) -> {
            nameValuePairs.add(new BasicNameValuePair(k, v));
        });
        return nameValuePairs;
    }

    public static HttpResponse doPostResponse( URI uri, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        List<BasicNameValuePair> nameValuePairs = buildParams(params);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

}
