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
 * The BadPriceException class is an Exception when the price is incorrect.
 * @author julia
 *
 */
public class BadPriceException extends Exception{

	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 */
	public BadPriceException (String record) {
		super("Error: invalid price\n" +
				"Record: " + record + "\n");
	}
}
