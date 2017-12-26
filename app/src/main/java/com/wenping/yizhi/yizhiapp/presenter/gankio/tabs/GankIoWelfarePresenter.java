package com.wenping.yizhi.yizhiapp.presenter.gankio.tabs;

import android.os.Bundle;

import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoWelfareContract;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoWelfareItemBean;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoWelfareListBean;
import com.wenping.yizhi.yizhiapp.model.gankio.tabs.GankIoWelfareModel;
import com.wenping.yizhi.yizhiapp.ui.activity.pic.ImageBrowseActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by WenPing on 12/26/2017.
 *
 */

public class GankIoWelfarePresenter extends GankIoWelfareContract.GankIoWelfarePresenter{

    private int mCurrentPage;
    private boolean isLoading;

    public static GankIoWelfarePresenter newInstance() {
        return new GankIoWelfarePresenter();
    }

    @Override
    public GankIoWelfareContract.IGankIoWelfareModel getModel() {
        return GankIoWelfareModel.newInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void loadLatestList() {
        if (mIModel != null && mIView != null) {
            mCurrentPage = 1;
            mRxManager.register(mIModel.getWelfareList(10,mCurrentPage).subscribe(new Consumer<GankIoWelfareListBean>() {
                @Override
                public void accept(GankIoWelfareListBean bean) throws Exception {
                    if (mIView != null) {
                        if (bean.isError()) {
                            mIView.showNetworkError();
                        } else {
                            mCurrentPage++;
                            mIView.updateContentList(bean.getResults());
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        if (mIView.isVisiable()) {
                            mIView.showToast("Network error");
                        } else {
                            mIView.showNetworkError();
                        }
                    }
                }
            }));
        }
    }

    @Override
    public void loadMoreList() {
        if (!isLoading) {
            isLoading = true;

            //一次加载20条数据
            mRxManager.register(mIModel.getWelfareList(10,mCurrentPage).subscribe(new Consumer<GankIoWelfareListBean>() {
                @Override
                public void accept(GankIoWelfareListBean bean) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        if (bean.isError()) {
                            //访问错误则显示网络异常
                            mIView.showNetworkError();
                        } else {
                            if (bean.getResults().size() > 0) {
                                //获取了数据
                                mCurrentPage++;
                                //接口的抽象方法，实现过程由fragment实现,
                                mIView.updateContentList(bean.getResults());
                            } else {
                                //如果没有更多数据了则显示没有更多数据！具体方法有fragment实现
                                mIView.showNoMoreData();
                            }
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading =false;
                    if (mIView != null) {
                        //请求异常则显示对应的error页面
                        mIView.showLoadMoreError();
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(int position, GankIoWelfareItemBean item) {
        //item的点击效果
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_IMAGE_BROWSE_URL, item.getUrl());
        mIView.startNewActivity(ImageBrowseActivity.class,bundle);
    }

}

































