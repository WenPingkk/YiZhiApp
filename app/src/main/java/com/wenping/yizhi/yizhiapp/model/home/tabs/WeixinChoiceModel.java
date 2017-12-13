package com.wenping.yizhi.yizhiapp.model.home.tabs;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.WeixinApi;
import com.wenping.yizhi.yizhiapp.config.DBConfig;
import com.wenping.yizhi.yizhiapp.config.ItemState;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WeixinContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.weixin.WeixinChoiceListBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.DBUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinChoiceModel extends BaseModel implements WeixinContract.IWeixinModel {

    @NonNull
    public static WeixinChoiceModel newInstance() {
        return new WeixinChoiceModel();
    }

    @Override
    public Observable<Boolean> recordItemIsRead(final String key) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                emitter.onNext(DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig
                        .TABLE_WEIXIN, key, ItemState.STATE_IS_READ));
                emitter.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }

    @Override
    public Observable<WeixinChoiceListBean> getWeixinChoiceList(int page, int pageStrip, String
            dttype, String key) {
        return RetrofitCreateHelper.createApi(WeixinApi.class, WeixinApi.HOST).getWeixinChoiceList
                (page, pageStrip, dttype, key).compose(RxHelper
                .<WeixinChoiceListBean>rxSchedulerHelper());
    }
}
