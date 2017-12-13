package com.wenping.yizhi.yizhiapp.presenter.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.detail.GankIoDetailContract;
import com.wenping.yizhi.yizhiapp.model.detail.GankIoDetailModel;

//import com.zyw.horrarndoo.yizhi.contract.detail.GankIoDetailContract;
//import com.zyw.horrarndoo.yizhi.model.detail.GankIoDetailModel;

/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public class GankIoDetailPresenter extends GankIoDetailContract.GankIoDetailPresenter{
    @NonNull
    public static GankIoDetailPresenter newInstance() {
        return new GankIoDetailPresenter();
    }

    @Override
    public void loadGankIoDetail(String url) {
        if (mIView == null)
            return;

        try {
            mIView.showGankIoDetail(url);
        } catch (Exception e) {
            mIView.showNetworkError();
            e.printStackTrace();
        }
    }

    @Override
    public GankIoDetailContract.IGankIoDetailModel getModel() {
        return GankIoDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
