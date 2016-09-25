package com.f3t.games.magarac;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.f3t.games.magarac.ServiceForCommunication.LocalBinder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainMenu extends Activity implements
		ServiceForCommunication.Callbacks {

	boolean mBounded;
	ServiceForCommunication mComService;	//reference to the service
	Intent serviceIntent; 					//Intent for start service 
	Button buttonMain;						//button for startingservice
	MediaPlayer intoSong;					//itroduction music
	RelativeLayout mDisplayRooms;
	ViewFlipper mViewFlipper1;
	RelativeLayout mRelLayout1;
	LinearLayout mAvailableRooms;
	TextView mTextView1;
	Button mRoom1Text;
	Button mRoom2Text;
	Button mRoom3Text;
	Button mRoom4Text;
	Button mRoom5Text;
	Button[] roomsArray;
	Typeface type;							//set font style
	Button[] bntArrayRooms;
	LinearLayout[] linlayoutArrayRooms;
	int max_rum = 5;
	String responseString = "prazno";
	String name;
	Button enteredName;
	EditText editTextEnteredName;
	String strRes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
				
		Intent mIntent = new Intent(this, ServiceForCommunication.class);
		bindService(mIntent, mConnection, BIND_AUTO_CREATE);
		  
		Log.d("DEBUG: MainManu activity ", "onCreate");
		Toast.makeText(MainMenu.this, "onCreate", Toast.LENGTH_SHORT).show();
		
		initialization();
	}

	

	
	private void initialization() {

		Toast.makeText(MainMenu.this, "initialize function called", Toast.LENGTH_SHORT).show();
		Log.d("DEBUG: initialize function called ", "initialize");
		
		
		
		
		bntArrayRooms = new Button[max_rum];
		linlayoutArrayRooms = new LinearLayout[max_rum];
		
		buttonMain = (Button) findViewById(R.id.buttonMain);
		buttonMain.setVisibility(View.GONE);
		buttonMain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mComService.registerClient(MainMenu.this); //Activity register in the service as client for callabcks! 
				setContentView(R.layout.display_available_rooms );
	
				//gui elements from available rooms window
				mDisplayRooms = (RelativeLayout)findViewById(R.id.display_rooms1);
				mViewFlipper1 = (ViewFlipper )findViewById(R.id.viewFlipper1);
				mRelLayout1 =(RelativeLayout )findViewById(R.id.rel_layout_1);
				mAvailableRooms= (LinearLayout )findViewById(R.id.available_rooms);
				mTextView1 = (TextView )findViewById(R.id.textView1);
				mRoom1Text = (Button)findViewById(R.id.room1_text);
				mRoom2Text = (Button)findViewById(R.id.room2_text);
				mRoom3Text = (Button)findViewById(R.id.room3_text);
				mRoom4Text = (Button)findViewById(R.id.room4_text);
				mRoom5Text = (Button)findViewById(R.id.room5_text);
				
				roomsArray = new Button[max_rum];
				
				roomsArray[0] = mRoom1Text;
				roomsArray[1] = mRoom2Text;
				roomsArray[2] = mRoom3Text;
				roomsArray[3] = mRoom4Text;
				roomsArray[4] = mRoom5Text;
				
				//set font
				type = Typeface.createFromAsset(getAssets(),"fonts/Grinched.ttf"); 
				mTextView1.setTypeface(type);
				mRoom1Text.setTypeface(type);
				mRoom2Text.setTypeface(type);
				mRoom3Text.setTypeface(type);
				mRoom4Text.setTypeface(type);
				mRoom5Text.setTypeface(type);
				
				mRoom1Text.setAlpha((float) 0.6);
				mRoom2Text.setAlpha((float) 0.6);
				mRoom3Text.setAlpha((float) 0.6);
				mRoom4Text.setAlpha((float) 0.6);
				mRoom5Text.setAlpha((float) 0.6);
			}
		});
		
	}
	

	
	public void displayAvailableRooms(final String s){
		
		Toast.makeText(MainMenu.this, "display available rooms: "+s, Toast.LENGTH_SHORT).show();

		  this.runOnUiThread(new Runnable() { 
		        @Override
		        public void run() {
		    	    try {
		    			JSONObject jsonRooms = new JSONObject(s);
		    			
		    			Iterator<String> keys = jsonRooms.keys();
		    						
		    			int i = 0;

		    			String rezultat = "";
		    			
		    			while( keys.hasNext() ) {

		    				String key = (String)keys.next();
		    				
		    				if(key.equals("number_rooms")){
		    					//nothing
		    				}else {
		    					rezultat = "Room name:";
		    					rezultat += key;
		    					rezultat += " players: ";
		    					rezultat += jsonRooms.getString(key);
		    					rezultat += "  JOIN";
		    					
		    					roomsArray[i].setText(rezultat);
		    					
		    					i++;
		    				}
		    				
		    			}
		    			
		    			Toast.makeText(MainMenu.this, "REZULTAT: " +rezultat, Toast.LENGTH_SHORT).show();

		    	    } catch (JSONException e) {
		    			e.printStackTrace();
		    		}
		    	    
		    		Toast.makeText(MainMenu.this, "kraj", Toast.LENGTH_SHORT).show();

		    		
		    		mRoom1Text.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub

		    				setContentView(R.layout.dialog);
//		    				enteredName = (Button)findViewById(R.id.dialogButtonOK);
		    //
//		    				enteredName.setOnClickListener(new OnClickListener() {
//		    					
//		    					@Override
//		    					public void onClick(View v) {
//		    						
//		    						editTextEnteredName = (EditText)findViewById(R.id.enter_name1);
//		    						Editable entName = editTextEnteredName.getText();
//		    						
//		    						setContentView(R.layout.game_room);
//		    					}
//		    				});

		    			}
		    		});
		    		
		    		mRoom2Text.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub

		    				setContentView(R.layout.dialog);
//		    				enteredName = (Button)findViewById(R.id.dialogButtonOK);
		    //
//		    				enteredName.setOnClickListener(new OnClickListener() {
//		    					
//		    					@Override
//		    					public void onClick(View v) {
//		    						
//		    						editTextEnteredName = (EditText)findViewById(R.id.enter_name1);
//		    						Editable entName = editTextEnteredName.getText();
//		    						
//		    						setContentView(R.layout.game_room);
//		    					}
//		    				});
		    //

		    			}
		    		});
		    		
		    		mRoom3Text.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub

		    				setContentView(R.layout.dialog);
//		    				enteredName = (Button)findViewById(R.id.dialogButtonOK);
//		    				
//		    				enteredName.setOnClickListener(new OnClickListener() {
//		    					
//		    					@Override
//		    					public void onClick(View v) {
//		    						
//		    						editTextEnteredName = (EditText)findViewById(R.id.enter_name1);
//		    						Editable entName = editTextEnteredName.getText();
//		    						
//		    						setContentView(R.layout.game_room);
//		    					}
//		    				});

		    			}
		    		});
		    		
		    		mRoom4Text.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub

		    				setContentView(R.layout.dialog);
//		    				enteredName = (Button)findViewById(R.id.dialogButtonOK);
		    //
//		    				enteredName.setOnClickListener(new OnClickListener() {
//		    					
//		    					@Override
//		    					public void onClick(View v) {
//		    						
//		    						editTextEnteredName = (EditText)findViewById(R.id.enter_name1);
//		    						Editable entName = editTextEnteredName.getText();
//		    						
//		    						setContentView(R.layout.game_room);
//		    					}
//		    				});

		    			}
		    		});

		    		mRoom5Text.setOnClickListener(new OnClickListener() {
		    			
		    			@Override
		    			public void onClick(View v) {
		    				// TODO Auto-generated method stub

		    				setContentView(R.layout.dialog);
//		    				enteredName = (Button)findViewById(R.id.dialogButtonOK);
		    //
//		    				enteredName.setOnClickListener(new OnClickListener() {
//		    					
//		    					@Override
//		    					public void onClick(View v) {
//		    						
//		    						editTextEnteredName = (EditText)findViewById(R.id.enter_name1);
//		    						Editable entName = editTextEnteredName.getText();
//		    						
//		    						setContentView(R.layout.game_room);
//		    					}
//		    				});

		    				
		    			}
		    		});
		        }
		    });
		
	}


	@Override
	 protected void onStart() {
	  super.onStart();

	
	  Toast.makeText(MainMenu.this, "onStart called", Toast.LENGTH_SHORT).show();


	  
		  final Handler handler1 = new Handler();
		  handler1.postDelayed(new Runnable() {
		    @Override
		    public void run() {
		      //Do something after 1000ms
		  	  mComService.getAvailableRooms();
		    }
		  }, 7000);
		
	  
	  //pozivamo klik button-a kako bi se pozvala metoda koja ce inicirati komunikaciju ka serveru
	  final Handler handler = new Handler();
	  handler.postDelayed(new Runnable() {
	    @Override
	    public void run() {
	      //Do something after 1000ms
	  	  buttonMain.performClick();
	    }
	  }, 6000);
	  
	  
	  final Handler handler2 = new Handler();
	  handler1.postDelayed(new Runnable() {
	    @Override
	    public void run() {
	      //Do something after 1000ms
	  	  intoSong = MediaPlayer.create(MainMenu.this, R.raw.introduction);
		  intoSong.start();
	    }
	  }, 3000);
	  
	  //odavde mozes da pozoves metodu iz servisa
	  //mComService.getTime();
	  
	  //Toast.makeText(MainMenu.this, "Return value: "+s, Toast.LENGTH_SHORT).show();

	 };
	
	 
	 @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		intoSong.release();
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			//Toast.makeText(MainMenu.this, "onServiceConnected called",Toast.LENGTH_SHORT).show();
			Log.d("MainManu activity ", "onServiceConnected");
			
			
			mBounded = true;
			LocalBinder mLocalBinder = (LocalBinder)service;
			mComService = mLocalBinder.getServerInstance();	
			
			

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
			Log.d("MainManu activity ", "onServiceDisconected");
			//Toast.makeText(MainMenu.this, "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
			mBounded = false;
			mComService = null;
		}
	};

	@Override
	public void updateClient(String data) {
		//Toast.makeText(MainMenu.this, "updateClient called from service"+data,
		//Toast.LENGTH_SHORT).show();

		//hardcoded responceString
		responseString = "{\"number_rooms\":5,\"RIM\":5,\"ATINA\":4,\"BEOGRAD\":3,\"VALjEVO\":4,\"BIJELjINA\":5}";
		displayAvailableRooms(responseString);

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
