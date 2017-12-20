package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.constant.TabFragmentIndex;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.GankIoMainContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.fragmenttabs.GankIoDayFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WenPing on 2017/12/13.
 * <p>
 */

public class GankIoFragment extends BaseMVPCompatFragment<GankIoMainContract.GankIoMainPresenter,
        GankIoMainContract.IGankIoMainModel> implements GankIoMainContract.IGankIoMainView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tl_tabs)
    TabLayout mTlTabs;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    @BindView(R.id.fab_classify)
    FloatingActionButton mFabClassify;
    @BindView(R.id.gank_io_container)
    CoordinatorLayout mGankIoContainer;

    private List<Fragment> fragments;

    public static GankIoFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoFragment fragment = new GankIoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        // TODO: 2017/12/20 RxBus
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
// TODO: 2017/12/20 RxBus,unregister
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void showTabList(String[] tabs) {
        Logger.w(Arrays.toString(tabs));
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.addTab(mTlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_GANK_DAY_INDEX:
                    fragments.add(GankIoDayFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_GANK_CUSTOM_INDEX:
                    fragments.add(GankIoDayFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_GANK_WELFARE_INDEX:
                    fragments.add(GankIoDayFragment.newInstance());
                    break;

                    default:

                        break;
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }

}
