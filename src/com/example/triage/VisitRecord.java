package com.example.triage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * A visit record for a specific patient at a specific time. One visit record
 * may be empty or contain several VitalSign objects (all created at a specific
 * time).
 */
public class VisitRecord implements Serializable {

    /**
     * Long serialID allows objects of VisitRecord type to be passed through
     * activities.
     */
    private static final long serialVersionUID = -6351267645256247866L;
    /**
     * All the VitalSign objects recorded in this VisitRecord.
     */
    private String allVitalSigns;
    /**
     * The time of arrival for this VisitRecord.
     */
    private String arrivalTime;
    /**
     * A list of vital sign updates as VitalSign objects.
     */
    private ArrayList<VitalSign> updates;
    /**
     * This VisitRecord's prescription.
     */
    private Prescription prescription;
    /**
     * This VisitRecord's recent VitalSign object.
     */
    private VitalSign mostRecentVitalSign;
    /**
     * Boolean whether the Patient has seen a doctor on this visit.
     */
    private boolean sawDoctor;
    /**
     * List of all the times the Patient has seen a doctor on this visit.
     */
    private List<String> timesSeen;

    /**
     * Initializes a visit record by an arrival time.
     * 
     * @param arrivalTime
     *            - The time of this visit
     */
    public VisitRecord(String arrivalTime) {
	this.arrivalTime = arrivalTime;
	this.updates = new ArrayList<VitalSign>();

	// changes
	this.sawDoctor = false;
	this.timesSeen = new ArrayList<String>();
	// end
    }

    /**
     * Returns if the Patient has already seen by the doctor at this visit.
     * 
     * @return boolean True if seen by doctor, false otherwise.
     */
    public boolean getSawDoctor() {
	return this.sawDoctor;
    }

    /**
     * Sets to true if the Patient has already seen by the doctor at this visit.
     */
    public void setSawDoctor() {
	this.sawDoctor = true;
	String newTimeSeen = Calendar.getInstance().getTime().toString();
	this.timesSeen.add(newTimeSeen);
    }

    /**
     * Returns the times when the Patient saw the doctor at this visit.
     * 
     * @return List of times when the Patient saw the doctor.
     */
    public List<String> getTimesSeen() {
	return this.timesSeen;
    }

    /**
     * Returns a string of times that the Patient saw the doctor on this visit.
     * 
     * @return String of times.
     */
    public String stringTimesSeen() {
	StringBuilder builder = new StringBuilder();
	Iterator<String> itr = this.timesSeen.iterator();
	while (itr.hasNext()) {
	    builder.append(itr.next());
	    builder.append("\n");
	}
	return builder.toString();
    }

    /**
     * Return the arrival time of this visit.
     * 
     * @return String of the arrival time
     */
    public String getArrivalTime() {
	return arrivalTime;
    }

    /**
     * Set the arrival time of this visit.
     * 
     * @param arrivalTime
     *            - String of the arrival time
     */
    public void setArrivalTime(String arrivalTime) {
	this.arrivalTime = arrivalTime;
    }

    /**
     * Add new vital signs to this visit record.
     * 
     * @param newVitalSigns
     *            - a recent VitalSign object
     */
    public void addUpdatedVitalSigns(VitalSign newVitalSigns) {
	this.mostRecentVitalSign = newVitalSigns;
	this.updates.add(newVitalSigns);
    }

    /**
     * Get all the vital signs recorded during this visit.
     * 
     * @return ArrayList<VitalSign> - a list of VitalSign objects
     */
    public ArrayList<VitalSign> getVitalSigns() {
	return updates;
    }

    /**
     * Returns this Visit prescription
     * 
     * @return This Visit prescription
     */
    public Prescription getprescription() {
	return prescription;
    }

    /**
     * Modifies this Visit prescription
     * 
     * @param prescription
     *            a prescription object
     */
    public void setprescription(Prescription prescription) {
	this.prescription = prescription;

    }

    /**
     * Returns this VisitRecord's recent VitalSign object.
     * 
     * @return This VisitRecord's recent VitalSign object.
     */
    public VitalSign getMostRecentVitalSign() {
	return mostRecentVitalSign;
    }

    /**
     * Returns a string representation of this object.
     * 
     * @overrides toString in class java.lang.Object
     * @return A string with the visit time, time of vital sign updates and the
     *         vital signs.
     */
    @Override
    public String toString() {
	allVitalSigns = "";
	for (VitalSign v : this.updates) {
	    allVitalSigns = allVitalSigns + arrivalTime + "," + v.toString()
		    + "\n";
	}
	return allVitalSigns;
    }
}