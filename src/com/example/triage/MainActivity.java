package com.example.triage;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * The main page of the application, which the user can log in using their user
 * name and password.
 */
public class MainActivity extends Activity {
    /**
     * The current user (nurse or physician).
     */
    private String user;

    /**
     * Initialize this activity and set the layout.
     * 
     * @overrides onCreate method in android.app.Activity
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	user = (String) getIntent().getSerializableExtra("USER");

	if (user.equals("physician")) {
	    Button addPatientButton1 = (Button) findViewById(R.id.addPatientButton);
	    addPatientButton1.setVisibility(View.INVISIBLE);
	}
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
     * Starts a new LoginActivity
     * 
     * @param v
     *            The view that was clicked.
     */
    public void logout(View v) {
	Intent i = new Intent(this, LoginActivity.class);
	setResult(RESULT_OK, i);
	startActivityForResult(i, 0);
	finish();
    }

    /**
     * Starts a new searchPatient activity
     * 
     * @param v
     *            The view that was clicked.
     */
    public void searchPatient(View v) {
	Intent i = new Intent(this, SearchActivity.class);
	i.putExtra("USER", user);
	startActivityForResult(i, 0);
	finish();
    }

    /**
     * Starts a new AddPatient activity
     * 
     * @param v
     *            The view that was clicked.
     */
    public void addPatient(View v) {
	Intent i = new Intent(this, AddPatient.class);
	i.putExtra("USER", user);
	startActivityForResult(i, 0);
	finish();
    }

    /**
     * Starts a new WaitingList Activity
     * 
     * @param v
     *            The view that was clicked.
     */
    public void viewWaitingList(View v) {
	Intent i = new Intent(this, WaitingListActivity.class);
	i.putExtra("USER", user);
	startActivityForResult(i, 0);
	finish();
    }
}