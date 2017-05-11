package com.gravity.tehranski.business;

import com.gravity.tehranski.app.TehranSkiApplication;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.net.VolleyHelper;
import com.gravity.tehranski.business.storage.cache.CacheHelper;

import javax.inject.Inject;


public class SkiResortRepository {
    @Inject
    CacheHelper cacheHelper;
    @Inject
    VolleyHelper volleyHelper;


    private static String height = "mid";

    public SkiResortRepository() {
        TehranSkiApplication.getsApplication().getAppComponent().inject(this);
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

   /* public static void clearCache(String key) {
        if (instance != null) {
            if (instance.cacheHelper.get(key) != null) {
                instance.cacheHelper.remove(key);
            }
        }
    }*/

    public interface SkiResortListener {

        void OnSuccess(SkiResort skiresort);

        void OnFailure(String message);

        void OnCached(SkiResort skiResort);
    }
}
