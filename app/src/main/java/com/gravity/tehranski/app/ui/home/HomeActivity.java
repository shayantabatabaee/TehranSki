package com.gravity.tehranski.app.ui.home;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.gravity.tehranski.R;
import com.gravity.tehranski.app.ui.home.skiresort.SkiResortAdapter;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.model.SkiResortList;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // data Objects
    private String currentBackground;
    private int currentPosition;
    private SparseArrayCompat<SkiResort> skiResortHashMap;
    private String playServiceError;

    //adapter object
    SkiResortAdapter adapter;

    //ButterKnife View Binding
    @BindView(R.id.background)
    View background;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.pageIndicator)
    CirclePageIndicator pageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initObjects();

        if (!IsPlayServiceAvailable()) {
            Toast.makeText(this, playServiceError, Toast.LENGTH_SHORT).show();
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        }
    }

    private void initObjects() {
        currentPosition = 0;
        currentBackground = "";
        skiResortHashMap = new SparseArrayCompat<>();
        //TODO:DI for adapter
        adapter = new SkiResortAdapter(getSupportFragmentManager(), SkiResortList.getInstance().getResortsName());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float normalPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalPosition / 5 + 0.8f);
                page.setScaleY(normalPosition / 5 + 0.8f);
            }
        });

        pageIndicator.setViewPager(viewPager);
        playServiceError = this.getResources().getString(R.string.play_service_error);

    }

    private boolean IsPlayServiceAvailable() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        } else
            return true;
    }

    public void setBackground(final SkiResort skiResort, int position) {
        skiResortHashMap.put(position, skiResort);

        setBackground();
    }

    private void setBackground() {
        final SkiResort skiResort = skiResortHashMap.get(currentPosition);

        if (skiResort == null) {
            return;
        }

        if (currentBackground.equals(skiResort.getForecasts().get(0).get_plcname())) {
            return;
        }
        currentBackground = skiResort.getForecasts().get(0).get_plcname();
        background.setBackgroundResource(getResources().getIdentifier(skiResort.getForecasts().get(0).get_plcname()
                , "drawable", this.getPackageName()));

        Animation FadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        background.startAnimation(FadeIn);
        FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.mainLayout).setBackgroundResource(getResources().getIdentifier(skiResort.getForecasts().get(0).get_plcname()
                        , "drawable", getPackageName()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        setBackground();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
