package com.wenping.yizhi.yizhiapp.model.home.tabs;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.WangyiApi;
import com.wenping.yizhi.yizhiapp.config.DBConfig;
import com.wenping.yizhi.yizhiapp.config.ItemState;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WangyiContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsListBean;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.DBUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/9/18.
 * <p>
 */

public class WangyiModel implements WangyiContract.IWangyiModel {
    @NonNull
    public static WangyiModel newInstance() {
        return new WangyiModel();
    }

    @Override
    public Observable<WangyiNewsListBean> getNewsList(int id) {
        return RetrofitCreateHelper.createApi(WangyiApi.class, WangyiApi.HOST).getNewsList(id)
                .compose(RxHelper.<WangyiNewsListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_WANGYI, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
