package com.gravity.tehranski.app.ui.home.skiresort;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class SkiResortAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> resortNames;

    public SkiResortAdapter(FragmentManager fm, ArrayList<String> resortNames) {
        super(fm);
        this.resortNames = resortNames;
    }

    @Override
    public int getCount() {
        return resortNames == null ? 0 : resortNames.size();
    }

    @Override
    public Fragment getItem(int position) {
        return SkiResortFragment.newInstance(resortNames.get(position), position);
    }
}

