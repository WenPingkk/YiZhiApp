package com.wenping.yizhi.yizhiapp.presenter.movie;

import android.widget.ImageView;

import com.wenping.yizhi.yizhiapp.contract.contract.movie.TopMovieContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.HotMovieBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.model.movie.TopMovieModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.MovieDetailActivity;

import io.reactivex.functions.Consumer;

/**
 * @author WenPing
 * @date 12/30/2017
 */

public class TopMoviePresenter extends TopMovieContract.TopMoivePresenter {

    private int mStart;
    private int mCount = 30;
    private boolean isLoading;

    public static TopMoviePresenter newInstance() {
        return new TopMoviePresenter();
    }

    @Override
    public TopMovieContract.ITopMovieModel getModel() {
        return TopMovieModel.newInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void loadTopMovieList() {
        if (mIModel != null && mIView != null) {
            mStart = 0;

            //一次加载20条数据
            mRxManager.register(mIModel.getTopMovieList(mStart, mCount).subscribe(new Consumer<HotMovieBean>() {
                @Override
                public void accept(HotMovieBean bean) throws Exception {
                    if (mIView != null) {
                        mStart += mCount;
                        mIView.updateContentList(bean.getSubjects());

                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        if (mIView.isVisiable()) {
                            mIView.showToast("Network error");
                        }

                        mIView.showNetworkError();
                    }
                }
            }));
        }
    }

    @Override
    public void loadMoreTopMovie() {
        if (!isLoading) {
            isLoading = true;
            //一次加载20条数据
            mRxManager.register(mIModel.getTopMovieList(mStart, mCount).subscribe(new Consumer<HotMovieBean>() {
                @Override
                public void accept(HotMovieBean bean) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        if (bean != null && bean.getSubjects() != null &&
                                bean.getSubjects().size() > 0) {
                            mStart += mCount;
                            mIView.updateContentList(bean.getSubjects());
                        } else {
                            mIView.showNoMoreData();
                        }
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        mIView.showLoadMoreError();
                    }
                }
            }));

        }
    }

    @Override
    public void onItemClick(int position, SubjectsBean item, ImageView imageView) {
        MovieDetailActivity.start(mIView.getBindActivity(),item,imageView);
    }
}





















































