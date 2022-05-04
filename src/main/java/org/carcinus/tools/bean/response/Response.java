package org.carcinus.tools.bean.response;

import lombok.Data;

@Data
public class Response {
    protected int code;
    protected String message;
    protected long ttl;
    protected boolean status;

    protected long ts;

}
