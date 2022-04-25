package org.carcinus.tools.bean.response.lottery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LotteryResult {
    /**
     * 一等奖结果
     */
    @JsonProperty("first_prize_result")
    private List<LotteryResultPerson> firstPrizeResults;

    /**
     * 二等奖结果
     */
    @JsonProperty("second_prize_result")
    private List<LotteryResultPerson> secondPrizeResults;

    /**
     * 三等奖结果
     */
    @JsonProperty("third_prize_result")
    private List<LotteryResultPerson> thirdPrizeResults;
}
