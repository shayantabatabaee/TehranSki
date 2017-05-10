package com.gravity.tehranski.app.ui.home.skiresort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gravity.tehranski.R;
import com.gravity.tehranski.app.TehranSkiApplication;
import com.gravity.tehranski.app.ui.home.HomeActivity;
import com.gravity.tehranski.business.model.SkiResort;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.gravity.tehranski.R.id.CurrentConditionImg;
import static com.gravity.tehranski.R.id.CurrentTemp;


public class SkiResortFragment extends Fragment implements SkiResortContract.View {


    // constant objects
    private static final String ARG_RESORT_NAME = "ARG_RESORT_NAME";
    private static final String ARG_POSITION = "ARG_POSITION";

    // data objects
    private String resortName;
    protected int position;
    private LayoutInflater mInflater;

    //Butter Knife Layout Objects
    Unbinder unbinder;
    @BindView(R.id.resortName)
    TextView resortNameText;
    @BindView(CurrentConditionImg)
    ImageView currentConditionImg;
    @BindView(CurrentTemp)
    TextView currentTemp;
    @BindView(R.id.minTemp)
    TextView minTemp;
    @BindView(R.id.maxTemp)
    TextView maxTemp;
    @BindView(R.id.snow)
    TextView snow;
    @BindView(R.id.rain)
    TextView rain;
    @BindView(R.id.wind)
    TextView wind;
    @BindView(R.id.BottomLayout)
    LinearLayout bottomLayout;
    @BindView(R.id.failureText)
    TextView failureText;
    @BindView(R.id.failureLayout)
    LinearLayout failureLayout;
    @BindView(R.id.progressBar)
    LinearLayout progressBar;
    @BindView(R.id.fragmentTopLayout)
    LinearLayout fragmentTopLayout;

    //Injection
    @Inject
    SkiResortContract.Presenter presenter;

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
        ((TehranSkiApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        mInflater = null;
        unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_skiresort, container, false);

        this.mInflater = inflater;
        unbinder = ButterKnife.bind(this, rootView);
        presenter.attachView(this);

        presenter.getSkiResort(resortName);

        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                presenter.refreshSkiResort(resortName);
                refreshLayout.setRefreshing(false);

            }
        });

        return rootView;

    }


    @Override
    public void displayData(SkiResort skiResort) {
        if (getActivity() == null) {
            // the fragment is not attached to any activity. so there is no need to show the data
            return;
        }

        resortNameText.setText(skiResort.getResortName());
        currentConditionImg.setImageResource(getResources().getIdentifier(skiResort.getForecasts().get(0).get_psymbol()
                , "drawable", getContext().getPackageName()));
        currentTemp.setText(skiResort.getForecasts().get(0).get_pminchill());
        minTemp.setText(skiResort.getForecasts().get(0).get_pmin() + " °C");
        maxTemp.setText(skiResort.getForecasts().get(0).get_pmax() + " °C");
        snow.setText(skiResort.getForecasts().get(0).get_psnow());
        rain.setText(skiResort.getForecasts().get(0).get_pprec());
        wind.setText(skiResort.getForecasts().get(0).get_pwsymbol());

        android.view.View bottomView;

        bottomLayout.removeAllViews();
        for (int i = 1; i < skiResort.getForecasts().size(); i++) {
            bottomView = mInflater.inflate(R.layout.fragment_bottom, bottomLayout, false);
            TextView dayName = ButterKnife.findById(bottomView, R.id.dayName);
            ImageView bottomImage = ButterKnife.findById(bottomView, R.id.BottomImage);
            TextView bottomSnowText = ButterKnife.findById(bottomView, R.id.BottomSnowText);
            TextView bottomMinMaxTemp = ButterKnife.findById(bottomView, R.id.MinMaxBottomTemp);
            dayName.setText(skiResort.getForecasts().get(i).get_pdayname());
            bottomImage.setImageResource(getResources().getIdentifier(skiResort.getForecasts().get(i).get_psymbol()
                    , "drawable", getContext().getPackageName()));

            bottomSnowText.setText(skiResort.getForecasts().get(i).get_psnow());
            bottomMinMaxTemp.setText(skiResort.getForecasts().get(i).get_pmin() + " / "
                    + skiResort.getForecasts().get(i).get_pmax() + " °C");

            bottomLayout.addView(bottomView);
        }

        fragmentTopLayout.setVisibility(android.view.View.VISIBLE);
        bottomLayout.setVisibility(android.view.View.VISIBLE);
        progressBar.setVisibility(android.view.View.GONE);
        failureLayout.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showOnFailureMessage(String message) {
        failureText.setText(message);
        progressBar.setVisibility(android.view.View.GONE);
        failureLayout.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setActivityBackground(SkiResort skiResort) {

        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).setBackground(skiResort, position);
        }

    }
}
