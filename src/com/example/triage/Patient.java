package com.example.triage;

import java.io.Serializable;
import java.util.Calendar;

/**
 * A patient that visits the hospital. Each patient has personal data and a
 * health record.
 */
public class Patient implements Serializable {

    /**
     * Long value allows objects of Patient type to be passed through
     * activities.
     */
    private static final long serialVersionUID = -8451347516000034306L;
    /**
     * String representing the Patient's health card number.
     */
    private String healthCardnum;
    /**
     * String representing the Patient's first and last name.
     */
    private String name;
    /**
     * String representing the Patient's date of birth.
     */
    private String birthdate;
    /**
     * The Patient's personal HealthRecord.
     */
    private HealthRecord healthRecord;
    /**
     * Boolean value indicating whether the patient has seen a doctor on their
     * most recent visit.
     */
    private boolean seenByDoctor;

    /**
     * Initializes this Patient based on the passed parameters
     * 
     * @param hCardnum
     *            - This Patient's health card number.
     * @param name
     *            - This Patient's name.
     * @param birthdate
     *            - This Patient's birthdate.
     */
    public Patient(String hCardnum, String name, String birthdate) {
	this.healthCardnum = hCardnum;
	this.name = name;
	this.birthdate = birthdate;
	this.healthRecord = new HealthRecord();
	this.seenByDoctor = false;
    }

    /**
     * Returns if this Patient has been seen by a doctor or not.
     * 
     * @return true if this Patient has seen by a doctor, false otherwise.
     */
    public boolean getSeenByDoctor() {
	return this.seenByDoctor;
    }

    /**
     * Modifies if this Patient has been seen by a doctor or not.
     * 
     * @param b
     *            - A boolean indicating whether this Patient has seen by a
     *            doctor or not.
     */
    public void setSeenByDoctor(boolean b) {
	// can be true or false; refers to most recent visit
	this.seenByDoctor = b; 
    }

    /**
     * Returns this Patient's health card number.
     * 
     * @return This Patient's health card number.
     */
    public String getHealthCardnum() {
	return healthCardnum;
    }

    /**
     * Modifies this Patient's health card number.
     * 
     * @param healthCardnum
     *            - This Patient's health card number.
     */
    public void setHealthCardnum(String healthCardnum) {
	this.healthCardnum = healthCardnum;
    }

    /**
     * Returns this Patient's name.
     * 
     * @return This Patient's name.
     */
    public String getName() {
	return name;
    }

    /**
     * Modifies this Patient's name.
     * 
     * @param name
     *            - This patient's name.
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Returns this Patient's birthdate.
     * 
     * @return this Patient's birthdate.
     */
    public String getBirthdate() {
	return birthdate;
    }

    /**
     * Modifies this Patient's birthdate.
     * 
     * @param birthdate
     *            - This patient's birthdate
     */
    public void setBirthdate(String birthdate) {
	this.birthdate = birthdate;
    }

    /**
     * Returns this Patient's health record.
     * 
     * @return This Patient's health record.
     */
    public HealthRecord getHealthRecord() {
	return healthRecord;
    }

    /**
     * Modifies this Patient's health record.
     * 
     * @param hr
     *            - This Patient's health record.
     */
    public void setHealthRecord(HealthRecord hr) {
	this.healthRecord = hr;
    }

    /**
     * Returns this Patient's age.
     * 
     * @return This Patient's age
     */
    public int getAge() {
	Calendar dob = Calendar.getInstance();
	String[] parts = this.getBirthdate().split("-");
	int year = Integer.parseInt(parts[0]);
	int month = Integer.parseInt(parts[1]);
	int date = Integer.parseInt(parts[2]);
	dob.set(year, month, date);
	Calendar today = Calendar.getInstance();
	int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
	if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
	    age -= 1;
	return age;
    }

    /**
     * Returns a string representation of this Patient.
     * 
     * @overrides toString in class java.lang.Object
     * @return A string with the health card number, name, birthdate and the
     *         health record of this patient
     */
    @Override
    public String toString() {

	healthRecord.getRecentVisitRecord().getMostRecentVitalSign()
		.calculateUrgency(getAge());
	return "Health Card Number: "
		+ healthCardnum
		+ "\nName: "
		+ name
		+ "\nBirthdate: "
		+ birthdate
		+ "\nArrival time: "
		+ healthRecord.getRecentVisitRecord().getArrivalTime()
		+ "\n"
		+ "Urgency level: "
		+ healthRecord.getRecentVisitRecord().getMostRecentVitalSign()
			.getUrgency() + "\n";
    }

    /**
     * Returns a hash code value for this Patient object.
     * 
     * @overrides hashCode in class java.lang.Object
     * @return A string with the dates of all visit to the hospital:
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((birthdate == null) ? 0 : birthdate.hashCode());
	result = prime * result
		+ ((healthCardnum == null) ? 0 : healthCardnum.hashCode());
	result = prime * result
		+ ((healthRecord == null) ? 0 : healthRecord.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    /**
     * Returns whether this Patient object is equal to another object.
     * 
     * @overrides equals in class java.lang.Object
     * @return boolean Whether the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Patient other = (Patient) obj;
	if (birthdate == null) {
	    if (other.birthdate != null)
		return false;
	} else if (!birthdate.equals(other.birthdate))
	    return false;
	if (healthCardnum == null) {
	    if (other.healthCardnum != null)
		return false;
	} else if (!healthCardnum.equals(other.healthCardnum))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}