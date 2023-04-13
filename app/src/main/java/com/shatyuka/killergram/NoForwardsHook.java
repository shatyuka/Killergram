package com.shatyuka.killergram;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class NoForwardsHook extends XC_MethodHook {

    boolean forConstructor;

    public NoForwardsHook(boolean forConstructor) {
        super();
        this.forConstructor = forConstructor;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) {
        try {
            Object obj = this.forConstructor ? param.thisObject : param.getResult();
            XposedHelpers.setBooleanField(obj, "noforwards", false);
        }
        catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}
