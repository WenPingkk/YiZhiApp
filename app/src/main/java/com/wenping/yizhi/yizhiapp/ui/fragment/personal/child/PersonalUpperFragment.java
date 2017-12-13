package com.wenping.yizhi.yizhiapp.ui.fragment.personal.child;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.contract.contract.personal.PersonalContract;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.BasePresenter;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseMVPCompatFragment;

import java.io.File;

/**
 * Created by YinZeTong on 2017/12/13.
 */

public class PersonalUpperFragment extends BaseMVPCompatFragment<PersonalContract
        .PersonalUpperPresenter, PersonalContract.IPersonalUpperModel> implements PersonalContract.IPersonalUpperView {

    public static PersonalUpperFragment newInstance() {
        Bundle args = new Bundle();
        PersonalUpperFragment fragment = new PersonalUpperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initPopupView() {

    }

    @Override
    public void showHead(Bitmap bitmap) {

    }

    @Override
    public void showPopupView() {

    }

    @Override
    public void dismissPopupView() {

    }

    @Override
    public boolean popupIsShowing() {
        return false;
    }

    @Override
    public void gotoHeadSettingActivity(Uri uri) {

    }

    @Override
    public void gotoSystemPhoto(int requestCode) {

    }

    @Override
    public void gotoSystemCamera(File tempFile, int requestCode) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_upper;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

    }
}
