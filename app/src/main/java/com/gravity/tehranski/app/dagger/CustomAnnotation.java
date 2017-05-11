package com.gravity.tehranski.app.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

public @interface CustomAnnotation {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @interface HeightLevelName {

    }
}
