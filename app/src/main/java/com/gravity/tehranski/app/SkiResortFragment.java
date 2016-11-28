package com.gravity.tehranski.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gravity.tehranski.R;
import com.gravity.tehranski.business.SkiResortRepository;
import com.gravity.tehranski.business.model.SkiResort;


public class SkiResortFragment extends Fragment implements ViewPager.OnPageChangeListener {

    // constant objects
    private static final String ARG_RESORT_NAME = "ARG_RESORT_NAME";
    private static final String ARG_POSITION = "ARG_POSITION";

    // layout objects
    private View bottomView;

    // data objects
    private String resortName;
    private int position;

    // repository objects
    private SkiResortRepository skiResortRepository;

    // viewPager objects
    private ViewPager viewPager;
    private int currentPagerPosition;
    private HomeActivity homeActivity;
    private SkiResort skiResort;

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
        skiResortRepository = new SkiResortRepository(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_skiresort, container, false);
        if (getActivity() instanceof HomeActivity) {
            homeActivity = (HomeActivity) getActivity();
            viewPager = homeActivity.getViewPager();
            viewPager.addOnPageChangeListener(this);
        }
        skiResortRepository.getSkiResort(resortName, new SkiResortRepository.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiResort) {
                SkiResortFragment.this.skiResort = skiResort;
                displayData(skiResort, rootView, inflater);
            }

            @Override
            public void OnFailure(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnCached(SkiResort skiResort) {
                SkiResortFragment.this.skiResort = skiResort;
                displayData(skiResort, rootView, inflater);

            }
        });
        return rootView;

    }

    public void displayData(SkiResort skiResort, View rootView, LayoutInflater inflater) {
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

//        bottomLayout.removeAllViews();

        LinearLayout bottomLayout = (LinearLayout) rootView.findViewById(R.id.BottomLayout);
        for (int i = 1; i < skiResort.getForecasts().size(); i++) {
            bottomView = inflater.inflate(R.layout.fragment_bottom, bottomLayout, false);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.currentPagerPosition = position;
        if (homeActivity != null && position == this.position && skiResort != null) {
            homeActivity.setBackground(skiResort);
            Log.d("debug", "condition = " + skiResort.getForecasts().get(0).get_plcname() + " ,position = " + this.position + " ,current pager position = " + position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
