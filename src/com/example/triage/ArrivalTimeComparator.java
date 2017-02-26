package com.example.triage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * A comparator class for comparing patients by arrival time.
 */
public class ArrivalTimeComparator implements Comparator<Patient> {
    /**
     * Compares current Patient with another Patient. Patients are sorted in
     * ascending arrival time.
     * 
     * @return -1, 0, 1 if this object comes before, is equal to, or comes after
     *         the other object, respectively.
     */
    @Override
    public int compare(Patient current, Patient another) {
	Calendar currentArrivalTime = Calendar.getInstance();
	Calendar anotherArrivalTime = Calendar.getInstance();
	String currentVisitTime = current.getHealthRecord()
		.getRecentVisitRecord().getArrivalTime();
	String anotherVisitTime = another.getHealthRecord()
		.getRecentVisitRecord().getArrivalTime();
	DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy",
		Locale.ENGLISH);
	Date currentArrivaltime = null;
	Date anotherArrivaltime = null;
	try {
	    currentArrivaltime = df.parse(currentVisitTime);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try {
	    anotherArrivaltime = df.parse(anotherVisitTime);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	currentArrivalTime.setTime(currentArrivaltime);
	anotherArrivalTime.setTime(anotherArrivaltime);
	int result = currentArrivalTime.compareTo(anotherArrivalTime);
	return result;
    }
}