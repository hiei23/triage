package com.example.triage;

import java.util.Comparator;

/**
 * A comparator class for comparing patients by urgency.
 */
public class UrgencyComparator implements Comparator<Patient> {
    /**
     * Compares current Patient to another Patient. Patients are sorted in
     * ascending order by date.
     * 
     * @return 1, 0, 1 if this object comes before, is equal to, or comes after
     *         the other object, respectively.
     */
    @Override
    public int compare(Patient current, Patient another) {
	Integer currentUrgency = current.getHealthRecord()
		.getRecentVisitRecord().getMostRecentVitalSign()
		.calculateUrgency(current.getAge());

	Integer anotherUrgency = another.getHealthRecord()
		.getRecentVisitRecord().getMostRecentVitalSign()
		.calculateUrgency(another.getAge());
	int result = currentUrgency.compareTo(anotherUrgency);
	return result;
    }
}