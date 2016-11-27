package com.gravity.tehranski.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gravity.tehranski.R;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.net.VolleyHelper;


public class SkiResortFragment extends Fragment {

    // constant objects
    private static final String ARG_RESORT_NAME = "ARG_RESORT_NAME";
    private static final String ARG_POSITION = "ARG_POSITION";

    // layout objects
    private View bottomView;
    private LinearLayout bottomLayout;

    // data objects
    private String resortName;
    private int position;
    private SkiResort skiResort;

    // helper objects
    private VolleyHelper volleyHelper;

    private LayoutInflater inflater1;

    public SkiResortFragment() {
        // it is necessary
    }

    public static SkiResortFragment newInstance(String skiResortName, int position) {
        SkiResortFragment skiResortFragment = new SkiResortFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RESORT_NAME, skiResortName);
        args.putInt(ARG_POSITION, position);
        skiResortFragment.setArguments(args);
        return skiResortFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resortName = getArguments().getString(ARG_RESORT_NAME);
        position = getArguments().getInt(ARG_POSITION);
        volleyHelper = new VolleyHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_skiresort, container, false);

        volleyHelper.getResortInfo(resortName, "min", new VolleyHelper.SkiResortListener() {
                    @Override
                    public void OnSuccess(SkiResort skiresort) {
                        displayData(skiresort, rootView);
                    }

                    @Override
                    public void OnFailure(String message) {
                        // TODO: implement this shit
                        "dfd".toString();
                    }

                    @Override
                    public void OnCached() {
                        // TODO: implement this shit
                        "dfd".toString();
                    }
                }
        );
        return rootView;

    }

    public void displayData(SkiResort skiResort, View rootView) {
        this.skiResort = skiResort;
        TextView ResortName = (TextView) rootView.findViewById(R.id.resortName);
        ImageView CurrentConditionImg = (ImageView) rootView.findViewById(R.id.CurrentConditionImg);
        TextView CurrentTemp = (TextView) rootView.findViewById(R.id.CurrentTemp);
        TextView MinTemp = (TextView) rootView.findViewById(R.id.minTemp);
        TextView MaxTemp = (TextView) rootView.findViewById(R.id.maxTemp);
        TextView Snow = (TextView) rootView.findViewById(R.id.snow);
        TextView Rain = (TextView) rootView.findViewById(R.id.rain);
        TextView Wind = (TextView) rootView.findViewById(R.id.wind);

        ResortName.setText(skiResort.getResortName());
        CurrentConditionImg.setImageResource(getResources().getIdentifier(skiResort.getForecasts().get(0).get_psymbol()
                , "drawable", getContext().getPackageName()));
        CurrentTemp.setText(skiResort.getForecasts().get(0).get_pminchill());
        MinTemp.setText(skiResort.getForecasts().get(0).get_pmin() + " °C");
        MaxTemp.setText(skiResort.getForecasts().get(0).get_pmax() + " °C");
        Snow.setText(skiResort.getForecasts().get(0).get_psnow());
        Rain.setText(skiResort.getForecasts().get(0).get_pprec());
        Wind.setText(skiResort.getForecasts().get(0).get_pwsymbol());

        bottomLayout.removeAllViews();

        for (int i = 1; i < skiResort.getForecasts().size(); i++) {
            bottomView = inflater1.inflate(R.layout.fragment_bottom, bottomLayout, false);
            TextView dayName = (TextView) bottomView.findViewById(R.id.dayName);
            ImageView bottomImage = (ImageView) bottomView.findViewById(R.id.BottomImage);
            TextView bottomSnowText = (TextView) bottomView.findViewById(R.id.BottomSnowText);
            TextView bottomMinMaxTemp = (TextView) bottomView.findViewById(R.id.MinMaxBottomTemp);
            dayName.setText(skiResort.getForecasts().get(i).get_pdayname());
            bottomImage.setImageResource(getResources().getIdentifier(skiResort.getForecasts().get(i).get_psymbol()
                    , "drawable", getContext().getPackageName()));

            bottomSnowText.setText(skiResort.getForecasts().get(i).get_psnow());
            bottomMinMaxTemp.setText(skiResort.getForecasts().get(i).get_pmin() + " / "
                    + skiResort.getForecasts().get(i).get_pmax() + " °C");

            bottomLayout.addView(bottomView);
        }

        rootView.findViewById(R.id.fragmentTopLayout).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.BottomLayout).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void hideData() {
//        rootView.findViewById(R.id.BottomLayout).setVisibility(View.GONE);
//        rootView.findViewById(R.id.fragmentTopLayout).setVisibility(View.GONE);
//        rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }
}
