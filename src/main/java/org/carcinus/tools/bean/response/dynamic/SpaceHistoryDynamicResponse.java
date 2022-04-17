package org.carcinus.tools.bean.response.dynamic;

import lombok.Getter;
import org.carcinus.tools.bean.response.Response;

import java.util.List;

public class SpaceHistoryDynamicResponse extends Response {

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
