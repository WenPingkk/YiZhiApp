package com.wenping.yizhi.yizhiapp.ui.fragment.home.base;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.home.HomeMainContract;
import com.wenping.yizhi.yizhiapp.model.home.HomeMainModel;


/**
 * Created by Horrarndoo on 2017/9/11.
 * <p>
 */

public class HomeMainPresenter extends HomeMainContract.HomeMainPresenter {

    @NonNull
    public static HomeMainPresenter newInstance() {
        return new HomeMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public HomeMainContract.IHomeMainModel getModel() {
        return HomeMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
