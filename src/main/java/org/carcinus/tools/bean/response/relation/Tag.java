package org.carcinus.tools.bean.response.relation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tag {

    @JsonProperty("tagid")
    private int tagId;
    /**
     * tag name
     */
    private String name;
    /**
     * people num in this tag
     */
    private int count;
    /**
     * tag describe
     */
    private String tips;
}
