package com.gravity.tehranski.app.ui.home.skiresort;

import com.gravity.tehranski.business.model.SkiResort;


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

        void attachView(SkiResortFragment skiResortFragment);

        void detachView();

    }
}
