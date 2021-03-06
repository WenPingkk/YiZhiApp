package com.wenping.yizhi.yizhiapp.global;

import android.content.Context;
import android.os.Handler;

import com.mob.MobApplication;
import com.mob.tools.proguard.ProtectedMemberKeeper;
import com.wansir.lib.logger.BuildConfig;
import com.wansir.lib.logger.LogLevel;
import com.wansir.lib.logger.Logger;

/**
 * Created by WenPing on 2017/12/11.
 * MobApplication导入了MobComons的jar包后引入的
 */

public class GlobalApplication extends MobApplication implements ProtectedMemberKeeper{

    private static final String LOG_TAG = "YZ_LOGGER";
    protected static Context context;
    protected static Handler handler;
    protected static int mainThreadId;
    private static GlobalApplication sApp;

    //同步锁,但是没用到这个方法
    public static synchronized GlobalApplication getApplication(){ return sApp;}

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        //myTid什么意思呢
        mainThreadId = android.os.Process.myTid();

        //初始化Looger,并且根据BuildConfigField的字段来判断是否打印log
        Logger.init(LOG_TAG).logLevel(BuildConfig.IS_SHOW_LOG? LogLevel.FULL:LogLevel.NONE);
    }

    /**
     * 获取上下文对象
     * @return
     */
    public static Context getContext(){
        return context;
    }

    /**
     * 获取主线程
     * @return
     */
    public static Handler getHandler(){
        return handler;
    }

    /**
     * 获取主线程id
     * @return
     */
    public static int getMainThreadId(){
        return mainThreadId;
    }
}
