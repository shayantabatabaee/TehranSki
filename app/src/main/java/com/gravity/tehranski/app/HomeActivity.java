package com.gravity.tehranski.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gravity.tehranski.R;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.model.SkiResortList;
import com.gravity.tehranski.business.net.VolleyHelper;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // layout Objects
    private View background;
    private ViewPager viewPager;

    // data Objects
    private int position;
    private String currentBackground;

    // adapter objects
    private FragmentAdapter adapter;

    // helper objects
    private VolleyHelper volleyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();

        initObjects();
    }

    private void findViews() {
        background = findViewById(R.id.background);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initObjects() {
        volleyHelper = new VolleyHelper(this);
        adapter = new FragmentAdapter(getSupportFragmentManager(), SkiResortList.getInstance().getResortsName());
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float normalPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalPosition / 5 + 0.8f);
                page.setScaleY(normalPosition / 5 + 0.8f);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        currentBackground = "";
    }

    @Override
    protected void onPause() {
        super.onPause();
//        adapter.hideData();
        findViewById(R.id.background).setBackgroundResource(R.color.colorTransparent);
        findViewById(R.id.mainLayout).setBackgroundResource(R.color.colorPrimary);
    }

    public void SetBackground(final SkiResort skiResort, int position) {

        if (position != this.position) {
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
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}