package com.shatyuka.killergram;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {

    public final static List<String> hookPackages = Arrays.asList(
            "org.telegram.messenger",
            "org.telegram.messenger.web",
            "org.telegram.messenger.beta",
            "nekox.messenger",
            "org.forkclient.messenger",
            "org.forkclient.messenger.beta",
            "com.exteragram.messenger",
            "com.exteragram.messenger.beta",
            "org.telegram.mdgram",
            "org.telegram.mdgramyou",
            "org.telegram.BifToGram",
            "ua.itaysonlab.messenger",
            "org.nift4.catox",
            "top.qwq2333.nullgram",
            "com.cool2645.nekolite",
            "me.ninjagram.messenger",
            "org.ninjagram.messenger",
            "ir.ilmili.telegraph",
            "org.telegram.plus",
            "com.iMe.android",
            "org.aka.messenger",
            "ellipi.messenger"
    );

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) {

        if (!hookPackages.contains(lpparam.packageName)) return;

        XposedClass messagesController = new XposedClass("org.telegram.messenger.MessagesController", lpparam.classLoader);
        messagesController.hookAllMethods("getSponsoredMessages", XC_MethodReplacement.returnConstant(null));
        messagesController.hookAllMethods("isChatNoForwards", XC_MethodReplacement.returnConstant(false));

        XposedClass chatUIActivity = new XposedClass("org.telegram.ui.ChatActivity", lpparam.classLoader);
        chatUIActivity.hookAllMethods("addSponsoredMessages", XC_MethodReplacement.returnConstant(null));

        XposedClass sponsoredMessages = new XposedClass("org.telegram.tgnet.TLRPC$messages_SponsoredMessages", lpparam.classLoader);
        sponsoredMessages.hookAllMethods("TLdeserialize", XC_MethodReplacement.returnConstant(null));

        XposedClass getSponsoredMessages = new XposedClass("org.telegram.tgnet.TLRPC$TL_channels_getSponsoredMessages", lpparam.classLoader);
        getSponsoredMessages.hookAllMethods("a", XC_MethodReplacement.returnConstant(null));

        XposedClass chat = new XposedClass("org.telegram.tgnet.TLRPC$Chat", lpparam.classLoader);
        //chat.hookAllConstructors(new NoForwardsHook(true));
        chat.hookAllMethods("TLdeserialize", new NoForwardsHook(false));

        /*
        XposedClass message = new XposedClass("org.telegram.tgnet.TLRPC$Message", lpparam.classLoader);
        //message.hookAllConstructors(new NoForwardsHook(true));
        message.hookAllMethods("TLdeserialize", new NoForwardsHook(false));
        */

        XposedClass sharedConfig = new XposedClass("org.telegram.messenger.SharedConfig", lpparam.classLoader);
        sharedConfig.hookAllMethods("getDevicePerformanceClass", XC_MethodReplacement.returnConstant(2));

        XposedClass userConfig = new XposedClass("org.telegram.messenger.UserConfig", lpparam.classLoader);
        userConfig.hookAllMethods("getMaxAccountCount", XC_MethodReplacement.returnConstant(999));
        userConfig.hookAllMethods("hasPremiumOnAccounts", XC_MethodReplacement.returnConstant(true));
    }
}
