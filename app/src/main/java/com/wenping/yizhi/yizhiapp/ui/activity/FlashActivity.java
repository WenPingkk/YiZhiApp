package com.wenping.yizhi.yizhiapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wansir.lib.logger.Logger;
import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by WenPing on 2017/12/11.
 */

public class FlashActivity extends BaseCompatActivity {

    @BindView(R.id.tv_count_down)
    TextView mTvCountDown;
    @BindView(R.id.ll_skip)
    LinearLayout mLlSkip;

    private int mTime = 3;
    private boolean mIsCancle = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限
        requestPermissions();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        mIsCancle = true;
        setIsTransAnim(false);
        this.finish();
    }

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(FlashActivity.this);

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
                    ToastUtils.showToast("App未获取suoyou quanxian ");
                    //不管是否获取全部权限，进入主页面
                }
                    initCountDownTimer();
            }
        });
        //分别请求权限
        //        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_CALENDAR,
        //                Manifest.permission.READ_CALL_LOG,
        //                Manifest.permission.READ_CONTACTS,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.READ_SMS,
        //                Manifest.permission.RECORD_AUDIO,
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.CALL_PHONE,
        //                Manifest.permission.SEND_SMS)
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限。魅族pro6s动态获取权限不会回调下面的方法
        //        rxPermission.requestEach(
        //                Manifest.permission.CAMERA,
        //                Manifest.permission.READ_PHONE_STATE,
        //                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //                Manifest.permission.READ_EXTERNAL_STORAGE,
        //                Manifest.permission.ACCESS_COARSE_LOCATION)
        //                .subscribe(new Consumer<Permission>() {
        //                    @Override
        //                    public void accept(Permission permission) throws Exception {
        //                        if (permission.granted) {
        //                            // 用户已经同意该权限
        //                            Log.d(TAG, permission.name + " is granted.");
        //                        } else if (permission.shouldShowRequestPermissionRationale) {
        //                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
        //                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
        //                        } else {
        //                            // 用户拒绝了该权限，并且选中『不再询问』
        //                            Log.d(TAG, permission.name + " is denied.");
        //                        }
        //                    }
        //                });
    }

    private void initCountDownTimer() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(3)//计数次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return mTime - aLong;//3-0,3-1,3-2
                    }
                }).compose(RxHelper.<Long>rxSchedulerHelper())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.e("value = " + aLong);
                        mTvCountDown.setText(String.valueOf(aLong));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (!mIsCancle) {
                            Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @OnClick(R.id.ll_skip)
    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_skip:
                mIsCancle = true;
                Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;

        }
    }
}








































































