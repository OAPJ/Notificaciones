package com.example.ovall.practia_iii;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyFirebaseMessagingService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
