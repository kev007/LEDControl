package com.ppp.ledcontrol;

import java.util.ArrayList;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
		
		holder.T.setText(String.valueOf(color.getT()) + " ms");

	    GradientDrawable border = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {currentColor, currentColor});
//	    border.setCornerRadius(0f);
	    border.setStroke(2, 0x000000);
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
					final EditText time = new EditText(getContext());
					time.setHint(String.valueOf(color.getT()) + " ms");
					time.setInputType(InputType.TYPE_CLASS_NUMBER);
					
					final InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
	                if (imm != null)
	                {
	                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
	                }
	                time.requestFocus();
					
					//Erstellen des Alertdialoges
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
					alertDialogBuilder
					.setTitle("Duration")
					.setMessage("Set the duration, in milliseconds, for this transition.")
					.setCancelable(false)
					.setView(time)
					.setPositiveButton("Set", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog,int id){
							imm.toggleSoftInput(0, 0);
							System.out.println("contents of time:" + time.getText());
							if (time.getText().toString().matches("")) {
								Toast.makeText(context, "'" + time.getText() + "' is an invalid entry!\nPlease enter an Integer larger than zero: x > 0", Toast.LENGTH_LONG).show();
							} else if (Integer.parseInt(time.getText().toString()) > 0){
								Profile.updateTime(index, Integer.parseInt(time.getText().toString()));
							} else {
								Toast.makeText(context, "'" + time.getText() + "' is an invalid entry!\nPlease enter an Integer larger than zero: x > 0", Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog,int id){
							dialog.cancel();
							imm.toggleSoftInput(0, 0);
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();					
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
						Profile.updateColor(index, prevColor, cp.getColor());
					}
					
				});
				cp.show();
				
//				Toast.makeText(context, "Color: " + cp.getColor(), Toast.LENGTH_SHORT).show();
			}
		});
	    
	    Profile.updateHeight(row, position, color.getT());
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