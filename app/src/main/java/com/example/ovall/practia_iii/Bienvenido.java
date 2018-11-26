package com.example.ovall.practia_iii;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Bienvenido extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //tVToken.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());
            }
        };

        if(SharedPrefManager.getInstance(Bienvenido.this).getToken() != null){
            Log.d("TAG", SharedPrefManager.getInstance(this).getToken());
        }
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));

    }
}
