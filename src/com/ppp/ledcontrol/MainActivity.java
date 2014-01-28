package com.ppp.ledcontrol;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.lang.Math;
import java.util.Vector;

import com.ppp.ledcontrol.ColorAdapter.ColorHolder;

public class MainActivity extends Activity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerKetten;
	static ListView mDrawerProfiles;
    private ActionBarDrawerToggle mDrawerToggle;

    //private CharSequence mDrawerTitle = "Menu";
    private CharSequence mTitle;
    private String[] navMenuKetten;
    public static String[] navMenuProfiles;
    
    public static ListView listView;
    public static ColorAdapter colorAdapter;
    public static ArrayList<SingleColor> colorArray;
    
    int position_left = 0;
    int position_right = 0;
	
	private static String speicherort;
	public static Vector <Container> vector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        speicherort = getFilesDir().getAbsolutePath() + File.separator ;
        vector = new Vector<Container>();
  
//      Container c = new Container(2, "tempcont", null, false, null, false, null, false, null, false, null, false);
//      saveSetting(c);
        findContainers();
        
		if (vector.size() == 0) {
		      Container c = new Container(2, "New Profile", null, false, null, false, null, false, null, false, null, false);
		      saveSetting(c);
		      vector = loadDir();
		}
		findContainers();
        
        initDrawer();
        
//      	initList();
    }
    
	private void initDrawer() {
		findContainers();
		
        navMenuKetten = getResources().getStringArray(R.array.ketten_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerProfiles = (ListView) findViewById(R.id.left_drawer);
        mDrawerKetten = (ListView) findViewById(R.id.right_drawer);
        mDrawerKetten.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuKetten));
        mDrawerProfiles.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuProfiles));
        mDrawerProfiles.setOnItemClickListener(new DrawerItemClickListenerLeft());
        mDrawerKetten.setOnItemClickListener(new DrawerItemClickListenerRight());
        mDrawerProfiles.setOnItemLongClickListener(new DrawerItemClickListenerLeft());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private class DrawerItemClickListenerLeft implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	position_left = position;
        	String[] menuItems = getResources().getStringArray(R.array.ketten_array);
        	mTitle = menuItems[position];
        	Bundle data = new Bundle();
        	data.putInt("position", position);
        	
        	Intent intent = new Intent (MainActivity.this, Profile.class);
            Bundle extras = new Bundle();
            extras.putInt("ProfilePosition", position_left);
            extras.putInt("KettenPosition", position_right);
        	startActivity(intent);

            mDrawerKetten.setItemChecked(position, true);
            setTitle(navMenuProfiles[position_left] + ": " + navMenuKetten[position_right]);
            mDrawerLayout.closeDrawer(mDrawerKetten);
            getActionBar().setTitle(mTitle);
            Toast.makeText(MainActivity.this, ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {
            final int index = position;
            deleteContainer(navMenuProfiles[index]);
//          Toast.makeText(MainActivity.this, "Delete?:  " + navMenuProfiles[index], Toast.LENGTH_SHORT).show();
        	findContainers();
//        	((ArrayAdapter<String>) mDrawerProfiles.getAdapter()).notifyDataSetChanged();
			return true;
        }
    }
	
	private class DrawerItemClickListenerRight implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	position_right = position;
        	String[] menuItems = getResources().getStringArray(R.array.ketten_array);
        	mTitle = menuItems[position];
        	Bundle data = new Bundle();
        	data.putInt("position", position);
        	
            mDrawerKetten.setItemChecked(position, true);
            setTitle(navMenuProfiles[position_left] + ": " + navMenuKetten[position_right]);
            mDrawerLayout.closeDrawer(mDrawerKetten);
            getActionBar().setTitle(mTitle);
            Toast.makeText(MainActivity.this, ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {
            final int index = position;
            deleteContainer(navMenuProfiles[index]);
//          Toast.makeText(MainActivity.this, "Delete?:  " + navMenuProfiles[index], Toast.LENGTH_SHORT).show();
        	findContainers();
//        	((ArrayAdapter<String>) mDrawerProfiles.getAdapter()).notifyDataSetChanged();
			return true;
        }
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);

      	initDrawer();
	    return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerKetten);
//        menu.findItem(R.id.add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       // Handle action buttons
       switch(item.getItemId()) {
       case R.id.action_settings:
    	   Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
    	   return true;
       case R.id.broadcast:
       		MainActivity.sendBroadcast();
       return true;
       case R.id.getProfiles:
    	   Management.sendPackage(new Container(3));

    	   findContainers();
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
	
	public static void loadKette() {
		
	} 

	private Container saveToContainer(Container c1, ArrayList<SingleColor> colorArray1, ArrayList<SingleColor> colorArray2, ArrayList<SingleColor> colorArray3, ArrayList<SingleColor> colorArray4, ArrayList<SingleColor> colorArray5) {
//    	System.out.println("colorArray1.size() = " + colorArray1.size());
    	int[][] kette1 = null;
    	int[][] kette2 = null;
    	int[][] kette3 = null;
    	int[][] kette4 = null;
    	int[][] kette5 = null;
    	boolean s1 = false;
    	boolean s2 = false;
    	boolean s3 = false;
    	boolean s4 = false;
    	boolean s5 = false;
    	
    	if (colorArray1 != null) {
    		kette1 = new int[colorArray1.size()][5];
    		s1 = true;
        	for (int i = 0; i < colorArray1.size(); i++) {
        		SingleColor color1 = colorArray1.get(i);
        		kette1[i][0] = color1.getR();
        		kette1[i][1] = color1.getG();
        		kette1[i][2] = color1.getB();
        		kette1[i][3] = color1.getL();
        		kette1[i][4] = color1.getT();
        	}
        	
        	c1.setKette1(kette1);
        	c1.setStatus1(s1);
    	} else {
    		s1 = false;
    	}
    	if (colorArray2 != null) {
    		kette2 = new int[colorArray1.size()][5];
    		s2 = true;
        	for (int i = 0; i < colorArray1.size(); i++) {
        		SingleColor color1 = colorArray1.get(i);
        		kette2[i][0] = color1.getR();
        		kette2[i][1] = color1.getG();
        		kette2[i][2] = color1.getB();
        		kette2[i][3] = color1.getL();
        		kette2[i][4] = color1.getT();
        	}
        	
        	c1.setKette2(kette2);
        	c1.setStatus2(s2);
    	} else {
    		s2 = false;
    	}
    	if (colorArray3 != null) {
    		kette3 = new int[colorArray1.size()][5];
    		s3 = true;
        	for (int i = 0; i < colorArray1.size(); i++) {
        		SingleColor color1 = colorArray1.get(i);
        		kette3[i][0] = color1.getR();
        		kette3[i][1] = color1.getG();
        		kette3[i][2] = color1.getB();
        		kette3[i][3] = color1.getL();
        		kette3[i][4] = color1.getT();
        	}
        	
        	c1.setKette3(kette3);
        	c1.setStatus3(s3);
    	} else {
    		s3 = false;
    	}
    	if (colorArray4 != null) {
    		kette4 = new int[colorArray1.size()][5];
    		s4 = true;
        	for (int i = 0; i < colorArray1.size(); i++) {
        		SingleColor color1 = colorArray1.get(i);
        		kette4[i][0] = color1.getR();
        		kette4[i][1] = color1.getG();
        		kette4[i][2] = color1.getB();
        		kette4[i][3] = color1.getL();
        		kette4[i][4] = color1.getT();
        	}
        	
        	c1.setKette4(kette4);
        	c1.setStatus4(s4);
    	} else {
    		s4 = false;
    	}
    	if (colorArray5 != null) {
    		kette5 = new int[colorArray1.size()][5];
    		s5 = true;
        	for (int i = 0; i < colorArray1.size(); i++) {
        		SingleColor color1 = colorArray1.get(i);
        		kette5[i][0] = color1.getR();
        		kette5[i][1] = color1.getG();
        		kette5[i][2] = color1.getB();
        		kette5[i][3] = color1.getL();
        		kette5[i][4] = color1.getT();
        	}
        	
        	c1.setKette5(kette5);
        	c1.setStatus5(s5);
    	} else {
    		s5 = false;
    	}




//    	c1 = new Container(2, "tempcont", kette1, s1, kette2, s2, kette3, s3, kette4, s4, kette5, s5);
    	saveSetting(c1);
    	return c1;
	}


	public void onClickColorPickerDialog(MenuItem item) {
		//The color picker menu item as been clicked. Show 
		//a dialog using the custom ColorPickerDialog class.
		final ColorPickerDialog cp = new ColorPickerDialog(this, Color.MAGENTA);
		cp.setAlphaSliderVisible(true);
		cp.setTitle("Pick a Color!");
		cp.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//MainActivity.updateColor(index, prevColor, cp.getColor());
				SingleColor temp = new SingleColor(255, 255, 255, 255, 255);
				temp.colorFill(cp.getColor());
				colorArray.add(colorArray.size(), temp);
				//colorArray.add(colorArray.size(), temp);
				//listView.invalidateViews();
				colorAdapter.notifyDataSetChanged();
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
	public static void saveSetting(Container c_save)
	{
		int checkuuid = MainActivity.checkUUID(c_save.getUUID());
		if(! (checkuuid==-1))
		{
				System.out.println("MainActivity: " + vector.get(checkuuid).getName() + " has a UUID of: " + vector.get(checkuuid).getUUID() + " and will be replaced by: \n" + "MainActivity: " + c_save.getName() + " has a UUID of: " + c_save.getUUID());
				String name = vector.get(checkuuid).getName();
				//UUID exisitert bereits --> überschreiben des alten Containers
				File file = new File(speicherort + name + ".ser");
				if(file.exists())
				{
					 	System.gc();
					 	file.delete();
				}
		} else {
			System.out.println("MainActivity: " + c_save.getName() + " has a UUID of: " + c_save.getUUID() + " and will be saved for the first time");
		}
		File file = new File(speicherort + c_save.getName() + ".ser");
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(c_save);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteContainer(final String name)
	{
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
	
	public void deleteAllContainers()
	{
			File file = new File(speicherort);
//			System.out.println("PFAD: " + file.getAbsolutePath());
			for (File temp_file : file.listFiles())
			{
			     	if (temp_file.toString().endsWith(".ser"))
			     	{
			     			temp_file.delete();
			     	}
			}
	}
	
	//Dateien einlesen und ContainerVektor zurückgeben
	public static Vector <Container> loadDir()
	{
			Vector <Container> vector = new Vector<Container>();
			FileInputStream fis;
			File [] liste = new File(speicherort).listFiles();
			for(File temp : liste)
			{
					try
					{
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
//			System.out.println("Vector size: " + vector.size());
			return vector;
	}
	
    public static void sendBroadcast() {
        Management.sendPackage(Management.createZeroContainer());
        Log.d("management", "send broadcast");
    }

	@Override
	public void onClick(View v) {
		
	}

	public static void findContainers() {
		vector = loadDir();
		navMenuProfiles = new String[vector.size()];
		if (vector.size() > 0) {
			for (int i=0; i < vector.size(); i++)
			{
				System.out.println("Profile found: " + vector.get(i).getName());
				navMenuProfiles[i] = vector.get(i).getName();	
			}
		} else {
			System.out.println("No profiles found!");
		}
	} 
	
	
	public static int checkUUID(UUID id)
	{
		findContainers();
		for(int i = 0; i<vector.size(); i++)
		{
				if(0==id.compareTo(vector.get(i).getUUID()))return i; 
		}
		return -1; 
	}
}
