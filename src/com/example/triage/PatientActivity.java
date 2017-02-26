package com.example.triage;

import java.io.File;
import java.io.IOException;

import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Represents the Patient page which appears in the application. Contains a
 * Patient's health card number, birthdate, name, and previous visit records.
 * Contains an add visit record button that leads to the Update Vital Signs
 * activity page.
 */
public class PatientActivity extends Activity {

    /**
     * An patient object that contains the Patient's health card number,
     * birthdate, and name.
     */
    private Patient patient;
    /**
     * A ListView that contains the dates and time of the previous VisitRecords.
     */
    private ListView listView;
    /**
     * String for tracking the user type (nurse or physician).
     */
    private String user;
    /**
     * ArrayAdapter for filling the ListView.
     */
    private ArrayAdapter<String> adapter;
    /**
     * Whether this activity page needs to be loaded.
     */
    private boolean load = false;

    /**
     * Initialize this activity page and set the layout. Gather entries from all
     * the EditText fields.
     * 
     * @overrides onCreate method in android.app.Activity.
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	patient = (Patient) getIntent().getSerializableExtra("PATIENT");
	user = (String) getIntent().getSerializableExtra("USER");
	load = (Boolean) getIntent().getSerializableExtra("LOAD");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_patient);
	TextView heath_card_textView = (TextView) findViewById(R.id.health_card_output_label);
	TextView name_textView = (TextView) findViewById(R.id.name_output_label);
	TextView birthdate_textView = (TextView) findViewById(R.id.birthdate_output_label);
	Button addVisitRecordButton1 = (Button) findViewById(R.id.addVisitRecordButton);

	String filepath = Environment.getDataDirectory().getPath().toString()
		+ "/data/com.example.triage/files/"
		+ this.patient.getHealthCardnum() + ".ser";
	File f = new File(filepath);

	if (f.exists() && load) {
	    try {
		loadFile();
	    } catch (StreamCorruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	if (user.equals("physician")) {
	    addVisitRecordButton1.setVisibility(View.INVISIBLE);
	}
	heath_card_textView.setText(patient.getHealthCardnum());
	name_textView.setText(patient.getName());
	birthdate_textView.setText(patient.getBirthdate());
	this.setListview();
    }

    /**
     * Loads the Record.
     * 
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadFile() throws StreamCorruptedException, IOException,
	    ClassNotFoundException {

	Record rc = new Record();
	this.patient = (Patient) rc.loadSavedData(this.patient, this);

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
	getMenuInflater().inflate(R.menu.patient, menu);
	return true;
    }

    /**
     * Refreshes the list view.
     */
    @Override
    protected void onResume() {
	// refresh the list view
	super.onResume();
	this.adapter.notifyDataSetChanged();
    }

    /**
     * Directs to the UpdateVitalSignsActivity page when theAdd Visit Record
     * button is clicked.
     * 
     * @param view
     *            - The view that was clicked.
     * @throws IOException
     */
    public void openUpateVitalSigns(View v) throws IOException {

	Intent i = new Intent(this, UpdateVitalSignsActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("NEWRECORD", true);
	i.putExtra("USER", this.user);
	startActivity(i);
    }

    /**
     * Records the dates and time to the previous visits scroll list when the
     * visit records is being created.
     */
    public void setListview() {

	listView = (ListView) findViewById(R.id.DatesListView);
	List<String> previousVisits = new ArrayList<String>();
	for (String date : this.patient.getHealthRecord().getAllVisitTimes()) {
	    previousVisits.add(date);
	}
	Collections.reverse(previousVisits);
	addlistener(previousVisits);
    }

    /**
     * Directs back to the MainActivity page.
     */
    public void gethome(View v) {
	Intent i = new Intent(this, MainActivity.class);
	i.putExtra("USER", user);
	setResult(RESULT_OK, i);
	startActivity(i);
	finish();
    }

    /**
     * Creates the menu of this patient's previous VisitRecords. If a previous
     * VisitRecord is clicked, go to the corresponding visit record page.
     * 
     * @param previousVisits
     *            - List of all previous VisitRecords
     */
    public void addlistener(List<String> previousVisits) {
	// Define a new Adapter
	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		previousVisits);
	// Assign adapter to ListView
	listView.setAdapter(adapter);

	// ListView Item Click Listener
	listView.setOnItemClickListener(new OnItemClickListener() {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		// ListView Clicked item value
		String itemValue = (String) listView
			.getItemAtPosition(position);
		// Show Alert
		Intent i = new Intent(view.getContext(),
			VisitRecordActivity.class);
		i.putExtra("VITALSIGNS", patient.getHealthRecord());
		i.putExtra("PATIENT", patient);
		i.putExtra("DATE", itemValue);
		i.putExtra("USER", user);
		startActivityForResult(i, 0);
	    }
	});
    }
}