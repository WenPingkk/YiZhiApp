package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.GankIoWelfareAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoWelfareContract;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoWelfareItemBean;
import com.wenping.yizhi.yizhiapp.presenter.gankio.tabs.GankIoWelfarePresenter;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.rxbus.Subscribe;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;

import static com.wenping.yizhi.yizhiapp.constant.RxBusCode.RX_BUS_CODE_GANKIO_WELFARE_TYPE;

/**
 * Created by WenPing on 12/26/2017.
 */

public class GankIoWelfareFragment extends BaseRecycleFragment<GankIoWelfareContract
        .GankIoWelfarePresenter, GankIoWelfareContract.IGankIoWelfareModel> implements
        GankIoWelfareContract.IGankIoWelfareView, BaseQuickAdapter.RequestLoadMoreListener {

    GankIoWelfareAdapter mGankIoWelfareAdapter;
    @BindView(R.id.rv_gankio_welfare)
    RecyclerView mRvGankioWelfare;

    /**
     * 返回fragment的实例
     *
     * @return
     */
    public static GankIoWelfareFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoWelfareFragment fragment = new GankIoWelfareFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        super.initData();
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gank_io_welfare;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空的list的adapter，网络错误时使用，第一次加载到数据时重新初始化adapter并摒挡recycleview
        mGankIoWelfareAdapter = new GankIoWelfareAdapter(R.layout.item_gank_io_welfare);
        mRvGankioWelfare.setAdapter(mGankIoWelfareAdapter);
        //getItemCount（）,返回值<=0，要设置LinearLayoutManager！否则后面更新数据RecycleView也不执行onBindViewHolder;
        mRvGankioWelfare.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadLatestList();
    }

    @Override
    public void onLoadMoreRequested() {
        //这里loadMoreComplete要放在前面，避免Presenter.loadMoreNewsList处理中直接showNoMoreData,出现无限加载显示加载item
        mGankIoWelfareAdapter.loadMoreComplete();
        mPresenter.loadMoreList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoWelfarePresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoWelfareItemBean> list) {

        if (mGankIoWelfareAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mGankIoWelfareAdapter.addData(list);
        }
    }

    private void initRecycleView(List<GankIoWelfareItemBean> list) {
        mGankIoWelfareAdapter = new GankIoWelfareAdapter(R.layout.item_gank_io_welfare, list);
        mGankIoWelfareAdapter.setOnLoadMoreListener(this,mRvGankioWelfare);
        mGankIoWelfareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (GankIoWelfareItemBean) adapter.getItem(position));
            }
        });
        mRvGankioWelfare.setAdapter(mGankIoWelfareAdapter);
        //构造器中，第一个参数表示列数或者行数，第二个参数表示滑动方向，瀑布流。
        mRvGankioWelfare.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void itemNotifyChanged(int position) {
        //无逻辑
    }

    @Override
    public void showNetworkError() {
        mGankIoWelfareAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
        mGankIoWelfareAdapter.loadMoreFail();
    }

    @Override
    public void showNoMoreData() {
        mGankIoWelfareAdapter.loadMoreEnd();
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestList();
    }

    @Override
    protected void showLoading() {
        mGankIoWelfareAdapter.setEmptyView(loadingView);
    }

    /**
     * day页面查看更多事件触发
     */
    @Subscribe(code = RX_BUS_CODE_GANKIO_WELFARE_TYPE)
    public void rxBusEvent() {
        mRvGankioWelfare.smoothScrollToPosition(0);
    }
}
