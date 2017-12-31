package com.wenping.yizhi.yizhiapp.ui.fragment.personal.child;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.constant.RxBusCode;
import com.wenping.yizhi.yizhiapp.contract.contract.personal.PersonalContract;
import com.wenping.yizhi.yizhiapp.model.bean.rxbus.RxEventHeadBean;
import com.wenping.yizhi.yizhiapp.presenter.personal.PersonalUpperPresenter;
import com.wenping.yizhi.yizhiapp.rxbus.RxBus;
import com.wenping.yizhi.yizhiapp.rxbus.Subscribe;
import com.wenping.yizhi.yizhiapp.ui.activity.personal.HeadSettingActivity;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;
import com.wenping.yizhi.yizhiapp.utils.AppUtils;
import com.wenping.yizhi.yizhiapp.utils.FileUtils;
import com.wenping.yizhi.yizhiapp.widget.PersonalPopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author WenPing
 * @date 2017/12/13
 */

public class PersonalUpperFragment extends BaseMVPCompatFragment<PersonalContract
        .PersonalUpperPresenter, PersonalContract.IPersonalUpperModel> implements PersonalContract.IPersonalUpperView {


    PersonalPopupWindow popupWindow;

    @BindView(R.id.civ_head)
    CircleImageView mCivHead;


    public static PersonalUpperFragment newInstance() {
        Bundle args = new Bundle();
        PersonalUpperFragment fragment = new PersonalUpperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_upper;
    }

    @Override
    public void initData() {
        super.initData();
        RxBus.get().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initPopupView();
    }

    @OnClick(R.id.civ_head)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_head:
                mPresenter.btnHeadClicked();
                break;
            default:
                break;
        }
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return PersonalUpperPresenter.newInstance();
    }

    /**
     * 初始化popupwindow
     */
    @Override
    public void initPopupView() {
        popupWindow = new PersonalPopupWindow(mActivity);
        popupWindow.setOnItemClickListener(new PersonalPopupWindow.OnItemClickListener() {
            @Override
            public void onCaremaClicked() {
                mPresenter.btnCameraClicked();
            }

            @Override
            public void onPhotoClicked() {
                mPresenter.btnPhotoClicked();
            }

            @Override
            public void onCancelClicked() {
                mPresenter.btnCancelClicked();
            }
        });
    }

    @Override
    public void showHead(Bitmap bitmap) {
        mCivHead.setImageBitmap(bitmap);
    }

    @Override
    public void showPopupView() {
        View parent = LayoutInflater.from(mActivity).inflate(R.layout.fragment_personal, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
    }

    @Override
    public void dismissPopupView() {
        popupWindow.dismiss();
    }

    @Override
    public boolean popupIsShowing() {
        return popupWindow.isShowing();
    }

    @Override
    public void gotoHeadSettingActivity(Uri uri) {
        if (uri != null) {
            Intent intent = new Intent(mActivity, HeadSettingActivity.class);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @Override
    public void gotoSystemPhoto(int requestCode) {
        //跳转到系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), requestCode);
    }

    @Override
    public void gotoSystemCamera(File tempFile, int requestCode) {
        //跳转到调用系统相机

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            //            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //            Uri contentUri = FileProvider.getUriForFile(mActivity, BuildConfig.APPLICATION_ID + "" +
            //                    ".fileProvider", tempFile);
            //            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mPresenter.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * rxBus接收图片Uri
     * @param bean
     */
    @Subscribe(code = RxBusCode.RX_BUS_CODE_HEAD_IMAGE_URI)
    public void rxBusEvent(RxEventHeadBean bean) {
        Uri uri = bean.getUri();
        if (uri != null) {
            String cropImagePath = FileUtils.getRealFilePathFromUri(AppUtils.getContext(), uri);
            Bitmap bitmap = BitmapFactory.decodeFile(cropImagePath);
            if (bitmap != null) {
                //设置图片
                mCivHead.setImageBitmap(bitmap);
            }
        }
    }
}





















