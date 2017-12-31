package com.wenping.yizhi.yizhiapp.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.child.PersonalLowerFragment;
import com.wenping.yizhi.yizhiapp.ui.fragment.personal.child.PersonalUpperFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author WenPing
 * @date 2017/12/12
 */

public class PersonalRootFragment extends BaseCompatFragment {

    @BindView(R.id.fl_personal_container_upper)
    FrameLayout mFlPersonalContainerUpper;
    @BindView(R.id.fl_personal_container_lower)
    FrameLayout mFlPersonalContainerLower;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    Unbinder unbinder;

    public static PersonalRootFragment newInstance() {
        Bundle bundle = new Bundle();
        PersonalRootFragment personalRootFragment = new PersonalRootFragment();
        personalRootFragment.setArguments(bundle);
        return personalRootFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("我的");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //加载fragment
        if (savedInstanceState == null) {
            loadFragment();
        } else {
            if (findChildFragment(PersonalUpperFragment.class) == null) {
                loadFragment();
            }
        }

    }

    private void loadFragment() {
        loadRootFragment(R.id.fl_personal_container_upper, PersonalUpperFragment.newInstance());
        loadRootFragment(R.id.fl_personal_container_lower, PersonalLowerFragment.newInstance());
    }
}
