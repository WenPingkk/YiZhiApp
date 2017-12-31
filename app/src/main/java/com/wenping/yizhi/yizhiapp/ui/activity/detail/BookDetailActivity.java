package com.wenping.yizhi.yizhiapp.ui.activity.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseMVPCompatActivity;
import com.wenping.yizhi.yizhiapp.constant.InternKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.book.BookDeatilContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookDetailBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookItemBean;
import com.wenping.yizhi.yizhiapp.presenter.book.tabs.BookDetailPresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.utils.DisplayUtils;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;
import com.wenping.yizhi.yizhiapp.utils.StatusBarUtils;
import com.wenping.yizhi.yizhiapp.utils.StringUtils;
import com.wenping.yizhi.yizhiapp.widget.child.CompatNestedScrollView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author WenPing
 * @date 12/31/2017
 */

public class BookDetailActivity extends BaseMVPCompatActivity<BookDeatilContract
        .BookDetailPresenter, BookDeatilContract.IBookDetailModel> implements BookDeatilContract
        .IBookDetailView {


    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.iv_header_bg)
    ImageView mIvHeaderBg;
    @BindView(R.id.iv_book_photo)
    ImageView mIvBookPhoto;
    @BindView(R.id.tv_book_rating_number)
    TextView mTvBookRatingNumber;
    @BindView(R.id.ll_rating)
    LinearLayout mLlRating;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    @BindView(R.id.tv_book_author)
    TextView mTvBookAuthor;
    @BindView(R.id.tv_book_publisher)
    TextView mTvBookPublisher;
    @BindView(R.id.tv_book_pupdate)
    TextView mTvBookPupdate;
    @BindView(R.id.ll_book_header)
    LinearLayout mLlBookHeader;
    @BindView(R.id.tv_book_sub_title)
    TextView mTvBookSubTitle;
    @BindView(R.id.ll_book_sub_title)
    LinearLayout mLlBookSubTitle;
    @BindView(R.id.tv_book_author_intro)
    TextView mTvBookAuthorIntro;
    @BindView(R.id.tv_book_summary)
    TextView mTvBookSummary;
    @BindView(R.id.tv_book_detail)
    TextView mTvBookDetail;
    @BindView(R.id.ll_book_detail_content)
    LinearLayout mLlBookDetailContent;
    @BindView(R.id.nsv_scrollview)
    CompatNestedScrollView mNsvScrollview;
    @BindView(R.id.iv_toolbar_bg)
    ImageView mIvToolbarBg;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    //include的部分
    @BindView(R.id.fl_net_view)
    FrameLayout flNetView;
    @BindView(R.id.v_network_error)
    View vNetworkError;


    private BookItemBean mBookItemBean;

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mBookItemBean = (BookItemBean) getIntent()
                    .getSerializableExtra(InternKeyConstant.INTENT_KEY_BOOK_BOOK_ITEM_BEAN);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(mToolbar, mBookItemBean.getTitle());
        initHeaderView(mBookItemBean);
        mNsvScrollview.bindAlphaView(mIvToolbarBg);
        mPresenter.loadBookDetail(mBookItemBean.getId());
    }

    @Override
    public void showBookDetail(BookDetailBean bean) {
        if (bean != null) {
            if (StringUtils.isEmpty(bean.getSubtitle())) {
                mLlBookSubTitle.setVisibility(View.GONE);
            } else {
                mTvBookSubTitle.setText(bean.getSubtitle());
            }

            mTvBookAuthorIntro.setText(bean.getAuthor_intro());
            mTvBookSummary.setText(bean.getSummary());
            mTvBookDetail.setText(bean.getCatalog());
        }
    }

    /**
     * @param bean
     */
    private void initHeaderView(BookItemBean bean) {
        mTvBookRatingNumber.setText(String.valueOf(bean.getRating().getAverage()));
        mTvCollectCount.setText(String.valueOf(bean.getRating().getNumRaters()));
        mTvBookAuthor.setText(bean.getAuthorsString());
        mTvBookPublisher.setText(bean.getPubdate());
        mTvBookPupdate.setText(bean.getPubdate());

        if (StringUtils.isEmail(bean.getSubtitle())) {
            mLlBookSubTitle.setVisibility(View.GONE);
        } else {
            mTvBookSubTitle.setText(bean.getSubtitle());
        }
        mTvBookAuthorIntro.setText(bean.getAuthor_intro());
        mTvBookSummary.setText(bean.getSummary());

        Glide.with(this).load(bean.getImages().getLarge()).asBitmap().into(mIvBookPhoto);
        DisplayUtils.displayBlurImg(this, bean.getImages().getLarge(), mIvHeaderBg);
        DisplayUtils.displayBlurImg(this, bean.getImages().getLarge(), mIvToolbarBg);

        int headerBgHeight = mToolbar.getLayoutParams().height + StatusBarUtils.getStatusBarHeight(this);
        //使用北京图向上移动到图片的最低端，保留[toolbar+状态栏]的高度
        //实际上此时mIvToolbarBg的高度还是330dp，但是除了toolbar外，剩下部分是透明状态
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) mIvToolbarBg.getLayoutParams();
        int marginTop = mIvToolbarBg.getLayoutParams().height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return BookDetailPresenter.newInstance();
    }

    @Override
    public void showNetworkError() {
        mNsvScrollview.setVisibility(View.GONE);
        vNetworkError.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.fl_net_view, R.id.ll_book_header})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_net_view:
                mNsvScrollview.setVisibility(View.VISIBLE);
                vNetworkError.setVisibility(View.GONE);
                mPresenter.loadBookDetail(mBookItemBean.getId());
                break;
            case R.id.ll_book_header:
                mPresenter.onHeaderClick(mBookItemBean);
                break;
            default:
                break;
        }
    }

    public static void start(Activity context, BookItemBean bookItemBean, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(InternKeyConstant.INTENT_KEY_BOOK_BOOK_ITEM_BEAN, bookItemBean);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context,imageView, ResourcesUtils.getString(R.string.transition_book_img));

        //和xml文件对应
        ActivityCompat.startActivity(context,intent,optionsCompat.toBundle());
    }
}

