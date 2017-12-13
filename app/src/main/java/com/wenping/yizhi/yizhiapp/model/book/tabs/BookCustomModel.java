package com.wenping.yizhi.yizhiapp.model.book.tabs;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.DoubanApi;
import com.wenping.yizhi.yizhiapp.contract.contract.book.tabs.BookCustomContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookListBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/21.
 * <p>
 */

public class BookCustomModel extends BaseModel implements BookCustomContract.IBookCustomModel {
    @NonNull
    public static BookCustomModel newInstance() {
        return new BookCustomModel();
    }

    @Override
    public Observable<BookListBean> getBookListWithTag(String tag, int start, int count) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getBookListWithTag
                (tag, start, count).compose(RxHelper.<BookListBean>rxSchedulerHelper());
    }
}
