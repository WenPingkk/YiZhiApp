package com.wenping.yizhi.yizhiapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * @author WenPing
 * @date 2017/12/13
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmetns;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmetns = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmetns.get(position);
    }

    @Override
    public int getCount() {
        return fragmetns == null ? 0 : fragmetns.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
