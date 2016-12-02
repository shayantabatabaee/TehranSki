package com.gravity.tehranski.business;

import android.content.Context;

import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.net.VolleyHelper;
import com.gravity.tehranski.business.storage.cache.CacheHelper;


/**
 * Created by shayantabatabee on 11/28/16.
 */
public class SkiResortRepository {

    private CacheHelper cacheHelper;
    private VolleyHelper volleyHelper;
    private static SkiResortRepository instance;

    public static SkiResortRepository getInstance(Context context){
        if(instance == null){
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

        volleyHelper.getResortInfo(key, "mid", new VolleyHelper.SkiResortListener() {
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

    public static void clearCache(){
        if(instance!=null){
            instance.cacheHelper.evictAll();
        }
    }

    public interface SkiResortListener {

        void OnSuccess(SkiResort skiresort);

        void OnFailure(String message);

        void OnCached(SkiResort skiResort);
    }
}
