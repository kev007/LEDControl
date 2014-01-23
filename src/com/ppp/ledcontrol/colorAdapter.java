package com.ppp.ledcontrol;

import java.util.ArrayList;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class colorAdapter extends ArrayAdapter<SingleColor>{
	Context context;
	int layoutResourceId;
	ArrayList<SingleColor> colorArray = new ArrayList<SingleColor>();

	public colorAdapter(Context context, int layoutResourceId, ArrayList<SingleColor> colorArray) {
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
			holder.R = (TextView) row.findViewById(R.id.textViewR);
			holder.G = (TextView) row.findViewById(R.id.textViewG);
			holder.B = (TextView) row.findViewById(R.id.textViewB);
			holder.L = (TextView) row.findViewById(R.id.textViewL);
			holder.btnColor = (Button) row.findViewById(R.id.btnColor);
			holder.btnTime = (Button) row.findViewById(R.id.btnTime);
			row.setTag(holder);
		} else {
			holder = (ColorHolder) row.getTag();
		}
		SingleColor color = colorArray.get(position);
		//Toast.makeText(context, "Position: " + position, Toast.LENGTH_SHORT).show();
				
		holder.R.setText(String.valueOf(color.getR()));
		holder.G.setText(String.valueOf(color.getG()));
		holder.B.setText(String.valueOf(color.getB()));
		holder.L.setText(String.valueOf(color.getL()));
		final int currentColor = color.getColor();
		
		holder.btnColor.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new ColorPickerDialog(context, currentColor).show();
				Toast.makeText(context, "Edit Color button Clicked",
				Toast.LENGTH_SHORT).show();
			}
		});
		
		holder.btnColor.setBackgroundColor(currentColor);
		
		int previousColor = color.getColor();
		if (position > 0) {
			SingleColor lastcolor = colorArray.get(position-1);
			previousColor = lastcolor.getColor();
		}
		holder.btnTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String time = timePicker();
				//holder.btnTime.setText(time);
				Toast.makeText(context, "Edit Time button Clicked",
				Toast.LENGTH_SHORT).show();
			}
		});
		GradientDrawable gd = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {previousColor, currentColor});
	    gd.setCornerRadius(0f);
	    holder.btnTime.setBackground(gd);
	    //holder.btnTime.setScaleY(8);
	    if (position == 0) {
	    	holder.btnTime.setScaleX(0);
	    	holder.btnTime.setScaleY(0);
	    	holder.btnTime.setHeight(0);
	    }
		
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
		Button btnColor;
		Button btnTime;
	}
}