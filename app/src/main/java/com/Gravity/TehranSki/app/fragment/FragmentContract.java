package com.Gravity.TehranSki.app.fragment;

import android.view.View;

import com.Gravity.TehranSki.business.model.SkiResort;

import java.lang.ref.WeakReference;


public interface FragmentContract {

    interface Fragment {

        void displayData(SkiResort skiResort, WeakReference<View> rootView);

        void showOnFailureMessage(String message, WeakReference<View> rootView);

        void showOnFailureToast(String message);

    }

    interface Presenter {

        void getSkiResort(final WeakReference<View> rootView, String resortName);

        void refreshSkiResort(final WeakReference<View> rootView, String resortName);

    }
}
