package org.carcinus.tools.bean.response.dynamic;

import lombok.Getter;

public class DynamicDetailResponse {
    private int code;
    private String msg;
    private String message;

    private DynamicMetaData data;

    public DynamicCard getCard() {
        return data.getCard();
    }



    private class DynamicMetaData {
        @Getter
        private DynamicCard card;
    }
}
