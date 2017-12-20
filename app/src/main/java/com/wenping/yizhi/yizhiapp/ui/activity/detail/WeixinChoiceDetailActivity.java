package com.wenping.yizhi.yizhiapp.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.detail.WeixinDetailContract;
import com.wenping.yizhi.yizhiapp.presenter.detail.WangyiDetailPresenter;
import com.wenping.yizhi.yizhiapp.presenter.detail.WeixinDetailPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.utils.DisplayUtils;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;
import com.wenping.yizhi.yizhiapp.utils.StatusBarUtils;

/**
 * Created by WenPing on 2017/12/20.
 * <p>
 */

public class WeixinChoiceDetailActivity extends BaseWebViewLoadActivity<WeixinDetailContract.WeixinDetailPresenter,WeixinDetailContract.IWeixinDetailModel>
implements WeixinDetailContract.IWeixinDetailView{

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) appBar.getChildAt(0).getLayoutParams();

        // 控件的高强制设成56dp+状态栏高度!
        layoutParams.height = DisplayUtils.dp2px(56)+ StatusBarUtils.getStatusBarHeight(mContext);
    }

    private String mTitle,mUrl,mImageUrl,mCopyright;

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WeixinDetailPresenter.newInstance();
    }

    @Override
    public void showWeixinChoiceDetail(String url) {
        flNetView.setVisibility(View.GONE);
        nswvDetailContent.loadUrl(url);
    }

    @Override
    protected void loadDetail() {
        mPresenter.loadWeixinChoiceDetail(mUrl);
    }

    @Override
    protected String getToolbarTitle() {
        return ResourcesUtils.getString(R.string.weixin_detail_title);
    }
}
