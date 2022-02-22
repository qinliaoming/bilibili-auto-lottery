package org.carcinus.tools.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    /**
     * status code
     * 0: success
     */
    protected int code;
    /**
     * unknown
     */
    protected boolean status;
    /**
     * timestamp
     */
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
//    /**
//     * message body
//     */
//    private Object data;
}
