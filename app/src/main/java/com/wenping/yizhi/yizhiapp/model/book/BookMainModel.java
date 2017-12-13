package com.wenping.yizhi.yizhiapp.model.book;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.book.BookMainContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;


/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookMainModel extends BaseModel implements BookMainContract.IBookMainModel {

    @NonNull
    public static BookMainModel newInstance() {
        return new BookMainModel();
    }

    @Override
    public String[] getTabs() {
        return new String[]{"文学", "文化", "生活"};
    }
}
