package com.gravity.tehranski.app.dagger;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @CustomAnnotation.HeightLevelName
    public String provideHeightLevel() {
        return "max";
    }

}
