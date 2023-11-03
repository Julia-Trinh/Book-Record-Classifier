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
 * The TooFewFieldsException class is an Exception when there are too few fields.
 * @author julia
 *
 */
public class TooFewFieldsException extends Exception{

	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 */
	public TooFewFieldsException(String record) {
		super("Error: too few fields\n" +
				"Record: " + record + "\n");
	}
}
