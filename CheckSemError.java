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
 * The CheckSemError class checks the semantic errors.
 * @author julia
 *
 */
public class CheckSemError {

	/**
	 * Attributes
	 */
	private String csvString;
	private String semanticMessage = "";
	private String[] csvArr;
	
	/**
	 * Parameterized constructor
	 * @param csvString a String corresponding to the record of the book
	 */
	public CheckSemError(String csvString) {
		this.csvString = csvString;
	}
	
	/**
	 * Checks for semantic errors
	 */
	public void generalCheck() {
		csvArr = csvString.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		
		try {
			checkPrice(Double.parseDouble(csvArr[2]));
			checkYear(Integer.parseInt(csvArr[5]));
			
			if(csvArr[3].length() == 10) {
				checkISBN10(csvArr[3]);
			}
			else if(csvArr[3].length() == 13) {
				checkISBN13(csvArr[3]);
			}
		}
		catch(BadIsbn10Exception e) {
			semanticMessage = e.getMessage() + "\n";
		}
		catch(BadIsbn13Exception e) {
			semanticMessage = e.getMessage() + "\n";
		}
		catch(BadPriceException e) {
			semanticMessage = e.getMessage() + "\n";
		}
		catch(BadYearException e) {
			semanticMessage = e.getMessage() + "\n";
		}
		
	}
	
	/**
	 * Checks the price
	 * @param price a double corresponding to the price of the book
	 * @throws BadPriceException
	 */
	public void checkPrice(double price) throws BadPriceException {
		// check if negative
		if (price < 0) {
			throw new BadPriceException(csvString);
		}
	}
	
	/**
	 * Checks the year
	 * @param year an integer corresponding to the year of the book
	 * @throws BadYearException
	 */
	public void checkYear(int year) throws BadYearException {
		if (year < 1995 || year > 2010) {
			throw new BadYearException(csvString);
		}
	}
	
	/**
	 * Checks the ISBN10
	 * @param ISBN10 a String corresponding to the ISBN-10
	 * @throws BadIsbn10Exception
	 */
	public void checkISBN10(String ISBN10) throws BadIsbn10Exception {
		int sum=0;
		
		// Check if ISBN contains only numbers
		try {
			int ISBN10Num = Integer.parseInt(ISBN10);
			while(ISBN10Num != 0) {
			sum+= ISBN10Num%10;
			ISBN10Num = ISBN10Num/10;
			}
			
			if (sum%11 != 0) {
				throw new BadIsbn10Exception(csvString);
			}
		}
		catch (NumberFormatException e) {
			throw new BadIsbn10Exception(csvString);
		}
	}
	
	/**
	 * Checks the ISBN13
	 * @param ISBN13 a String corresponding to the ISBN-13
	 * @throws BadIsbn13Exception
	 */
	public void checkISBN13(String ISBN13) throws BadIsbn13Exception {
		int sum=0;
		
		try {
			double ISBN13Num = Double.parseDouble(ISBN13); //double since too big to be integer
			while(ISBN13Num != 0) {
				sum+= ISBN13Num%10;
				ISBN13Num = ISBN13Num/10;
			}
			
			if (sum%10 != 0) {
				throw new BadIsbn13Exception(csvString);
			}
		}
		catch (NumberFormatException e) {
			throw new BadIsbn13Exception(csvString);
		}
	}
	
	/**
	 * Getter for semanticMessage
	 * @return a String corresponding to the semantic error message
	 */
	public String getSemanticMessage() {
		return semanticMessage;
	}
	
	/**
	 * Creates a Book object
	 * @return a Book
	 */
	public Book createBook() {
		return new Book(Double.parseDouble(csvArr[2]), Integer.parseInt(csvArr[5]), csvArr[0], csvArr[1], csvArr[3], csvArr[4]);
	}
}
