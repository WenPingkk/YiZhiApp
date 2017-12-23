package com.wenping.yizhi.yizhiapp.presenter.gankio.tabs;


import android.os.Bundle;

import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.constant.RxBusCode;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoDayContract;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoDayItemBean;
import com.wenping.yizhi.yizhiapp.model.gankio.tabs.GankIoDayModel;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.ui.activity.pic.ImageBrowseActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by WenPing on 2017/12/23.
 * <p>
 */

public class GankIoDayPresenter extends GankIoDayContract.GankIoDayPresenter {
    private String mYear = "2016";
    private String mMonth = "11";
    private String mDay = "24";

    private int mAndroidPages = 0;
    private int mIOSPages = 0;
    private List<GankIoDayItemBean> mList = new ArrayList<>();

    public static GankIoDayPresenter newInstance() {
        return new GankIoDayPresenter();
    }

    @Override
    public void loadLatestList() {
        if (mIModel == null || mIView == null) {
            return;
        } else {
            mRxManager.register(mIModel.getGankIoDayList(mYear,mMonth,mDay)
            .subscribe(new Consumer<List<GankIoDayItemBean>>() {
                @Override
                public void accept(List<GankIoDayItemBean> beans) throws Exception {
                    if (mIView != null) {
                        mList = beans;
                        mIView.updateContentList(mList);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        if (mIView.isVisiable()) {
                            mIView.showToast("Network error");
                            mIView.showNetworkError();
                        }
                    }
                }
            }));
        }
    }

    @Override
    public void loadMoreList() {
        //每日数据没有更多
    }

    @Override
    public void onMoreClick(int position, GankIoDayItemBean item) {
        if (item.getType().equals("福利")) {//更多福利直接跳到福利界面
            RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_WELFARE_TYPE);
            RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_SELECT_TO_CHILD, 2);
        } else {//跳到custom界面
            RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_CUSTOM_TYPE, item.getType());
            RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_SELECT_TO_CHILD, 1);
        }
    }

    @Override
    public void onRefeshClick(int position, GankIoDayItemBean item) {
        if (mIModel != null && mIView != null) {
            if (item.getType().equals("Android")) {
                mAndroidPages++;
                mIView.itemNotifyChanged(position, mIModel.getGankIoDayAndroid(mAndroidPages % 6));
            } else {
                mIOSPages++;
                mIView.itemNotifyChanged(position,mIModel.getGankIoDayIOS(mIOSPages%3));
            }
        }
    }

    @Override
    public void onItemClick(int position, GankIoDayItemBean item) {
        Bundle bundle = new Bundle();
        if (item.getType().equals("福利")) {
            bundle.putString(BundleKeyConstant.ARG_KEY_IMAGE_BROWSE_URL, item.getUrl());
            mIView.startNewActivity(ImageBrowseActivity.class, bundle);
        } else {
            bundle.putString(BundleKeyConstant.ARG_KEY_GANKIO_DETAIL_URL, item.getUrl());
            bundle.putString(BundleKeyConstant.ARG_KEY_GANKIO_DETAIL_TITLE, item.getDesc());
//            mIView.startNewActivity(GankIoDetailActivity.class, bundle);
        }
    }

    /**
     * 主要逻辑是在gankIoDayModel中进行的网络请求
     *
     * @return
     */
    @Override
    public GankIoDayContract.IGankIoDayModel getModel() {
        return GankIoDayModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
