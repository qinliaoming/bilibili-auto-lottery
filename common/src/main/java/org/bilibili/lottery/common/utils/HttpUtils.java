package org.bilibili.lottery.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static HttpClient httpClient;
    static {
        httpClient = HttpClientBuilder
                .create()
                .build();
    }

    public static boolean judgeStatus(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    public static String getEntity(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }
    public static String doGetEntity(String uri) throws IOException {
        HttpResponse response = doGet(uri);
        assert response != null;
        return getEntity(response);
    }
    public static HttpResponse doGet(String uri) throws IOException {
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);
        if (judgeStatus(response)) {
            return response;
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
    public static String doPostEntity(String uri, Map<String, String> params) throws Exception {
        HttpResponse response = doPost(uri, params);
        if(judgeStatus(response)){
            return getEntity(response);
        }
        return null;
    }


    public static HttpResponse doPost(String uri, Map<String, String> params) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        List<BasicNameValuePair> nameValuePairs = buildParams(params);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        return response;
    }

}
