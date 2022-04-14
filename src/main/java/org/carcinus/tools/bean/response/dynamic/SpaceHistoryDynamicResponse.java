package org.carcinus.tools.bean.response.dynamic;

import lombok.Getter;

import java.util.List;

public class SpaceHistoryDynamicResponse {
    private int code;
    private String msg;
    private String message;

    private DynamicMetaData data;


    public List<DynamicCard> getCards() {
        return this.data.getCards();
    }
    public DynamicMetaData getData() {
        return data;
    }
    public class DynamicMetaData {

        public List<DynamicCard> getCards() {
            return this.cards;
        }

        private List<DynamicCard> cards;
    }
}
