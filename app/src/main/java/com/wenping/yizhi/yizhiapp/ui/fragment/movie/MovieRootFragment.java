package com.wenping.yizhi.yizhiapp.ui.fragment.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;

/**
 * Created by YinZeTong on 2017/12/12.
 */

public class MovieRootFragment extends BaseCompatFragment{

    public static MovieRootFragment newInstance() {
        Bundle bundle = new Bundle();
        MovieRootFragment movieRootFragment = new MovieRootFragment();
        movieRootFragment.setArguments(bundle);
        return movieRootFragment;
    }
    @Override
    public int getLayoutId() {

        return R.layout.fragment_movie;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    //懒加载
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //加载fragment
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, MovieRootFragment.newInstance());
        } else {
            //这里可能出现该Fragment没被初始化时,就被强杀导致的没有load子Fragment
            if (findChildFragment(MovieRootFragment.class) == null) {
                loadRootFragment(R.id.fl_container,MovieRootFragment.newInstance());
            }
        }
    }
}
