//-----------------------------------------
//Assignment 3
//Question: 1
//Written by: Julia Trinh 40245980
//-----------------------------------------

/**
 * Name and ID   Julia Trinh 40245980
 * COMP249
 * Assignment 3
 * Due Date      March 29, 2023
 */

/**
 * The MissingFieldException class is an Exception when there is a missing field.
 * @author julia
 *
 */
public class MissingFieldException extends Exception{
	
	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 * @param field a String which corresponds to the messing field
	 */
	public MissingFieldException(String record, String field) {
		super("Error: missing " + field + "\n" +
				"Record: " + record + "\n");
	}

}
