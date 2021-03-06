package com.wenping.yizhi.yizhiapp.ui.fragment.gankio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.GankIoFragment;

/**
 * Created by WenPing on 2017/12/12.
 * <p>
 */

public class GankIoRootFragment extends BaseCompatFragment{

    public static GankIoRootFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoRootFragment gankIoRootFragment = new GankIoRootFragment();
        gankIoRootFragment.setArguments(bundle);
        return gankIoRootFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(GankIoFragment.class) == null) {
            loadRootFragment(R.id.fl_container,GankIoFragment.newInstance());
        }
    }
}
