package com.wenping.yizhi.yizhiapp.model.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.detail.GankIoDetailContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;


/**
 * Created by Horrarndoo on 2017/10/11.
 * <p>
 */

public class GankIoDetailModel extends BaseModel implements GankIoDetailContract
        .IGankIoDetailModel {

    @NonNull
    public static GankIoDetailModel newInstance() {
        return new GankIoDetailModel();
    }
}
