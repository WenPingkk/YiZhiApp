package com.wenping.yizhi.yizhiapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoWelfareItemBean;

import java.util.List;

/**
 * Created by WenPing on 12/26/2017.
 *
 */

public class GankIoWelfareAdapter extends BaseCompatAdapter<GankIoWelfareItemBean, BaseViewHolder>{

    public GankIoWelfareAdapter(int layoutResId, @Nullable List<GankIoWelfareItemBean> data) {
        super(layoutResId, data);
    }

    public GankIoWelfareAdapter(@Nullable List<GankIoWelfareItemBean> data) {
        super(data);
    }

    public GankIoWelfareAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIoWelfareItemBean item) {

        Glide.with(mContext)
                .load(item.getUrl())
                .crossFade(500)
                .placeholder(R.mipmap.img_default_meizi)
                .into((ImageView) helper.getView(R.id.iv_item_image));
    }
}


































