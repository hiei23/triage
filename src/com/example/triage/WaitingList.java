package com.example.triage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serializable singleton class that handles the waiting list of patients
 * who have not yet seen a doctor on their most recent visit.
 */
public class WaitingList implements Serializable {

    /**
     * Long value allows objects of LoginInformation type to be passed through
     * activities.
     */
    private static final long serialVersionUID = 1343446578L;
    /**
     * The single instance of WaitingList which is null initially.
     */
    public static WaitingList INSTANCE = null;
    /**
     * This WaitingList object's list of Patients.
     */
    private List<Patient> waitingList;

    /**
     * Private constructor that initializes the WaitingList.
     */
    private WaitingList() {
	this.waitingList = new ArrayList<Patient>();
    }
    
    /**
     * Replaces a WaitingList object from a stream when it is being deserialized.
     * This prevents another instance of WaitingList from being created.
     * 
     * @return The single instance of WaitingList.
     */
    private Object readResolve() {
	return getInstance();
    }

    /**
     * Returns this WaitingList.
     * 
     * @return This WaitingList.
     */
    public List<Patient> getWaitingList() {
	return waitingList;
    }

    /**
     * Adds patients to this WaitingList.
     * 
     * @param patient
     */
    public void addToWaitingList(Patient patient) {
	if (!this.waitingList.contains(patient)) {
	    this.waitingList.add(patient);
	}
    }

    /**
     * Remove patients to this WaitingList.
     * 
     * @param patient
     */
    public void removeFromWaitingList(Patient patient) {
	this.waitingList.remove(patient);
    }
    
    /**
     * Sorts this WaitingList by decreasing urgency.
     */
    public void sortByDecreasingUrgency() {
	UrgencyComparator uc = new UrgencyComparator();
	Collections.sort(this.getWaitingList(), uc);
	Collections.reverse(this.getWaitingList());
    }

    /**
     * Sorts this WaitingList by ascending arrival time.
     */
    public void sortByArrivalTime() {
	ArrivalTimeComparator atc = new ArrivalTimeComparator();
	Collections.sort(this.getWaitingList(), atc);

    }

    /**
     * Creates a new WaitingList if it does not exist.
     * 
     * @return new instance of this WaitingList.
     * @throws IOException.
     * @throws StreamCorruptedException. 
     */
    public static synchronized WaitingList getInstance() {
	if (WaitingList.INSTANCE == null) {
	    INSTANCE = new WaitingList();
	}
	else {
	    try {
		FileInputStream fis = new FileInputStream("waitingList.ser");
		ObjectInputStream is = new ObjectInputStream(fis);
		INSTANCE = (WaitingList) is.readObject();
		is.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	}
	return INSTANCE;
    }
    
    /**
     * Returns a string representation of this WaitingList.
     * 
     * @overrides toString in class java.lang.Object.
     * @return A string of patients' health card number.
     */
    public String toString() {
    	String str = "";
    	for (Patient p: getWaitingList()) {
    	    str = str + p.getHealthCardnum() + ", ";
    	}
    	return str;
    }
    /**
     * Creates and returns a copy of this WaitingList.	
     * @overrides clone in java.lang.Object.
     * @return a copy of this object.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
	throw new CloneNotSupportedException();
    }
}