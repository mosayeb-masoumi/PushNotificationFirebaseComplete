package com.example.pushnotification.notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.pushnotification.App;
import com.example.pushnotification.ForthActivity;
import com.example.pushnotification.R;
import com.example.pushnotification.SecondActivity;
import com.example.pushnotification.ThirdActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String title,body,image_url;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();


        title = data.get("title"); // "title" must be exactly what we wrote in post man
        body = data.get("body");  // "body"  must be exactly what we wrote in post man
        image_url = data.get("image-url");


        App.notifModel.setTitle(title);
        App.notifModel.setBody(body);
        App.notifModel.setImage_url(image_url);


            // convert srting url to bitmap
            Bitmap bitmap = getBitmapfromUrl(image_url);
            sendNotification(title, body, bitmap);


    }

    private void sendNotification(String title, String body, Bitmap bitmap) {

        //open activity directly
        if(title.equals("salam")) {
            getApplicationContext().startActivity(new Intent(getApplicationContext(), ForthActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }


        // touching on notification will take us to the activity which we define at below to open and notification will be disappear
        //by comment this below line  the line   notificationBuilder.setContentIntent(pendingIntentSecondActivity);  touching on notification wont have any action
        //just click on addAction would take action
        PendingIntent pendingIntentSecondActivity = PendingIntent.getActivity(this, 0, new Intent(getBaseContext(), SecondActivity.class), PendingIntent.FLAG_ONE_SHOT);


        //goto third activity
        PendingIntent pendingIntentThirdActivity = PendingIntent.getActivity(this, 0, new Intent(getBaseContext(), ThirdActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        // open link
        Intent notificationLink = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        PendingIntent linkPendingIntent = PendingIntent.getActivity(this, 0, notificationLink, 0);


        // open call
        Intent notificationCall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));
        PendingIntent callPendingIntent = PendingIntent.getActivity(this, 0, notificationCall, 0);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setLargeIcon(bitmap);
        notificationBuilder.setSmallIcon(R.drawable.ic_notif);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultSoundUri);

        //touch on notification and disappear notification
        notificationBuilder.setContentIntent(pendingIntentSecondActivity);
        //touch on button
        notificationBuilder.addAction(android.R.drawable.alert_light_frame, "third", pendingIntentThirdActivity);
        //touch on button
        notificationBuilder.addAction(android.R.drawable.ic_menu_share, "link", linkPendingIntent);
        //touch on button
        notificationBuilder.addAction(android.R.drawable.stat_sys_phone_call, "call", callPendingIntent);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


    }



    private Bitmap getBitmapfromUrl(String image_url) {
        try {
            URL url = new URL(image_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



///////////////////////////    POSTMAN   explanation    /////////////////////////

//    address    POST  https://fcm.googleapis.com/fcm/send


//        Headers
//        ---------

//        key                                                value
//
//        Authorization                                   key = AIzaSyD9DzCYhT3Auh54qvpJeTq5Wb-XZqOqHPI
//        Content-Type                                    application/json
//
//
//
//        (key = AIzaSyD9DzCYhT3Auh54qvpJeTq5Wb-XZqOqHPI) location == open firebase/goto console/choose app / setting/cloud messaging/legacy server key
//
//        -----------------------------------------------------------------
//
//        Body
//        -----
//
//        raw
//
//        {
//        "to":"egjkYQ2e8Uk:APA91bG9X5CKROa87iAqgdBHfk-nOAF2WTk-rqa-wiJfsrP0H-Os860OOS6RIrN9xvxnPwoyabbOqiOU2sKgJpxTiYBwGyCAI8rMeN0QFfH6ETIrsbecmtdxBgcUKHdQ5Obs3JBreGYj",
//
//        "content_available": true,
//
//        "data":{
//
//        "title": "salam",
//
//        "body": "this is bodyy"
//
//        }
//
//        }
//
//        ----------------------
//
//        "to" :"location" = get token from (FirebaseInstanceId.getInstance().getToken()
