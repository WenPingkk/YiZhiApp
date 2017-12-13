package com.wenping.yizhi.yizhiapp.presenter.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.detail.WangyiDetailContract;
import com.wenping.yizhi.yizhiapp.helper.json.JsonHelper;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsDetailBean;
import com.wenping.yizhi.yizhiapp.model.detail.WangyiDetailModel;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public class WangyiDetailPresenter extends WangyiDetailContract.WangyiDetailPresenter {
    @NonNull
    public static WangyiDetailPresenter newInstance() {
        return new WangyiDetailPresenter();
    }

    @Override
    public void loadNewsDetailWithUrl(String url) {
        if (mIView == null)
            return;

        try {
            mIView.showNewsDetail(url);
        } catch (Exception e) {
            mIView.showNetworkError();
            e.printStackTrace();
        }
    }

    @Override
    public void loadNewsDetailWithId(final String id) {
        mRxManager.register(mIModel.getNewsDetail(id).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                if (mIView == null)
                    return;
                //新闻的Json数据比较特殊，返回的json key不固定，需要手动的获取json数据，然后再用gson解析
                WangyiNewsDetailBean bean = JsonHelper.getNewsDetailBeans(responseBody.string(),
                        id);
                mIView.showNewsDetail(bean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView == null)
                    return;
                mIView.showNetworkError();
            }
        }));
    }

    @Override
    public WangyiDetailContract.IWangyiDetailModel getModel() {
        return WangyiDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
