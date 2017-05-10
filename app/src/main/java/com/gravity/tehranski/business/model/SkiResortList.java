package com.gravity.tehranski.business.model;

import java.util.ArrayList;

public class SkiResortList {

    private static SkiResortList ourInstance = new SkiResortList();
    private static ArrayList<String> resortNames;


    public static SkiResortList getInstance() {
        return ourInstance;
    }

    private SkiResortList() {

        resortNames = new ArrayList<>();
        resortNames.add("Tochal");
        resortNames.add("Shemshak");
        resortNames.add("Dizin");
        resortNames.add("Badaling");
        resortNames.add("Darbandsar");
        resortNames.add("Alta");
        resortNames.add("Abali");

    }

    public ArrayList<String> getResortsName() {
        return resortNames;
    }
}
