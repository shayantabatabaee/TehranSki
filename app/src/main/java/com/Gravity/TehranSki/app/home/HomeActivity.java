package com.Gravity.TehranSki.app.home;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.Gravity.TehranSki.R;
import com.Gravity.TehranSki.app.fragment.FragmentAdapter;
import com.Gravity.TehranSki.business.model.SkiResortList;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.viewpagerindicator.CirclePageIndicator;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, HomeContract.HomeActivity {

    // layout Objects
    private View background;
    private ViewPager viewPager;

    // data Objects
    private String currentBackground;
    private String playServiceError;

    //presenter object
    public HomeContract.HomePresenter homePresenter;

    //adapter object
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();

        initObjects();

        if (!IsPlayServiceAvailable()) {
            Toast.makeText(this, playServiceError, Toast.LENGTH_SHORT).show();
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);
        }

    }

    private void findViews() {
        background = findViewById(R.id.background);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initObjects() {
        currentBackground = "";

        homePresenter = new HomePresenter(this);
        homePresenter.setCurrentPosition(0);

        adapter = new FragmentAdapter(getSupportFragmentManager(), SkiResortList.getInstance().getResortsName());
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

        CirclePageIndicator pageIndicator = (CirclePageIndicator) findViewById(R.id.pageIndicator);
        pageIndicator.setViewPager(viewPager);
        playServiceError = this.getResources().getString(R.string.play_service_error);

    }

    @Override
    public void setActivityBackground(String presenterBackground) {

        if (currentBackground.equals(presenterBackground)) {
            return;
        }
        currentBackground = presenterBackground;
        background.setBackgroundResource(getResources().getIdentifier(currentBackground
                , "drawable", this.getPackageName()));

        Animation FadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

        background.startAnimation(FadeIn);
        FadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.mainLayout).setBackgroundResource(getResources().getIdentifier(currentBackground
                        , "drawable", getPackageName()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private boolean IsPlayServiceAvailable() {
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        } else
            return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        homePresenter.setCurrentPosition(position);
        homePresenter.setBackground();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}