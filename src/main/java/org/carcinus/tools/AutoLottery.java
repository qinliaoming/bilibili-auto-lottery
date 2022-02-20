package org.carcinus.tools;

import org.carcinus.tools.bootstrap.BootStrapApplication;
import org.carcinus.tools.context.GlobalContext;


public class AutoLottery {


    public static void main(String[] args) {

        GlobalContext globalContext = GlobalContext.newBuilder()
                .getOrCreate();

        if (BootStrapApplication.bootstrap(globalContext)) {

        }else {

        }
    }
}
