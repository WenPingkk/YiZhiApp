package com.wenping.yizhi.yizhiapp.model.home.tabs;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.ZhihuApi;
import com.wenping.yizhi.yizhiapp.config.DBConfig;
import com.wenping.yizhi.yizhiapp.config.ItemState;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.ZhihuContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyListBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.DBUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */

public class ZhihuModel extends BaseModel implements ZhihuContract.IZhihuModel {

    @NonNull
    public static ZhihuModel newInstance() {
        return new ZhihuModel();
    }

    @Override
    public Observable<ZhihuDailyListBean> getDailyList(String date) {
        return RetrofitCreateHelper.createApi(ZhihuApi.class, ZhihuApi.HOST).getDailyListWithDate
                (date).compose(RxHelper.<ZhihuDailyListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<ZhihuDailyListBean> getDailyList() {
        return RetrofitCreateHelper.createApi(ZhihuApi.class, ZhihuApi.HOST).getLastDailyList()
                .compose(RxHelper.<ZhihuDailyListBean>rxSchedulerHelper());
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_ZHIHU, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
