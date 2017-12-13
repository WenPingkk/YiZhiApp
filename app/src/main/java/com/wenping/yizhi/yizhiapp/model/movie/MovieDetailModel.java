package com.wenping.yizhi.yizhiapp.model.movie;

import android.support.annotation.NonNull;


import com.wenping.yizhi.yizhiapp.api.DoubanApi;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieDetailContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.MovieDetailBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.PersonBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Horrarndoo on 2017/10/18.
 * <p>
 */

public class MovieDetailModel extends BaseModel implements MovieDetailContract.IMovieDetailModel {
    @NonNull
    public static MovieDetailModel newInstance() {
        return new MovieDetailModel();
    }

    @Override
    public Observable<MovieDetailBean> getMovieDetail(String id) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getMovieDetail(id)
                .compose(RxHelper.<MovieDetailBean>rxSchedulerHelper())
                .map(new Function<MovieDetailBean, MovieDetailBean>() {
                    @Override
                    public MovieDetailBean apply(MovieDetailBean bean) throws Exception {
                        //返回的数据没有person type，根据数组类型指定
                        for (PersonBean bean1 : bean.getCasts()) {
                            bean1.setType("主演");
                        }
                        for (PersonBean bean2 : bean.getDirectors()) {
                            bean2.setType("导演");
                        }
                        return bean;
                    }
                });
    }
}
