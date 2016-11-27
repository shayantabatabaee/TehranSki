package com.gravity.tehranski.business.model;

import java.util.ArrayList;

public class SkiResortList {

    private static SkiResortList ourInstance = new SkiResortList();
    private static ArrayList<String> resortsName;


    public static SkiResortList getInstance() {
        return ourInstance;
    }

    private SkiResortList() {

        resortsName = new ArrayList<>();

        resortsName.add("Montafon");
        resortsName.add("SecretGarden");
        resortsName.add("Shemshak");
        resortsName.add("Tochal");
        resortsName.add("Cochran");
        resortsName.add("Dizin");
        resortsName.add("Darbandsar");
        resortsName.add("Abali");

    }

    public ArrayList<String> getResortsName() {
        return resortsName;
    }
}
