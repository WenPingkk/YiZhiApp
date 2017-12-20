package com.wenping.yizhi.yizhiapp.ui.fragment.home.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.WeixinAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WeixinContract;
import com.wenping.yizhi.yizhiapp.model.bean.weixin.WeixinChoiceItemBean;
import com.wenping.yizhi.yizhiapp.presenter.home.tabs.WeixinPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WenPing on 2017/12/18.
 * <p>
 */

public class WeiXinFragment extends BaseRecycleFragment<WeixinContract.WeixinPresenter,
        WeixinContract.IWeixinModel> implements WeixinContract.IWeixinView, BaseQuickAdapter
        .RequestLoadMoreListener {

    @BindView(R.id.rv_weixin)
    RecyclerView mRvWeixin;

    private WeixinAdapter mWeixinAdapter;

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
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空的list的adapter,网络错误时使用,第一次加载到数据时重新初始化,adapter并绑定recyclerview
        mWeixinAdapter = new WeixinAdapter(R.layout.item_recycle_home);
        mRvWeixin.setAdapter(mWeixinAdapter);
        mRvWeixin.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @Override
    public void onLoadMoreRequested() {
        mWeixinAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WeixinPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<WeixinChoiceItemBean> list) {
        if (mWeixinAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mWeixinAdapter.addData(list);
        }
    }

    private void initRecycleView(List<WeixinChoiceItemBean> list) {
        mWeixinAdapter = new WeixinAdapter(R.layout.item_recycle_home, list);
        mWeixinAdapter.setOnLoadMoreListener(this, mRvWeixin);
        mWeixinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (WeixinChoiceItemBean) adapter.getItem(position));
            }
        });
        mRvWeixin.setAdapter(mWeixinAdapter);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mWeixinAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNetworkError() {
        mWeixinAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mWeixinAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mWeixinAdapter.loadMoreEnd();
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestList();
    }

    @Override
    protected void showLoading() {
        mWeixinAdapter.setEmptyView(loadingView);
    }
}
