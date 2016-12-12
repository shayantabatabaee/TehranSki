package com.Gravity.Tehranski.business.model;

import java.util.ArrayList;

public final class SkiResort {

    private String resortName;
    private String heightLevel;
    private ArrayList<ForeCast> forecasts;


    public SkiResort(String resortName, String heightLevel) {
        this.heightLevel =heightLevel;
        this.resortName = resortName;
    }

    public void setForecasts(ArrayList<ForeCast> forecasts) {
        this.forecasts = forecasts;
    }

    public String getHeightLevel() {
        return heightLevel;
    }

    public String getResortName() {
        return resortName;
    }

    public ArrayList<ForeCast> getForecasts() {
        return forecasts;
    }
}