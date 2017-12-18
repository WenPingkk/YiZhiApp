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
import com.wenping.yizhi.yizhiapp.adapter.WangyiAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.home.tabs.WangyiContract;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsItemBean;
import com.wenping.yizhi.yizhiapp.presenter.home.tabs.WangyiPresenter;
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

public class WangyiFragment extends BaseRecycleFragment<WangyiContract.WangyiPresenter, WangyiContract.IWangyiModel>
        implements WangyiContract.IWangyiView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_wangyi)
    RecyclerView mRvWangyi;

    WangyiAdapter mWangyiAdapter = null;

    public static WangyiFragment newInstance() {
        Bundle bundle = new Bundle();
        WangyiFragment fragment = new WangyiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_wangyi;
    }

    /**
     * 在该方法中初始化一个list集合为空的Adapter，网络错误时使用
     * 第一次加载到数据时重新初始化adapter，并绑定recyclerview
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mWangyiAdapter = new WangyiAdapter(R.layout.item_recycle_home);
        mRvWangyi.setAdapter(mWangyiAdapter);
        mRvWangyi.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return WangyiPresenter.newInstance();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @Override
    public void updateContentList(List<WangyiNewsItemBean> list) {
        if (mWangyiAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mWangyiAdapter.addData(list);
        }
    }

    private void initRecycleView(List<WangyiNewsItemBean> list) {
        mWangyiAdapter = new WangyiAdapter(R.layout.item_recycle_home, list);
        //mWangyiAdapter，加载更多的监听
        mWangyiAdapter.setOnLoadMoreListener(this, mRvWangyi);
        //mWangyiAdapter对应item点击效果
        mWangyiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (WangyiNewsItemBean) adapter.getItem(position));
            }
        });
        mRvWangyi.setAdapter(mWangyiAdapter);
    }

    /**
     * 这里loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中
     * 直接showNoMoreData,出现无限展示加载item；
     */
    @Override
    public void onLoadMoreRequested() {
        mWangyiAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    @Override
    protected void showLoading() {
        mWangyiAdapter.setEmptyView(loadingView);
    }

    @Override
    public void itemNotifyChanged(int position) {
        mWangyiAdapter.notifyItemChanged(position);
    }

    @Override
    public void showNetworkError() {
        mWangyiAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mWangyiAdapter.loadMoreFail();
    }

    /**
     * if true gone the load more view
     */
    @Override
    public void showNoMoreData() {
        mWangyiAdapter.loadMoreEnd(false);
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadMoreList();
    }
}
