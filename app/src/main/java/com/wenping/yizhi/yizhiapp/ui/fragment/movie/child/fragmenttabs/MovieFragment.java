package com.wenping.yizhi.yizhiapp.ui.fragment.movie.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.HotMovieAdapter;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieMainContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.presenter.movie.MovieMainPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseRecycleFragment;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WenPing on 12/27/2017.
 *
 */

public class MovieFragment extends BaseRecycleFragment<MovieMainContract.MovieMainPresenter,
        MovieMainContract.IMovieMainModel> implements MovieMainContract.IMovieMainView {

    @BindView(R.id.rv_hot_movie)
    RecyclerView mRvHotMovie;

    private HotMovieAdapter mHotMovieAdapter;
    private View headView;

    public static MovieFragment newInstance() {
        Bundle bundle = new Bundle();
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_hot;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.loadHotMovieList();
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        //初始化一个空list的adapter，网络错误时使用，第一次加载到数据时重新初始化adapter并绑定recycleview
        mHotMovieAdapter = new HotMovieAdapter(R.layout.item_hot_movie);
        mRvHotMovie.setAdapter(mHotMovieAdapter);
        mRvHotMovie.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MovieMainPresenter.newInstance();
    }

    @Override
    public void updateContentList(List<SubjectsBean> list) {
        if (mHotMovieAdapter.getData().size() == 0) {
            initRecycleView(list);
        } else {
            mHotMovieAdapter.addData(list);
        }
    }

    private void initRecycleView(List<SubjectsBean> list) {
        mHotMovieAdapter = new HotMovieAdapter(R.layout.item_hot_movie, list);
        mHotMovieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //由于有headview click position需要+1 adapter.getItem返回的是数据list的position，所以不用+1
                mPresenter.onItemClick(position+1, ((SubjectsBean) adapter.getItem(position)), ((ImageView) view.findViewById(R.id.iv_moive_photo)));
            }
        });
        initHeadView();
        mHotMovieAdapter.addHeaderView(headView);
        mRvHotMovie.setAdapter(mHotMovieAdapter);
    }

    private void initHeadView() {
        if (headView == null) {
            headView = ResourcesUtils.inflate(R.layout.sub_movie_top_header);
        }
        headView.findViewById(R.id.ll_movie_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onHeaderClick();
            }
        });
    }


    @Override
    public void showNetworkError() {
        mHotMovieAdapter.setEmptyView(errorView);
    }

    @Override
    protected void onErrorViewClick(View view) {
        mPresenter.loadHotMovieList();
    }

    @Override
    protected void showLoading() {
        mHotMovieAdapter.setEmptyView(loadingView);
    }

}
