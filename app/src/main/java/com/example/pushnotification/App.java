package com.example.pushnotification;

import android.app.Application;
import android.content.Context;

import com.example.pushnotification.notification.NotificationModel;

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static NotificationModel notifModel = new NotificationModel();
}
