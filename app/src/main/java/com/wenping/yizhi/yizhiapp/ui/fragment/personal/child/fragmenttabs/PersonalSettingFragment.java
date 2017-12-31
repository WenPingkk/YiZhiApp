package com.wenping.yizhi.yizhiapp.ui.fragment.personal.child.fragmenttabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;

import butterknife.BindView;

/**
 *
 * @author WenPing
 * @date 12/31/2017
 */

public class PersonalSettingFragment extends BaseCompatFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static PersonalSettingFragment newInstance() {
        Bundle bundle = new Bundle();
        PersonalSettingFragment fragment = new PersonalSettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_setting;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
    }
}
