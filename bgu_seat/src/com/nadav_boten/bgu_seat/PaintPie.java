package com.nadav_boten.bgu_seat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PaintPie {

	private Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);

	private float[] value_degree;
	private int[] COLORS={Color.RED,Color.GREEN};
	private int[] blank={Color.GRAY};
	RectF rectf = new RectF (10, 10, 200, 200);
	int temp=0;
	int total;

	public PaintPie( float[] values , int total) {

		value_degree=new float[values.length];
		for(int i=0;i<values.length;i++)
		{
			value_degree[i]=values[i];
		}
		this.total=total;
	}

	public Bitmap drawIt() {

		if (total==0){
			Bitmap bit = Bitmap.createBitmap(300, 300,Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bit);
			paint.setColor(blank[0]);
			canvas.drawArc(rectf, 0, 360, true, paint);
			return bit;
      
		}

		else{
			Bitmap bit = Bitmap.createBitmap(300, 300,Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bit);
			for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
				if (i == 0) {
					paint.setColor(COLORS[i]);
					canvas.drawArc(rectf, 0, value_degree[i], true, paint);
				} 
				else
				{
					temp += (int) value_degree[i - 1];
					paint.setColor(COLORS[i]);
					canvas.drawArc(rectf, temp, value_degree[i], true, paint);
					// txt_paint.setColor(Color.BLUE);
					// txt_paint.setTextSize(20);
					// paint.setStyle(Style.FILL);
					// canvas.drawText("wooow", 50, 50, txt_paint);
				}
			}
			return bit;
		}
	}

}
