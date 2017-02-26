package com.example.triage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
 * Gather the vital signs entered on the UpdateVitalSignsActivity page and add
 * them to this patient's most recent visit record.
 */
public class UpdateVitalSignsActivity extends Activity {

    /**
     * The Patient passed from VisitRecordActivity via Serializable.
     */
    private Patient patient;
    /**
     * Number taken from string entered in the temperature field.
     */
    private double temperature;
    /**
     * Number taken from string entered in the systolic blood pressure EditText.
     */
    private int bloodpressure_sys;
    /**
     * Number taken from string entered in the diastolic blood pressure
     * EditText.
     */
    private int bloodpressure_dia;
    /**
     * Number taken from string entered in the heart rate EditText.
     */
    private int heartrate;
    /**
     * EditText taken from heart rate field.
     */
    private EditText heartrateText;
    /**
     * EditText taken from diastolic blood pressure field.
     */
    private EditText diabloodpressureText;
    /**
     * EditText taken from systolic blood pressure field.
     */
    private EditText sysbloodpressureText;
    /**
     * EditText taken from temperature field.
     */
    private EditText tempText;
    /**
     * TextView that is visible when there are empty fields present.
     */
    private TextView tv;
    /**
     * Boolean to check whether there is a new VisitRecord added to this
     * HealthRecord.
     */
    private boolean newRecord;
    /**
     * The current WaitingList instance.
     */
    private WaitingList waitingList;
    /**
     * String for tracking the user type (nurse or physician).
     */
    private String user;

    /**
     * This patient visit record
     */
    private VisitRecord visitRecord;

    /**
     * Initialize this activity and set the layout. Gather entries from all the
     * EditText fields.
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
	setContentView(R.layout.activity_update_vital_signs);
	patient = (Patient) getIntent().getSerializableExtra("PATIENT");
	newRecord = (Boolean) getIntent().getSerializableExtra("NEWRECORD");
	tempText = (EditText) findViewById(R.id.temperature_field);
	user = (String) getIntent().getSerializableExtra("USER");
	sysbloodpressureText = (EditText) findViewById(R.id.bloodpressure_sys_field1);
	diabloodpressureText = (EditText) findViewById(R.id.bloodpressure_dia_field1);
	heartrateText = (EditText) findViewById(R.id.heartrate_field);
	tv = (TextView) findViewById(R.id.noneEmptyFieldsTextView);
	waitingList = WaitingList.getInstance();
    }

    /**
     * Auto generated method to initialize the contents of this activity's
     * standard options menu.
     * 
     * @param menu
     * @return True for the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.update_vital_signs, menu);
	return true;
    }

    /**
     * Auto generated method called whenever an item in your options menu is
     * selected.
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
     * Called from the "Save" button on the UpdateVitalSigns activity page.
     * Retrieve the vitals signs (temperature, systolicBloodPressure,
     * diastolicBloodPressure and heartRate) and add them to this patient's most
     * recent visit record.
     * 
     * @param view
     *            the view that was clicked.
     */
    public void saveToVisitRecord(View view) {

	// get temperature from 1st field
	String temp = tempText.getText().toString();
	// sys blood rate from 2nd
	String sysbloodp = sysbloodpressureText.getText().toString();
	// dia blood rate from 3rd
	String diabloodp = diabloodpressureText.getText().toString();
	// heart rate from 4th
	String heartr = heartrateText.getText().toString();

	if (!isNumeric(temp, 0) || !isNumeric(sysbloodp, 1)
		|| !isNumeric(diabloodp, 1) || !isNumeric(heartr, 1)) {
	    tv.setVisibility(View.VISIBLE);
	}

	if (!temp.isEmpty() && !sysbloodp.isEmpty() && !diabloodp.isEmpty()
		&& !heartr.isEmpty()) {
	    parseValues(temp, sysbloodp, diabloodp, heartr);
	    VitalSign vitalSign = new VitalSign(temperature, bloodpressure_sys,
		    bloodpressure_dia, heartrate);
	    if (this.newRecord) {
		createNewVisitRecord(vitalSign);
	    } else {
		updateVisitRecord(vitalSign);
	    }
	    passIntent();
	}
    }

    /**
     * Create a new visit record for this patient and put them into the waiting
     * list
     * 
     * @param vitalSign
     *            This patient vital sign
     */
    public void createNewVisitRecord(VitalSign vitalSign) {
	HealthRecord healthRecord = this.patient.getHealthRecord();
	healthRecord.addVisitRecord();
	visitRecord = this.patient.getHealthRecord().getRecentVisitRecord();
	visitRecord.addUpdatedVitalSigns(vitalSign);
	visitRecord.setprescription(new Prescription("None", "None"));
	waitingList.addToWaitingList(this.patient);
	// new visit = not seen by doctor
	this.patient.setSeenByDoctor(false);
	this.saveToFile();
    }

    /**
     * Update this patient vital sign for the given Visit Record and it updates
     * the waiting list
     */
    public void updateVisitRecord(VitalSign vitalSign) {
	visitRecord = this.patient.getHealthRecord().getRecentVisitRecord();
	waitingList.removeFromWaitingList(this.patient);
	visitRecord.addUpdatedVitalSigns(vitalSign);
	waitingList.addToWaitingList(this.patient);
    }

    /**
     * Pass intents to another activity
     */
    public void passIntent() {
	Intent i = new Intent(this, PatientActivity.class);
	i.putExtra("PATIENT", patient);
	i.putExtra("USER", this.user);
	i.putExtra("LOAD", false);
	setResult(RESULT_OK, i);
	startActivity(i);
	finish();
    }

    /**
     * Saves the waitingList to the file waitingList.ser.
     */
    public void saveToFile() {
	try {
	    FileOutputStream fos = this.openFileOutput("waitingList.ser",
		    Context.MODE_PRIVATE);
	    ObjectOutputStream os = new ObjectOutputStream(fos);
	    os.writeObject(waitingList);
	    os.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create numeric values out of the strings stringTemperature,
     * stringSystolic, stringDiastolic and stringHeartRate.
     * 
     * @param stringTemperature
     *            - a string of temperature.
     * @param stringSystolic
     *            - a string of systolic blood pressure.
     * @param stringDiastolic
     *            - a string of diastolic blood pressure.
     * @param stringHeartRate
     *            - a string of heart rate.
     */
    public void parseValues(String stringTemperature, String stringSystolic,
	    String stringDiastolic, String stringHeartRate) {

	this.temperature = Double.parseDouble(stringTemperature);
	this.bloodpressure_sys = Integer.parseInt(stringSystolic);
	this.bloodpressure_dia = Integer.parseInt(stringDiastolic);
	this.heartrate = Integer.parseInt(stringHeartRate);
    }

    /**
     * Check whether string is a double (x = 0) or whether string is an integer
     * (x = 1).
     * 
     * @param string
     *            - string to be checked.
     * @param x
     *            - indicates whether to check for double (0) or integer (1).
     * @return boolean. True if string is a double or an integer, false if
     *         neither.
     */
    public boolean isNumeric(String string, int x) {
	if (x == 0) {
	    try {
		Double.parseDouble(string);
	    } catch (NumberFormatException dExc) {
		return false;
	    }
	    return true;
	} else if (x == 1) {
	    try {
		Integer.parseInt(string);
	    } catch (NumberFormatException iExc) {
		return false;
	    }
	    return true;
	}
	return false;
    }
}