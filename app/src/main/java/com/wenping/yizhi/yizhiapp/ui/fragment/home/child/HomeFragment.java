package com.wenping.yizhi.yizhiapp.ui.fragment.home.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.FragmentAdapter;
import com.wenping.yizhi.yizhiapp.anim.ToolbarAnimManager;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.constant.TabFragmentIndex;
import com.wenping.yizhi.yizhiapp.contract.contract.home.HomeMainContract;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.WebViewLoadActivity;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.base.HomeMainPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.fragmenttabs.WangyiFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.fragmenttabs.WeiXinFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.child.fragmenttabs.ZhihuFragment;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * homefragmnet
 * Created by WenPing on 2017/12/12.
 */

public class HomeFragment extends BaseMVPCompatFragment<HomeMainContract.HomeMainPresenter,
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

    protected OnOpenDrawerLayoutListener mOnOpenDrawerLayoutListener;
    private List<Fragment> mFragments;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Mainactivity实现了OnOpenDrawerLayoutListener接口,Homefragment 依赖的activity为Mainactivity时
        if (context instanceof OnOpenDrawerLayoutListener) {
            mOnOpenDrawerLayoutListener = (OnOpenDrawerLayoutListener) context;
        }
        mFragments = new ArrayList<>();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnOpenDrawerLayoutListener = null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTabList();
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
                    //开启抽屉;接口回调
                    mOnOpenDrawerLayoutListener.onOpen();
                }
            }
        });
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
        mFabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE, "YiZhiApp");
                bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL, "https://github.com/WenPingkk/YiZhiApp");
                startNewActivity(WebViewLoadActivity.class,bundle);
            }
        });
        //toolbar右侧menu的显示效果
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.getMenu().findItem(R.id.night)
                .setChecked(SpUtils.getNightModel(mContext));
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.night:
        //夜间模式的实现原理:通过SpUtils的setNightMode方法,把当前的状态保存[true/false];接着调用BaseCompatActivity的reload方法,重新进入项目
        //此时会调用BaseCompatActivity中onCreate的方法下的init方法:setTheme(ThemeUtils.themeArr[SpUtils.getThemeIndex(this)][SpUtils.getNightModel(this) ? 1 : 0]);
        //实现切换到夜间模式
                        item.setChecked(!item.isChecked());
                        SpUtils.setNightModel(mContext, item.isChecked());
                        //重新设置夜间模式后要重新reload一次，才能显示效果。
                        ((BaseCompatActivity) mActivity).reload();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        ToolbarAnimManager.animIn(mContext, mToolbar);
    }


    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return HomeMainPresenter.newInstance();
    }

    @Override
    public void showTabList(String[] tabs) {
        //显示tab
        Logger.w(Arrays.toString(tabs));
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.addTab(mTlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_ZHIHU_INDEX:
                    mFragments.add(ZhihuFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_WANGYI_INDEX:
                    mFragments.add(WangyiFragment.newInstance());
                    break;
                case TabFragmentIndex.TAB_WEIXIN_INDEX:
                    mFragments.add(WeiXinFragment.newInstance());
                    break;

                default:
                    break;
            }
        }
        mVpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragments));
        mVpFragment.setCurrentItem(TabFragmentIndex.TAB_ZHIHU_INDEX);
        mTlTabs.setupWithViewPager(mVpFragment);
        //Set the position of the vertical scroll bar
        mTlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_ZHIHU_INDEX);
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.getTabAt(i).setText(tabs[i]);
        }
    }

    /**
     * fragment打开DrawerLayout监听
     */
    public interface OnOpenDrawerLayoutListener {
        void onOpen();
    }
}
