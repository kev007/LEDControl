package com.ppp.ledcontrol;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.UnknownHostException;



public abstract class MainActivity extends Activity implements OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //private CharSequence mDrawerTitle = "Menu";
    private CharSequence mTitle;
    private String[] navMenuTitles;
    
    private TableLayout table;
    private int keyframeCount = 0; 
	
    private Management m;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navMenuTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item_list, navMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
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
        
      	init();
    }
    
	private void init() {
		table = (TableLayout)findViewById(R.id.table);
        ShapeDrawable border = new ShapeDrawable(new RectShape());
		border.getPaint().setStyle(Style.STROKE);
		border.getPaint().setColor(Color.BLACK);
		//table.setBackground(border);
		
		TableRow row1 = new TableRow(this);
		TableRow rowgrad = new TableRow(this);
		TableRow rowLast = new TableRow(this);
		TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
		row1.setLayoutParams(lp);
		rowgrad.setLayoutParams(lp);
		rowLast.setLayoutParams(lp);
        
		TextView time1 = new TextView(this);
		TextView timegrad = new TextView(this);
		TextView timeLast = new TextView(this);
		time1.setText("Start Color");
		timegrad.setText("500ms");
		timeLast.setText("End Color");
		Button button1 = new Button(this);
		Button grad = new Button(this);
		Button buttonLast = new Button(this);
        button1.setOnClickListener(this);
        //grad.setOnClickListener(this);
        buttonLast.setOnClickListener(this);
        button1.setBackgroundColor(0xFFFF0000);
        buttonLast.setBackgroundColor(0xFFFFB000);
        ImageButton add = new ImageButton(this);
        add.setImageResource(android.R.drawable.ic_delete);
        
        GradientDrawable gd = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xFFFF0000,0xFFFFB000});
	    gd.setCornerRadius(0f);

        grad.setBackground(gd);
        grad.setMinHeight(200);
        
        
        row1.addView(time1);
        row1.addView(button1);
        row1.setPadding(1, 5, 1, 5);
        rowgrad.addView(timegrad);
        rowgrad.addView(grad);
        rowgrad.setPadding(1, 5, 1, 5);
        rowLast.addView(timeLast);
        rowLast.addView(buttonLast);
        rowLast.setPadding(1, 5, 1, 5);
        
        keyframeCount++;
        table.addView(row1, 1);
        table.addView(rowgrad, 2);
        table.addView(rowLast, 3);
        //table.addView(row, 2);
        //time.setText("End Color");
        //row.addView(add);
        //table.addView(row, 2);

        createManagement();
        sendBroadcast();
	}

	public void onClick(View v) {
	
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
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
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
}