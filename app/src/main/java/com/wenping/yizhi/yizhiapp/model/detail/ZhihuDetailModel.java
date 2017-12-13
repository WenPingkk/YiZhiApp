package com.wenping.yizhi.yizhiapp.model.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.ZhihuApi;
import com.wenping.yizhi.yizhiapp.contract.contract.detail.ZhihuDetailContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyDetailBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/9/13.
 * <p>
 */

public class ZhihuDetailModel extends BaseModel implements ZhihuDetailContract.IZhihuDetailModel {

    @NonNull
    public static ZhihuDetailModel newInstance() {
        return new ZhihuDetailModel();
    }

    @Override
    public Observable<ZhihuDailyDetailBean> getDailyDetail(String id) {
        return RetrofitCreateHelper.createApi(ZhihuApi.class, ZhihuApi.HOST).getZhihuDailyDetail
                (id).compose(RxHelper.<ZhihuDailyDetailBean>rxSchedulerHelper());
    }
}
