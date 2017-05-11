package com.gravity.tehranski.app.dagger;


import com.gravity.tehranski.app.ui.home.skiresort.SkiResortContract;
import com.gravity.tehranski.app.ui.home.skiresort.SkiResortPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    SkiResortContract.Presenter provideSkiResortPresenter() {
        return new SkiResortPresenter();
    }
}
