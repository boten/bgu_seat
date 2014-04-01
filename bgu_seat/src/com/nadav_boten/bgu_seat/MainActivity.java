package com.nadav_boten.bgu_seat;







import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

public class MainActivity extends  Activity {
	private EditText filterText = null;
	ArrayAdapter<Room> adapter = null;
	RoomsProvider rp;
	WebView myWebView;
	private Builder dialog; 
	boolean internet;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
			filterText = (EditText) findViewById(R.id.search_box);
			filterText.addTextChangedListener(filterTextWatcher);

			rp = new RoomsProvider(this);
			rp.feedDatabase();

			RoomAdapter adapter = new RoomAdapter(this, R.id.roomName, rp.roomList);
			GridView gridView = (GridView) findViewById(R.id.grid);
			gridView.setAdapter(adapter);


			gridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					// Sending image id to FullScreenActivity
					Intent i = new Intent(getApplicationContext(),Building_full_page.class);
					// passing array index
					i.putExtra("link", rp.roomList.get(position).link);
					startActivity(i);
				}
			});


			final Button refreshButton = (Button) findViewById(R.id.refresh);
			refreshButton.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					rp.feedDatabase();
				}
			});



		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private TextWatcher filterTextWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			//adapter.getFilter().filter(s); // to activate search filed
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		filterText.removeTextChangedListener(filterTextWatcher);
	}



}