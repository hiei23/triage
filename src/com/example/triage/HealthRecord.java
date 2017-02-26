package com.example.triage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A health record for a patient that contains all of their visit records.
 */
public class HealthRecord implements Serializable {
    /**
     * Long value allows objects of HealthRecord type to be passed through
     * activities.
     */
    private static final long serialVersionUID = 5241975397270194837L;
    /**
     * A health record is a map of unique strings (arrival times) to a specific
     * visit record.
     */
    private Map<String, VisitRecord> healthRecord;
    /**
     * The most recent visit record in this health record.
     */
    private VisitRecord mostRecentVisitRecord;
    /**
     * The most recent visit (identified by a unique time).
     */
    private String mostRecentVisit;
    /**
     * This Patient's arrival time.
     */
    private String arrivalTime;

    /**
     * Initializes this HealthRecord.
     */
    public HealthRecord() {
	this.healthRecord = new TreeMap<String, VisitRecord>();
	this.arrivalTime = Calendar.getInstance().getTime().toString();

    }

    /**
     * Returns this Patient's arrival time.
     * 
     * @return This Patient's arrival time
     */
    public String getArrivalTime() {
	return this.arrivalTime;
    }

    /**
     * Modifies this Patient's arrival time.
     * 
     * @param time
     *            - This Patient's arrival time.
     */
    public void setArrivalTime(String time) {
	this.arrivalTime = time;
    }

    /**
     * Returns a VisitRecord associated with this date.
     * 
     * @param date
     *            - The date of this Patient's visit.
     * @return a VisitRecord associated with this date.
     */
    public VisitRecord getVisitRecord(String date) { // precondition: there is a
						     // Visit Record

	return this.healthRecord.get(date);
    }

    /**
     * Returns the most recent VisitRecord of this Patient.
     * 
     * @return The most recent VisitRecord.
     */
    public VisitRecord getRecentVisitRecord() {
	// precondition: there is visit
	// record
	return this.healthRecord.get(this.mostRecentVisit);
    }

    /**
     * Creates a new VisitRecord object with the current time as the arrival
     * time.
     */
    public void addVisitRecord() {
	this.setArrivalTime(Calendar.getInstance().getTime().toString());
	this.healthRecord.put(this.getArrivalTime(),
		new VisitRecord(this.getArrivalTime()));
	this.mostRecentVisit = this.getArrivalTime();
    }

    /**
     * Creates a VisitRecord with the given arrival time.
     * 
     * @param arrivaltime
     *            - A new VisitRecord's arrival time.
     */
    public void addVisitRecord(String arrivaltime) {

	this.arrivalTime = arrivaltime;
	this.healthRecord.put(arrivaltime,
		new VisitRecord(this.getArrivalTime()));
	this.mostRecentVisit = arrivaltime;
    }

    /**
     * Updates the most recent VisitRecord of this patient.
     * 
     * @param vitalSigns
     *            - this Patient's current vital Signs
     */
    public void updateRecentVisit(VitalSign vitalSigns) {
	// precondition: there is a Visit Record
	mostRecentVisitRecord = this.getRecentVisitRecord(); // this is a visit
							     // record
	mostRecentVisitRecord.addUpdatedVitalSigns(vitalSigns);
	this.healthRecord.put(this.getArrivalTime(), mostRecentVisitRecord);
    }

    /**
     * Returns all dates that this Patient visited the hospital.
     * 
     * @return all visit times of this Patient
     */
    public Set<String> getAllVisitTimes() {
	return this.healthRecord.keySet();
    }

    /**
     * Returns a string representation of this HealthRecord.
     * 
     * @overrides toString in class java.lang.Object
     * @return A string with the dates of all visit to the hospital:
     */

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("");
	int counter = 0;

	for (String date : this.getAllVisitTimes()) {
	    if (counter == this.getAllVisitTimes().size()) {
		sb.append(date);
	    } else {
		sb.append(date + ", ");
	    }
	}
	return "HealthRecord: " + sb.toString();
    }

    /**
     * Returns if there is a HealthRecord or not.
     * 
     * @return boolean. True if there is a HealthRecord, false otherwise.
     */
    public boolean hasVisits() {
	return (this.healthRecord.size() == 0);
    }
}