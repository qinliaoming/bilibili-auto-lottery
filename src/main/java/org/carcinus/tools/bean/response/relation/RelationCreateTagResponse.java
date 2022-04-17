package org.carcinus.tools.bean.response.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.carcinus.tools.bean.response.Response;

@Data
public class RelationCreateTagResponse extends Response {

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
