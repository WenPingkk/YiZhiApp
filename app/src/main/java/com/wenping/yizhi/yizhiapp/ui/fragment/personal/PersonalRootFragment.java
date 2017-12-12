package com.wenping.yizhi.yizhiapp.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wenping.yizhi.yizhiapp.R;
import com.wenping.yizhi.yizhiapp.ui.fragment.base.fragment.BaseCompatFragment;

/**
 * Created by YinZeTong on 2017/12/12.
 */

public class PersonalRootFragment extends BaseCompatFragment{
    
    public static PersonalRootFragment newInstance() {
        Bundle bundle = new Bundle();
        PersonalRootFragment personalRootFragment  = new PersonalRootFragment();
        personalRootFragment.setArguments(bundle);
        return personalRootFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        // TODO: 2017/12/12  
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //加载fragment
        if (savedInstanceState == null) {
            loadFragment();
        } else {
            if (findChildFragment(PersonalRootFragment.class)==null) {
                loadFragment();
            }
        }
            
    }

    private void loadFragment() {
        // TODO: 2017/12/12  
//        loadRootFragment(R.id.fl_personal_container_upper, PersonalUpperFragment.newInstance());
//        loadRootFragment(R.id.fl_personal_container_lower, PersonalLowerFragment.newInstance());
    }
}
