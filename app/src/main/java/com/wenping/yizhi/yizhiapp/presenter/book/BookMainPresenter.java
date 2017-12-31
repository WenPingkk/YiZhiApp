package com.wenping.yizhi.yizhiapp.presenter.book;

import com.wenping.yizhi.yizhiapp.contract.contract.book.BookMainContract;
import com.wenping.yizhi.yizhiapp.model.book.BookMainModel;

/**
 *
 * @author WenPing
 * @date 12/30/2017
 */

public class BookMainPresenter extends BookMainContract.BookMainPresenter{

    public static BookMainPresenter newInstance() {
        return new BookMainPresenter();
    }

    @Override
    public BookMainContract.IBookMainModel getModel() {
        return BookMainModel.newInstance();
    }

    @Override
    public void onStart() {

    }

    /**
     * 继承抽象类BookMainContract.BookMainPresenter实现ta的方法
     */
    @Override
    public void getTabList() {
        if (mIView != null && mIModel != null) {
            mIView.showTabList(mIModel.getTabs());
        }
    }
}
