package org.carcinus.tools.bean.response.relation;

import lombok.Data;
import org.carcinus.tools.bean.response.Response;

@Data
public class RelationTagsResponse extends Response {

    /**
     * tags
     */
    private Tag[] data;
}
