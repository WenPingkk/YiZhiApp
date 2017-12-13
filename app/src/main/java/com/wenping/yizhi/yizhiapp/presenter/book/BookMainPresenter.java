package com.wenping.yizhi.yizhiapp.presenter.book;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.book.BookMainContract;
import com.wenping.yizhi.yizhiapp.model.book.BookMainModel;


/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookMainPresenter extends BookMainContract.BookMainPresenter {
    @NonNull
    public static BookMainPresenter newInstance() {
        return new BookMainPresenter();
    }

    @Override
    public void getTabList() {
        if (mIView == null || mIModel == null)
            return;

        mIView.showTabList(mIModel.getTabs());
    }

    @Override
    public BookMainContract.IBookMainModel getModel() {
        return BookMainModel.newInstance();
    }

    @Override
    public void onStart() {
    }
}
