package org.carcinus.tools.bean.response.relation;

import lombok.Data;

@Data
public class RelationCreateTagResponse {
    private int code;
    private String message;
    private long ttl;
    /**
     * tags
     */
    private TagData data;

    private class TagData {

        private int tagid;
    }

    public int getTagId() {
        return this.data.tagid;
    }
}
