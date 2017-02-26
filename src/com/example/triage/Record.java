package com.example.triage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.content.Context;

/**
 * Class that handles the serialization and deserialization of patient objects.
 */
public class Record {

    /**
     * Initializes a Record object.
     */
    public Record() {
    }

    /**
     * Loads saved data.
     * 
     * @throws IOException
     * @throws StreamCorruptedException
     * @throws ClassNotFoundException
     */
    public Patient loadSavedData(Patient patient, Context context)
	    throws StreamCorruptedException, IOException,
	    ClassNotFoundException {
	String fileName = patient.getHealthCardnum() + ".ser";
	FileInputStream fis = context.openFileInput(fileName);
	ObjectInputStream is = new ObjectInputStream(fis);
	patient = (Patient) is.readObject();
	is.close();
	return patient;
    }

    /**
     * Saves the patient's record.
     * 
     * @param patient
     *            - the patient object.
     * @param context
     * @throws IOException
     */
    public void saveFile(Patient patient, Context context) throws IOException {
	String fileName = patient.getHealthCardnum() + ".ser";
	FileOutputStream fos = context.openFileOutput(fileName,
		Context.MODE_PRIVATE);
	ObjectOutputStream os = new ObjectOutputStream(fos);
	os.writeObject(patient);
	os.close();
    }
}