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
    private int code;
    /**
     * unknown
     */
    private boolean status;
    /**
     * timestamp
     */
    private long ts;

//    /**
//     * message body
//     */
//    private Object data;
}
