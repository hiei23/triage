package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Activity page for a physician to add a prescription to a patient's visit
 * record.
 */
public class PrescriptionsActivity extends Activity {
    /**
     * Medication taken from string entered in the medication EditText
     */
    private Prescription prescription;
    /**
     * EditText taken from instruction field.
     */
    private EditText instruction;
    /**
     * EditText taken from medication field.
     */
    private EditText medication;
    /**
     * Patient object passed from intent.
     */
    private Patient patient;

    /**
     * Initialize this activity page and set the layout. Gather entries from all
     * the EditText fields.
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
	setContentView(R.layout.activity_prescriptions);
	instruction = (EditText) findViewById(R.id.instruction_field);
	medication = (EditText) findViewById(R.id.medication_field);
	patient = (Patient) getIntent().getSerializableExtra("PATIENT");

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
	getMenuInflater().inflate(R.menu.update_vital_signs, menu);
	return true;
    }

    /**
     * Saves the prescription to the visit record. Directs back to the
     * PatientActivity page.
     * 
     * @param view
     *            - the view that was clicked.
     */
    public void saveVisitRecord(View view) {

	String ins = instruction.getText().toString();
	String med = medication.getText().toString();
	prescription = new Prescription(ins, med);
	this.patient.getHealthRecord().getRecentVisitRecord()
		.setprescription(prescription);
	passIntent();
    }

    /**
     * Passes intents to the next activity
     */
    public void passIntent() {
	Intent i = new Intent(this, PatientActivity.class);
	i.putExtra("PATIENT", this.patient);
	i.putExtra("LOAD", false);
	i.putExtra("USER", "physician");
	startActivity(i);
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
}