package com.shatyuka.killergram;

import java.util.Arrays; 
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public final static String[] hookPackages = new String[] {
        "org.telegram.messenger",
        "tw.nekomimi.nekogram"
    };

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (Arrays.stream(hookPackages).anyMatch(lpparam.packageName::equals)) {
            XposedHelpers.findAndHookMethod("org.telegram.ui.ChatActivity", lpparam.classLoader, "addSponsoredMessages", boolean.class, XC_MethodReplacement.returnConstant(null));
        }
    }
}
