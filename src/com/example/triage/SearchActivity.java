package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Represents the main page of the application, which can search up the patient
 * based on the health card number.
 */
public class SearchActivity extends Activity {
    /**
     * A Map that contains the Patient's data.
     */
    private Map<Long, Patient> patient_data = new TreeMap<Long, Patient>();
    /**
     * A Patient object that contains the Patient's health card number,
     * birthdate, and name.
     */
    private Patient patient;
    /**
     * String for tracking the user type (nurse or physician).
     */
    private String user;

    /**
     * Initializes the page.
     * 
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_search);
	user = (String) getIntent().getSerializableExtra("USER");
	try {
	    this.loadSavedRecord();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * Initializes the contents of the PatientActivity's standard option menu.
     * Inflate the menu; this adds items to the action bar if it is present.
     * 
     * @param menu
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.search, menu);
	return true;
    }

    /**
     * Imports the patient text file into the application. Takes out the
     * Patients' name, birthdate, and health card number and create Patient
     * objects.
     * 
     * @throws FileNotFoundException
     *             if no text file exists.
     */
    public void loadSavedRecord() throws FileNotFoundException {
	String filepath = Environment.getDataDirectory().getPath().toString()
		+ "/data/com.example.triage/files/patient_records.txt";
	Scanner file = new Scanner(new FileInputStream(filepath));
	while (file.hasNextLine()) {
	    String line = file.nextLine();
	    if (!line.isEmpty()) {
		String[] parts = line.split(",");
		String cardnum = parts[0];
		String name = parts[1];
		String birthdate = parts[2];
		patient_data.put(Long.parseLong(cardnum), new Patient(cardnum,
			name, birthdate));
	    }
	}
	file.close();
    }

    /**
     * Searches for the corresponding Patient based on the health card number.
     * Directs to the PatientActivity page. Displays the file not found text if
     * the health card number is not in the database.
     * 
     * @param v
     *            the view that was clicked.
     * @throws FileNotFoundException
     */
    public void searchPatient(View v) throws FileNotFoundException {

	EditText healthCardNumberText = (EditText) findViewById(R.id.newHealthCardnumInputText);
	String healthcard = healthCardNumberText.getText().toString();
	TextView notfoundtxtView = (TextView) findViewById(R.id.notfoundtextView2);

	this.loadSavedRecord();
	// Longpatient_healthcardnum

	if (!healthcard.isEmpty()
		&& this.patient_data.containsKey(Long.parseLong(healthcard))) {
	    Patient p = this.patient_data.get(Long.parseLong(healthcard));
	    this.patient = p;
	    passIntent();
	} else {
	    notfoundtxtView.setVisibility(View.VISIBLE);
	}
    }

    /**
     * Passes intents to the next activity
     */
    public void passIntent() {
	Intent i = new Intent(this, PatientActivity.class);
	i.putExtra("PATIENT", patient);
	i.putExtra("USER", user);
	i.putExtra("LOAD", true);
	setResult(RESULT_OK, i);
	startActivity(i);
	finish();
    }
}