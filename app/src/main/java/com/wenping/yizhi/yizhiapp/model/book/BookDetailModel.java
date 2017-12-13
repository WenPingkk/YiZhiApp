package com.wenping.yizhi.yizhiapp.model.book;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.api.DoubanApi;
import com.wenping.yizhi.yizhiapp.contract.contract.book.BookDeatilContract;
import com.wenping.yizhi.yizhiapp.helper.RetrofitCreateHelper;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.douban.book.BookDetailBean;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by Horrarndoo on 2017/10/23.
 * <p>
 */

public class BookDetailModel extends BaseModel implements BookDeatilContract.IBookDetailModel {

    @NonNull
    public static BookDetailModel newInstance() {
        return new BookDetailModel();
    }

    @Override
    public Observable<BookDetailBean> getBookDetail(String id) {
        return RetrofitCreateHelper.createApi(DoubanApi.class, DoubanApi.HOST).getBookDetail(id)
                .compose(RxHelper.<BookDetailBean>rxSchedulerHelper());
    }
}
