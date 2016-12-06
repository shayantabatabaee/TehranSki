package com.gravity.tehranski.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gravity.tehranski.R;


public class NotificationActivity extends AppCompatActivity {


    //Views Objcets
    private TextView title;
    private TextView body;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViews();

        if (getIntent().getExtras() != null) {
            String textTitle = getIntent().getExtras().get("title").toString();
            String textBody = getIntent().getExtras().get("body").toString();

            title.setText(textTitle);
            body.setText(textBody);
        }
    }

    private void findViews() {
        title = (TextView) findViewById(R.id.notificationTitle);
        body = (TextView) findViewById(R.id.textBody);
    }


}
