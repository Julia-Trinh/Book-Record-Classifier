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
 * The UnknownGenreException class is an Exception when there is an unknown genre.
 * @author julia
 *
 */
public class UnknownGenreException extends Exception{

	/**
	 * Parameterized constructor
	 * @param record a String which correcponds to the record of the book
	 */
	public UnknownGenreException(String record) {
		super("Error: invalid genre\n" +
				record + "\n");
	}
}
