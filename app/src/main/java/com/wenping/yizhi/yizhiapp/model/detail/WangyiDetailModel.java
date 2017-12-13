package com.wenping.yizhi.yizhiapp.model.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.WangyiApi;
import com.wenping.yizhi.yizhiapp.contract.contract.detail.WangyiDetailContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Horrarndoo on 2017/9/19.
 * <p>
 */

public class WangyiDetailModel extends BaseModel implements WangyiDetailContract
        .IWangyiDetailModel {

    @NonNull
    public static WangyiDetailModel newInstance() {
        return new WangyiDetailModel();
    }

    @Override
    public Observable<ResponseBody> getNewsDetail(String id) {
        return RetrofitCreateHelper.createApi(WangyiApi.class, WangyiApi.HOST).getNewsDetail(id)
                .compose(RxHelper.<ResponseBody>rxSchedulerHelper());
    }
}
