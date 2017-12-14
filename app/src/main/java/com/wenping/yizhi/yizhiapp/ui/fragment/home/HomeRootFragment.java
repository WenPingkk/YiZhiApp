package com.wenping.yizhi.yizhiapp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.HomeFragment;

/**
 * Created by WenPing on 2017/12/12.
 */

public class HomeRootFragment extends BaseCompatFragment{

    public static HomeRootFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeRootFragment fragment = new HomeRootFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.fl_container,HomeFragment.newInstance());
        }
    }
}
