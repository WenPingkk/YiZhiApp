package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child;

import android.content.Context;
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
import com.wenping.yizhi.yizhiapp.adapter.FragmentAdapter;
import com.wenping.yizhi.yizhiapp.constant.RxBusCode;
import com.wenping.yizhi.yizhiapp.constant.TabFragmentIndex;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.GankIoMainContract;
import com.wenping.yizhi.yizhiapp.presenter.gankio.GankIoMainPresenter;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.rxbus.Subscribe;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.fragmenttabs.GankIoDayFragment;

import java.util.ArrayList;
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

    /**
     * 重写baseCompataFragment在onviewcreated中的方法
     * RxBus的注册和在onDestroy方法中的反注册
     */
    @Override
    public void initData() {
        super.initData();
//        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Logger.e("RxBus.get().register(this)");
        RxBus.get().unRegister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_;
    }

    /**
     * 布局的初始化操作
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //第2个tab显示>右下角的悬浮按钮显示
                if (mVpFragment.getCurrentItem() == 1) {
                    mFabClassify.show();
                } else {
                    mFabClassify.hide();
                }
            }
        });

        //悬浮按钮的点击效果：弹出底部菜单按钮列表
        mFabClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.get().send(RxBusCode.RX_BUS_CODE_GANKIO_PARENT_FAB_CLICK);
            }
        });

        //viewPager的页面切换监听
        mVpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mFabClassify.setVisibility(View.GONE);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    mFabClassify.setVisibility(View.VISIBLE);
                } else {
                    mFabClassify.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //获取
        mPresenter.getTabList();
    }


    /**
     * 实现IbaseView后的实现方法
     * Presenter的实例！
     *
     * @return
     */
    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoMainPresenter.newInstance();
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

        mVpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        //设置当前viewpager页面
        mVpFragment.setCurrentItem(TabFragmentIndex.TAB_GANK_DAY_INDEX);
        //.setAdapter后才起作用
        mTlTabs.setupWithViewPager(mVpFragment);
        mTlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_GANK_DAY_INDEX);
        //tlTabs.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        //重新设置标题等
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.getTabAt(i).setText(tabs[i]);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragments = new ArrayList<>();
    }

    @Subscribe(code = RxBusCode.RX_BUS_CODE_GANKIO_SELECT_TO_CHILD)
    public void rxBusEvent(Integer index) {
        Logger.e("index = " + index);
        //Set the position of the vertical scroll bar
        mTlTabs.setVerticalScrollbarPosition(index);
        mVpFragment.setCurrentItem(index);
    }
}
