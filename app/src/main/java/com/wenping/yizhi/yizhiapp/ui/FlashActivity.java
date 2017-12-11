package com.wenping.yizhi.yizhiapp.ui;

import android.Manifest;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by YinZeTong on 2017/12/11.
 */

public class FlashActivity extends BaseCompatActivity {

    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.ll_skip)
    LinearLayout mLlSkip;

    @Override
    protected void initView(Bundle savedInstanceState) {
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限
        requestPermissions();
    }

    private void requestPermissions() {
        RxPermissions rxPermissions  = new RxPermissions(FlashActivity.this);

        //请求权限,获取全部结果
        rxPermissions.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (!granted) {
                    ToastUtils.showToast("App未获取");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash;
    }

}
