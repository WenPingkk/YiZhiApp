package com.wenping.yizhi.yizhiapp.ui.fragment.home.model;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.contract.HomeMainContract;

/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 * 主页model
 */

public class HomeMainModel extends BaseModel implements HomeMainContract.IHomeMainModel {

    @NonNull
    public static HomeMainModel newInstance() {
        return new HomeMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"知乎日报", "热点新闻", "微信精选"};
    }
}
