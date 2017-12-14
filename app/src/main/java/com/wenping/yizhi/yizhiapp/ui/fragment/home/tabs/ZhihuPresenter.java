package com.wenping.yizhi.yizhiapp.ui.fragment.home.tabs;

import android.os.Bundle;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.ZhihuContract;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyItemBean;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyListBean;
import com.wenping.yizhi.yizhiapp.model.home.tabs.ZhihuModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.ZhihuDailyDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by WenPing on 2017/12/14.
 *
 */

public class ZhihuPresenter extends ZhihuContract.ZhihuPresenter {

    /**
     * 日报日期
     */
    private String mDate;

    public static ZhihuPresenter newInstance() {
        return new ZhihuPresenter();
    }

    @Override
    public ZhihuContract.IZhihuModel getModel() {
        return ZhihuModel.newInstance();
    }

    @Override
    public void onStart() {
        //null
    }

    @Override
    public void loadLatestList() {
        if (mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.getDailyList().subscribe(new Consumer<ZhihuDailyListBean>() {
            @Override
            public void accept(ZhihuDailyListBean bean) throws Exception {
                if (mIView != null) {
                    mIView.updateContentList(bean.getStories());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    if (mIView.isVisiable()) {
                        mIView.showToast("Network Error");
                    }
                    mIView.showNetworkError();
                }
            }
        }));
    }

    @Override
    public void loadMoreList() {
        if (mIModel == null) {
            return;
        }
        mRxManager.register(mIModel.getDailyList(mDate).subscribe(new Consumer<ZhihuDailyListBean>() {
            @Override
            public void accept(ZhihuDailyListBean bean) throws Exception {
                if (mDate.equals(bean.getDate())) {
                    return;
                }
                mDate = bean.getDate();
                if (mIView != null) {
                    mIView.updateContentList(bean.getStories());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    mIView.showLoadMoreError();
                }
            }
        }));
    }

    @Override
    public void onItemClick(final int position, ZhihuDailyItemBean item) {
        mRxManager.register(mIModel.recordItemIsRead(item.getId()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (mIView == null) {
                    return;
                }
                if (aBoolean) {
                    mIView.itemNotifyChanged(position);
                } else {
                    Logger.e("写入点击状态值失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));
        if (mIView == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_ZHIHU_DETAIL_ID,item.getId());
        bundle.putString(BundleKeyConstant.ARG_KEY_ZHIHU_DETAIL_TITLE,item.getTitle());
        mIView.startNewActivity(ZhihuDailyDetailActivity.class,bundle);
    }
}
