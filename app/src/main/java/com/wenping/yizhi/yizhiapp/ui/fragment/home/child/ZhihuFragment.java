package com.wenping.yizhi.yizhiapp.ui.fragment.home.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.ZhihuContract;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyItemBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

/**
 * Created by YinZeTong on 2017/12/13.
 */

public class ZhihuFragment extends BaseRecycleFragment<ZhihuContract.ZhihuPresenter,ZhihuContract.IZhihuModel> implements ZhihuContract.IZhihuView,BaseQuickAdapter.RequestLoadMoreListener{

    public static ZhihuFragment newInstance() {
        Bundle bundle = new Bundle();
        ZhihuFragment fragment = new ZhihuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onLoadMoreRequested() {

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void updateContentList(List<ZhihuDailyItemBean> list) {

    }

    @Override
    public void itemNotifyChanged(int position) {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showLoadMoreError() {

    }

    @Override
    public void showNoMoreData() {

    }

    @Override
    protected void onErrorViewClick(View view) {

    }

    @Override
    protected void showLoading() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_zhihu;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
