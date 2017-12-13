package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.GankIoMainContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;

/**
 * Created by YinZeTong on 2017/12/13.
 * <p>
 */

public class GankIoFragment extends BaseMVPCompatFragment<GankIoMainContract.GankIoMainPresenter,
        GankIoMainContract.IGankIoMainModel> implements GankIoMainContract.IGankIoMainView {

    public static GankIoFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoFragment fragment = new GankIoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void showTabList(String[] tabs) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
