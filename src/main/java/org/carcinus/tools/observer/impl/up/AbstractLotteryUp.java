package org.carcinus.tools.observer.impl.up;

import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.observer.LotteryEventObservable;
import org.carcinus.tools.observer.LotteryEventObserver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class AbstractLotteryUp implements LotteryEventObservable {
    protected String uname;
    protected int uid;
    private List<LotteryEventObserver> observers;


    public abstract List<LotteryEvent> getLotteryEvents(GlobalContext context);

    public void checkDynamic(GlobalContext context) {
        List<LotteryEvent> events = getLotteryEvents(context);
        if (events != null) observers.forEach(observer -> observer.callback(events));
    }

    public AbstractLotteryUp(String name, int uid) {
        this.uname = name;
        this.uid = uid;
        observers = new ArrayList<>(3);
    }

    @Override
    public void addObserver(LotteryEventObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(LotteryEventObserver observer) {
        this.observers.remove(observer);
    }

    protected LotteryEvent getLotteryEvent(String dynamicId){



        return null;
    }
}
