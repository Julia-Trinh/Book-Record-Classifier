//-----------------------------------------
//Assignment 3
//Question: 2
//Written by: Julia Trinh 40245980
//-----------------------------------------

/**
 * Name and ID   Julia Trinh 40245980
 * COMP249
 * Assignment 3
 * Due Date      March 29, 2023
 */

/**
 * The BadIsbn10Exception class is an Exception when the format of the ISBN-10 is incorrect.
 * @author julia
 *
 */
public class BadIsbn10Exception extends Exception{

	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 */
	public BadIsbn10Exception (String record) {
		super("Error: invalid ISBN-10\n" +
				"Record: " + record + "\n");
	}
}
