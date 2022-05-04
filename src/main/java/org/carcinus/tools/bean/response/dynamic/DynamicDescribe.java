package org.carcinus.tools.bean.response.dynamic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DynamicDescribe {
    private int uid;
    @JsonProperty("dynamic_id")
    public long dynamicId;

    @JsonProperty("dynamic_id_str")
    private String dynamicIdStr;

}
