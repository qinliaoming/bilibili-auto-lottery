package org.carcinus.tools.observer.impl.up;


public class LotteryUp {
    protected String uname;
    protected int uid;

    public LotteryUp(String uname, int uid) {
        this.uname = uname;
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public int getUid() {
        return uid;
    }
}
