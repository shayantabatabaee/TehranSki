package com.gravity.tehranski.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.model.SkiResortList;

public class FragmentAdapter extends FragmentPagerAdapter {

    private MyFragment[] myFragment;


    public FragmentAdapter(FragmentManager fm) {
        super(fm);

        myFragment = new MyFragment[SkiResortList.getInstance().getResortsName().size()];
    }

    @Override
    public int getCount() {
        return SkiResortList.getInstance().getResortsName().size();
    }

    @Override
    public Fragment getItem(int position) {


        if (myFragment[position] == null) {
            myFragment[position] = new MyFragment();

        }

        return myFragment[position];
    }

    public SkiResort getSkiResort(int position) {
        return myFragment[position].getSkiResort();
    }

    public void displayData(SkiResort skiResort, int position) {

        if (myFragment[position] != null) {
            myFragment[position].displayData(skiResort);
        }
    }

    public void hideData() {

        for (int i = 0; i < getCount(); i++) {
            if (myFragment[i] != null) {
                myFragment[i].hideData();
            }
        }
    }


}

