package com.wenping.yizhi.yizhiapp.ui.fragment.home.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WeixinContract;
import com.wenping.yizhi.yizhiapp.model.bean.weixin.WeixinChoiceItemBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

/**
 * Created by WenPing on 2017/12/18.
 * <p>
 */

public class WeiXinFragment extends BaseRecycleFragment<WeixinContract.WeixinPresenter,
        WeixinContract.IWeixinModel> implements WeixinContract.IWeixinView, BaseQuickAdapter
        .RequestLoadMoreListener {

    public static WeiXinFragment newInstance() {
        WeiXinFragment fragment = new WeiXinFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_weixin;
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
    public void updateContentList(List<WeixinChoiceItemBean> list) {

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
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
