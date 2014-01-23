package com.ppp.ledcontrol;

import afzkl.development.colorpickerview.dialog.ColorPickerDialog;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;



public class MainActivity extends Activity implements OnClickListener {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //private CharSequence mDrawerTitle = "Menu";
    private CharSequence mTitle;
    private String[] navMenuTitles;
    
    public ListView listView;
    public colorAdapter colorAdapter;
    public ArrayList<SingleColor> colorArray = new ArrayList<SingleColor>();
    //private int keyframeCount = 0; 
	
    private Management m;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawer();

      	initList();
      	
      	createManagement();
        sendBroadcast();
    }
    
	private void initList() {
		for (int i = 0; i < 5; i++)
		{
			colorArray.add(i, new SingleColor(i*50, i*50, i*50, 255-(i*50), 255));
		}
		
		// Define a new Adapter
		colorAdapter = new colorAdapter(this, R.layout.row, colorArray);
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setItemsCanFocus(false);
        listView.setAdapter(colorAdapter); 
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
            	Log.i("List View Clicked", "**********");
                Toast.makeText(MainActivity.this,
                  "List View Clicked: " + position, Toast.LENGTH_SHORT)
                  .show();
            }
        }); 
        
        colorAdapter.insert(new SingleColor(255, 255, 255, 255, 255), 0);
	}   
	
	private void initDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
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
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
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
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
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
		
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int initialValue = prefs.getInt("color_2", 0xFF000000);
		
		Log.d("mColorPicker", "initial value:" + initialValue);
				
		final ColorPickerDialog colorDialog = new ColorPickerDialog(this, initialValue);
		
		colorDialog.setAlphaSliderVisible(true);
		colorDialog.setTitle("Pick a Color!");
		
		colorDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "Selected Color: " + colorDialog.getColor(), Toast.LENGTH_LONG).show();
							
				//Save the value in our preferences.
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("color_2", colorDialog.getColor());
				editor.commit();
			}
		});
		
		colorDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Nothing to do here.
			}
		});
		
		colorDialog.show();
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
    }*/
    
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
		// TODO Auto-generated method stub
		
	}  
}