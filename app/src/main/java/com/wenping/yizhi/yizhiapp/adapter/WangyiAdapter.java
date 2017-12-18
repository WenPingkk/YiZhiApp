package com.wenping.yizhi.yizhiapp.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.config.DBConfig;
import com.wenping.yizhi.yizhiapp.config.ItemState;
import com.wenping.yizhi.yizhiapp.model.bean.wangyi.WangyiNewsItemBean;
import com.wenping.yizhi.yizhiapp.utils.DBUtils;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;

import java.util.List;

/**
 * Created by WenPing on 2017/12/18.
 * 网易新闻Adapter
 * <p>
 */

public class WangyiAdapter extends BaseCompatAdapter<WangyiNewsItemBean,BaseViewHolder>{

    public WangyiAdapter(int layoutResId, @Nullable List<WangyiNewsItemBean> data) {
        super(layoutResId, data);
    }

    public WangyiAdapter(@Nullable List<WangyiNewsItemBean> data) {
        super(data);
    }


    public WangyiAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, WangyiNewsItemBean item) {
        if (DBUtils.getDB(mContext).isRead(DBConfig.TABLE_WANGYI, item.getDocid(), ItemState.STATE_IS_READ)) {
            helper.setTextColor(R.id.tv_item_title, Color.GRAY);
        } else {
            if (SpUtils.getNightModel(mContext)) {
                helper.setTextColor(R.id.tv_item_title, Color.WHITE);
            } else {
                helper.setTextColor(R.id.tv_item_title,Color.BLACK);
            }
        }
        helper.setText(R.id.tv_item_title, item.getTitle());
        helper.setText(R.id.tv_item_who, item.getSource());
        helper.setText(R.id.tv_item_time, item.getPtime());
        Glide.with(mContext).load(item.getImgsrc()).crossFade().into((ImageView) helper.getView(R.id.iv_item_image));
    }
}
