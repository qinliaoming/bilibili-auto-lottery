package org.carcinus.tools.bean.response.lottery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NoticeLotteryData {
    @JsonProperty("business_id")
    private int businessId;

    @JsonProperty("lottery_id")
    private int lotteryId;
    /**
     * 开奖时间
     */
    @JsonProperty("lottery_time")
    private long lotteryTime;

    @JsonProperty("lottery_result")
    private LotteryResult lotteryResults;

    /**
     * 奖品描述
     */
    @JsonProperty("first_prize_cmt")
    private String firstPrizeCmt;

    @JsonProperty("second_prize_cmt")
    private String secondPrizeCmt;

    @JsonProperty("third_prize_cmt")
    private String thirdPrizeCmt;

    @JsonProperty("need_post")
    private int needPost;
}
