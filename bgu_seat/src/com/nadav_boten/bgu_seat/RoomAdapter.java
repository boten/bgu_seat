package com.nadav_boten.bgu_seat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;





import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.widget.Filter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RoomAdapter extends ArrayAdapter<Room> implements Filterable {
	public ArrayList<Room> subItems;
	public ArrayList<Room> allItems;
	Context context;
	
    
    private static LayoutInflater inflater = null;

	
    public RoomAdapter(Context context, int textViewResourceId, ArrayList<Room> items) {
    	super(context, textViewResourceId, items);
		
    	this.context=context;
    	this.subItems = items;
        this.allItems = this.subItems;
        inflater = LayoutInflater.from(context);
        }
    public int getCount() {
         return subItems.size();
    }
  

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView==null) 
		{
			vi = inflater.inflate(R.layout.one_box, null);
		}

        TextView roomName = (TextView) vi.findViewById(R.id.roomName);
        TextView roomNumber = (TextView) vi.findViewById(R.id.roomNumber);
        TextView free = (TextView) vi.findViewById(R.id.free);
        TextView ocupied = (TextView) vi.findViewById(R.id.ocupied);
        ImageView image = (ImageView) vi.findViewById(R.id.image_building);
        Room room = subItems.get(position);

        roomName.setText(room.building);
        roomNumber.setText(room.room);
        free.setText(String.valueOf(room.total-room.occupied));
        ocupied.setText(String.valueOf(room.occupied));
        float values[]={room.occupied,(room.total-room.occupied)};
        values=calculateData(values);
        
        PaintPie pie=new PaintPie(values,room.total);
        Bitmap bit =pie.drawIt();

        image.setImageBitmap(bit);
        
      //  vi.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 60));
        
        return vi;
	    }
	@Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Room> filteredArrayRooms = new ArrayList<Room>();

                if (constraint == null || constraint.length() == 0) {
                    results.count = allItems.size();
                    results.values = allItems;
                } else {
                    constraint = constraint.toString();
                    for (int index = 0; index < allItems.size(); index++) {
                        Room room = allItems.get(index);

                        if (room.room.contains(
                        constraint.toString())) {
                            filteredArrayRooms.add(room);
                        }
                    }

                    results.count = filteredArrayRooms.size();
                    System.out.println(results.count);

                    results.values = filteredArrayRooms;
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
            FilterResults results) {

                subItems = (ArrayList<Room>) results.values;
                notifyDataSetChanged();
            }

        };

        return filter;
    }
	
	
	public float[] calculateData(float[] data) {
        // TODO Auto-generated method stub
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
        data[i]=360*(data[i]/total);            
        }
        return data;

    }
	
	public static Bitmap getBitmapFromView(View view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable =view.getBackground();
	    if (bgDrawable!=null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}
	
	
//	public static Bitmap createDrawableFromView(Context context, View view) {
//    	DisplayMetrics displayMetrics = new DisplayMetrics();
//    	((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//    	view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//    	view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//    	view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//    	view.buildDrawingCache();
//    	Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//
//    	Canvas canvas = new Canvas(bitmap);
//    	view.draw(canvas);
//
//    	return bitmap;
//    }
}
