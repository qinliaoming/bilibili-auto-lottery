package org.carcinus.tools.bean.lottery;

import lombok.Data;

import java.util.Objects;

/**
 * 抽奖活动的基本单元
 */
@Data
public class LotteryEvent implements Comparable<LotteryEvent> {
    private int lotteryId;
    private int uid;
    private int dynamicId;
    private long lotteryTime;

    private String firstPrize;
    private String secondPrize;
    private String thirdPrize;

    public boolean isOpen() {
        return isOpen(System.currentTimeMillis());
    }

    public boolean isOpen(long currentTimeMillis) {
        return this.lotteryTime < currentTimeMillis;
    }

    @Override
    public int compareTo(LotteryEvent event) {
        return (int) (event.lotteryTime - this.lotteryTime);
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
