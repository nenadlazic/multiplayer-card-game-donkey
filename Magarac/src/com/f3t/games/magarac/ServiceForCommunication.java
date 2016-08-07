package com.f3t.games.magarac;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ServiceForCommunication extends Service {

	IBinder mBinder = new LocalBinder();

	Callbacks activity;
	
	@Override
	public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onBind called", Toast.LENGTH_SHORT).show();
		return mBinder;
	}

	public class LocalBinder extends Binder {
		public ServiceForCommunication getServerInstance() {
			return ServiceForCommunication.this;
		}
	}

	public String getTime() {
        Toast.makeText(getApplicationContext(), "getTime called", Toast.LENGTH_SHORT).show();
        
        activity.updateClient(1);
        //activity.function1();
		return "cao poz";
	}
	
    //callbacks interface for communication with service clients! 
    public interface Callbacks{
        public void updateClient(long data);
        public void function1();
        public void function2();
        public void function3();
    }
    
    public void registerClient(Activity activity){
        this.activity = (Callbacks)activity;
    }
}
