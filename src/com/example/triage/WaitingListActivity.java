package com.example.triage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity page where the user can view patients who have not seen a doctor on
 * their most recent visit. The user can sort the list by urgency or arrival
 * time.
 */
public class WaitingListActivity extends Activity {
    /**
     * The single WaitingList instance.
     */
    private WaitingList waitinglist = WaitingList.getInstance();
    /**
     * List of Patient objects represented in a String who are waiting to see a
     * doctor.
     */
    private List<String> waitingPatients;
    /**
     * String for tracking the user type (nurse or physician).
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
	setContentView(R.layout.activity_waiting_list);
	user = (String) getIntent().getSerializableExtra("USER");
	setListview();
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
	getMenuInflater().inflate(R.menu.waiting_list, menu);
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
     * Refreshes the list view.
     */
    @Override
    protected void onResume() {
	// refresh the list view
	super.onResume();
	this.onCreate(null);
    }

    /**
     * Loads the list view with patients who are waiting to see a doctor.
     */
    public void setListview() {

	ListView listView = (ListView) findViewById(R.id.WaitingListView2);
	// Define a new Adapter
	waitingPatients = new ArrayList<String>();
	for (Patient patient : waitinglist.getWaitingList()) {
	    waitingPatients.add(patient.toString());
	}
	ArrayAdapter<String> adapter;
	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		waitingPatients);
	listView.setAdapter(adapter);

    }

    /**
     * Sorts the list by the Patients' urgency ratings.
     * 
     * @param v
     *            This view
     */
    public void sortByUrgency(View v) {
	waitinglist.sortByDecreasingUrgency();
	this.onResume();
    }

    /**
     * Sorts the list by the Patients' arrival times (most recent time last).
     * 
     * @param v
     *            This view
     */
    public void sortByArrival(View v) {
	waitinglist.sortByArrivalTime();
	this.onResume();
    }

    /**
     * A home button that directs to the MainActivity page.
     */
    public void gethome(View v) {
	Intent i = new Intent(this, MainActivity.class);
	i.putExtra("USER", user);
	setResult(RESULT_OK, i);
	startActivity(i);
	finish();
    }
}