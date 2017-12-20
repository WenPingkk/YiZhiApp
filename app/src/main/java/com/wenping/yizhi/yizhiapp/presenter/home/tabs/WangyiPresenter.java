package com.wenping.yizhi.yizhiapp.presenter.home.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WangyiContract;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsItemBean;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsListBean;
import com.wenping.yizhi.yizhiapp.model.home.tabs.WangyiModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.WangyiDailyDetailActivity;
import com.wenping.yizhi.yizhiapp.utils.StringUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by WenPing on 2017/12/18.
 * <p>
 */

public class WangyiPresenter extends WangyiContract.WangyiPresenter {
    private int mCurrentIndex;
    private boolean isLoading;


    public static WangyiPresenter newInstance() {
        return new WangyiPresenter();
    }

    @Override
    public WangyiContract.IWangyiModel getModel() {
        return WangyiModel.newInstance();
    }

    /**
     * 在model中进行网络请求获取的数据；
     * 典型的MVP,Presenter操作model来获取数据，传给view层；
     */
    @Override
    public void loadLatestList() {
        mCurrentIndex = 0;
        mRxManager.register(mIModel.getNewsList(mCurrentIndex).subscribe(new Consumer<WangyiNewsListBean>() {
            @Override
            public void accept(WangyiNewsListBean bean) throws Exception {
                if (mIView != null) {
                    /*
                    获取数据
                     */
                    List<WangyiNewsItemBean> list = bean.getNewsList();
                    for (int i = 0; i < list.size(); i++) {
                        if (StringUtils.isEmpty(list.get(i).getUrl())) {
                            //移除、过滤没有效的url【新闻】
                            list.remove(i);
                        }
                    }
                    mCurrentIndex += 20;
                    //这就是在WangyiFragment中重写的！把当前数据显示到页面上
                    mIView.updateContentList(list);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable()) {
                        mIView.showToast("Network error.");
                        mIView.showNetworkError();
                    }
                }
            }
        }));
    }

    @Override
    public void loadMoreList() {
        if (!isLoading) {
            isLoading = true;
            mRxManager.register(mIModel.getNewsList(mCurrentIndex).subscribe(new Consumer<WangyiNewsListBean>() {
                @Override
                public void accept(WangyiNewsListBean bean) throws Exception {
                    isLoading = false;
                    if (mIView == null)
                        return;
                    if (bean.getNewsList().size() > 0) {
                        mCurrentIndex += 20;
                        mIView.updateContentList(bean.getNewsList());
                    } else {
                        //如果没有数据
                        mIView.showNoMoreData();
                    }

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading = false;
                    if (mIView == null) {
                        return;
                    } else {
                        mIView.showLoadMoreError();
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(final int position, WangyiNewsItemBean item) {
        mRxManager.register(mIModel.recordItemIsRead(item.getDocid()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (mIView != null) {
                    if (aBoolean) {
                        mIView.itemNotifyChanged(position);
                    } else {
                        Logger.e("写入点击状态失败了");
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //抛出异常
                throwable.printStackTrace();
            }
        }));

        if (mIView != null) {
            Bundle bundle = new Bundle();
            bundle.putString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_ID, item.getDocid());
            bundle.putString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_URL, item.getUrl());
            bundle.putString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_TITLE, item.getTitle());
            bundle.putString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_IMAGE_URL, item.getImgsrc());
            bundle.putString(BundleKeyConstant.ARG_KEY_WANGYI_DETAIL_COPYRIGHT, item.getSource());
            mIView.startNewActivity(WangyiDailyDetailActivity.class, bundle);
        }
    }

    @Override
    public void onStart() {

    }
}
