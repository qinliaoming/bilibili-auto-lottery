package org.carcinus.tools.bean.lottery;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * 抽奖活动的基本单元
 */
@Data
@ToString
public class LotteryEvent implements Comparable<LotteryEvent> {
    private int lotteryId;
    private int uid;
    private long dynamicId;
    private long lotteryTime;

    private String firstPrize;
    private String secondPrize;
    private String thirdPrize;

    public boolean isOpen() {
        return isOpen(System.currentTimeMillis()/ 1000);
    }

    public boolean isOpen(long currentTimeMillis) {
        return this.lotteryTime < currentTimeMillis;
    }

    @Override
    public int compareTo(LotteryEvent event) {
        return (int) (this.lotteryTime - event.lotteryTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryEvent event = (LotteryEvent) o;
        return lotteryId == event.lotteryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lotteryId);
    }
}
