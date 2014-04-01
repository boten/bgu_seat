package com.nadav_boten.bgu_seat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Cs_pic_adapter extends ArrayAdapter<Room>{
	
	List<Bitmap> bitmaps ;
	public ArrayList<Bitmap> allItems;
	Context context;
    private static LayoutInflater inflater = null;
    String[] names;
	
	 public Cs_pic_adapter(Context context, int ImageviewResourceId, ArrayList<Bitmap> items, String[] names) {
	    	super(context, ImageviewResourceId);
			bitmaps=items;
	    	this.context=context;
	        this.allItems = items;
	        this.names=names;
	        inflater = LayoutInflater.from(context);
	        }
	    public int getCount() {
	         return allItems.size();
	    }
	    
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if(convertView==null) 
			{
				vi = inflater.inflate(R.layout.cs_box, null);
			}
            TextView txt = (TextView) vi.findViewById(R.id.cs_txt);
	        ImageView image = (ImageView) vi.findViewById(R.id.cs_image);
	        txt.setText(names[position]);
            image.setImageBitmap(bitmaps.get(position));
	        
	        return vi;
		    } 
	    
	    
	    

}
