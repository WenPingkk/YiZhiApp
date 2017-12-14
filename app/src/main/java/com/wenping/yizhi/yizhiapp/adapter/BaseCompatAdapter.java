package com.wenping.yizhi.yizhiapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenping.yizhi.yizhiapp.widget.RvLoadMoreView;

import java.util.List;



/**
 * Created by WenPing on 2017/12/14.
 */

public abstract class BaseCompatAdapter<T,K extends BaseViewHolder> extends BaseQuickAdapter<T,K>{
    public BaseCompatAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        init();
    }

    public BaseCompatAdapter(@Nullable List<T> data) {
        super(data);
        init();

    }

    public BaseCompatAdapter(int layoutResId) {
        super(layoutResId);
        init();
    }

    public void init() {
        setLoadMoreView(new RvLoadMoreView());
        setEnableLoadMore(true);
        //开启默认动画载入(仅开启加载 新item时开启动画)
        openLoadAnimation();
    }

}
