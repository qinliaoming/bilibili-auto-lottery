package org.carcinus.tools.bean.response.dynamic;

import lombok.Getter;
import org.carcinus.tools.bean.response.Response;

public class DynamicDetailResponse extends Response {


    private DynamicMetaData data;

    public DynamicCard getCard() {
        return data.getCard();
    }


    public static class DynamicMetaData {
        @Getter
        private DynamicCard card;
    }
}
