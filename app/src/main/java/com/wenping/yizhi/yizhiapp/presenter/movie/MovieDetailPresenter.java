package com.wenping.yizhi.yizhiapp.presenter.movie;

import android.os.Bundle;

import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieDetailContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.MovieDetailBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.PersonBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.model.movie.MovieDetailModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.WebViewLoadActivity;

import io.reactivex.functions.Consumer;

/**
 *
 * @author WenPing
 * @date 12/28/2017
 */

public class MovieDetailPresenter extends MovieDetailContract.MovieDetailPresenter{


    public static MovieDetailPresenter newInstance() {
        return new MovieDetailPresenter();
    }

    @Override
    public MovieDetailContract.IMovieDetailModel getModel() {
        return MovieDetailModel.newInstance();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void loadMovieDetail(String id) {
        if (mIView != null && mIModel != null) {
            mRxManager.register(mIModel.getMovieDetail(id).subscribe(new Consumer<MovieDetailBean>() {
                @Override
                public void accept(MovieDetailBean bean) throws Exception {
                    if (mIView != null) {
                        mIView.showMovieDetail(bean);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        mIView.showToast("Network Error");
                        mIView.showNetworkError();
                    }
                }
            }));
        }
    }

    @Override
    public void onItemClick(int position, PersonBean item) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE, item.getName());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL, item.getAlt());
        mIView.startNewActivity(WebViewLoadActivity.class, bundle);
    }

    @Override
    public void onHeaderClick(SubjectsBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE, bean.getTitle());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL, bean.getAlt());
        mIView.startNewActivity(WebViewLoadActivity.class, bundle);
    }
}
