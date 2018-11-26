package com.example.ovall.practia_iii;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        String tag = "El mensaje: ";

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(tag, "From: " + remoteMessage.getFrom());
        //Toast.makeText(getApplicationContext(), "A "+ remoteMessage.getFrom(), Toast.LENGTH_SHORT).show();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(tag, "Message data payload: " + remoteMessage.getData());
          //  Toast.makeText(getApplicationContext(), "B "+ remoteMessage.getData().toString(), Toast.LENGTH_SHORT).show();

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                // scheduleJob();
            } else {
                // Handle message within 10 seconds
                // handleNow();
            }

        }

        // Check if message contains a notification payload.
        Log.d(tag, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        //Toast.makeText(getApplicationContext(), "C "+ remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();

        if (remoteMessage.getNotification() != null) {
            Log.d(tag, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //Toast.makeText(getApplicationContext(), "D "+ remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        notifyUser(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
    }

    public void notifyUser(String from, String notification){
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());

        myNotificationManager.showNotification(from, notification, new Intent(getApplicationContext(), Bienvenido.class));
    }
}