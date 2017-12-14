package com.wenping.yizhi.yizhiapp.ui.fragment.home.child;

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
import com.wenping.yizhi.yizhiapp.adapter.ZhihuAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.ZhihuContract;
import com.wenping.yizhi.yizhiapp.model.bean.zhihu.ZhihuDailyItemBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.home.tabs.ZhihuPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WenPing on 2017/12/13.
 */

public class ZhihuFragment extends BaseRecycleFragment<ZhihuContract.ZhihuPresenter, ZhihuContract.IZhihuModel> implements ZhihuContract.IZhihuView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_zhihu)
    RecyclerView mRvZhihu;
    private ZhihuAdapter mZhihuAdapter;

    public static ZhihuFragment newInstance() {
        Bundle bundle = new Bundle();
        ZhihuFragment fragment = new ZhihuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_zhihu;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_recycle_home);
        mRvZhihu.setAdapter(mZhihuAdapter);
        mRvZhihu.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return ZhihuPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<ZhihuDailyItemBean> list) {
        if (mZhihuAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mZhihuAdapter.addData(list);
        }
    }

    private void initRecycleView(List<ZhihuDailyItemBean> list) {
        mZhihuAdapter = new ZhihuAdapter(R.layout.item_recycle_home, list);
        mZhihuAdapter.setOnLoadMoreListener(this,mRvZhihu);
        mZhihuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, ((ZhihuDailyItemBean) adapter.getItem(position)));
            }
        });
        mRvZhihu.setAdapter(mZhihuAdapter);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mZhihuAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mZhihuAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mZhihuAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mZhihuAdapter.loadMoreEnd(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mZhihuAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestList();
    }

    @Override
    protected void showLoading() {
        mZhihuAdapter.setEmptyView(loadingView);
    }
}
