package com.wenping.yizhi.yizhiapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wenping on 12/12/2017.
 */

public class MainActivity extends BaseCompatActivity {


    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.bviv_bar)
    BottomNavigationView mBvivBar;
    @BindView(R.id.nv_menu)
    NavigationView mNvMenu;
    @BindView(R.id.dl_root)
    DrawerLayout mDlRoot;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
