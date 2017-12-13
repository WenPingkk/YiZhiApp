package com.wenping.yizhi.yizhiapp.model.gankio.tabs;

import android.support.annotation.NonNull;


import com.wenping.yizhi.yizhiapp.api.GankioApi;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoWelfareContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoWelfareListBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class GankIoWelfareModel extends BaseModel implements GankIoWelfareContract
        .IGankIoWelfareModel {

    @NonNull
    public static GankIoWelfareModel newInstance() {
        return new GankIoWelfareModel();
    }

    @Override
    public Observable<Boolean> recordItemIsRead(String key) {
        //不记录
        return null;
    }

    @Override
    public Observable<GankIoWelfareListBean> getWelfareList(int pre_page, int page) {
        return RetrofitCreateHelper.createApi(GankioApi.class, GankioApi.HOST)
                .getGankIoWelfareList(pre_page, page).compose(RxHelper
                        .<GankIoWelfareListBean>rxSchedulerHelper());
    }
}
