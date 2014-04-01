package com.nadav_boten.bgu_seat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
	ArrayList<Bitmap> bitmaps ;
	GridView grid;
	Cs_pic_adapter pic_adapter;
	Context contex;
	Activity activity;
	private ProgressDialog pdia;
	String names[];

	

	public DownloadImageTask(Activity activity,ArrayList<Bitmap> items, GridView grid, String[] names) {
		this.bitmaps = items;
		this.grid=grid;
		this.activity=activity;
		contex=activity.getApplicationContext();
		this.names=names;

	}
	


	protected  ArrayList<Bitmap> doInBackground(String... urls) {
		//  String []urldisplay = urls;
		// Bitmap mIcon11 = null;
		bitmaps.clear();
		for (String url : urls) {
			try {
				InputStream in = new java.net.URL(url).openStream();
				bitmaps.add(BitmapFactory.decodeStream(in));
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}
		return bitmaps;
	}
	
	
  protected void onPreExecute(){ 
     super.onPreExecute();
          pdia = new ProgressDialog(activity);
          pdia.setMessage("...שולח הוביט קטן שיברר מה מצב המחשבים בכיתות");
          pdia.show();
  }

	protected void onPostExecute(ArrayList<Bitmap> result) {
         pic_adapter =new Cs_pic_adapter(contex, R.id.cs_image, bitmaps,names);
         GridView gridView = (GridView) activity.findViewById(R.id.cs_grid);
         gridView.setAdapter(pic_adapter);
         pic_adapter.notifyDataSetChanged();
         pdia.dismiss();
         
	}
}


