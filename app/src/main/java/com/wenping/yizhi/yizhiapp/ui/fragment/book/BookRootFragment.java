package com.wenping.yizhi.yizhiapp.ui.fragment.book;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;

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
        return R.layout.fragment_gank_io;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        if (findChildFragment(BookRootFragment.class) == null) {
            loadRootFragment(R.id.fl_container,BookRootFragment.newInstance());
        }
    }
}
