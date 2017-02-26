package com.example.triage;

import java.io.Serializable;
import java.util.Calendar;

/**
 * The vital signs of a patient, the time of their vital signs, and the vital
 * signs temperature, systolic blood pressure, diastolic blood pressure and
 * heart rate.
 */
public class VitalSign implements Serializable {
    private final double ABOVE_AVERAGE = 39.0;
    /**
     * This Patient's urgency points.
     */
    private int urgencyPoints;
    /**
     * Long serialID allows objects of VitalSign type to be passed through
     * activities.
     */
    private static final long serialVersionUID = 6182110383739347127L;
    /**
     * Decimal number temperature of a Patient.
     */
    private double temperature;
    /**
     * Integer number systolic blood pressure of a Patient.
     */
    private int systolicBloodPressure;
    /**
     * Integer number diastolic blood pressure of a Patient.
     */
    private int diastolicBloodPressure;
    /**
     * Integer number heart rate of a Patient.
     */
    private int heartRate;
    /**
     * The time that this VitalSign object was created (generated from the
     * internal clock).
     */
    private String updateTime;

    /**
     * Constructor to create a new VitalSign object.
     * 
     * @param temperature
     * @param systolicBloodPressure
     * @param diastolicBloodPressure
     * @param heartRate
     */
    public VitalSign(double temperature, int systolicBloodPressure,
	    int diastolicBloodPressure, int heartRate) {
	this.temperature = temperature;
	this.systolicBloodPressure = systolicBloodPressure;
	this.diastolicBloodPressure = diastolicBloodPressure;
	this.heartRate = heartRate;
	this.updateTime = Calendar.getInstance().getTime().toString();
    }

    /**
     * Return the time that this VitalSign object was created.
     * 
     * @return String
     */
    public String getUpdateTime() {
	return updateTime;
    }

    /**
     * Set the time that this VitalSign object was created.
     * 
     * @param updateTime
     */
    public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
    }

    /**
     * Return this Patient's temperature.
     * 
     * @return double
     */
    public double getTemperature() {
	return temperature;
    }

    /**
     * Set this Patient's temperature.
     * 
     * @param temperature
     */
    public void setTemperature(double temperature) {
	this.temperature = temperature;
    }

    /**
     * Return this Patient's systolic blood pressure.
     * 
     * @return integer
     */
    public int getSystolicBloodPressure() {
	return systolicBloodPressure;
    }

    /**
     * Set this Patient's patient's systolic blood pressure.
     * 
     * @param systolicBloodPressure
     */
    public void setSystolicBloodPressure(int systolicBloodPressure) {
	this.systolicBloodPressure = systolicBloodPressure;
    }

    /**
     * Return this Patient's diastolic blood pressure.
     * 
     * @return integer
     */
    public int getDiastolicBloodPressure() {
	return diastolicBloodPressure;
    }

    /**
     * Set this Patient's diastolic blood pressure.
     * 
     * @param diastolicBloodPressure
     */
    public void setDiastolicBloodPressure(int diastolicBloodPressure) {
	this.diastolicBloodPressure = diastolicBloodPressure;
    }

    /**
     * Return this Patient's heart rate.
     * 
     * @return integer
     */
    public int getHeartRate() {
	return heartRate;
    }

    /**
     * Set this Patient's heart rate.
     * 
     * @param heartRate
     */
    public void setHeartRate(int heartRate) {
	this.heartRate = heartRate;
    }

    /**
     * Calculates this Patient's urgency specified by the given
     * Hospital Policy.
     * 
     * @param age
     *            - This Patient's age.
     * @return urgency points based on the given Hospital Policy.
     */
    public int calculateUrgency(int age) {
	this.urgencyPoints = 0;
	if (age < 2) {
	    this.urgencyPoints += 1;
	}
	if (this.getTemperature() >= ABOVE_AVERAGE) {
	    this.urgencyPoints += 1;
	}
	if (this.getSystolicBloodPressure() >= 140
		|| this.getDiastolicBloodPressure() >= 90) {
	    this.urgencyPoints += 1;
	}

	if (this.getHeartRate() >= 100 || this.getHeartRate() <= 50) {
	    this.urgencyPoints += 1;
	}
	return urgencyPoints;
    }

    /**
     * Returns this Patient's urgency rating.
     * 
     * @return this Patient's urgency rating.
     */
    public int getUrgency() {
	return urgencyPoints;
    }

    /**
     * Returns a string representation of this VitalSign object.
     * 
     * @overrides toString in class java.lang.Object
     * @return A string with the update time and vital signs at that time.
     */
    @Override
    public String toString() {
	return updateTime + "," + temperature + "," + systolicBloodPressure
		+ "," + diastolicBloodPressure + "," + heartRate;
    }
}