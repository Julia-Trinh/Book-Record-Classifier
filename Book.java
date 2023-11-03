import java.io.Serializable;

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
 * The Book class stores each book record.
 * @author julia
 *
 */
public class Book implements Serializable{

	/**
	 * Attributes
	 */
	private double price;
	private int year;
	private String title;
	private String authors;
	private String isbn;
	private String genre;
	
	/**
	 * Parameterized constructor
	 * @param price a double corresponding to the price of the book
	 * @param year an integer corresponding to the year of the book
	 * @param title a String corresponding to the title of the book
	 * @param authors a String corresponding to the authors of the book
	 * @param isbn a String corresponding to the ISBN of the book
	 * @param genre a String corresponding to the genre of the book
	 */
	public Book(double price, int year, String title, String authors, String isbn, String genre) {
		this.price = price;
		this.year = year;
		this.title = title;
		this.authors = authors;
		this.isbn = isbn;
		this.genre = genre;
	}
	
	/**
	 * Checks if two Book objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if(this.getClass() != obj.getClass()) return false;
		
		Book otherBook = (Book) obj;
		return (this.price == otherBook.price && this.year == otherBook.year && this.title == otherBook.title
				&& this.authors == otherBook.authors && this.isbn == otherBook.isbn && this.genre == otherBook.genre);
	}
	
	/**
	 * Describes a Book
	 */
	@Override
	public String toString() {
		return this.title+", "+this.authors+", "+this.price+", "+this.isbn+", "+this.genre+", "+this.year;
	}
	
	/**
	 * Getter for price
	 * @return a double corresponding to the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Setter for price
	 * @param price a double corresponding to the price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Getter for year
	 * @return an integer corresponding to the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setter for year
	 * @param year an integer corresponding to the year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Getter for title
	 * @return a String corresponding to the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * @param title a String corresponding to the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for authors
	 * @return a String corresponding to the authors
	 */
	public String getAuthors() {
		return authors;
	}

	/**
	 * Setter for authors
	 * @param authors a String corresponding to the authors
	 */
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	/**
	 * Getter for ISBN
	 * @return a String corresponding to the ISBN
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Setter for ISBN
	 * @param isbn a String corresponding to the ISBN
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Getter for genre
	 * @return a String corresponding to the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Setter for genre
	 * @param genre a String corresponding to the genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
}
