package com.Gravity.TehranSki.app.home;

import android.support.v4.util.SparseArrayCompat;

import com.Gravity.TehranSki.business.model.SkiResort;


public class HomePresenter implements HomeContract.HomePresenter {


    //data objects
    private SparseArrayCompat<SkiResort> skiResortHashMap;
    private int currentPosition;
    //view object
    private HomeContract.HomeActivity homeActivity;

    public HomePresenter(HomeActivity homeActivity) {
        skiResortHashMap = new SparseArrayCompat<>();
        this.homeActivity = homeActivity;
    }

    @Override
    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }


    @Override
    public void setBackground(int position, SkiResort skiResort) {
        skiResortHashMap.put(position, skiResort);
        setBackground();

    }

    @Override
    public void setBackground() {
        final SkiResort skiResort = skiResortHashMap.get(currentPosition);

        if (skiResort == null) {
            return;
        }

        homeActivity.setActivityBackground(skiResort.getForecasts().get(0).get_plcname());

    }
}
