package org.carcinus.tools.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String doGet(URI uri) throws Exception {
        HttpGet httpGet = new HttpGet(uri);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    public static String doPost(URI uri) throws Exception {

        HttpPost httpPost = new HttpPost(uri);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
    private static List<BasicNameValuePair> buildParams(Map<String, String> params) {
        ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<>();
        params.forEach((k, v) -> {
            nameValuePairs.add(new BasicNameValuePair(k, v));
        });
        return nameValuePairs;
    }

    public static String doPost(URI uri, Map<String, String> params) throws Exception {

        HttpPost httpPost = new HttpPost(uri);
        List<BasicNameValuePair> nameValuePairs = buildParams(params);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    public static String doPostJson(URI uri, String json) throws Exception {

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
        StringEntity entity = new StringEntity(json, StandardCharsets.UTF_8);
        entity.setContentType("application/json;charset=utf-8");
        httpPost.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    public static String doPostFile(URI uri, File file) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        FileBody bin = new FileBody(file);
        HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).build();
        httpPost.setEntity(reqEntity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }

    public static String doPostStream(URI uri, byte[] bytes) throws Exception {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        httpPost.setEntity(new ByteArrayEntity(bytes));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("请求失败...");
            }
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
    }
}
