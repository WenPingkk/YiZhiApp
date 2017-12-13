package com.wenping.yizhi.yizhiapp.model.movie;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.DoubanApi;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.TopMovieContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class TopMovieModel extends BaseModel implements TopMovieContract.ITopMovieModel {

    @NonNull
    public static TopMovieModel newInstance() {
        return new TopMovieModel();
    }

    @Override
    public Observable<HotMovieBean> getTopMovieList(int start, int count) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getMovieTop250
                (start, count).compose(RxHelper.<HotMovieBean>rxSchedulerHelper());
    }
}
