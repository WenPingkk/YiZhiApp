package com.wenping.yizhi.yizhiapp.ui.fragment.movie.child.top;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.TopMovieAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.TopMovieContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.presenter.movie.TopMoviePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author WenPing
 * @date 12/30/2017
 */

public class TopMoiveFragment extends BaseRecycleFragment<TopMovieContract.TopMoivePresenter,
        TopMovieContract.ITopMovieModel> implements TopMovieContract.ITopMovieView,
        BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_top_movie)
    RecyclerView mRvTopMovie;
    private TopMovieAdapter mTopMovieAdapter;

    public static TopMoiveFragment newInstance() {
        Bundle bundle = new Bundle();
        TopMoiveFragment fragment = new TopMoiveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_top;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("豆瓣电影Top25x");
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        mTopMovieAdapter = new TopMovieAdapter(R.layout.item_top_movie);
        mRvTopMovie.setAdapter(mTopMovieAdapter);
        //getItemCount()返回值<=0,要设置LinearLayoutManager，否则后面数据更新RecycleView也不执行onBindViewHolder;
        mRvTopMovie.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return TopMoviePresenter.newInstance();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadTopMovieList();
    }

    @Override
    public void onLoadMoreRequested() {
        //这里的loadMoreComplete要放在前面，避免在Presenter.loadMoreNewsList处理中直接showNoMoreData，出现无限显示加载item
        mTopMovieAdapter.loadMoreComplete();;
        mPresenter.loadMoreTopMovie();

    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        if (mTopMovieAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mTopMovieAdapter.addData(list);
        }
    }

    private void initRecycleView(List<SubjectsBean> list) {
        mTopMovieAdapter = new TopMovieAdapter(R.layout.item_top_movie, list);
        mTopMovieAdapter.setOnLoadMoreListener(this,mRvTopMovie);
        mTopMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position,(SubjectsBean) adapter.getItem(position),
                        (ImageView) view.findViewById(R.id.iv_top_moive_photo));
            }
        });
        mRvTopMovie.setAdapter(mTopMovieAdapter);
        //构造器中，第一个参数表示列或者行数量，第二个表示滑动方向，瀑布流
        mRvTopMovie.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void showNetworkError() {
        mTopMovieAdapter.setEmptyView(errorView);
    }

    @Override
    public void showNoMoreData() {
        mTopMovieAdapter.loadMoreEnd(true);
    }

    @Override
    public void showLoadMoreError() {
        mTopMovieAdapter.loadMoreFail();
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadTopMovieList();
    }

    @Override
    protected void showLoading() {
        mTopMovieAdapter.setEmptyView(loadingView);
    }
}
