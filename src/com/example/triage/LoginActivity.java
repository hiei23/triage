package com.example.triage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity page for a user (either a nurse or a physician) to log in to the
 * application.
 */
public class LoginActivity extends Activity {

    /**
     * A Map that contains the data for login information.
     */
    private Map<String, LoginInformation> login_data;
    /**
     * A String that contains passwords.txt.
     */
    private String showText = "";
    /**
     * String that contains patient_records.txt.
     */
    private String patientFileText = "";

    /**
     * Initialize this activity and set the layout.
     * 
     * @overrides onCreate method in android.app.Activity.
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);
	login_data = new TreeMap<String, LoginInformation>();
	String filepath = Environment.getDataDirectory().getPath().toString()
		+ "/data/com.example.triage/files/patient_records.txt";
	File f = new File(filepath);
	try {
	    this.loadpasswordFile();
	    if (!f.exists()) {
		this.loadpatientFile();
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	this.organizedLoginInfo();
    }

    /**
     * Initializes the contents of the standard option menu. Inflate the menu;
     * this adds items to the action bar if it is present.
     * 
     * @param menu
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    /**
     * Initializes the contents of the standard option menu. Inflate the menu;
     * this adds items to the action bar if it is present.
     * 
     * @param menu
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.action_settings) {
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

    /**
     * Loads the passwords.txt from the raw file and saves it in the internal
     * storage of the device.
     * 
     * @throws FileNotFoundException
     *             if no text file exists.
     */
    public void loadpasswordFile() throws FileNotFoundException {
	FileOutputStream outputStream;

	try {
	    // open the file from raw
	    InputStream is = getResources().openRawResource(R.raw.passwords);
	    int fileLen = is.available();
	    // Read the entire resource into a local byte buffer.
	    byte[] fileBuffer = new byte[fileLen];
	    is.read(fileBuffer);
	    is.close();
	    showText = new String(fileBuffer);

	    try {
		// save file in internal storage
		outputStream = openFileOutput("passwords.txt",
			Context.MODE_PRIVATE);
		outputStream.write(showText.getBytes());
		outputStream.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	} catch (IOException e) {
	    // exception handling
	}
    }

    /**
     * Loads the patient_records.txt from the raw file and saves it in the
     * internal storage of the device.
     * 
     * @throws FileNotFoundException
     *             if no text file exists.
     */
    public void loadpatientFile() throws FileNotFoundException {
	FileOutputStream outputStream;

	try {
	    // open the file from raw
	    InputStream is = getResources().openRawResource(
		    R.raw.patient_records);
	    int fileLen = is.available();
	    // Read the entire resource into a local byte buffer.
	    byte[] fileBuffer = new byte[fileLen];
	    is.read(fileBuffer);
	    is.close();
	    patientFileText = new String(fileBuffer);

	    // save file in internal storage
	    outputStream = openFileOutput("patient_records.txt",
		    Context.MODE_PRIVATE);
	    outputStream.write(patientFileText.getBytes());
	    outputStream.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Loads the patient_records.txt from the internal storage. Takes out the
     * user's username, password, and type (nurse or physician) to create
     * LoginInformation objects.
     */
    public void organizedLoginInfo() {
	// read file from internal, put info in Map
	String[] passwordText = showText.split("\n");
	String username;
	String passwords;
	String type;
	for (String e : passwordText) {
	    String[] parts = e.split(",");
	    username = parts[0];
	    passwords = parts[1];
	    type = parts[2].trim();

	    login_data.put(username, new LoginInformation(username, passwords,
		    type));
	}
    }

    /**
     * Login based on the username and password. Directs to the SearchActivity
     * page. Displays an error message if the username or password is wrong, or
     * if there are empty fields.
     * 
     * @param v
     *            the view that was clicked.
     */
    public void login(View v) {
	EditText userNameText = (EditText) findViewById(R.id.username);
	String userName = userNameText.getText().toString();
	EditText passWordText = (EditText) findViewById(R.id.password);
	String passWord = passWordText.getText().toString();
	TextView emptytxtView = (TextView) findViewById(R.id.emptytextView1);
	TextView wrongtxtView = (TextView) findViewById(R.id.wrongtextView1);

	// check if empty
	if (!userName.isEmpty() && !passWord.isEmpty()) {
	    // check if username in data if password is correct
	    if (this.login_data.containsKey(userName)
		    && this.login_data.get(userName).getPassword()
			    .equals(passWord)) {
		// check nurse type(nurse of physician)
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("USER", this.login_data.get(userName).getType());
		startActivity(i);
		finish();
	    } else {
		wrongtxtView.setVisibility(View.VISIBLE);
		emptytxtView.setVisibility(View.INVISIBLE);
	    }
	} else {
	    emptytxtView.setVisibility(View.VISIBLE);
	    wrongtxtView.setVisibility(View.INVISIBLE);
	}
    }
}