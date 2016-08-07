package com.f3t.games.magarac;

import com.f3t.games.magarac.ServiceForCommunication.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity implements
		ServiceForCommunication.Callbacks {

	 boolean mBounded;
	 ServiceForCommunication mComService;
	
	Intent serviceIntent; // Intent for start service which comunicate with
							// server
	
	Button buttonMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Log.d("MainManu activity ", "onCreate");
		Toast.makeText(MainMenu.this, "onCreate", Toast.LENGTH_SHORT).show();

		initialization();
	}

	//service is been started
	private void initialization() {
		// TODO add initialization

		Toast.makeText(MainMenu.this, "initialize function called", Toast.LENGTH_SHORT).show();

		buttonMain = (Button) findViewById(R.id.buttonMain);
		buttonMain.setVisibility(View.GONE);
		
		buttonMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				mComService.registerClient(MainMenu.this); //Activity register in the service as client for callabcks! 

				String s = mComService.getTime();
				  
				Toast.makeText(MainMenu.this, "Return value: "+s, Toast.LENGTH_SHORT).show();

			}
		});
	}

	 @Override
	 protected void onStart() {
	  super.onStart();
	  Intent mIntent = new Intent(this, ServiceForCommunication.class);
	  bindService(mIntent, mConnection, BIND_AUTO_CREATE);
	
	  Toast.makeText(MainMenu.this, "onStart called", Toast.LENGTH_SHORT).show();

	  
	  //pozivamo klik button-a kako bi se pozvala metoda koja ce inicirati komunikaciju ka serveru
	  final Handler handler = new Handler();
	  handler.postDelayed(new Runnable() {
	    @Override
	    public void run() {
	      //Do something after 1000ms
	  	  buttonMain.performClick();
	    }
	  }, 1000);
	  
	  
	  //odavde mozes da pozoves metodu iz servisa
	  //mComService.getTime();
	  
	  //Toast.makeText(MainMenu.this, "Return value: "+s, Toast.LENGTH_SHORT).show();

	 };
	
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Toast.makeText(MainMenu.this, "onServiceConnected called",Toast.LENGTH_SHORT).show();
			Log.d("MainManu activity ", "onServiceConnected");
			
			
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder)service;
			mComService = mLocalBinder.getServerInstance();			

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
			Log.d("MainManu activity ", "onServiceDisconected");
			Toast.makeText(MainMenu.this, "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
			mBounded = false;
			mComService = null;
		}
	};

	@Override
	public void updateClient(long data) {
		// TODO Auto-generated method stub
		Log.d("MAGARAC main manu activity", "updateClient called");
		
		Toast.makeText(MainMenu.this, "updateClient called from service",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void function1() {
		// TODO Auto-generated method stub
		Toast.makeText(MainMenu.this, "function1 called",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void function2() {
		// TODO Auto-generated method stub
		Toast.makeText(MainMenu.this, "function2 called",
				Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void function3() {
		// TODO Auto-generated method stub
		Toast.makeText(MainMenu.this, "function3 called",
				Toast.LENGTH_SHORT).show();
		
	}
}
