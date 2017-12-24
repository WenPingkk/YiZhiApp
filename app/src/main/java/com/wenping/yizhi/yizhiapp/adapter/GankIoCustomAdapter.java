package com.wenping.yizhi.yizhiapp.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.config.DBConfig;
import com.wenping.yizhi.yizhiapp.config.ItemState;
import com.wenping.yizhi.yizhiapp.model.bean.gankio.GankIoCustomItemBean;
import com.wenping.yizhi.yizhiapp.utils.DBUtils;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;
import com.wenping.yizhi.yizhiapp.utils.StringUtils;
import com.wenping.yizhi.yizhiapp.widget.RvLoadMoreView;

import java.util.List;

/**
 * Created by WenPing on 12/24/2017.
 *
 */

public class GankIoCustomAdapter extends BaseMultiItemQuickAdapter<GankIoCustomItemBean,
        BaseViewHolder> {
    private String mImageSize = "?imageView2/0/w/200";
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GankIoCustomAdapter(List<GankIoCustomItemBean> data) {
        super(data);
        setLoadMoreView(new RvLoadMoreView());

        setEnableLoadMore(true);

        openLoadAnimation();

        addItemType(GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_NORMAL, R.layout
                .item_gank_io_custom_normal);
        addItemType(GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_IMAGE, R.layout
                .item_gank_io_custom_image);
        addItemType(GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_NO_IMAGE, R.layout
                .item_gank_io_custom_no_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIoCustomItemBean item) {
        //初始化多种type,类型.
        initTypeImage(helper,item);

        helper.setText(R.id.tv_item_who, StringUtils.isEmpty(item.getWho()) ? "佚名" : item
                .getWho());
        helper.setText(R.id.tv_item_type, item.getType());
        helper.setText(R.id.tv_item_time, item.getCreatedAt().substring(0, 10));


        switch (helper.getItemViewType()) {
            case GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_NORMAL:
                helper.setText(R.id.tv_item_title, item.getDesc());
                initTitleColor(helper, item);
                if (item.getImages() != null) {
                    if (item.getImages().size() > 0 && !TextUtils.isEmpty(item.getImages().get(0)))
                        Glide.with(mContext).load(item.getImages().get(0) + mImageSize)
                                .asBitmap()
                                .into((ImageView) helper.getView(R.id.iv_item_image));
                }
                break;
            case GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_IMAGE:
                Glide.with(mContext)
                        .load(item.getUrl())
                        .asBitmap()
                        .centerCrop()
                        .placeholder(R.mipmap.img_default_meizi)
                        .into((ImageView) helper.getView(R.id.iv_item_image));
                break;
            case GankIoCustomItemBean.GANK_IO_DAY_ITEM_CUSTOM_NO_IMAGE:
                helper.setText(R.id.tv_item_title, item.getDesc());
                initTitleColor(helper, item);
                break;
            default:
                break;
        }
    }

    private void initTitleColor(BaseViewHolder helper, GankIoCustomItemBean item) {
        if (DBUtils.getDB(mContext).isRead(DBConfig.TABLE_GANKIO_CUSTOM, item.getType() + item
                .get_id(), ItemState.STATE_IS_READ)) {
            helper.setTextColor(R.id.tv_item_title, Color.GRAY);
        } else {
            if (SpUtils.getNightModel(mContext)) {
                helper.setTextColor(R.id.tv_item_title, Color.WHITE);
            } else {
                helper.setTextColor(R.id.tv_item_title, Color.BLACK);
            }
        }
    }

    private void initTypeImage(BaseViewHolder helper, GankIoCustomItemBean item) {
        switch (item.getType()) {
            case "福利":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable
                        .ic_vector_title_welfare);
                break;
            case "Android":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable
                        .ic_vector_title_android);
                break;
            case "iOS":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_title_ios);
                break;
            case "前端":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_title_front);
                break;
            case "休息视频":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_title_video);
                break;
            case "瞎推荐":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_item_tuijian);
                break;
            case "拓展资源":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_item_tuozhan);
                break;
            case "App":
                helper.setImageResource(R.id.iv_type_item_title, R.drawable.ic_vector_item_app);
                break;
            default:
                break;
        }
    }
}
