package com.gravity.tehranski.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.gravity.tehranski.R;
import com.gravity.tehranski.business.model.SkiResort;
import com.gravity.tehranski.business.model.SkiResortList;
import com.gravity.tehranski.business.net.VolleyHelper;

public class MyFragmentActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private VolleyHelper volleyHelper;
    private FragmentAdapter adapter;
    private int position;
    View background;
    private String currentBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        background = findViewById(R.id.background);

        adapter = new FragmentAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                float normalPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalPosition / 5 + 0.8f);
                page.setScaleY(normalPosition / 5 + 0.8f);
            }
        });
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    protected void onResume() {

        super.onResume();
        volleyHelper = new VolleyHelper(this);
        currentBackground = "";
        onPageSelected(position);
    }

    @Override
    protected void onPause() {

        super.onPause();
        adapter.hideData();
        findViewById(R.id.background).setBackgroundResource(R.color.colorTransparent);
        findViewById(R.id.mainLayout).setBackgroundResource(R.color.colorPrimary);
    }

    @Override
    public void onPageSelected(final int position) {

        this.position = position;
        final String resortName = SkiResortList.getInstance().getResortsName().get(position);
        volleyHelper.getResortInfo(resortName, "max", new VolleyHelper.SkiResortListener() {
            @Override
            public void OnSuccess(SkiResort skiresort) {
                if (!skiresort.getForecasts().get(0).get_plcname().equals(currentBackground)) {
                    SetBackground(skiresort);
                }
                adapter.displayData(skiresort, position);

            }

            @Override
            public void OnFailure(String message) {

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                volleyHelper.CancelAll(resortName);
            }

            @Override
            public void OnCached() {
                if (!adapter.getSkiResort(position).getForecasts().get(0).get_plcname().equals(currentBackground)) {
                    SetBackground(adapter.getSkiResort(position));
                }
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void SetBackground(final SkiResort skiResort) {

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