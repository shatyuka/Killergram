package com.shatyuka.killergram;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedClass {

    private final Class<?> aClass;

    public XposedClass(String className, ClassLoader loader) {
        aClass = XposedHelpers.findClassIfExists(className, loader);
    }

    public void hookAllMethods(String methodName, XC_MethodHook callback) {
        try {
            if (aClass == null) return;
            XposedBridge.hookAllMethods(aClass, methodName, callback);
        }
        catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}
