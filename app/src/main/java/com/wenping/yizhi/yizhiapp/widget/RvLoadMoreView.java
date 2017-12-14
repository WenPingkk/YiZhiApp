package com.wenping.yizhi.yizhiapp.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.wenping.yizhi.yizhiapp.R;

/**
 * Created by WenPing on 2017/12/14.
 * 加载更多
 */

public class RvLoadMoreView extends LoadMoreView{
    @Override
    public int getLayoutId() {
        return R.layout.item_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
