package com.wenping.yizhi.yizhiapp.model.movie;

import android.support.annotation.NonNull;


import com.wenping.yizhi.yizhiapp.api.DoubanApi;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieMainContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */

public class MovieMainModel extends BaseModel implements MovieMainContract.IMovieMainModel {

    @NonNull
    public static MovieMainModel newInstance() {
        return new MovieMainModel();
    }

    @Override
    public Observable<HotMovieBean> getHotMovieList() {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getHotMovie()
                .compose(RxHelper.<HotMovieBean>rxSchedulerHelper());
    }
}
