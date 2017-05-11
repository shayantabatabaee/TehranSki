package com.gravity.tehranski.app.dagger;


import com.gravity.tehranski.app.ui.home.skiresort.SkiResortContract;
import com.gravity.tehranski.app.ui.home.skiresort.SkiResortPresenter;
import com.gravity.tehranski.business.SkiResortRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    SkiResortContract.Presenter provideSkiResortPresenter(SkiResortRepository skiResortRepository) {
        return new SkiResortPresenter(skiResortRepository);
    }
}
