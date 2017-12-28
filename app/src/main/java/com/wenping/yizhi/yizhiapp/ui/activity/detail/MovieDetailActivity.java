package com.wenping.yizhi.yizhiapp.ui.activity.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.adapter.MovieDetailAdapter;
import com.wenping.yizhi.yizhiapp.base.activity.BaseMVPCompatActivity;
import com.wenping.yizhi.yizhiapp.contract.contract.movie.MovieDetailContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.MovieDetailBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.PersonBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.presenter.movie.MovieDetailPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.utils.DisplayUtils;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;
import com.wenping.yizhi.yizhiapp.widget.child.CompatNestedScrollView;

import java.util.List;

import butterknife.BindView;

import static com.wenping.yizhi.yizhiapp.constant.InternKeyConstant.INTENT_KEY_MOVIE_SUBJECTBEAN;
import static com.wenping.yizhi.yizhiapp.utils.StatusBarUtils.getStatusBarHeight;

/**
 * @author WenPing
 * @date 12/28/2017
 */


public class MovieDetailActivity extends BaseMVPCompatActivity<MovieDetailContract
        .MovieDetailPresenter, MovieDetailContract.IMovieDetailModel>
        implements MovieDetailContract.IMovieDetailView {


    @BindView(R.id.iv_header_bg)
    ImageView mIvHeaderBg;
    @BindView(R.id.iv_movie_photo)
    ImageView mIvMoviePhoto;
    @BindView(R.id.tv_movie_rating_rate)
    TextView mTvMovieRatingRate;
    @BindView(R.id.tv_movie_rating_number)
    TextView mTvMovieRatingNumber;
    @BindView(R.id.ll_rating)
    LinearLayout mLlRating;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    @BindView(R.id.tv_movie_directors)
    TextView mTvMovieDirectors;
    @BindView(R.id.tv_movie_casts)
    TextView mTvMovieCasts;
    @BindView(R.id.tv_movie_genres)
    TextView mTvMovieGenres;
    @BindView(R.id.tv_movie_date)
    TextView mTvMovieDate;
    @BindView(R.id.tv_movie_city)
    TextView mTvMovieCity;
    @BindView(R.id.ll_movie_header)
    LinearLayout mLlMovieHeader;
    @BindView(R.id.tv_movie_sub_title)
    TextView mTvMovieSubTitle;
    @BindView(R.id.tv_moive_summary)
    TextView mTvMoiveSummary;
    @BindView(R.id.rv_movie_detail)
    RecyclerView mRvMovieDetail;
    @BindView(R.id.nsv_scrollview)
    CompatNestedScrollView mNsvScrollview;
    @BindView(R.id.iv_toolbar_bg)
    ImageView mIvToolbarBg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private SubjectsBean mSubjectsBean;
    private MovieDetailAdapter mMovieDetailAdapter;
    private View errorView;

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return MovieDetailPresenter.newInstance();
    }

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mSubjectsBean = (SubjectsBean) getIntent().getSerializableExtra(INTENT_KEY_MOVIE_SUBJECTBEAN);
        }
    }

    @Override
    public void showMovieDetail(MovieDetailBean bean) {
        if (mMovieDetailAdapter.getData().size() == 0) {
            initRecycleView(bean);
            mTvMovieCity.setText("制片国家/地区" + bean.getCountriesString());
            mTvMovieSubTitle.setText(bean.getAkaString());
            mTvMoiveSummary.setText(bean.getSummary());
        } else {
            mMovieDetailAdapter.addData(bean.getCasts());
        }
    }

    private void initRecycleView(MovieDetailBean bean) {
        List<PersonBean> list = bean.getDirectors();
        list.addAll(bean.getCasts());
        mMovieDetailAdapter = new MovieDetailAdapter(R.layout
                .item_movie_detail_person, list);
        mMovieDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener
                () {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.onItemClick(position, (PersonBean) adapter.getItem(position));
            }
        });
        mRvMovieDetail.setAdapter(mMovieDetailAdapter);
    }

    @Override
    public void showNetworkError() {
        mMovieDetailAdapter.setEmptyView(errorView);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar, mSubjectsBean.getTitle());
        initHeadView(mSubjectsBean);

        mMovieDetailAdapter = new MovieDetailAdapter(R.layout.item_movie_detail_person);

        mRvMovieDetail.setAdapter(mMovieDetailAdapter);
        mRvMovieDetail.setLayoutManager(new LinearLayoutManager(this));
        mRvMovieDetail.setNestedScrollingEnabled(false);

        mNsvScrollview.bindAlphaView(mIvToolbarBg);
        mPresenter.loadMovieDetail(mSubjectsBean.getId());

        errorView = getLayoutInflater().inflate(R.layout.view_network_error, mRvMovieDetail, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadMovieDetail(mSubjectsBean.getId());
            }
        });
    }

    private void initHeadView(SubjectsBean bean) {
        mTvMovieRatingNumber.setText(String.valueOf(mSubjectsBean.getRating().getAverage()));
        mTvCollectCount.setText(String.valueOf(mSubjectsBean.getCollect_count()));
        mTvMovieDirectors.setText(mSubjectsBean.getDirectorsString());
        mTvMovieCasts.setText(mSubjectsBean.getActorsString());
        mTvMovieGenres.setText(mSubjectsBean.getGenresString());
        mTvMovieDate.setText(mSubjectsBean.getYear());

        Glide.with(this).load(mSubjectsBean.getImages().getLarge()).asBitmap().into(mIvMoviePhoto);
        DisplayUtils.displayBlurImg(this, mSubjectsBean.getImages().getLarge(), mIvHeaderBg);
        DisplayUtils.displayBlurImg(this, mSubjectsBean.getImages().getLarge(), mIvToolbarBg);


        int headerBgHeight = mToolbar.getLayoutParams().height + getStatusBarHeight(this);
        // 使背景图向上移动到图片的最低端，保留（toolbar+状态栏）的高度
        // 实际上此时ivToolbarBg高度还是330dp，只是除了toolbar外，剩下部分是透明状态
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams)
                mIvToolbarBg.getLayoutParams();
        int marginTop = mIvToolbarBg.getLayoutParams().height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_moive_detail;
    }


    /**
     * @param context      activity
     * @param subjectsBean bean
     * @param imageView    imageView
     */
    public static void start(Activity context, SubjectsBean subjectsBean, ImageView imageView) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(INTENT_KEY_MOVIE_SUBJECTBEAN, subjectsBean);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                (context, imageView, ResourcesUtils.getString(R.string.transition_movie_img));
        //与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
