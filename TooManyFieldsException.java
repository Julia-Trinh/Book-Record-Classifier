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
 * The TooManyFieldsException class is an Exception when there are too many fields.
 * @author julia
 *
 */
public class TooManyFieldsException extends Exception{

	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 */
	public TooManyFieldsException(String record) {
		super("Error: too many fields\n" +
				"Record: " + record + "\n");
	}
	
}
