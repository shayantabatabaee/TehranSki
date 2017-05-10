package com.gravity.tehranski.app.home.skiresort;

import android.content.Context;

import com.gravity.tehranski.business.SkiResortRepository;
import com.gravity.tehranski.business.model.SkiResort;

public class SkiResortPresenter implements SkiResortContract.Presenter {

    //view objects
    private SkiResortContract.View view;

    // repository objects
    private SkiResortRepository skiResortRepository;


    public SkiResortPresenter(SkiResortFragment skiResortFragment, Context context) {
        this.view = skiResortFragment;
        skiResortRepository = SkiResortRepository.getInstance(context);
    }

    @Override
    public void getSkiResort(String resortName) {

        skiResortRepository.getSkiResort(resortName, new SkiResortRepository.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiResort) {
                view.displayData(skiResort);
                view.setActivityBackground(skiResort);
            }

            @Override
            public void OnFailure(String message) {
                view.showOnFailureMessage(message);
            }

            @Override
            public void OnCached(SkiResort skiResort) {
                view.displayData(skiResort);
                view.setActivityBackground(skiResort);
            }
        });

    }

    @Override
    public void refreshSkiResort(String resortName) {
        skiResortRepository.refreshSkiResort(resortName, new SkiResortRepository.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiresort) {
                view.displayData(skiresort);
                view.setActivityBackground(skiresort);
            }

            @Override
            public void OnFailure(String message) {
                view.showError(message);
            }

            @Override
            public void OnCached(SkiResort skiResort) {
            }
        });
    }


}
