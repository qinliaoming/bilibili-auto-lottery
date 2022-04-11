package org.carcinus.tools.bean.response.login;


public class LoginUrlResponse {

    protected int code;

    protected boolean status;

    protected long ts;

    public int getCode() {
        return code;
    }

    public boolean getStatus() {
        return status;
    }

    public long getTs() {
        return ts;
    }

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