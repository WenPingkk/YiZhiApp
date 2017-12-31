package com.wenping.yizhi.yizhiapp.ui.fragment.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.book.child.BookFragment;

/**
 * Created by WenPing on 2017/12/12.
 */

public class BookRootFragment extends BaseCompatFragment {

    public static BookRootFragment newInstance(){
        Bundle bundle = new Bundle();
        BookRootFragment bookRootFragment = new BookRootFragment();
        bookRootFragment.setArguments(bundle);
        return bookRootFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(BookFragment.class) == null) {
            loadRootFragment(R.id.fl_container, BookFragment.newInstance());
        }
    }
}
