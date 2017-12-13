package com.wenping.yizhi.yizhiapp.model.detail;

import android.support.annotation.NonNull;

import com.wenping.yizhi.yizhiapp.contract.contract.detail.WebViewLoadConaract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BaseModel;

/**
 * Created by Horrarndoo on 2017/10/20.
 * <p>
 */

public class WebViewLoadModel extends BaseModel implements
        WebViewLoadConaract.IWebViewLoadModel {

    @NonNull
    public static WebViewLoadModel newInstance() {
        return new WebViewLoadModel();
    }
}
