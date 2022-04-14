package org.carcinus.tools.bean.response.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
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
        @JsonProperty("tagid")
        private int tagId;
    }

    public int getTagId() {
        return this.data.tagId;
    }
}
