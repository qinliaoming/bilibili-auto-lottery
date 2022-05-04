package org.carcinus.tools.bean.response.login;


import org.carcinus.tools.bean.response.Response;

public class LoginUrlResponse extends Response {


    private LoginUrlData data;

    public LoginUrlData getData() {
        return data;
    }

    public static class LoginUrlData {

        private String url;

        private String oauthKey;

        public String getUrl() {
            return url;
        }

        public String getOauthKey() {
            return oauthKey;
        }
    }
}