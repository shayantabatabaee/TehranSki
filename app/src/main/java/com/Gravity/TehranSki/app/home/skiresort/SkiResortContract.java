package com.Gravity.TehranSki.app.home.skiresort;

import com.Gravity.TehranSki.business.model.SkiResort;


public interface SkiResortContract {

    interface View {

        void displayData(SkiResort skiResort);

        void setActivityBackground(SkiResort skiResort);

        void showOnFailureMessage(String message);

        void showError(String message);

    }

    interface Presenter {

        void getSkiResort(String resortName);

        void refreshSkiResort(String resortName);

    }
}
