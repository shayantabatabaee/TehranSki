package com.gravity.tehranski.app.dagger;


import com.gravity.tehranski.app.ui.home.skiresort.SkiResortFragment;
import com.gravity.tehranski.app.ui.home.skiresort.SkiResortPresenter;
import com.gravity.tehranski.business.SkiResortRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class, RepositoryModule.class})
public interface AppComponent {

    void inject(SkiResortFragment target);

    void inject(SkiResortPresenter target);

    void inject(SkiResortRepository target);
}
