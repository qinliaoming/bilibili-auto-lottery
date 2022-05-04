package org.carcinus.tools.bean.response.dynamic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DynamicCard {

    private DynamicDescribe desc;

    /**
     * 动态文本
     */
    private String card;
    @JsonProperty("extend_json")
    private String extendJson;
}
