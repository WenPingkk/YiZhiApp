package com.wenping.yizhi.yizhiapp.ui.fragment.home.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.base.HomeMainPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.contract.HomeMainContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**homefragmnet
 * Created by YinZeTong on 2017/12/12.
 */

public class HomeFragment
        extends BaseMVPCompatFragment<HomeMainContract.HomeMainPresenter,
        HomeMainContract.IHomeMainModel> implements HomeMainContract.IHomeMainView {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tl_tabs)
    TabLayout mTlTabs;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    @BindView(R.id.fab_download)
    FloatingActionButton mFabDownload;
    @BindView(R.id.home_container)
    CoordinatorLayout mHomeContainer;
    Unbinder unbinder;

    protected OnOpenDrawerLayoutListener mOnOpenDrawerLayoutListener;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return HomeMainPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("首页");
        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnOpenDrawerLayoutListener != null) {
                    mOnOpenDrawerLayoutListener.onOpen();
                }
            }
        });
        // TODO: 2017/12/12
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mFabDownload.show();
                } else {
                    mFabDownload.hide();
                }
            }
        });
        // TODO: 2017/12/12
        mFabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE,"YiZhi");
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL,"https://github.com/Horrarndoo/YiZhi");
//                startNewActivity(WebViewLoadActivity.);
            }
        });
    }

    @Override
    public void showTabList(String[] tabs) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * fragment打开DrawerLayout监听
     */
    public interface OnOpenDrawerLayoutListener {
        void onOpen();
    }
}
