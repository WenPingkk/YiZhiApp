package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.GankIoDayAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoDayContract;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoDayItemBean;
import com.wenping.yizhi.yizhiapp.presenter.gankio.tabs.GankIoDayPresenter;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WenPing on 2017/12/20.
 * <p>
 */

public class GankIoDayFragment extends BaseRecycleFragment<GankIoDayContract
        .GankIoDayPresenter, GankIoDayContract.IGankIoDayModel> implements GankIoDayContract
        .IGankIoDayView {

    @BindView(R.id.rv_gankio_day)
    RecyclerView mRvGankioDay;

    private GankIoDayAdapter mGankIoDayAdapter;

    public static GankIoDayFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoDayFragment gankIoDayFragment = new GankIoDayFragment();
        gankIoDayFragment.setArguments(bundle);
        return gankIoDayFragment;
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
        return R.layout.fragment_gank_io_day;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空的list集合:网络错误时使用,第一次加载到数据时重新初始化adapter并绑定recyclerview
        mGankIoDayAdapter = new GankIoDayAdapter(null);
        mRvGankioDay.setAdapter(mGankIoDayAdapter);
        mRvGankioDay.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //第一次显示时加载最新的列表
        mPresenter.loadLatestList();
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return GankIoDayPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<GankIoDayItemBean> list) {

        if (mGankIoDayAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mGankIoDayAdapter.addData(list);
        }

    }


    private void initRecycleView(List<GankIoDayItemBean> list) {
        mGankIoDayAdapter = new GankIoDayAdapter(list);

        mGankIoDayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (GankIoDayItemBean) adapter.getItem(position));
            }
        });

        mGankIoDayAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_more:
                        mPresenter.onMoreClick(position, (GankIoDayItemBean) adapter.getItem(position));
                        break;
                    case R.id.ll_refesh:
                        mPresenter.onRefeshClick(position, (GankIoDayItemBean) adapter.getItem(position));
                        break;
                    default:
                        break;
                }
            }
        });
        mRvGankioDay.setAdapter(mGankIoDayAdapter);
    }



    @Override
    public void showNetworkError() {
        mGankIoDayAdapter.setEmptyView(errorView);
    }

    @Override
    public void showLoadMoreError() {
    }

    @Override
    public void showNoMoreData() {
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadLatestList();
    }

    @Override
    protected void showLoading() {
        mGankIoDayAdapter.setEmptyView(loadingView);
    }

    @Override
    public void itemNotifyChanged(int position, GankIoDayItemBean bean) {
        mGankIoDayAdapter.refeshItem(position,bean);
    }

    @Override
    public void itemNotifyChanged(int position) {

    }
}
