package com.wenping.yizhi.yizhiapp.ui.activity.personal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.base.activity.BaseCompatActivity;
import com.wenping.yizhi.yizhiapp.constant.HeadConstant;
import com.wenping.yizhi.yizhiapp.helper.RxHelper;
import com.wenping.yizhi.yizhiapp.model.bean.rxbus.RxEventHeadBean;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.utils.ResourcesUtils;
import com.wenping.yizhi.yizhiapp.widget.headclip.ClipViewLayout;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

import static com.wenping.yizhi.yizhiapp.constant.RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI;

/**
 *
 * @author WenPing
 * @date 12/31/2017
 */

public class HeadSettingActivity extends BaseCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cvl_rect)
    ClipViewLayout cvlRect;

    @Override
    protected void initData() {
        super.initData();
//        Logger.e("RxBus.get().register(this)");
        RxBus.get().register(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbar.setTitle("选取头像");
        toolbar.setTitleTextColor(ResourcesUtils.getColor(R.color.md_white));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });

        cvlRect.setImageSrc(getIntent().getData());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_head_setting;
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                onBackPressedSupport();
                break;
            case R.id.tv_ok:
                Observable.create(new ObservableOnSubscribe<Uri>() {
                    @Override
                    public void subscribe(ObservableEmitter<Uri> e) throws
                            Exception {
                        e.onNext(generateUri());
                        e.onComplete();
                    }
                }).compose(RxHelper.<Uri>rxSchedulerHelper())
                        .subscribe(new Consumer<Uri>() {
                            @Override
                            public void accept(Uri uri) throws Exception {
                                RxEventHeadBean rxEventHeadBean = new RxEventHeadBean(uri);
                                RxBus.get().send(RX_BUS_CODE_HEAD_IMAGE_URI, rxEventHeadBean);
                                onBackPressedSupport();
                            }
                        });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Logger.e("RxBus.get().unRegister(this)");
        RxBus.get().unRegister(this);
    }

    /**
     * 生成Uri
     */
    private Uri generateUri() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = cvlRect.clip();
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), HeadConstant.HEAD_IMAGE_NAME + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mSaveUri;
    }
}
