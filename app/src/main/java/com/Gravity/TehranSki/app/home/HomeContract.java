package com.Gravity.TehranSki.app.home;


import com.Gravity.TehranSki.business.model.SkiResort;

public interface HomeContract {

    interface HomeActivity {

        void setActivityBackground(String presenterBackground);

    }

    interface HomePresenter {

        void setBackground(int position, SkiResort skiResort);

        void setBackground();

        void setCurrentPosition(int position);

    }

}
