package com.ppp.ledcontrol;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import com.ppp.ledcontrol.ColorAdapter.ColorHolder;

public class MainActivity extends Activity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	static ListView mDrawerList2;
    private ActionBarDrawerToggle mDrawerToggle;

    //private CharSequence mDrawerTitle = "Menu";
    private CharSequence mTitle;
    private String[] navMenuTitles;
    public static String[] navMenuTitles2;
    
    public static ListView listView;
    public static ColorAdapter colorAdapter;
    public static ArrayList<SingleColor> colorArray;
	
    private Management m;
	private static String speicherort;
	private static Vector <Container> vector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speicherort = getFilesDir().getAbsolutePath() + File.separator ;
        vector = new Vector<Container>();
  
//      Container c = new Container(2, "tempcont", null, false, null, false, null, false, null, false, null, false);
//      saveSetting(c);
        		
        initDrawer();
        
      	initList();
      	
      	createManagement();
        sendBroadcast();
    }
    

	private void initList() {
		colorArray = new ArrayList<SingleColor>();
		
		// Define a new Adapter
		colorAdapter = new ColorAdapter(this, R.layout.row, colorArray);
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setItemsCanFocus(false);
        listView.setAdapter(colorAdapter); 
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "List View Clicked: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        
        for (int i = 0; i < 22; i++)
		{
			colorArray.add(colorArray.size(), new SingleColor(i*75, i*25, i*-50, 0, 255));
		}
        
        
	}   
	
	@SuppressLint("CutPasteId")
	public static void updateColor(int index, int prevColor, int newColor){
	    View row = listView.getChildAt(index - listView.getFirstVisiblePosition());
	    ColorHolder holder = (ColorHolder) row.getTag();
	    
		holder.btnColor = (Button) row.findViewById(R.id.btnColor);
		holder.btnTime = (Button) row.findViewById(R.id.btnTime);
		holder.T = (TextView) row.findViewById(R.id.textViewT);
	    colorArray.get(index).colorFill(newColor);
	    SingleColor color = colorArray.get(index);
	    
	    //holder.btnColor.setBackgroundColor(newColor);
	    GradientDrawable border = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {newColor, newColor});
	    border.setCornerRadius(0f);
	    border.setStroke(2, 0xEEEEEE);
	    holder.btnColor.setBackground(border);
	    
	    if (index < (colorArray.size()-1)) {
	    	row = listView.getChildAt(index - listView.getFirstVisiblePosition() + 1);
	    	Button btnNextTime = (Button) row.findViewById(R.id.btnTime);
	    	int nextColor = colorArray.get(index + 1).getColor();
	    	GradientDrawable gd = new GradientDrawable(
		            GradientDrawable.Orientation.TOP_BOTTOM,
		            new int[] {newColor, nextColor});
		    gd.setCornerRadius(0f);
		    btnNextTime.setBackground(gd);
	    }
	    if (index > 0) {
	    	prevColor = colorArray.get(index - 1).getColor();
	    }
	    if (index < 1) {
	    	row = listView.getChildAt(index - listView.getFirstVisiblePosition());
	    	holder.btnTime = (Button) row.findViewById(R.id.btnTime);
		    holder.btnTime.setBackgroundColor(Color.TRANSPARENT);
	    } else {
		    GradientDrawable gd = new GradientDrawable(
		            GradientDrawable.Orientation.TOP_BOTTOM,
		            new int[] {prevColor, newColor});
		    gd.setCornerRadius(0f);
		    holder.btnTime.setBackground(gd);

	    }
	    
//	    listView.invalidateViews();
//	    listView.invalidate();
	    colorAdapter.notifyDataSetChanged();
	    setHeight(row, index, color.getT());
	}
	
	public static void setHeight(View row, int index, int height){
	    ColorHolder holder = (ColorHolder) row.getTag();
		holder.btnTime = (Button) row.findViewById(R.id.btnTime);
		holder.T = (TextView) row.findViewById(R.id.textViewT);
	    SingleColor color = colorArray.get(index);
	    color.setT(height);

	    holder.T.setText(String.valueOf(height));
	    holder.btnTime.setHeight(height);
	}
	
	private void initDrawer() {
		loadContainers();
		
        navMenuTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList2 = (ListView) findViewById(R.id.right_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuTitles));
        mDrawerList2.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuTitles2));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList2.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList2.setOnItemLongClickListener(new DrawerItemClickListener());
        
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

	private class DrawerItemClickListener implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	String[] menuItems = getResources().getStringArray(R.array.drawer_array);
        	mTitle = menuItems[position];
        	Fragment fragment = new Fragment();
        	Bundle data = new Bundle();
        	data.putInt("position", position);
        	//data.putInt(Fragment.ITEM, position);
            //data.putString("url", getUrl(position));
            fragment.setArguments(data);

            FragmentManager fragmentManager;
	        fragmentManager = getFragmentManager();
	        FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            
            mDrawerList.setItemChecked(position, true);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            getActionBar().setTitle(mTitle);
            Toast.makeText(MainActivity.this, ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
        }
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) 
        {
            final int index = position;
            deleteContainer(navMenuTitles2[index]);
//          Toast.makeText(MainActivity.this, "Delete?:  " + navMenuTitles2[index], Toast.LENGTH_SHORT).show();
        	loadContainers();
//        	((ArrayAdapter<String>) mDrawerList2.getAdapter()).notifyDataSetChanged();
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
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.add).setVisible(!drawerOpen);
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
       case R.id.menu_color_picker_dialog:
			onClickColorPickerDialog(item);
			return true;
       case R.id.action_settings:
    	   Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
    	   return true;
       case R.id.broadcast:
       		sendBroadcast();
       return true;
       case R.id.getProfiles:
    	   m.sendPackage(new Container(3));


    	   return true;
       case R.id.add:
    	   Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
    	   onClickColorPickerDialog(item);
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
/*    
    private void selectItem(int position) {
            
            Fragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(MenuFragment.ITEM, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(navMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
*/
    public static class MenuFragment extends Fragment {
        public static final String ITEM = "item_number";

        public MenuFragment() {
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_item, container, false);
            int i = getArguments().getInt(ITEM);
            String fragment = getResources().getStringArray(R.array.drawer_array)[i];

            int fragmentId = getResources().getIdentifier(fragment.toLowerCase(Locale.getDefault()),
                            "layout", getActivity().getPackageName());
            
            Log.d(getTag(), "Inside onCreateView");
            Log.d(getTag(), fragment);
            Log.d(getTag(), String.valueOf(fragmentId));
            
            //rootView.findViewById(R.layout.drawer_item_list).set;
            //getActivity().setTitle(fragment);
            return rootView;
        }
    }

	//Speichert einen Zustand am angegebene Speicherort
	public static void saveSetting(Container c_save)
	{
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
		alertDialogBuilder.setTitle("Delete");
		
		alertDialogBuilder
		.setMessage("Do you really want to delete " + name + " ?")
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
			System.out.println("V: " + vector.size());
			return vector;
	}
    
    
    private void sendBroadcast() {
            m.sendPackage(m.createZeroContainer());
            Log.d("management", "send broadcast");
        }

    private void createManagement() {
		int PORT = 4501;
		//String ADDRESS = "127.0.0.1";
		String ADDRESS ="255.255.255.255" ;
		//String ADDRESS ="192.168.1.100" ;
		//String ADDRESS ="10.0.2.2" ;
		InetAddress BROAD_ADDRESS = null;
		//InetAddress erstellen
		try {BROAD_ADDRESS = InetAddress.getByName(ADDRESS);
		} catch (UnknownHostException e) {e.printStackTrace();}
		m = new Management(BROAD_ADDRESS, PORT);
		Log.d("management", "create management");
    }

	@Override
	public void onClick(View v) {
		
	}

	public static void loadContainers() {
		vector = loadDir();
		navMenuTitles2 = new String[vector.size()];
		for (int i=0; i < vector.size(); i++)
		{
			navMenuTitles2[i] = vector.get(i).getName();	
		}
	}  
}
