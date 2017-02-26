package com.example.triage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The activity page of the application where nurses can add patients to the
 * records.
 */
public class AddPatient extends Activity {
    /**
     * EditText to retrieve the name of a new Patient.
     */
    private EditText nameText;
    /**
     * EditText to retrieve the health card number of a new Patient.
     */
    private EditText healthcardnumText;
    /**
     * EditText to retrieve the year of birth of a new Patient.
     */
    private EditText birth_yearText;
    /**
     * EditText to retrieve the month of birth of a new Patient.
     */
    private EditText birth_monthText;
    /**
     * EditText to retrieve the day of birth of a new Patient.
     */
    private EditText birth_dateText;
    /**
     * String containing the file patient_records.txt.
     */
    private String patientrecordText;
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
	setContentView(R.layout.activity_add_patient);
	user = (String) getIntent().getSerializableExtra("USER");
	nameText = (EditText) findViewById(R.id.patientName);
	healthcardnumText = (EditText) findViewById(R.id.healthcardnum);
	birth_yearText = (EditText) findViewById(R.id.birth_year);
	birth_monthText = (EditText) findViewById(R.id.birth_month);
	birth_dateText = (EditText) findViewById(R.id.birth_date);
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
	getMenuInflater().inflate(R.menu.add_patient, menu);
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
     * Loads the patient_records.txt from the raw file and saves it in the
     * internal storage of the device.
     * 
     * @throws FileNotFoundException
     *             if no text file exists.
     */
    public void loadPatientFile() {
	FileOutputStream outputStream;

	try {
	    InputStream is = getResources().openRawResource(
		    R.raw.patient_records);
	    int fileLen = is.available();
	    byte[] fileBuffer = new byte[fileLen];
	    is.read(fileBuffer);
	    is.close();
	    patientrecordText = new String(fileBuffer);

	    try {
		outputStream = openFileOutput("patient_records.txt",
			Context.MODE_PRIVATE);
		outputStream.write(patientrecordText.getBytes());
		outputStream.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} catch (IOException e) {
	    // exception handling
	}
    }

    /**
     * Saves a new patient and their information in the patient_records text
     * file. Directs to the SearchActivity page.
     * 
     * @param v
     *            v the view that was clicked.
     * @throws IOException
     *             - throw if file is not found.
     */
    public void savePatient(View v) throws IOException {

	String patientName = nameText.getText().toString();
	String healthcardNum = healthcardnumText.getText().toString();
	String birthyear = birth_yearText.getText().toString();
	String birthmonth = birth_monthText.getText().toString();
	String birthdate = birth_dateText.getText().toString();
	String birthday = birthyear + "-" + birthmonth + "-" + birthdate;
	String newPatient = "\r\n" + healthcardNum + "," + patientName + ","
		+ birthday;
	TextView emptytxtView = (TextView) findViewById(R.id.emptytextView2);
	FileOutputStream outputStream;
	// open the file in raw first, but save it in internal storage and use
	// it.
	if (patientName.isEmpty() || healthcardNum.isEmpty()
		|| birthyear.isEmpty() || birthmonth.isEmpty()
		|| birthdate.isEmpty()) {
	    emptytxtView.setVisibility(View.VISIBLE);
	} else {
	    try {
		outputStream = openFileOutput("patient_records.txt",
			Context.MODE_APPEND);
		outputStream.write(newPatient.getBytes());
		outputStream.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    passIntent();
	}
    }

    /**
     * Passes intent to the next activity.
     */
    public void passIntent() {
	Intent i = new Intent(this, MainActivity.class);
	i.putExtra("USER", user);
	startActivityForResult(i, 0);
	finish();
    }
}