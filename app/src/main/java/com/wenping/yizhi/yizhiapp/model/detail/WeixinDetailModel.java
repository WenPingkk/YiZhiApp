package com.wenping.yizhi.yizhiapp.model.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.detail.WeixinDetailContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;


/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinDetailModel extends BaseModel implements WeixinDetailContract.IWeixinDetailModel {
    @NonNull
    public static WeixinDetailModel newInstance() {
        return new WeixinDetailModel();
    }
}
