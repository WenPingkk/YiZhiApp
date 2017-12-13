package com.wenping.yizhi.yizhiapp.presenter.gankio;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.gankio.GankIoMainContract;
import com.wenping.yizhi.yizhiapp.model.gankio.GankIoMainModel;


/**
 * Created by Horrarndoo on 2017/10/7.
 * <p>
 */

public class GankIoMainPresenter extends GankIoMainContract.GankIoMainPresenter{

    @NonNull
    public static GankIoMainPresenter newInstance() {
        return new GankIoMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public GankIoMainContract.IGankIoMainModel getModel() {
        return GankIoMainModel.newInstance();
    }

    @Override
    public void onStart() {
        //getTabList();
    }
}
