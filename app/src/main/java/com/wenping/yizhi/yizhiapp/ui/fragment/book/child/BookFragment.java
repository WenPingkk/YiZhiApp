package com.wenping.yizhi.yizhiapp.ui.fragment.book.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.book.BookMainContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;

/**
 * Created by YinZeTong on 2017/12/13.
 */

public class BookFragment extends BaseMVPCompatFragment<BookMainContract.BookMainPresenter,
        BookMainContract.IBookMainModel> implements BookMainContract.IBookMainView{

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void showTabList(String[] tabs) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
