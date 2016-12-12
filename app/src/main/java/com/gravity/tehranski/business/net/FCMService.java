package com.Gravity.Tehranski.business.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.Gravity.Tehranski.R;


public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Uri notificationSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);


        NotificationCompat.Builder notificationBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_small)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSound(notificationSound)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(remoteMessage.getNotification().getBody()))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.drawable.notification_icon))
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true);

        //TODO : Create a PopUp Dialog to show content
//        Intent resultIntent = new Intent(this, HomeActivity.class)
//                .putExtra("title", remoteMessage.getNotification().getTitle())
//                .putExtra("body", remoteMessage.getNotification().getBody());
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(HomeActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent pendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//
//        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }
}
