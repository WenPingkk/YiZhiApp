package com.wenping.yizhi.yizhiapp.presenter.book.tabs;

import android.os.Bundle;

import com.wenping.yizhi.yizhiapp.constant.BundleKeyConstant;
import com.wenping.yizhi.yizhiapp.contract.contract.book.BookDeatilContract;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookDetailBean;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookItemBean;
import com.wenping.yizhi.yizhiapp.model.book.BookDetailModel;
import com.wenping.yizhi.yizhiapp.ui.activity.detail.WebViewLoadActivity;

import io.reactivex.functions.Consumer;

/**
 *
 * @author WenPing
 * @date 12/31/2017
 */

public class BookDetailPresenter extends BookDeatilContract.BookDetailPresenter {

    public static BookDetailPresenter newInstance() {
        return new BookDetailPresenter();
    }

    @Override
    public BookDeatilContract.IBookDetailModel getModel() {
        return BookDetailModel.newInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void loadBookDetail(String id) {
        if (mIView != null && mIModel != null) {
            mRxManager.register(mIModel.getBookDetail(id).subscribe(new Consumer<BookDetailBean>() {
                @Override
                public void accept(BookDetailBean bean) throws Exception {
                    if (mIView != null) {
                        mIView.showBookDetail(bean);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    if (mIView != null) {
                        mIView.showToast("Network error.");
                        mIView.showNetworkError();
                    }
                }
            }));
        }
    }

    @Override
    public void onHeaderClick(BookItemBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_TITLE, bean.getTitle());
        bundle.putString(BundleKeyConstant.ARG_KEY_WEB_VIEW_LOAD_URL, bean.getAlt());
        mIView.startNewActivity(WebViewLoadActivity.class, bundle);
    }
}
