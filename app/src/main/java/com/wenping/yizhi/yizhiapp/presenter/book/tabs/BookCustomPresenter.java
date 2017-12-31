package com.wenping.yizhi.yizhiapp.presenter.book.tabs;

import android.widget.ImageView;

import com.wenping.yizhi.yizhiapp.contract.contract.book.tabs.BookCustomContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookItemBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookListBean;
import com.wenping.yizhi.yizhiapp.model.book.tabs.BookCustomModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.BookDetailActivity;

import io.reactivex.functions.Consumer;

/**
 *
 * @author WenPing
 * @date 12/30/2017
 */

public class BookCustomPresenter extends BookCustomContract.BookCustomPresenter{

    private int mStart;
    private int mCount = 30;
    private boolean isLoading;

    public static BookCustomPresenter newInstance() {
        return new BookCustomPresenter();
    }

    @Override
    public BookCustomContract.IBookCustomModel getModel() {
        return BookCustomModel.newInstance();    }

    @Override
    public void onStart() {
    }

    @Override
    public void loadLatestBookList() {
        if (mIModel != null && mIView != null) {
            mStart = 0;
            //一次加载20条数据
            mRxManager.register(mIModel.getBookListWithTag(mIView.getBookTags(),mStart,mCount)
            .subscribe(new Consumer<BookListBean>() {
                @Override
                public void accept(BookListBean bean) throws Exception {
                    if (mIView != null) {
                        mStart +=mCount;
                        mIView.updateContentList(bean.getBooks());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        if (mIView != null) {
                            if (mIView.isVisiable()) {
                                mIView.showToast("Network error");
                            }
                            mIView.showNetworkError();
                        }
                    }
                }
            }));
        }
    }

    @Override
    public void loadMoreBookList() {
        if (!isLoading) {
            isLoading = true;
            //一次加载20条数据
            mRxManager.register(mIModel.getBookListWithTag(mIView.getBookTags(), mStart, mCount)
            .subscribe(new Consumer<BookListBean>() {
                @Override
                public void accept(BookListBean bean) throws Exception {
                    isLoading = false;
                    if (mIView != null) {
                        if (bean != null && bean.getBooks() != null && bean.getBooks().size() > 0) {
                            mStart += mCount;
                            mIView.updateContentList(bean.getBooks());
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
    public void onItemClick(int position, BookItemBean item, ImageView imageView) {
        BookDetailActivity.start(mIView.getBindActivity(),item,imageView);
    }
}
