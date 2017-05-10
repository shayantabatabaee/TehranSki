package com.gravity.tehranski.business.storage.cache;

import android.support.v4.util.LruCache;

import com.gravity.tehranski.business.model.SkiResort;

public class CacheHelper extends LruCache<String, SkiResort> {


    public CacheHelper(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, SkiResort value) {
        return value.toString().getBytes().length;
    }
}
