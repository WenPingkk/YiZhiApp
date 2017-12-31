package com.wenping.yizhi.yizhiapp.ui.fragment.book.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.FragmentAdapter;
import com.wenping.yizhi.yizhiapp.constant.TabFragmentIndex;
import com.wenping.yizhi.yizhiapp.contract.contract.book.BookMainContract;
import com.wenping.yizhi.yizhiapp.presenter.book.BookMainPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.book.child.fragmenttabs.BookCustomFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author WenPing
 * @date 2017/12/13
 */

public class BookFragment extends BaseMVPCompatFragment<BookMainContract.BookMainPresenter,
        BookMainContract.IBookMainModel> implements BookMainContract.IBookMainView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tl_tabs)
    TabLayout mTlTabs;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.vp_fragment)
    ViewPager mVpFragment;
    @BindView(R.id.book_container)
    CoordinatorLayout mBookContainer;

    private List<Fragment> fragments;

    public static BookFragment newInstance() {
        Bundle bundle = new Bundle();
        BookFragment fragment = new BookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_;
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTabList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        //没有这句会出现空指针
        return BookMainPresenter.newInstance();
    }

    @Override
    public void showTabList(String[] tabs) {
        Logger.e(Arrays.toString(tabs));

        //实际上3个布局是一样的，都只有一个
        //recycleview，但是为了后续的升级
        //子fragment都使用单独的布局文件

        for (int i = 0; i < tabs.length; i++) {

            mTlTabs.addTab(mTlTabs.newTab().setText(tabs[i]));
            switch (i) {
                case TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("文学"));
                    break;
                case TabFragmentIndex.TAB_BOOK_CULTURE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("文化"));
                    break;
                case TabFragmentIndex.TAB_BOOK_LIFE_INDEX:
                    fragments.add(BookCustomFragment.newInstance("生活"));
                    break;
                default:
                    break;
            }
        }

        mVpFragment.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        //要设置到viewPager.setAdapter后才起作用
        mVpFragment.setCurrentItem(TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX);
        mTlTabs.setupWithViewPager(mVpFragment);
        mTlTabs.setVerticalScrollbarPosition(TabFragmentIndex.TAB_BOOK_LITERATURE_INDEX);
        //tlTabs.setupWithViewPager方法内部会remove所有的tabs，这里重新设置一遍tabs的text，否则tabs的text不显示
        for (int i = 0; i < tabs.length; i++) {
            mTlTabs.getTabAt(i).setText(tabs[i]);
        }
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragments = new ArrayList<>();
    }
}




































