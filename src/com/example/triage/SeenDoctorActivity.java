package com.example.triage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Activity page that allows a nurse to add a new time that a patient has seen a
 * doctor. This page also displays previous times that a patient has seen a
 * doctor on this visit.
 */
public class SeenDoctorActivity extends Activity {
    /**
     * Patient object that was passed through intent.
     */
    private Patient patient;
    /**
     * The VisitRecord that is currently being viewed.
     */
    private VisitRecord thisVisit;
    /**
     * Boolean to keep track of whether the checkbox is checked.
     */
    private boolean checked;
    /**
     * The waiting list instance.
     */
    private WaitingList waitingList;
    /**
     * TextView to display the times that the patient has seen a doctor.
     */
    private TextView viewTimesSeen;
    /**
     * ListView to display the times that the patient has seen a doctor.
     */
    private ListView listView;
    /**
     * Adapter to help fill the ListView.
     */
    private ArrayAdapter<String> adapter;
    /**
     * String to keep track of the user type (either nurse or physician).
     */
    private String user;

    /**
     * Initialize this activity page and set the layout.
     * 
     * @overrides onCreate method in android.app.Activity
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	patient = (Patient) getIntent().getSerializableExtra("PATIENT");
	thisVisit = (VisitRecord) getIntent().getSerializableExtra(
		"VISIT_RECORD");
	this.user = (String) getIntent().getSerializableExtra("USER");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_seen_doctor);
	viewTimesSeen = (TextView) findViewById(R.id.SeenTextView1);
	isPhysician();
	this.setListView();
    }

    /**
     * displays the list view with the dates that a patient has been seen by a
     * doctor
     */
    public void setListView() {
	thisVisit = this.patient.getHealthRecord().getRecentVisitRecord();
	listView = (ListView) findViewById(R.id.seenByDoctorlistView);
	Collections.sort(thisVisit.getTimesSeen());
	Collections.reverse(thisVisit.getTimesSeen());
	if (!thisVisit.getTimesSeen().isEmpty()) {
	    String s = this.patient.getName() + " has seen a doctor on: ";
	    viewTimesSeen.setText(s);
	    viewTimesSeen.setVisibility(View.VISIBLE);
	}
	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		thisVisit.getTimesSeen());
	listView.setAdapter(adapter);
    }

    /**
     * Checks if the user is a physician and displays physician settings
     */
    public void isPhysician() {
	if (user.equals("physician")) {
	    CheckBox seen = (CheckBox) findViewById(R.id.checkBox1);
	    seen.setVisibility(View.INVISIBLE);
	    Button save = (Button) findViewById(R.id.button1);
	    save.setVisibility(View.INVISIBLE);
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
	getMenuInflater().inflate(R.menu.seen_doctor, menu);
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
     * Refreshes this page.
     */
    public void refresh() {
	Intent refresh = new Intent(this, SeenDoctorActivity.class);
	startActivity(refresh);
	this.finish();
    }

    /**
     * Checks if the patient has been seen by a doctor or not.
     * 
     * @param view
     *            the view that was clicked.
     */
    public void setVisitSeen(View view) {
	CheckBox seen = (CheckBox) findViewById(R.id.checkBox1);
	if (seen.isChecked()) {
	    // remember it was checked
	    checked = true;
	} else {
	    // it's been unchecked
	    checked = false;
	}
    }

    /**
     * Saves the seen by doctor information. Directs back to the PatientActivity
     * page.
     * 
     * @param view
     * @throws IOException
     */
    public void onClickSave(View view) throws IOException {
	// first check if this is the most recent visit record
	if (this.checked == true) { // if checked == false do nothing
	    CheckBox seen = (CheckBox) findViewById(R.id.checkBox1);
	    seen.setChecked(false);
	    checked = false;
	    patient.setSeenByDoctor(true);
	    thisVisit.setSawDoctor();
	    waitingList = WaitingList.getInstance();
	    waitingList.removeFromWaitingList(patient);
	    // write to file
	    this.saveTofile();
	    adapter.notifyDataSetChanged();
	    passIntent();
	}
    }

    /**
     * Passes intents to the next activity
     */
    public void passIntent() {
	Intent i = new Intent(this, PatientActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("USER", this.user);
	i.putExtra("LOAD", false);
	startActivity(i);
	finish();
    }

    /**
     * Saves the seen by doctor information.
     * 
     * @throws IOException
     */
    public void saveTofile() throws IOException {
	try {
	    FileOutputStream fos = this.openFileOutput("waitingList.ser",
		    Context.MODE_PRIVATE);
	    ObjectOutputStream os = new ObjectOutputStream(fos);
	    os.writeObject(waitingList);
	    os.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	Record rec = new Record();
	rec.saveFile(this.patient, this);
    }
}