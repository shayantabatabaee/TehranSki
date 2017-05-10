package com.gravity.tehranski.app.dagger;

import android.content.Context;

import com.gravity.tehranski.business.SkiResortRepository;
import com.gravity.tehranski.business.net.VolleyHelper;
import com.gravity.tehranski.business.storage.cache.CacheHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    SkiResortRepository provideSkiResortRepository(Context context) {
        return SkiResortRepository.getInstance(context);
    }

    @Singleton
    @Provides
    CacheHelper provideCacheHelper(Context context) {
        return CacheHelper.getInstance(context);
    }

    @Provides
    VolleyHelper provideVolleyHelper(Context context) {
        return new VolleyHelper(context);
    }
}
