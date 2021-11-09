package com.shatyuka.killergram;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public final static String hookPackage = "org.telegram.messenger";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (hookPackage.equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod("org.telegram.ui.ChatActivity", lpparam.classLoader, "addSponsoredMessages", boolean.class, XC_MethodReplacement.returnConstant(null));
        }
    }
}
