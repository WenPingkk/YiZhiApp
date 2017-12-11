package com.wenping.yizhi.yizhiapp.base.app;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.wenping.yizhi.yizhiapp.global.GlobalApplication;

/**
 * Created by YinZeTong on 2017/12/11.
 */

public class BaseApplication extends GlobalApplication {

    //聚合数据的KEY,具体指的是那个方面的key?
    public static final String JU_HE_APP_KEY = null;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    public static BaseApplication sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //初始化屏幕宽高
        getScreenSize();
    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenSize() {
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        DIMEN_RATE = metrics.density/1.0F;
        DIMEN_DPI = metrics.densityDpi;

        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;

        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
