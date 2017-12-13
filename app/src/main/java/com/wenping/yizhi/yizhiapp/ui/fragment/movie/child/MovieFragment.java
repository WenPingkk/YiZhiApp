package com.wenping.yizhi.yizhiapp.ui.fragment.movie.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieMainContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

/**
 * Created by YinZeTong on 2017/12/13.
 */

public class MovieFragment extends BaseRecycleFragment<MovieMainContract.MovieMainPresenter,
        MovieMainContract.IMovieMainModel> implements MovieMainContract.IMovieMainView {


    public static MovieFragment newInstance() {
        Bundle args = new Bundle();
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    protected void onErrorViewClick(View view) {

    }

    @Override
    protected void showLoading() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_hot;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
