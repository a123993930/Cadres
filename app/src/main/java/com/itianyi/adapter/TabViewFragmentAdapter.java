package com.itianyi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.itianyi.cadres.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by 沫 on 2015/7/21.
 */
public class TabViewFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private Context context;
    public TabViewFragmentAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }
}
