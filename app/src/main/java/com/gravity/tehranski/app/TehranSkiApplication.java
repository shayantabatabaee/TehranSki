package com.gravity.tehranski.app;

import android.app.Application;

import com.gravity.tehranski.app.dagger.AppComponent;
import com.gravity.tehranski.app.dagger.AppModule;
import com.gravity.tehranski.app.dagger.DaggerAppComponent;


public class TehranSkiApplication extends Application {

    private AppComponent appComponent;
    private static TehranSkiApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
        sApplication = this;
    }

    protected AppComponent initDagger(TehranSkiApplication tehranSkiApplication) {
        return DaggerAppComponent.builder().appModule(new AppModule(tehranSkiApplication)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    public static TehranSkiApplication getsApplication() {
        return sApplication;
    }
}
