package com.wenping.yizhi.yizhiapp.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.detail.GankIoDetailContract;
import com.wenping.yizhi.yizhiapp.presenter.detail.GankIoDetailPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.utils.DisplayUtils;
import com.wenping.yizhi.yizhiapp.utils.StatusBarUtils;

/**
 * Created by Wenping on 12/24/2017.
 *
 */

public class GankIoDetailActivity extends BaseWebViewLoadActivity<GankIoDetailContract
        .GankIoDetailPresenter, GankIoDetailContract.IGankIoDetailModel> implements
                GankIoDetailContract.IGankIoDetailView {

    private String mTitle,mUrl;

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(BundleKeyConstant.ARG_KEY_GANKIO_DETAIL_URL);
            mTitle = bundle.getString(BundleKeyConstant.ARG_KEY_GANKIO_DETAIL_TITLE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) appBar.getChildAt(0).getLayoutParams();
        //控件的高,强制设置成56dp+状态栏高度
        params.height = DisplayUtils.dp2px(56) + StatusBarUtils.getStatusBarHeight(mContext);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoDetailPresenter.newInstance();
    }

    @Override
    public void showGankIoDetail(String url) {
        flNetView.setVisibility(View.GONE);
        nswvDetailContent.loadUrl(url);
    }

    @Override
    protected void loadDetail() {
        mPresenter.loadGankIoDetail(mUrl);
    }

    @Override
    protected String getToolbarTitle() {
        return mTitle;
    }
}
