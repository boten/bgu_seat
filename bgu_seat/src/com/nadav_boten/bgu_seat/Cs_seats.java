package com.nadav_boten.bgu_seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

public class Cs_seats extends Activity {

	private EditText filterText = null;
	ArrayList<Bitmap> bitmaps ;
	DownloadImageTask pic_prov=null;
	private Builder dialog; 
	boolean internet;
	Cs_pic_adapter pic_adapter;
	String [] pics;
	String [] names;
	GridView grid;
	



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cs_seats);   
		bitmaps = new ArrayList<Bitmap>();
		initilizePic();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetworkInfo == null || (!activeNetworkInfo.isConnected()) ){

			dialog = new AlertDialog.Builder(this);
			dialog.setMessage("בבקשה הפעל את " +
					"החיבור לאינטרנט " );
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					// TODO Auto-generated method stub
					finish();

				}
			});
			dialog.show();

		}

		else{
			grid=(GridView) findViewById(R.id.cs_grid) ;
			pic_prov=new DownloadImageTask(this,bitmaps,grid,names);
			pic_prov.execute(pics);
			

			 Button showAll = (Button) findViewById(R.id.show_all_butt);
			showAll.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
				}
			});
		

		}
	}
	private void initilizePic() {
		pics = new String[7];
		pics[0]="http://www.cs.bgu.ac.il/~dbase/classes_images/34-302.jpg";
		pics[1]="http://www.cs.bgu.ac.il/~dbase/classes_images/34-307.jpg";
		pics[2]="http://www.cs.bgu.ac.il/~dbase/classes_images/34-314.jpg";
		pics[3]="http://www.cs.bgu.ac.il/~dbase/classes_images/34-316.jpg";
		pics[4]="http://www.cs.bgu.ac.il/~dbase/classes_images/90-142.jpg";
		pics[5]="http://www.cs.bgu.ac.il/~dbase/classes_images/92-003.jpg";
		pics[6]="http://www.cs.bgu.ac.il/~dbase/classes_images/92-004.jpg";
		
		names = new String[7];
		names[0]="Lab 302/34";
		names[1]="Lab 307/34";
		names[2]="Lab 314/34";
		names[3]="Lab 316/34";
		names[4]="Lab 142/90";
		names[5]="Lab 003/92";
		names[6]="Lab 004/92";

		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void refresh(View view){
		
		new DownloadImageTask(this,bitmaps,grid,names).execute(pics);
		
	}
	

}
