package com.f3t.games.magarac;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Scanner;

import org.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ServiceForCommunication extends Service {

	IBinder mBinder = new LocalBinder();

	Callbacks activity;
	
	String stringic = "prazno";
	
	String URL = "http://magarac-bgandroid.rhcloud.com/getGameRooms";
	
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

	public void getAvailableRooms() {
        //Toast.makeText(getApplicationContext(), "getAvailableRooms called", Toast.LENGTH_SHORT).show();
        
        //sent http post request from thread and return reponse in json string
        sendJson("nenadlazic13@gmail.com", "start");
        
//        while(stringic.equals("prazno")){
//        	Log.d("DEBUG:","waiting");
//        }
        
        //activity.updateClient(str);
        
	}
	

    
    public void registerClient(Activity activity){
        this.activity = (Callbacks)activity;
    }
    
    protected void  sendJson(final String email, final String pwd) {
    	
    	Thread t = new Thread() {
        	public void run() {
        		Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpPost post = new HttpPost(URL);
                    //json.put("email", email);
                    //json.put("password", pwd);
                    StringEntity se = new StringEntity( json.toString());  
                    se.setContentType((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /*Checking response */
                    if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                        
                        String s = convertStreamToString(in);
                        
                        
                        stringic = s;

                       // Toast.makeText(getApplicationContext(), "Response u stringic je: "+stringic, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Response je: "+s, Toast.LENGTH_SHORT).show();


                        
                        activity.updateClient(s);
                        return;
//                        return;

                    }

                } catch(Exception e) {
                    //e.printStackTrace();
                    //TODO kreirati dijalog
                    //createDialog("Error", "Cannot Estabilish Connection");
                    //Toast.makeText(getApplicationContext(), "Cannot Estabilish Connection", Toast.LENGTH_SHORT).show();
                }

                Looper.loop(); //Loop in the message queue
            }
        };
        
        
        //add starting thread
        t.start();   
        
        

        
    }

    
    
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } 
    }
    
    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    
    //callbacks interface for communication with service clients! 
    public interface Callbacks{
        public void updateClient(String data);
        public void function1();
        public void function2();
        public void function3();
    }
    
    //useful function
    static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
}
