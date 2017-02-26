package com.example.triage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Represents the Visit Record page that appears in the application. Displays
 * the visit record at a particular time for a patient. Allows the user to
 * update vital signs or add prescriptions, and save their changes.
 */
public class VisitRecordActivity extends Activity {
    /**
     * Allows information to be presented in a list.
     */
    private ListView listView;
    /**
     * This Patient object, for which the page is displaying a VisitRecord.
     */
    private Patient patient;
    /**
     * The VitalSign objects for this Patient.
     */
    private ArrayList<VitalSign> vitalSigns;
    /**
     * This Patient's HealthRecord.
     */
    private HealthRecord hr;
    /**
     * The date of the VisitRecord.
     */
    private String date;
    /**
     * A list of the values contained in this VisitRecord.
     */
    private List<String> values;
    /**
     * StringBuilder to help with formatting the vital signs.
     */
    private StringBuilder sb;
    /**
     * The patient's prescription for this VisitRecord.
     */
    private Prescription ps;
    /**
     * String representing the instructions for this medication.
     */
    private String instruction;
    /**
     * String representing the medication name.
     */
    private String medication;
    /**
     * String for tracking the user type (nurse or physician).
     */
    private String user;

    /**
     * Initialize this activity and set the layout (a list of all information).
     * Gather entries from all the EditText fields.
     * 
     * @overrides onCreate method in android.app.Activity
     * @param savedInstanceState
     *            contains the data it most recently supplied in
     *            <code> onSaveInstanceState()</code> if the activity is being
     *            re-initialized after previously being shut down.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	hr = (HealthRecord) getIntent().getSerializableExtra("VITALSIGNS");
	patient = (Patient) getIntent().getSerializableExtra("PATIENT");
	date = (String) getIntent().getSerializableExtra("DATE");
	user = (String) getIntent().getSerializableExtra("USER");
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_visit_record);
	this.isPhysician();
	this.setListView();
    }
    
    /**
     * Checks if the user is physician and it displays physician settings 
     */
    public void isPhysician()
    {	Button updateVitalSignButton1 = (Button) findViewById(R.id.UpdateVitalSignsbutton);
	Button addPrescriptionButton = (Button) findViewById(R.id.AddprescriptionButton);
	if (user.equals("physician")) {
	    addPrescriptionButton.setVisibility(View.VISIBLE);
	    updateVitalSignButton1.setVisibility(View.INVISIBLE);
	}
    }
    
    /**
     * Set this list view with the collected patient information
     */
    public void setListView()
    {
	vitalSigns = new ArrayList<VitalSign>();
	vitalSigns.addAll(hr.getVisitRecord(date).getVitalSigns());
	ps = patient.getHealthRecord().getVisitRecord(date).getprescription();
	instruction = ps.getinstruction();
	medication = ps.getmedication();
	listView = (ListView) findViewById(R.id.WaitingListView);
	this.values = new ArrayList<String>();
	this.values.add("Medication: \n" + medication);
	this.values.add("Instruction: \n" + instruction);
	// this will rearrange it to the most recent update
	Collections.reverse(vitalSigns);
	this.formatVitalSigns(vitalSigns);
	// Define a new Adapter
	ArrayAdapter<String> adapter;
	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, android.R.id.text1,
		this.values);
	listView.setAdapter(adapter);
    }
    /**
     * Appends values from a list of VitalSign objects to a new string.
     * 
     * @param vitalsigns
     */
    public void formatVitalSigns(ArrayList<VitalSign> vitalsigns) {
	sb = new StringBuilder("\t\t\t\t\t\tLatest Update\n");
	int counter = 0;

	for (VitalSign vs : vitalSigns) {
	    if (counter == 1) {
		sb.append("\t\t\t\t\t\tPrevious Updates\n");
	    }
	    sb.append("Updated: " + vs.getUpdateTime() + "\n");
	    sb.append("Temperature: " + vs.getTemperature() + "\n");
	    sb.append("Diastolic Blood Pressure: "
		    + vs.getDiastolicBloodPressure() + "\n");
	    sb.append("Systolic Blood Pressure: "
		    + vs.getSystolicBloodPressure() + "\n");
	    sb.append("HeartRate: " + vs.getHeartRate() + "\n\n");
	    this.values.add(sb.toString());
	    counter += 1;
	    sb = new StringBuilder();// resets the string builder otherwise duplicates occur
	}
    }

    /**
     * @overrides onCreateOptions menu from
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.visit_record, menu);
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
     * Starts the UpdateVitalSignsActivity. Sends a message with an identifier
     * "PATIENT" containing this patient object. Sends a message with an
     * identifier "NEWRECORD" containing a boolean value.
     * 
     * @param v
     *            - The View of this Activity
     */
    public void updateVitalSigns(View v) {
	Intent i = new Intent(this, UpdateVitalSignsActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("NEWRECORD", false);
	i.putExtra("USER", this.user);
	setResult(RESULT_OK, i);
	startActivityForResult(i, 0);
	finish();
    }

    /**
     * Called when the "save" button is clicked on this page. Add this
     * information to the patient's text file and return to the patient page.
     * 
     * @param v
     *            the view that was clicked.
     * @throws IOException
     *             - Throw if file is not found
     */
    public void saveFile(View v) throws IOException {
	Record rc = new Record();
	rc.saveFile(this.patient, this);
	Intent i = new Intent(this, PatientActivity.class);
	i.putExtra("PATIENT", patient);
	i.putExtra("USER", this.user);
	i.putExtra("LOAD", false);
	startActivity(i);
    }

    /**
     * Called when the "Add Prescription" button is clicked. Directs to the
     * PrescriptionActivity page.
     * 
     * @param v
     *            The view that was clicked.
     */
    public void Addprescription(View v) {
	Intent i = new Intent(this, PrescriptionsActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("VITALSIGNS", patient.getHealthRecord());
	i.putExtra("Date", date);
	startActivity(i);
	finish();
    }

    /**
     * Called when the "Seen by Doctor" button is clicked. Directs to the
     * SeenDoctorActivity page.
     * 
     * @param v
     *            The view that was clicked.
     */
    public void onClickSeenByDoctor(View v) {
	Intent i = new Intent(this, SeenDoctorActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("USER", this.user);
	i.putExtra("VISIT_RECORD", hr.getVisitRecord(date));
	startActivity(i);
	finish();
    }
}