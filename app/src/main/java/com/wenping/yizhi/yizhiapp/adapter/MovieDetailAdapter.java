package com.wenping.yizhi.yizhiapp.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.PersonBean;

import java.util.List;

/**
 *
 * @author WenPing
 * @date 12/28/2017
 */

public class MovieDetailAdapter extends BaseCompatAdapter<PersonBean, BaseViewHolder> {

    public MovieDetailAdapter(int layoutResId, @Nullable List<PersonBean> data) {
        super(layoutResId, data);
    }

    public MovieDetailAdapter(@Nullable List<PersonBean> data) {
        super(data);
    }

    public MovieDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tv_person_name, item.getName());
        helper.setText(R.id.tv_person_type, item.getType());
        Glide.with(mContext).load(item.getAvatars().getLarge()).crossFade().into((ImageView)
                helper.getView(R.id.iv_avatar_photo));
    }
}
