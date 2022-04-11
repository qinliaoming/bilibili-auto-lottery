package org.carcinus.tools.bean.response.relation;

import lombok.Data;

@Data
public class RelationTagsResponse {
    private int code;
    private String message;
    private long ttl;
    /**
     * tags
     */
    private Tag[] data;
}
