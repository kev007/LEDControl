package com.ppp.ledcontrol;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import com.ppp.ledcontrol.ColorAdapter.ColorHolder;

public class Profile extends Activity implements OnClickListener {
	private static DrawerLayout mDrawerLayout;
	private static ListView mDrawerKetten;
	static ListView mDrawerProfiles;
	private ActionBarDrawerToggle mDrawerToggle;
	
	public static String[] navMenuKetten;
	public static String[] navMenuProfiles;

	public static ListView listView;
	public static ColorAdapter colorAdapter;
	public static ArrayList<SingleColor> colorArray;
	
	public static int profileIndex = 0;
	public static int ketteIndex = 0;

	private static String speicherort;
	public static Vector<Container> vector;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		navMenuKetten = getResources().getStringArray(R.array.ketten_array);
		speicherort = getFilesDir().getAbsolutePath() + File.separator;
		vector = MainActivity.vector;
		profileIndex = MainActivity.profileIndex;
		
		initDrawer();

		initList();

		mDrawerLayout.openDrawer(mDrawerKetten);
		
		loadKette(profileIndex, ketteIndex);
	}
	
	public void loadKette(int profile, int kette) {
    	colorArray.clear();
		colorAdapter.notifyDataSetChanged();
    	
		if (profile < 0) {
			System.out.println("Invalid!");
			finish();
		} else {
			Container c = vector.get(profile);
			int[][] selectedKette = null;
			
			navMenuKetten[0] = "\n\n" + c.getName() + "\n\n";
			if(c.status1 == false) navMenuKetten[1] = "LED 1 (Disabled)";
			else navMenuKetten[1] = "LED 1";
			if(c.status2 == false) navMenuKetten[2] = "LED 2 (Disabled)";
			else navMenuKetten[2] = "LED 2";
			if(c.status3 == false) navMenuKetten[3] = "LED 3 (Disabled)";
			else navMenuKetten[3] = "LED 3";
			if(c.status4 == false) navMenuKetten[4] = "LED 4 (Disabled)";
			else navMenuKetten[4] = "LED 4";
			if(c.status5 == false) navMenuKetten[5] = "LED 5 (Disabled)";
			else navMenuKetten[5] = "LED 5";
					
	    	if (! (kette >= 0 && kette <= 4)) {
	    		System.out.println("Incorrect kette selection: " + kette);
	    	} else {
	        	switch(kette){
		        	case 0:
		        		selectedKette = c.getKette1();
		            break;
		        	case 1:
		        		selectedKette = c.getKette2();
		            break;
		        	case 2:
		        		selectedKette = c.getKette3();
		            break;
		        	case 3:
		        		selectedKette = c.getKette4();
		            break;
		        	case 4:
		        		selectedKette = c.getKette5();
		            break;
	        	}
//	        	System.out.println("selectedKette = " + selectedKette);
	        	if (selectedKette == null){
	        		//This kette is empty and will be filled with default values
	        		selectedKette = new int[][]{{0,0,0,255,0}};
	        	}
	        	if (selectedKette.length == 0){
	        		//This kette is empty and will be filled with default values
	        		selectedKette = new int[][]{{0,0,0,255,0}};
	        	}
	        	for (int i = 0; i < selectedKette.length; i++) {
	    			colorArray.add(new SingleColor(selectedKette[i][4], selectedKette[i][0], selectedKette[i][1], selectedKette[i][2], selectedKette[i][3]));
	    		}
	    		System.out.println("Profile '" + c.getName() + "' has loaded LED " + (kette+1) + " which contained " + colorArray.size() + " entries");
	    		
	    		saveKette(c, kette);
	    	}
    	}
	}
	
	private static void saveKette(Container c, int kette) {
		if (c == null) c = vector.get(profileIndex);
		
		int[][] newKette = null;
		newKette = new int[colorArray.size()][5];
		
		for (int i = 0; i < colorArray.size(); i++) {
			SingleColor color1 = colorArray.get(i);
			newKette[i][0] = color1.getR();
			newKette[i][1] = color1.getG();
			newKette[i][2] = color1.getB();
			newKette[i][3] = color1.getL();
			newKette[i][4] = color1.getT();
		}
		
		switch(kette){
	    	case 0:
	    		c.setKette1(newKette);
	        break;
	    	case 1:
	    		c.setKette2(newKette);
	        break;
	    	case 2:
	    		c.setKette3(newKette);
	        break;
	    	case 3:
	    		c.setKette4(newKette);
	        break;
	    	case 4:
	    		c.setKette5(newKette);
	        break;
		}
		
		saveSetting(c);
	}

	private void initList() {
		colorArray = new ArrayList<SingleColor>();

		// Define a new Adapter
		colorAdapter = new ColorAdapter(this, R.layout.row, colorArray);

		listView = (ListView) findViewById(R.id.listView);
		listView.setItemsCanFocus(false);
		listView.setAdapter(colorAdapter);
		listView.setOnItemClickListener(new ListViewItemClickListener());
		listView.setOnItemLongClickListener(new ListViewItemClickListener());
	}
	
	private class ListViewItemClickListener implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	SingleColor temp = new SingleColor(colorAdapter.getItem(position).getT(), colorAdapter.getItem(position).getR(), colorAdapter.getItem(position).getG(), colorAdapter.getItem(position).getB(), colorAdapter.getItem(position).getL());
        	if (position == 0) temp.setT(500);
        	colorAdapter.insert(temp, position + 1);
        	colorAdapter.notifyDataSetChanged();
        	saveKette(null, ketteIndex);
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	if (position > 0) colorAdapter.remove(colorAdapter.getItem(position));
        	colorAdapter.notifyDataSetChanged();
        	saveKette(null, ketteIndex);
        	return true;
        }
    }

	@SuppressLint("CutPasteId")
	public static void updateTime(int index, int time) {
		View row = listView.getChildAt(index
				- listView.getFirstVisiblePosition());
		ColorHolder holder = (ColorHolder) row.getTag();

		holder.btnTime = (Button) row.findViewById(R.id.btnTime);
		holder.T = (TextView) row.findViewById(R.id.textViewT);
		colorArray.get(index).setT(time);

		if (index == 0) {
			holder.T.setText("Start");
		} else {
			holder.T.setText(Integer.toString(time));
		}

		// listView.invalidateViews();
		// listView.invalidate();
		colorAdapter.notifyDataSetChanged();
		// updateHeight(row, index, time);
		saveKette(null, ketteIndex);
	}

	@SuppressLint("CutPasteId")
	public static void updateColor(int index, int prevColor, int newColor) {
		View row = listView.getChildAt(index
				- listView.getFirstVisiblePosition());
		ColorHolder holder = (ColorHolder) row.getTag();

		holder.btnColor = (Button) row.findViewById(R.id.btnColor);
		holder.btnTime = (Button) row.findViewById(R.id.btnTime);
		holder.T = (TextView) row.findViewById(R.id.textViewT);
		colorArray.get(index).colorFill(newColor);

		// holder.btnColor.setBackgroundColor(newColor);
		GradientDrawable border = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, new int[] { newColor,
						newColor });
		border.setCornerRadius(0f);
		border.setStroke(2, 0x000000);
		holder.btnColor.setBackground(border);

		if (index < (colorArray.size() - 1)) {
			row = listView.getChildAt(index
					- listView.getFirstVisiblePosition() + 1);
			Button btnNextTime = (Button) row.findViewById(R.id.btnTime);
			int nextColor = colorArray.get(index + 1).getColor();
			GradientDrawable gd = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							newColor, nextColor });
			gd.setCornerRadius(0f);
			btnNextTime.setBackground(gd);
		}
		if (index > 0) {
			prevColor = colorArray.get(index - 1).getColor();
		}
		if (index < 1) {
			row = listView.getChildAt(index
					- listView.getFirstVisiblePosition());
			holder.btnTime = (Button) row.findViewById(R.id.btnTime);
			holder.btnTime.setBackgroundColor(Color.TRANSPARENT);
		} else {
			GradientDrawable gd = new GradientDrawable(
					GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
							prevColor, newColor });
			gd.setCornerRadius(0f);
			holder.btnTime.setBackground(gd);

		}

		// listView.invalidateViews();
		// listView.invalidate();
		colorAdapter.notifyDataSetChanged();
		// updateHeight(row, index, color.getT());
		saveKette(null, ketteIndex);
	}

	public static void updateHeight(View row, int index, int height) {
		ColorHolder holder = (ColorHolder) row.getTag();
		holder.btnTime = (Button) row.findViewById(R.id.btnTime);

		int pixels = 0;
		if (height <= 150) {
			pixels = 75;
		} else if (height >= 1500) {
			pixels = 750;
		} else {
			pixels = height/2;
		}
		if (height == 0) {
			pixels = 5;
			holder.T.setText("Start");
		} else {
			holder.T.setText(String.valueOf(height) + " ms");
		}

//		System.out.println("New height for row " + index + " is: " + pixels);

		RelativeLayout.LayoutParams params = (LayoutParams) holder.btnTime
				.getLayoutParams();
		params.height = pixels;
		holder.btnTime.setLayoutParams(params);

		holder.btnTime.setHeight(height);
	}

	private void initDrawer() {
		updateProfileList();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
		mDrawerProfiles = (ListView) findViewById(R.id.left_drawer);
		mDrawerKetten = (ListView) findViewById(R.id.right_drawer);

		mDrawerProfiles.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuProfiles));
		mDrawerProfiles.setOnItemClickListener(new DrawerItemClickListenerLeft());
		mDrawerProfiles.setOnItemLongClickListener(new DrawerItemClickListenerLeft());
		mDrawerKetten.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuKetten));
		mDrawerKetten.setOnItemClickListener(new DrawerItemClickListenerRight());
		mDrawerKetten.setOnItemLongClickListener(new DrawerItemClickListenerRight());
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private class DrawerItemClickListenerLeft implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	mDrawerLayout.closeDrawer(mDrawerProfiles);
        	if (position < vector.size()){
            	profileIndex = position;
            	
                //the profile selected in the left drawer menu and the first led chain will be loaded
                //additionally, the led chain selection is automatically set to the first one
                //this combination was chosen so that the user may swipe close the right drawer menu and have something to see right away
            	ketteIndex = 0;
        		loadKette(profileIndex, ketteIndex);    		
        		mDrawerLayout.openDrawer(mDrawerKetten);

            	mDrawerProfiles.setItemChecked(position, true);
                setTitle(navMenuProfiles[profileIndex]);
                mDrawerLayout.closeDrawer(mDrawerProfiles);
                
            	//Action bar will display only the name of the profile to prevent the title from being too long to display
//            	String title = (navMenuProfiles[profileIndex] + ": " + navMenuKetten[ketteIndex]);
            	String title = (navMenuProfiles[profileIndex]);
                getActionBar().setTitle(title);
        	} else if (position == vector.size()) {
        		createProfile();
        	}

        	mDrawerProfiles.setItemChecked(position, true);
            setTitle(navMenuProfiles[profileIndex]);
            mDrawerKetten.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerProfiles);
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {
            //the profile which was long-click selected will be deleted and the profile list is the refreshed
        	if (position < vector.size()) deleteContainer(vector.get(position));

        	updateProfileList();
//        	((ArrayAdapter<String>) mDrawerProfiles.getAdapter()).notifyDataSetChanged();
			return true;
        }
    }
	
	private class DrawerItemClickListenerRight implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
    		mDrawerLayout.closeDrawer(mDrawerProfiles);
    		
            if (position == 0) {
            	final EditText name = new EditText(Profile.this);
            	name.setText(vector.get(profileIndex).getName());
            	final InputMethodManager imm = (InputMethodManager)Profile.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
                }
				name.requestFocus();
				
				//Erstellen des Alertdialoges
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile.this);
				alertDialogBuilder
				.setTitle("Rename")
				.setMessage("Please enter the new name for " + vector.get(profileIndex).getName())
				.setCancelable(false)
				.setView(name)
				.setPositiveButton("Set", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id){
						imm.toggleSoftInput(0, 0);						
						navMenuKetten[0] = "\n\n" + name.getText().toString() + "\n\n";
						vector.get(profileIndex).setName(name.getText().toString());
						System.out.println("New name: " + vector.get(profileIndex).getName());
						saveSetting(vector.get(profileIndex));
						updateProfileList();
						initDrawer();

//						loadKette(profileIndex, ketteIndex);
						mDrawerLayout.openDrawer(mDrawerKetten);
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id){
						imm.toggleSoftInput(0, 0);
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();		
            } else {
            	ketteIndex = position - 1;
            	//Action bar will display only the number of the LED which is being controlled to prevent the title from being too long to display
//            	String title = (navMenuProfiles[profileIndex] + ": " + navMenuKetten[ketteIndex]);
                
            	loadKette(profileIndex, ketteIndex);

            	String title = ("LED " + position);
                getActionBar().setTitle(title);
            }
        	
            
            mDrawerKetten.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerKetten);
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {	
    		final Container c = vector.get(profileIndex);
        	//the boolean status of the selected LED chain will be toggled
    		//in the case of the first entry, the name 
        	switch(position){
        	case 0:
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile.this);
        		alertDialogBuilder
        				.setTitle(c.getName())
        				.setMessage("Do you want to duplicate the profile " + c.getName() + "?")
        				.setCancelable(false)
        				.setPositiveButton("Yes",
        						new DialogInterface.OnClickListener() {
        							// Funktion bei "Ja"
        							public void onClick(DialogInterface dialog, int id) {
        								Container c2 = new Container(c.getModus(), c.getName()+ " (Copy)", c.getKette1(), c.status1, c.getKette2(), c.status2, c.getKette3(), c.status3, c.getKette4(), c.status4, c.getKette5(), c.status5);
        								c2.setUUID(UUID.randomUUID());
        								System.out.println("New profile: " + c2.getName() + " UUID: " + c2.getUUID());
        								vector.add(c2);
        								updateProfileList();
        					        	saveSetting(c2);
        							}
        						})
        				.setNegativeButton("No", new DialogInterface.OnClickListener() {
        					// Funktion bei "Nein"
        					public void onClick(DialogInterface dialog, int id) {
        						dialog.cancel();
        					}
        				});

        		AlertDialog alertDialog = alertDialogBuilder.create();
        		alertDialog.show();
            break;
        	case 1:
        		if (c.status1 == false) {
        			c.status1 = true;
        			navMenuKetten[1] = "LED 1";
        		} else {
        			c.status1 = false;
        			navMenuKetten[1] = "LED 1 (Disabled)";
        		}
            break;
        	case 2:
        		if (c.status2 == false) {
        			c.status2 = true;
        			navMenuKetten[2] = "LED 2";
        		} else {
        			c.status2 = false;
        			navMenuKetten[2] = "LED 2 (Disabled)";
        		}
            break;
        	case 3:
        		if (c.status3 == false) {
        			c.status3 = true;
        			navMenuKetten[3] = "LED 3";
        		} else {
        			c.status3 = false;
        			navMenuKetten[3] = "LED 3 (Disabled)";
        		}
            break;
        	case 4:
        		if (c.status4 == false) {
        			c.status4 = true;
        			navMenuKetten[4] = "LED 4";
        		} else {
        			c.status4 = false;
        			navMenuKetten[4] = "LED 4 (Disabled)";
        		}
            break;
        	case 5:
        		if (c.status5 == false) {
        			c.status5 = true;
        			navMenuKetten[5] = "LED 5";
        		} else {
        			c.status5 = false;
        			navMenuKetten[5] = "LED 5 (Disabled)";
        		}
            break;
        	}
        	saveSetting(c);
        	initDrawer();
//        	((ArrayAdapter<String>) mDrawerProfiles.getAdapter()).notifyDataSetChanged();
			return true;
        }
    }
    
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.colorgui, menu);

		initDrawer();
		return super.onCreateOptionsMenu(menu);
	}

	@SuppressWarnings("unused")
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerKetten);
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
//		if (mDrawerToggle.onOptionsItemSelected(item)) {
//			return true;
//		}

		mDrawerLayout.closeDrawer(mDrawerKetten);
		mDrawerLayout.closeDrawer(mDrawerProfiles);
		
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.menu_color_picker_dialog:
			onClickColorPickerDialog(item);
			return true;
		case R.id.send_now:
			saveKette(null, ketteIndex);
			send(2, vector.get(profileIndex));
			
			Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.broadcast:
			MainActivity.sendBroadcast();
			return true;
		case R.id.getProfiles:
			mDrawerLayout.closeDrawer(mDrawerKetten);
			mDrawerLayout.closeDrawer(mDrawerProfiles);
			Management.sendPackage(new Container(3));
			colorAdapter.clear();
            getActionBar().setTitle("");
			initDrawer();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void send(int modus, Container c1) {
		c1.setModus(modus);
		System.out.println("Container Modus: " + c1.getModus());
		System.out.println("Container Name: " + c1.getName());
		System.out.println("Container UUID: " + c1.getUUID());
		Management.sendPackage(c1);
	}

	public void onClickColorPickerDialog(MenuItem item) {
		// The color picker menu item as been clicked. Show
		// a dialog using the custom ColorPickerDialog class.
		final ColorPickerDialog cp = new ColorPickerDialog(this, Color.MAGENTA);
		cp.setAlphaSliderVisible(true);
		cp.setTitle("Pick a Color!");
		cp.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// MainActivity.updateColor(index, prevColor,
						// cp.getColor());
						SingleColor temp = new SingleColor(500, 255, 255, 255, 255);
						temp.colorFill(cp.getColor());
						colorArray.add(colorArray.size(), temp);
						// colorArray.add(colorArray.size(), temp);
						// listView.invalidateViews();
						colorAdapter.notifyDataSetChanged();

						saveKette(null, ketteIndex);
					}
				});
		cp.show();
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	//Speichert einen Zustand am angegebene Speicherort
	public static void saveSetting(Container c)
	{
		int checkuuid = MainActivity.checkUUID(c.getUUID());
		if(! (checkuuid==-1))
		{
				System.out.println("Profile: " + vector.get(checkuuid).getName() + " has a UUID of: " + vector.get(checkuuid).getUUID() + " and will be replaced by: \n" + "Profile: " + c.getName() + " has a UUID of: " + c.getUUID());
				String name = vector.get(checkuuid).getName();
				//UUID exisitert bereits --> �berschreiben des alten Containers
				File file = new File(speicherort + name + ".ser");
				if(file.exists())
				{
					 	System.gc();
					 	file.delete();
				}
		} else {
			System.out.println("Profile: " + c.getName() + " has a UUID of: " + c.getUUID() + " and will be saved for the first time");
		}
		File file = new File(speicherort + c.getName() + ".ser");
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(c);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteContainer(final Container container)
	{
//        mDrawerLayout.closeDrawer(mDrawerProfiles);
		final String name = container.getName();
      //Erstellen des Alertdialoges
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
		.setTitle("Delete")
		.setMessage("Do you really want to delete " + name + "?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener()
		{
				//Funktion bei "Ja"
				public void onClick(DialogInterface dialog,int id)
				{	
		        	if (vector.indexOf(container) == profileIndex) {
		            	System.out.println("Deleting currently displayed LED Chain at position: " + profileIndex) ;
		            	if (profileIndex == 0) loadKette(-1, 1);
		            	else loadKette(0, 1);
		        	}
					vector.remove(container);
					updateProfileList();
					File file = new File(speicherort + name + ".ser");
					if(file.exists())
					{
						 	System.out.println(name + " deleted");
						 	System.gc();
						 	file.delete();
						 	
					    	initDrawer();
					}
				}
		  })
		.setNegativeButton("No", new DialogInterface.OnClickListener()
		{
				//Funktion bei "Nein"
				public void onClick(DialogInterface dialog,int id)
				{
						dialog.cancel();
				}
		});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void deleteAllContainers() {
		File file = new File(speicherort);
		// System.out.println("PFAD: " + file.getAbsolutePath());
		for (File temp_file : file.listFiles()) {
			if (temp_file.toString().endsWith(".ser")) {
				temp_file.delete();
			}
		}
	}

	// Dateien einlesen und ContainerVektor zur�ckgeben
		public static void loadDir() {
			vector.clear();
			FileInputStream fis;
			File[] liste = new File(speicherort).listFiles();
			for (File temp : liste) {
				try {
					fis = new FileInputStream(temp);

					ObjectInputStream is = new ObjectInputStream(fis);
					Container c = (Container) is.readObject();
					vector.add(c);
					is.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		 System.out.println("Vector size: " + vector.size());
		 updateProfileList();
		}

	@Override
	public void onClick(View v) {

	}

	public static void updateProfileList() {
		navMenuProfiles = new String[vector.size() + 1];
		if (vector.size() > 0) {
			for (int i=0; i < vector.size(); i++)
			{
//				System.out.println("Profile found: " + vector.get(i).getName());
				navMenuProfiles[i] = vector.get(i).getName();	
			}
		} else {
			System.out.println("No profiles found!");
		}

		navMenuProfiles[vector.size()] = "\n\n\t\t\t\t\t\t\t Add Profile \n\n";
	} 

	public static int checkUUID(UUID id) {
		updateProfileList();
		for (int i = 0; i < vector.size(); i++) {
			if (0 == id.compareTo(vector.get(i).getUUID()))
				return i;
		}
		return -1;
	}
	
	private void createProfile() {
		updateProfileList();
		
		final EditText name = new EditText(Profile.this);
    	name.setText("New Profile");
    	final InputMethodManager imm = (InputMethodManager)Profile.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
        {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
        }
		name.requestFocus();
		
		//Erstellen des Alertdialoges
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile.this);
		alertDialogBuilder
		.setTitle("New Profile")
		.setMessage("Please enter a name")
		.setCancelable(false)
		.setView(name)
		.setPositiveButton("Set", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int id){
				imm.toggleSoftInput(0, 0);
				Boolean unique = true;
				for(int i=0; i < vector.size(); i++){
					if (vector.get(i).getName().equals(name.getText().toString())) unique = false;
				}
				if (unique == true) {
				    Container c = new Container(2, name.getText().toString(), null, true, null, false, null, false, null, false, null, false);
				    saveSetting(c);
				    vector.add(c);
				    updateProfileList();
				    profileIndex = vector.indexOf(c);
//				    profileIndex = vector.size() - 1;
				    
				    loadKette(profileIndex, ketteIndex);
	                mDrawerLayout.openDrawer(mDrawerKetten);
				} else {
					Toast.makeText(Profile.this, "A profile with this name already exists!", Toast.LENGTH_LONG).show();
		        	mDrawerLayout.openDrawer(mDrawerProfiles);
				}
				
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int id){
				imm.toggleSoftInput(0, 0);
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
