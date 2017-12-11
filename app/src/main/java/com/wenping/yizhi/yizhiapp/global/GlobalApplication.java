package com.wenping.yizhi.yizhiapp.global;

import android.content.Context;
import android.os.Handler;

import com.mob.MobApplication;
import com.mob.tools.proguard.ProtectedMemberKeeper;
import com.wansir.lib.logger.BuildConfig;
import com.wansir.lib.logger.LogLevel;
import com.wansir.lib.logger.Logger;

/**
 * Created by YinZeTong on 2017/12/11.
 */

public class GlobalApplication extends MobApplication implements ProtectedMemberKeeper{

    private static final String LOG_TAG = "YZ_LOGGER";
    protected static Context sContext;
    protected static Handler sHandler;
    protected static int sMainThreadId;
    private static GlobalApplication sApp;

    public static synchronized GlobalApplication getApplication(){ return sApp;}

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sHandler = new Handler();
        //myTid什么意思呢
        sMainThreadId = android.os.Process.myTid();

        //这是什么意思呢
        Logger.init(LOG_TAG).logLevel(BuildConfig.IS_SHOW_LOG? LogLevel.FULL:LogLevel.NONE);
    }

    /**
     * 获取上下文对象
     * @return
     */
    public static Context getsContext(){
        return sContext;
    }

    /**
     * 获取主线程
     * @return
     */
    public static Handler getsHandler(){
        return sHandler;
    }

    /**
     * 获取主线程id
     * @return
     */
    public static int getsMainThread(){
        return sMainThreadId;
    }
}
