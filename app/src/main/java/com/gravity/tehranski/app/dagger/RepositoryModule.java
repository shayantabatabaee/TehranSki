package com.gravity.tehranski.app.dagger;

import android.app.ActivityManager;
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
        return new SkiResortRepository(context);
    }

    @Singleton
    @Provides
    CacheHelper provideCacheHelper(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int availableMemoryInBytes = activityManager.getMemoryClass() * 1024 * 1024;
        int maxSize = availableMemoryInBytes / 16;
        return new CacheHelper(maxSize);
    }

    @Provides
    VolleyHelper provideVolleyHelper(Context context) {
        return new VolleyHelper(context);
    }
}
