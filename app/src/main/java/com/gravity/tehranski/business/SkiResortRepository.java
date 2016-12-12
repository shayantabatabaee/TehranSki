package com.Gravity.TehranSki.business;

import android.content.Context;

import com.Gravity.TehranSki.business.model.SkiResort;
import com.Gravity.TehranSki.business.net.VolleyHelper;
import com.Gravity.TehranSki.business.storage.cache.CacheHelper;


public class SkiResortRepository {

    private CacheHelper cacheHelper;
    private VolleyHelper volleyHelper;
    private static SkiResortRepository instance;
    private static String height = "mid";

    public static SkiResortRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SkiResortRepository(context);
        }
        return instance;
    }

    private SkiResortRepository(Context context) {
        cacheHelper = CacheHelper.getInstance(context);
        volleyHelper = new VolleyHelper(context);
    }

    public void getSkiResort(final String key, final SkiResortListener listener) {
        if (cacheHelper.get(key) != null) {
            listener.OnCached(cacheHelper.get(key));
            return;
        }

        volleyHelper.getResortInfo(key, height, new VolleyHelper.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiresort) {
                cacheHelper.put(key, skiresort);
                listener.OnSuccess(skiresort);
            }

            @Override
            public void OnFailure(String message) {
                listener.OnFailure(message);
            }
        });
    }

    public void refreshSkiResort(final String key, final SkiResortListener listener) {
        volleyHelper.clearCache(key);
        volleyHelper.getResortInfo(key, height, new VolleyHelper.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiresort) {
                cacheHelper.put(key, skiresort);
                listener.OnSuccess(skiresort);
            }

            @Override
            public void OnFailure(String message) {
                listener.OnFailure(message);
            }
        });
    }

    public static void clearCache(String key) {
        if (instance != null) {
            if (instance.cacheHelper.get(key) != null) {
                instance.cacheHelper.remove(key);
            }
        }
    }

    public interface SkiResortListener {

        void OnSuccess(SkiResort skiresort);

        void OnFailure(String message);

        void OnCached(SkiResort skiResort);
    }
}
