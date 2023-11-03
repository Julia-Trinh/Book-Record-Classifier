import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

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
* The ClassifyFiles class classify the books into the according files
* @author julia
*
*/
public class ClassifyFiles {

	/**
	 * Attributes
	 */
	private String fileName;
	private String[] genreFileNames = {"Cartoons_Comics_Books.csv.txt", "Hobbies_Collectibles_Books.csv.txt",
									"Movies_TV.csv.txt", "Music_Radio_Books.csv.txt", "Nostalgia_Eclectic_Books.csv.txt",
									"Old_Time_Radio.csv.txt", "Sports_Sports_Memorabilia.csv.txt", "Trains_Planes_Automobiles.csv.txt"};
	private String syntaxErrorFile = "syntax_error_file.txt";
	private String syntaxString = "";
	private String[][] genreArr = {{"","","","","","","",""},{"0","0","0","0","0","0","0","0"}};
	
	/**
	 * Parameterized constructor
	 * @param fileName a String which correcponds to the name of the file
	 */
	public ClassifyFiles(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * The classify method reads from the file and classifies them into their corresponding file
	 * @throws TooFewFieldsException
	 * @throws TooManyFieldsException
	 * @throws MissingFieldException
	 * @throws UnknownGenreException
	 */
	public void classify() throws TooFewFieldsException, TooManyFieldsException, MissingFieldException, UnknownGenreException {
		
		// _________________________________________READ_________________________________________
		// create a scanner to read a file
		Scanner fileScanner = null;
		int numBooks=0;
		try {
			fileScanner = new Scanner(new FileInputStream(fileName));
			
			// Check how many books there are in the file
			while(fileScanner.hasNext()){
				numBooks++;
				fileScanner.nextLine();
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File "+fileName+" not found.");
		}
		
		// Only execute the rest if there are books
		if (numBooks > 0) {
			// Reinitialize the scanner to read from the beginning of the file
			try {
				fileScanner = new Scanner(new FileInputStream(fileName));
			}
			catch(FileNotFoundException e)
			{
				System.exit(0);
			}
			
			// create a temporary String array to scan the file
			String[][] CSVString = new String[3][numBooks]; // Store csv of book, genre file, and exception message
			String[] CSV;
			
			for (int j=0; j<numBooks; j++) {
				// split the CSV line and store it into the array
				CSVString[0][j] = fileScanner.nextLine();
				CSV = CSVString[0][j].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // using the corresponding regex
				
				// take out extra space
				for (int i = 0; i < CSV.length; i++) {
					CSV[i] = CSV[i].trim();
				}
				
				// Catch any exceptions
				try {
					// Check the syntax of the string
					checkSyntax(CSV, CSVString[0][j]);
					
					// Classify the genre
					CSVString[1][j] = classifyGenre(CSV, CSVString[0][j]);
				}
				catch(TooManyFieldsException e) {
					CSVString[2][j] = e.getMessage();
				}
				catch(TooFewFieldsException e) {
					CSVString[2][j] = e.getMessage();
				}
				catch(MissingFieldException e) {
					CSVString[2][j] = e.getMessage();
				}
				catch(UnknownGenreException e) {
					CSVString[2][j] = e.getMessage();
				}
			}
			
			// Write in syntaxString
			for (int i=0; i<numBooks; i++) {
				if (CSVString[2][i] != null) {
					syntaxString += "\n"+CSVString[2][i];
				}
			}
			
			// Write in genreArr
			for (int i=0; i<8; i++) {
				for (int j=0; j<numBooks; j++) {
					if (CSVString[1][j] == genreFileNames[i] && CSVString[2][j] == null) {
						genreArr[0][i] += CSVString[0][j]+"\n";
						genreArr[1][i] = String.valueOf(Integer.parseInt(genreArr[1][i])+1);
					}
				}
			}
		}
	}
	
	/**
	 * The checkSyntax method checks the syntax of each book record
	 * @param CSV is an array of Strings which contains the record of a book, separated by commas
	 * @param CSVString is a String which corresponds to the record of the book
	 * @throws TooFewFieldsException
	 * @throws TooManyFieldsException
	 * @throws MissingFieldException
	 */
	public void checkSyntax(String[] CSV, String CSVString) throws TooFewFieldsException, TooManyFieldsException, MissingFieldException {
		if (CSV.length < 6) {
			throw new TooFewFieldsException(CSVString);
		}
		else if (CSV.length > 6) {
			throw new TooManyFieldsException(CSVString);
		}
		
		for (int i=0; i<6; i++) {
			if (CSV[i].equals("")) {
				switch(i){
					case 0: throw new MissingFieldException(CSVString, "title");
					case 1: throw new MissingFieldException(CSVString, "authors");
					case 2: throw new MissingFieldException(CSVString, "price");
					case 3: throw new MissingFieldException(CSVString, "isbn");
					case 4: throw new MissingFieldException(CSVString, "genre");
					case 5: throw new MissingFieldException(CSVString, "year");
				}
			}
		}	
	}
	
	/**
	 * The classifyGenre method checks the genre of each book and classifies them
	 * @param CSV is an array of Strings which contains the record of a book, separated by commas
	 * @param CSVString is a String which corresponds to the record of the book
	 * @return String which corresponds a file name 
	 * @throws UnknownGenreException
	 */
	public String classifyGenre(String[] CSV, String CSVString) throws UnknownGenreException {
		
		String fName=null;
		
		if (CSV.length == 6) {
			switch(CSV[4]) {
				case "CCB": fName = genreFileNames[0];
							break;
				case "HCB": fName = genreFileNames[1];
							break;
				case "MTV": fName = genreFileNames[2];
							break;
				case "MRB": fName = genreFileNames[3];
							break;
				case "NEB": fName = genreFileNames[4];
							break;
				case "OTR": fName = genreFileNames[5];
							break;
				case "SSM": fName = genreFileNames[6];
							break;
				case "TPA": fName = genreFileNames[7];
							break;
				default: throw new UnknownGenreException(CSVString);
			}
		}
		
		return fName;
	}
	
	/**
	 * Getter for syntaxString
	 * @return a String which corresponds to what should be written in the syntax error file
	 */
	public String getSyntaxString() {
		return syntaxString;
	}
	
	/**
	 * Getter for genreArr
	 * @return a 2D array of Strings which corresponds to what should be written in the genre files
	 */
	public String[][] getGenreArr() {
		return genreArr;
	}
	
	/**
	 * Getter for genreFileNames
	 * @return an array of Strings which corresponds to the names of the genre files
	 */
	public String[] getGenreFileNames() {
		return genreFileNames;
	}
	
}
