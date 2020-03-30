package com.example.nihilist.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class NotificationDaemon extends Service {

    private Context ctx;

    public NotificationDaemon(){
        super();
        this.ctx=this.getApplicationContext();
    }

    public NotificationDaemon(Context c){
        super();
        this.ctx=c;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
