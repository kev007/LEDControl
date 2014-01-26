package com.ppp.ledcontrol;

import java.util.ArrayList;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ColorAdapter extends ArrayAdapter<SingleColor>{
	Context context;
	int layoutResourceId;
	ArrayList<SingleColor> colorArray = new ArrayList<SingleColor>();
	//ColorHolder[] holderArray;

	public ColorAdapter(Context context, int layoutResourceId, ArrayList<SingleColor> colorArray) {
		super(context, layoutResourceId, colorArray);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.colorArray = colorArray;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ColorHolder holder = null;
				
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ColorHolder();
//			holder.R = (TextView) row.findViewById(R.id.textViewR);
//			holder.G = (TextView) row.findViewById(R.id.textViewG);
//			holder.B = (TextView) row.findViewById(R.id.textViewB);
//			holder.L = (TextView) row.findViewById(R.id.textViewL);
			holder.T = (TextView) row.findViewById(R.id.textViewT);
			holder.btnColor = (Button) row.findViewById(R.id.btnColor);
			holder.btnTime = (Button) row.findViewById(R.id.btnTime);
			row.setTag(holder);
		} else {
			holder = (ColorHolder) row.getTag();
		}
		final SingleColor color = colorArray.get(position);
		//Toast.makeText(context, "Position: " + position, Toast.LENGTH_SHORT).show();
				

		final int currentColor = color.getColor();
		final int index = position;
		
		
//		holder.R.setText(String.valueOf(color.getR()));
//		holder.G.setText(String.valueOf(color.getG()));
//		holder.B.setText(String.valueOf(color.getB()));
//		holder.L.setText(String.valueOf(color.getL()));
		holder.T.setText(String.valueOf(color.getT()));

		//holder.btnColor.setBackgroundColor(currentColor);
	    GradientDrawable border = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {currentColor, currentColor});
	    border.setCornerRadius(0f);
	    border.setStroke(2, 0xEEEEEE);
	    holder.btnColor.setBackground(border);
		
		int previousColor = color.getColor();
		if (position > 0) {
			SingleColor lastcolor = colorArray.get(position-1);
			previousColor = lastcolor.getColor();
		}
		if (position == 0) {
	    	holder.btnTime.setBackgroundColor(Color.TRANSPARENT);
	    	holder.btnTime.setText(null);
	    } else {
			holder.btnTime.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
					
					
					Toast.makeText(context, "Edit Time button Clicked: " + index,
					Toast.LENGTH_SHORT).show();
				}
			});
			
			GradientDrawable gd = new GradientDrawable(
		            GradientDrawable.Orientation.TOP_BOTTOM,
		            new int[] {previousColor, currentColor});
		    gd.setCornerRadius(0f);
		    holder.btnTime.setBackground(gd);
	    }


	    final int prevColor = previousColor;
	    holder.btnColor.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final ColorPickerDialog cp = new ColorPickerDialog(context, color.getColor());
				cp.setAlphaSliderVisible(true);
				cp.setTitle("Pick a Color!");
				cp.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.updateColor(index, prevColor, cp.getColor());
					}
					
				});
				cp.show();
				
				Toast.makeText(context, "Color: " + cp.getColor(), Toast.LENGTH_SHORT).show();
			}
		});
	    
	    MainActivity.setHeight(row, position, color.getT());
		return row;		
	}
	
	public String timePicker() {
		String time = null;
		
		return time;
	}
	
	static class ColorHolder {
		TextView R;
		TextView G;
		TextView B;
		TextView L;
		TextView T;
		Button btnColor;
		Button btnTime;
	}
}