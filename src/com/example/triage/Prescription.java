package com.example.triage;

import java.io.Serializable;

/**
 * A physician's prescription for a patient, containing the medication name and
 * the instructions for use.
 */
public class Prescription implements Serializable {
    /**
     * Long value allows objects of LoginInformation type to be passed through
     * activities.
     */
    private static final long serialVersionUID = 1L;
    /**
     * String representing the instructions for this medication.
     */
    private String instruction;
    /**
     * String representing the medication name.
     */
    private String medication;

    /**
     * Initializes the Prescription based on the passed parameters.
     * 
     * @param instruction
     *            - The instruction for using the medicine.
     * @param medication
     *            - The name of the medicine that is being used.
     */
    public Prescription(String instruction, String medication) {
	this.instruction = instruction;
	this.medication = medication;
    }

    /**
     * Modifies the instruction for the medicine.
     * 
     * @param instruction
     *            - this medication's instruction.
     */
    public void setinstruction(String instruction) {
	this.instruction = instruction;
    }

    /**
     * Returns this medication's instruction.
     * 
     * @return This medication's instruction.
     */
    public String getinstruction() {
	return instruction;
    }

    /**
     * Modifies the medication that is being used.
     * 
     * @param medication
     *            - the medicine that is being used.
     */
    public void setmedication(String medication) {
	this.medication = medication;
    }

    /**
     * Returns the medication.
     * 
     * @return The medication that is being used.
     */
    public String getmedication() {
	return medication;
    }
}