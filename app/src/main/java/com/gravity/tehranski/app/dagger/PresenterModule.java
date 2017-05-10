package com.gravity.tehranski.app.dagger;


import com.gravity.tehranski.app.ui.home.skiresort.SkiResortPresenter;

import javax.inject.Singleton;

import dagger.Provides;

public class PresenterModule {
    @Provides
    @Singleton
    SkiResortPresenter provideSkiResortPresenter(){
        return new SkiResortPresenter();
    }
}
