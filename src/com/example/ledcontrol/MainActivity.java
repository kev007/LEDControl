package com.example.ledcontrol;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItems;
    
    private Management m;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mDrawerItems = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, mDrawerItems));
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
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        if (savedInstanceState == null) {
            selectItem(0);
        }

        createManagement();
        sendBroadcast();
    }


    private void sendBroadcast() {
    	m.sendPackage(m.createZeroContainer());
    	Log.d("management", "send broadcast");
	}


	private void createManagement() {
    	//Kostanten
		int PORT = 4500;
		//String ADDRESS = "127.0.0.1";
		String ADDRESS ="255.255.255.255" ;
		InetAddress BROAD_ADDRESS = null;
		
		//InetAddress erstellen
		try 
		{
				BROAD_ADDRESS = InetAddress.getByName(ADDRESS);
		} catch (UnknownHostException e)
		{
				e.printStackTrace();
		}	
		//Fenster und Management erzeugen + übergeben
		m = new Management(BROAD_ADDRESS, PORT);
		Log.d("management", "create management");
	}


	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       // Handle action buttons
       /*switch(item.getItemId()) {
       case R.id.action_websearch:
           // create intent to perform web search for this planet
           Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
           intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
           // catch event that there's no activity to handle intent
           if (intent.resolveActivity(getPackageManager()) != null) {
               startActivity(intent);
           } else {
               Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
           }
           return true;
       default:
           return super.onOptionsItemSelected(item);
       }*/
	return false;
   }
    
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
        	selectItem(position);
        }
    }
    
    private void selectItem(int position) {
    	
    	Fragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(MenuFragment.ITEM, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        /*
        Fragment newFragment;
        switch (position){
        case 0:
    		newFragment = new StartFragment(); 
    		break;
        case 1:
        	newFragment = new Item2(); 
    		break;
        case 2:
        	newFragment = new Item3(); 
    		break;
        case 3:
        	newFragment = new Item4(); 
    		break;
        	
        }
        */
        
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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
    }
    
}
