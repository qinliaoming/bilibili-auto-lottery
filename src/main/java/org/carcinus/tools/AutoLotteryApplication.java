package org.carcinus.tools;

import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.observer.LotteryEventObservable;
import org.carcinus.tools.observer.impl.LotteryEventSubscribe;
import org.carcinus.tools.observer.impl.up.LotteryUpJLDCJ;

import java.util.ArrayList;
import java.util.List;

public class AutoLotteryApplication {
    private GlobalContext context;
    private List<LotteryEventObservable> observables;
    private final long SLEEP_TIME = 43200000;
    public AutoLotteryApplication(GlobalContext context) {
        this.context = context;
        observables = new ArrayList<>();
    }

    public void start() throws InterruptedException {
        init();
        while (true) {
            observables.forEach(lotteryEventObservable -> {
                lotteryEventObservable.update(context);
            });
            Thread.sleep(SLEEP_TIME);
        }
    }

    private void init() {
        if (observables != null) {
            LotteryEventSubscribe lotteryEventSubscribe = new LotteryEventSubscribe();
            LotteryUpJLDCJ lotteryUp = new LotteryUpJLDCJ();
            lotteryUp.addObserver(lotteryEventSubscribe);
            observables.add(lotteryUp);
        }
    }
}
