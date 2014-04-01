package com.nadav_boten.bgu_seat;


import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Building_full_page extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building_full_page);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.color.blue));
		 Intent intent = getIntent();
	     String link = intent.getStringExtra("link");
	     setContentView(R.layout.activity_building_full_page);
	        
	        WebView wv = (WebView) findViewById(R.id.webview);
	        wv.setBackgroundColor(Color.parseColor("#000000"));
	        wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
	        wv.loadUrl(link);
	        
	        wv.setWebViewClient(new WebViewClient() {
	        	@Override
	            public void onPageFinished(WebView view, String url) {
	        		findViewById(R.id.textView1).setVisibility(View.GONE);
	        		findViewById(R.id.webview).setVisibility(View.VISIBLE);
	           }
	        });
	        
	        
	        
	        
	     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.building_full_page, menu);
		return true;
	}

}
