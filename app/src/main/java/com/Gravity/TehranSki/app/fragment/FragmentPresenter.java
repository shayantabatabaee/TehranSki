package com.Gravity.TehranSki.app.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.Gravity.TehranSki.app.home.HomeActivity;
import com.Gravity.TehranSki.business.SkiResortRepository;
import com.Gravity.TehranSki.business.model.SkiResort;

public class FragmentPresenter implements FragmentContract.Presenter {

    //view objects
    private FragmentContract.Fragment fragment;

    // repository objects
    private SkiResortRepository skiResortRepository;


    public FragmentPresenter(SkiResortFragment skiResortFragment, Context context) {
        this.fragment = skiResortFragment;
        skiResortRepository = SkiResortRepository.getInstance(context);
    }

    @Override
    public void getSkiResort(final View rootView, final LayoutInflater inflater, String resortName) {

        skiResortRepository.getSkiResort(resortName, new SkiResortRepository.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiResort) {
                fragment.displayData(skiResort, rootView, inflater);
                setActivityBackground(skiResort);
            }

            @Override
            public void OnFailure(String message) {
                fragment.showOnFailureMessage(message, rootView);
            }

            @Override
            public void OnCached(SkiResort skiResort) {
                fragment.displayData(skiResort, rootView, inflater);
                setActivityBackground(skiResort);
            }
        });

    }

    @Override
    public void refreshSkiResort(final View rootView, final LayoutInflater inflater, String resortName) {
        skiResortRepository.refreshSkiResort(resortName, new SkiResortRepository.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiresort) {
                fragment.displayData(skiresort, rootView, inflater);
                setActivityBackground(skiresort);
            }

            @Override
            public void OnFailure(String message) {
                fragment.showOnFailureToast(message);
            }

            @Override
            public void OnCached(SkiResort skiResort) {
            }
        });
    }

    private void setActivityBackground(SkiResort skiResort) {

        if (((SkiResortFragment) fragment).getActivity() instanceof HomeActivity) {
            ((HomeActivity) ((SkiResortFragment) fragment).getActivity()).homePresenter.setBackground(((SkiResortFragment) fragment).position, skiResort);

        }
    }
}
