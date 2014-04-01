package com.nadav_boten.bgu_seat;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

public class RoomsProvider {
	ArrayList<Room> roomList= new ArrayList<Room>();
	Context context;
	MainActivity activity;
	RoomAdapter adapter;
	public static final String lastUpdatePrefKey = "LastDBUpdate";
	 InputStream in;
	
	public RoomsProvider(MainActivity fromActivity) {
		context = fromActivity;
		activity = fromActivity;
		
	}
	
	public void feedDatabase() {
		new ParseSite().execute("http://gezer.bgu.ac.il/compclass/compclass.php");
	}

	private class ParseSite extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog pdia;

        protected Boolean doInBackground(String... arg) {
            try {
            	URL url = new URL(arg[0]);
        		URLConnection urlConnection = url.openConnection();
                 in = new BufferedInputStream(urlConnection.getInputStream());
                RoomsHTMLParser parser = new RoomsHTMLParser(in);
        		parser.getRoomsStates();
        		roomList=parser.get_room_list();
        		adapter = new RoomAdapter(activity,R.id.roomName, roomList);
            } catch(Exception e) {
                e.printStackTrace();
                Log.d("error", ""+e);
            }
            
            return true;
        }
        
        @Override
        protected void onPreExecute(){ 
           super.onPreExecute();
                pdia = new ProgressDialog(context);
                pdia.setMessage("שולח שוב את ההוביט...מזל שהוא היפראקטיבי");
                pdia.show();
        }

        protected void onPostExecute(Boolean result) {
        	GridView gridView = (GridView) activity.findViewById(R.id.grid);
            gridView.setAdapter(adapter);
        	adapter.notifyDataSetChanged();
         	EditText filterText = (EditText) activity.findViewById(R.id.search_box);
         	adapter.getFilter().filter(filterText.getText().toString());
        	pdia.dismiss();
        }
    }
}