package com.ppp.ledcontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity implements OnClickListener {
	private static DrawerLayout mDrawerLayout;
	static ListView mDrawerProfiles;
	private static ListView mDrawerKetten;
	private ActionBarDrawerToggle mDrawerToggle;
	private Button b_auto, b_manu;
	private static TextView ip;
	

	public static String[] navMenuProfiles;

	static int profileIndex = 0;

	Management m;
	private static String speicherort;
	public static Vector<Container> vector;
	public static boolean serverFound = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m = new Management();
		vector = new Vector<Container>();
		sendBroadcast();
		
		b_auto = (Button) findViewById(R.id.b_auto);
		b_manu = (Button) findViewById(R.id.b_manu);
		ip = (TextView) findViewById(R.id.ip);

		speicherort = getFilesDir().getAbsolutePath() + File.separator;
		loadDir();

		if (vector.size() == 0) {
			createProfile();
		}

		initDrawer();
		if (serverFound) displayIP();
	}
	
	private void createProfile() {
		updateProfileList();

		final EditText name = new EditText(MainActivity.this);
		name.setText("New Profile");
		final InputMethodManager imm = (InputMethodManager) MainActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
		}
		name.requestFocus();

		// Erstellen des Alertdialoges
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				MainActivity.this);
		alertDialogBuilder
				.setTitle("New Profile")
				.setMessage("Please enter a name")
				.setCancelable(false)
				.setView(name)
				.setPositiveButton("Set",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								imm.toggleSoftInput(0, 0);
								Boolean unique = true;
								for (int i = 0; i < vector.size(); i++) {
									if (vector.get(i).getName()
											.equals(name.getText().toString()))
										unique = false;
								}
								if (unique == true) {
									Container c = new Container(2, name
											.getText().toString(), null, true,
											null, false, null, false, null,
											false, null, false);
									saveSetting(c);
									vector.add(c);
									profileIndex = vector.indexOf(c);

									initDrawer();
									Intent intent = new Intent(
											MainActivity.this, Profile.class);
									startActivity(intent);
								} else {
									Toast.makeText(
											MainActivity.this,
											"A profile with this name already exists!",
											Toast.LENGTH_LONG).show();
									mDrawerLayout.openDrawer(mDrawerProfiles);
								}

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								imm.toggleSoftInput(0, 0);
								dialog.cancel();
							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void initDrawer() {
		updateProfileList();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerProfiles = (ListView) findViewById(R.id.left_drawer);
		mDrawerProfiles.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_item_list, navMenuProfiles));
		mDrawerProfiles
				.setOnItemClickListener(new DrawerItemClickListenerLeft());
		mDrawerProfiles
				.setOnItemLongClickListener(new DrawerItemClickListenerLeft());

		mDrawerKetten = (ListView) findViewById(R.id.right_drawer);

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

	private class DrawerItemClickListenerLeft implements
			ListView.OnItemClickListener, ListView.OnItemLongClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mDrawerLayout.closeDrawer(mDrawerProfiles);
			if (position < vector.size()) {
				profileIndex = position;

				Intent intent = new Intent(MainActivity.this, Profile.class);
				startActivity(intent);

				mDrawerProfiles.setItemChecked(position, true);
				setTitle(navMenuProfiles[profileIndex]);
				mDrawerLayout.closeDrawer(mDrawerProfiles);
			} else if (position == vector.size()) {
				createProfile();
			}
		}

		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			deleteContainer(vector.get(position));
			updateProfileList();
			return true;
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		initDrawer();
		return super.onCreateOptionsMenu(menu);
	}

	@SuppressWarnings("unused")
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerProfiles);
		// menu.findItem(R.id.add).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.broadcast:
			serverFound = false;
			sendBroadcast();
			if (serverFound) displayIP();
			return true;
		case R.id.getProfiles:
			mDrawerLayout.closeDrawer(mDrawerKetten);
			mDrawerLayout.closeDrawer(mDrawerProfiles);
			Management.sendPackage(new Container(3));
			initDrawer();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// Speichert einen Zustand am angegebene Speicherort
	public static void saveSetting(Container c) {
		int checkuuid = MainActivity.checkUUID(c.getUUID());
		if (!(checkuuid == -1)) {
			System.out.println("MainActivity: "
					+ vector.get(checkuuid).getName() + " has a UUID of: "
					+ vector.get(checkuuid).getUUID()
					+ " and will be replaced by: \n" + "MainActivity: "
					+ c.getName() + " has a UUID of: " + c.getUUID());
			String name = vector.get(checkuuid).getName();
			// UUID exisitert bereits --> überschreiben des alten Containers
			File file = new File(speicherort + name + ".ser");
			if (file.exists()) {
				System.gc();
				file.delete();
			}
		} else {
			System.out.println("MainActivity: " + c.getName()
					+ " has a UUID of: " + c.getUUID()
					+ " and will be saved for the first time");
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

	public void deleteContainer(final Container container) {
		// mDrawerLayout.closeDrawer(mDrawerProfiles);
		final String name = container.getName();
		// Erstellen des Alertdialoges
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setTitle("Delete")
				.setMessage("Do you really want to delete " + name + "?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							// Funktion bei "Ja"
							public void onClick(DialogInterface dialog, int id) {
								vector.remove(container);
								updateProfileList();
								File file = new File(speicherort + name
										+ ".ser");
								if (file.exists()) {
									System.out.println(name + " deleted");
									System.gc();
									file.delete();

									initDrawer();
								}
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

	// Dateien einlesen und ContainerVektor zurückgeben
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

	public static void sendBroadcast() {
		Management.sendPackage(Management.createZeroContainer());
		Log.d("management", "send broadcast");
	}

	public static void displayIP() {
		ip.setText(Management.address.toString() + ":" + Management.finalPort);
	}

	public void onClick(View v) {
		if(v == b_manu)
		{
			final EditText serverIP = new EditText(MainActivity.this);
			final EditText serverPort = new EditText(MainActivity.this);
			
			InputFilter[] filters = new InputFilter[1];
		    filters[0] = new InputFilter() {
		        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		            if (end > start) {
		                String destTxt = dest.toString();
		                String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
		                if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) { 
		                    return "";
		                } else {
		                    String[] splits = resultingTxt.split("\\.");
		                    for (int i=0; i<splits.length; i++) {
		                        if (Integer.valueOf(splits[i]) > 255) {
		                            return "";
		                        }
		                    }
		                }
		            }
		        return null;
		        }
		    };
		    
            serverIP.setFilters(filters);
			serverIP.setHint("255.255.255.255");
			serverPort.setHint("2797");
			serverIP.setInputType(InputType.TYPE_CLASS_PHONE);
			serverPort.setInputType(InputType.TYPE_CLASS_NUMBER);
			
			final InputMethodManager imm = (InputMethodManager) MainActivity.this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) {
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
			}
			serverIP.requestFocus();
			
			LinearLayout layout = new LinearLayout(MainActivity.this);
			layout.addView(serverIP);
			layout.addView(serverPort);
			
			// Erstellen des Alertdialoges
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);
			alertDialogBuilder
					.setTitle("Manual Server Connection")
					.setMessage("Please enter the IP-Address and Port of your Server")
					.setCancelable(false)
					.setView(layout)
					.setPositiveButton("Set",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									imm.toggleSoftInput(0, 0);
									String PATTERN = 
									        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
									        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
									        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
									        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
								    Pattern pattern = Pattern.compile(PATTERN);
								    Matcher matcher = pattern.matcher(serverIP.getText().toString());     
									if (matcher.matches()){
										InetAddress address = null;
										int port = 0;
										try {
											address = InetAddress.getByName(serverIP.getText().toString());
											if (serverPort.getText().toString().matches("")) port = 2797;
											else port = Integer.parseInt(serverPort.getText().toString());
											Management.detectedServer(address, port);
											displayIP();
										} catch (UnknownHostException e) {
											e.printStackTrace();
										}
										System.out.println("Chosen Server IP and Port. " + address.toString() + ":" + port);
									} else {
						            	Toast.makeText(MainActivity.this, "Please enter a valid IP", Toast.LENGTH_LONG).show();
									}
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									imm.toggleSoftInput(0, 0);
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
		else if(v==b_auto)
		{
			serverFound = false;
			ip.setText("Searching. Please wait...");
			final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", 
                    "Searching. Please wait...", true);
			sendBroadcast();
			new CountDownTimer(7500,750){
				int i = 0;
	            public void onTick(long miliseconds){
	            	i++;
	            	if (serverFound && i > 2) {
	            		dialog.dismiss();
	            		displayIP();
	            	}
	            }
	            public void onFinish(){
	               //after 5 seconds, execute:
	            	dialog.dismiss();
	            	if (serverFound) displayIP();
	            	else Toast.makeText(MainActivity.this, "No Server found!\nCheck your server status and ask your Network Admin about UDP broadcasts", Toast.LENGTH_LONG).show();
	            }
	        }.start();
		}
	}

	public static void updateProfileList() {
		navMenuProfiles = new String[vector.size() + 1];
		if (vector.size() > 0) {
			for (int i = 0; i < vector.size(); i++) {
				// System.out.println("Profile found: " +
				// vector.get(i).getName());
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
}
