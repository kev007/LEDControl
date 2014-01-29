package com.ppp.ledcontrol;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

public class MainActivity extends Activity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
//	private ListView mDrawerKetten;
	static ListView mDrawerProfiles;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;
//    private String[] navMenuKetten;
    public static String[] navMenuProfiles;
    
    public static ListView listView;
    public static ColorAdapter colorAdapter;
    public static ArrayList<SingleColor> colorArray;
    
    static int profileIndex = 0;
	
	private static String speicherort;
	public static Vector <Container> vector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        speicherort = getFilesDir().getAbsolutePath() + File.separator ;
        vector = new Vector<Container>();
  
        findContainers();
        
		if (vector.size() == 0) {
			createProfile();
		}
		findContainers();
        
        initDrawer();
    }
    
	private void createProfile() {
		findContainers();
		
		final EditText name = new EditText(MainActivity.this);
    	name.setText("New Profile");
    	final InputMethodManager imm = (InputMethodManager)MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
        {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
        }
		name.requestFocus();
		
		//Erstellen des Alertdialoges
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
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
				    vector = loadDir();
				    
				    profileIndex = vector.size() - 1;
				    
	            	Intent intent = new Intent (MainActivity.this, Profile.class);
	            	startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, "A profile with this name already exists!", Toast.LENGTH_LONG).show();
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

	private void initDrawer() {
		findContainers();
				
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerProfiles = (ListView) findViewById(R.id.left_drawer);
        mDrawerProfiles.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuProfiles));
        mDrawerProfiles.setOnItemClickListener(new DrawerItemClickListenerLeft());
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
        	mDrawerLayout.closeDrawer(mDrawerProfiles);
        	if (position < vector.size()){
            	profileIndex = position;
            	
            	Intent intent = new Intent (MainActivity.this, Profile.class);
            	startActivity(intent);

            	mDrawerProfiles.setItemChecked(position, true);
                setTitle(navMenuProfiles[profileIndex]);
                mDrawerLayout.closeDrawer(mDrawerProfiles);
                getActionBar().setTitle(mTitle);
        	} else if (position == vector.size()) {
        		createProfile();
        	}
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
	/*
	private class DrawerItemClickListenerRight implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	position_right = position;
        	String[] menuItems = getResources().getStringArray(R.array.ketten_array);
        	mTitle = menuItems[position];
        	Bundle data = new Bundle();
        	data.putInt("position", position);
        	
            mDrawerKetten.setItemChecked(position, true);
            setTitle(navMenuProfiles[profileIndex] + ": " + navMenuKetten[position_right]);
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
    */
    public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);

      	initDrawer();
	    return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
    	// If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerProfiles);
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
