package com.wenping.yizhi.yizhiapp.presenter.movie;

import android.widget.ImageView;

import com.wenping.yizhi.yizhiapp.cache.Cache;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieMainContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.model.movie.MovieMainModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.MovieDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * Created by WenPing on 12/27/2017.
 *
 */

public class MovieMainPresenter extends MovieMainContract.MovieMainPresenter {


    public static MovieMainPresenter newInstance() {
        return new MovieMainPresenter();
    }

    @Override
    public MovieMainContract.IMovieMainModel getModel() {
        return MovieMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void loadHotMovieList() {
        if (mIModel != null && mIView != null) {
            mRxManager.register(mIModel.getHotMovieList().subscribe(new Consumer<HotMovieBean>() {
                @Override
                public void accept(HotMovieBean bean) throws Exception {
                    if (mIView != null) {
                        mIView.updateContentList(bean.getSubjects());
                        Cache.saveHotMovieCache(bean.getSubjects());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        if (mIView.isVisiable()) {
                            mIView.showToast("Network error.");
                        }

                        if (Cache.getHotMovieCache().size() == 0) {
                            mIView.showNetworkError();
                        } else {
                            mIView.updateContentList(Cache.getHotMovieCache());
                        }
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(int position, SubjectsBean item, ImageView imageView) {
        //跳转到activity
        MovieDetailActivity.start(mIView.getBindActivity(),item,imageView);
    }

    @Override
    public void onHeaderClick() {
        mIView.startNewFragment(TopMoiveFragment.newInstance());
    }
}
