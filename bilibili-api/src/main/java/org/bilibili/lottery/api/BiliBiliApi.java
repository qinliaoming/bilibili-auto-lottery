package org.bilibili.lottery.api;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class BiliBiliApi {

    private final static Logger logger = LoggerFactory.getLogger(BiliBiliApi.class);

    public static List<String> getLotteryDynamicIds(int uid) {

        try {
            return ArticleApi.getDynamicIdsInArticle(uid);
        } catch (Exception e) {
            logger.error("catch Exception: ", e);
        }
        return Collections.emptyList();
    }

}
