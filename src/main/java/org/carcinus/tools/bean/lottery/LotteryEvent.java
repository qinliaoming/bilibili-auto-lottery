package org.carcinus.tools.bean.lottery;

/**
 * 抽奖活动的基本单元
 */
public class LotteryEvent {
    private int uid;
    /**
     * 动态类型, 目前已知:
     * 1 -- 转发动态
     * 8 -- 投稿视频
     * 64 -- 专栏
     */
    private int type;
    private String dynamicId;
}
