package com.Gravity.TehranSki.app.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.Gravity.TehranSki.business.model.SkiResort;


public interface FragmentContract {

    interface Fragment {

        void displayData(SkiResort skiResort, View rootView, LayoutInflater inflater);

        void showOnFailureMessage(String message, View rootView);

        void showOnFailureToast(String message);

    }

    interface Presenter {

        void getSkiResort(final View rootView, final LayoutInflater inflater, String resortName);

        void refreshSkiResort(final View rootView, final LayoutInflater inflater, String resortName);

    }
}
