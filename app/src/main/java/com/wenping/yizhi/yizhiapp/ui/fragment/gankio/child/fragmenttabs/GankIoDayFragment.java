package com.wenping.yizhi.yizhiapp.ui.fragment.gankio.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.GankIoDayAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.gankio.tabs.GankIoDayContract;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoDayItemBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WenPing on 2017/12/20.
 * <p>
 */
// TODO: 2017/12/20 待完成
public class GankIoDayFragment extends BaseRecycleFragment<GankIoDayContract
        .GankIoDayPresenter, GankIoDayContract.IGankIoDayModel> implements GankIoDayContract
        .IGankIoDayView {

    @BindView(R.id.rv_gankio_day)
    RecyclerView mRvGankioDay;
    Unbinder unbinder;
    private GankIoDayAdapter mGankIoDayAdapter;

    public static GankIoDayFragment newInstance() {
        Bundle bundle = new Bundle();
        GankIoDayFragment gankIoDayFragment = new GankIoDayFragment();
        gankIoDayFragment.setArguments(bundle);
        return gankIoDayFragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void updateContentList(List<GankIoDayItemBean> list) {

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
    public void itemNotifyChanged(int position, GankIoDayItemBean bean) {

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
}
