package com.Gravity.Tehranski.business.storage.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.util.LruCache;

import com.Gravity.Tehranski.business.model.SkiResort;

/**
 * Created by shayantabatabee on 11/28/16.
 */
public class CacheHelper extends LruCache<String, SkiResort> {

    private static CacheHelper instance;

    private CacheHelper(int maxSize) {
        super(maxSize);
    }

    public static CacheHelper getInstance(Context context) {
        if (instance == null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            int availableMemoryInBytes = activityManager.getMemoryClass() * 1024 * 1024;
            int maxSize = availableMemoryInBytes / 16;
            instance = new CacheHelper(maxSize);
        }
        return instance;
    }

    @Override
    protected int sizeOf(String key, SkiResort value) {
        return value.toString().getBytes().length;
    }
}
