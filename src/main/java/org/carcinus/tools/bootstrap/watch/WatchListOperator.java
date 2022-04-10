package org.carcinus.tools.bootstrap.watch;

import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.util.List;
import java.util.Optional;

/**
 * 对关注列表的操作
 */
public class WatchListOperator {

    private final static String LOTTERY_WATCH_GROUPS_NAME = "lottery";
    private final String GET_FLOWING_GROUP_URL = "https://api.bilibili.com/x/relation/tags?jsonp=jsonp&callback=__jp3";

    private final String CREATE_FLOWING_GROUP_URL = "https://api.bilibili.com/x/relation/tag/create";

    public List<String> getWatchGroups(GlobalContext context) {
        try {
            String watchGroupStr = HttpUtils.doGet(GET_FLOWING_GROUP_URL, null);

            System.out.println(watchGroupStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean judgeInCloudLotteryWatchGroups(List<String> watchGroups) {
        Optional<String> lotteryWatchGroups = watchGroups.stream()
                .filter(LOTTERY_WATCH_GROUPS_NAME::equals)
                .findAny();
        return lotteryWatchGroups.isPresent();
    }

    public void createLotteryWatchGroups(GlobalContext context) {

    }
}
