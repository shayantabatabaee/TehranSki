package com.gravity.tehranski.app.dagger;


import android.content.Context;

import com.gravity.tehranski.app.ui.home.skiresort.SkiResortContract;
import com.gravity.tehranski.app.ui.home.skiresort.SkiResortPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    SkiResortContract.Presenter provideSkiResortPresenter(Context context) {
        return new SkiResortPresenter(context);
    }
}
