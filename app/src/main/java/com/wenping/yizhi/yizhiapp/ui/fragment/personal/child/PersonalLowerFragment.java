package com.wenping.yizhi.yizhiapp.ui.fragment.personal.child;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.activity.AboutActivity;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.child.fragmenttabs.PersonalSettingFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author WenPing
 * @date 12/31/2017
 */

public class PersonalLowerFragment extends BaseCompatFragment {


    @BindView(R.id.tv_btn_settings)
    TextView mTvBtnSettings;
    @BindView(R.id.tv_btn_about)
    TextView mTvBtnAbout;

    public static PersonalLowerFragment newInstance() {
        Bundle bundle = new Bundle();
        PersonalLowerFragment fragment = new PersonalLowerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_lower;
    }


    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public boolean onBackPressedSupport() {
        //不处理，直接丢给Activity onBackPressedSupportchuli
        //若此处要拦截回退逻辑到HomeFragment，直接使用rxbus
        return false;
    }

    @OnClick({R.id.tv_btn_settings, R.id.tv_btn_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_settings:
                start(PersonalSettingFragment.newInstance());
                break;
            case R.id.tv_btn_about:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            default:
                break;
        }
    }
}
