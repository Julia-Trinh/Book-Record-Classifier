import java.util.Scanner;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

//-----------------------------------------
//Assignment 3
//Question: 1, 2, and 3
//Written by: Julia Trinh 40245980
//-----------------------------------------
// This program reads from files containing books and classifies them into specific genres files, or in syntax/semantic 
// error files. The valid books are then accessible to display by navigating through a menu.

/**
 * Name and ID   Julia Trinh 40245980
 * COMP249
 * Assignment 3
 * Due Date      March 29, 2023
 */

/**
 * The A3_Main class is the main driver to showcase everything.
 * @author julia
 *
 */
public class A3_Main {

	public static void main(String[] args) throws TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownGenreException {
		
		do_part1();
		do_part2();
		
		// display welcome message
		System.out.println("\n----------------------------------------");
		System.out.println("Welcome to Julia Trinh's book navigator.");
		System.out.println("----------------------------------------");
		
		do_part3();
	}

	//___________________________________________Part 1___________________________________________
	/**
	 * This static method classifies the books into their corresponding genre files and into a syntax error file.
	 * @throws TooManyFieldsException
	 * @throws TooFewFieldsException
	 * @throws MissingFieldException
	 * @throws UnknownGenreException
	 */
	public static void do_part1() throws TooManyFieldsException, TooFewFieldsException, MissingFieldException, UnknownGenreException{
		
		// create a scanner to read a file
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new FileInputStream("part1_input_file_names.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found.");
			System.exit(0);
		}
		
		// skip the first line
		fileScanner.nextLine();
		
		// Create a ClassifyFiles object
		ClassifyFiles cf;
		String syntaxStr = "";
		String[][] genreStrArr = {{"","","","","","","",""},{"0","0","0","0","0","0","0","0"}};
		
		// process each file
		while(fileScanner.hasNext()) {
			String fileToReadName = fileScanner.nextLine();
			cf = new ClassifyFiles(fileToReadName);
			cf.classify();
			
			if (cf.getSyntaxString() != "") {
				syntaxStr += "syntax error in file: "+fileToReadName+"\n====================";
				syntaxStr += cf.getSyntaxString() + "\n";
			}
			String[][] tempArr = cf.getGenreArr();
			for (int i=0; i<8; i++) {
				genreStrArr[0][i] += tempArr[0][i];
				genreStrArr[1][i] = String.valueOf(Integer.parseInt(tempArr[1][i])+Integer.parseInt(genreStrArr[1][i]));
			}
		}
		// genre file names
		String[] genreFiles = {"Cartoons_Comics_Books.csv.txt", "Hobbies_Collectibles_Books.csv.txt",
				"Movies_TV.csv.txt", "Music_Radio_Books.csv.txt", "Nostalgia_Eclectic_Books.csv.txt",
				"Old_Time_Radio.csv.txt", "Sports_Sports_Memorabilia.csv.txt", "Trains_Planes_Automobiles.csv.txt"};
		
		fileScanner.close();
		
		// Write in the Syntax error file first
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream("syntax_error_file.txt"));
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		pw.println(syntaxStr);
		pw.close();
		
		
		// Write in the genre files
		for (int i=0; i<8; i++) {
			PrintWriter pw2 = null;
			try {
				pw2 = new PrintWriter(new FileOutputStream(genreFiles[i]));
			}
			catch(FileNotFoundException e) {
				System.out.println("File not found.");
				System.exit(0);
			}
			pw2.println(genreStrArr[1][i]);
			pw2.println(genreStrArr[0][i]);
			pw2.close();
		}
	}
	
	//___________________________________________Part 2___________________________________________
	/**
	 * This static method checks for semantic errors and produces binary files to store the valid books.
	 */
	public static void do_part2() {
		String[] genreFiles = {"Cartoons_Comics_Books.csv.txt", "Hobbies_Collectibles_Books.csv.txt",
				"Movies_TV.csv.txt", "Music_Radio_Books.csv.txt", "Nostalgia_Eclectic_Books.csv.txt",
				"Old_Time_Radio.csv.txt", "Sports_Sports_Memorabilia.csv.txt", "Trains_Planes_Automobiles.csv.txt"};
		String semanticStr="";
		String[] binFiles = {"Cartoons_Comics_Books.csv.ser", "Hobbies_Collectibles_Books.csv.ser",
				"Movies_TV.csv.ser", "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser",
				"Old_Time_Radio.csv.ser", "Sports_Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"};
		
		// Create 2D array Books
		Book[][] books = new Book[8][];
		
		// create a scanner to read each file
		for (int i=0; i<8; i++) {
			Scanner fileScanner = null;
			try {
				fileScanner = new Scanner(new FileInputStream(genreFiles[i]));
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File not found.");
				System.exit(0);
			}
			// skip the first line
			fileScanner.nextLine();
			
			// Create CheckSemError object
			CheckSemError cse;
			
			// Create a temporary semantic error String
			String tempSemStr="";
			
			// Process each line
			while(fileScanner.hasNext()) {
				String csvString = fileScanner.nextLine();
				cse = new CheckSemError(csvString);
				cse.generalCheck();
				tempSemStr += cse.getSemanticMessage();
				
				// Store in Book array if valid
				if (cse.getSemanticMessage() == "") {
					Book tempBook = cse.createBook();
					
					// if not empty
					if (books[i] != null) {
						Book[] tempBookArr = new Book[books[i].length];
						// copy
						for(int j=0; j<books[i].length; j++) {
							tempBookArr[j] = books[i][j];
						}
						// reinitialize books with an extra slot
						books[i] = new Book[tempBookArr.length+1];
						// recopy back
						for(int j=0; j<tempBookArr.length; j++) {
							books[i][j] = tempBookArr[j];
						}
						books[i][books[i].length-1] = tempBook;
					}
					else {
						books[i] = new Book[1];
						books[i][0] = tempBook;
					}
				}
			}	
			
			// Only write in semantic error file if there is an error
			if (tempSemStr != "") {
				semanticStr += "semantic error in file: " + genreFiles[i] + "\n====================\n" + tempSemStr + "\n";
			}
		}
		
		// Write in the semantic error file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream("semantic_error_file.txt"));
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
		pw.println(semanticStr);
		pw.close();
		
		// Write to binary files
		for (int i=0; i<8; i++) {
			ObjectOutputStream oos = null;
			FileOutputStream fos = null;
			
			try {
				fos = new FileOutputStream(binFiles[i]);
				oos = new ObjectOutputStream(fos);
				
				// serialize all books
				if (books[i] != null) {
					for (int j=0; j<books[i].length; j++) {
						oos.writeObject(books[i][j]);
					}
				}
				
				oos.flush();
				oos.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//___________________________________________Part 1___________________________________________
	/**
	 * This static method extract the books from the binary files and creates a main menu where the user can navigate.
	 */
	public static void do_part3() {
		
		String[] binFiles = {"Cartoons_Comics_Books.csv.ser", "Hobbies_Collectibles_Books.csv.ser",
				"Movies_TV.csv.ser", "Music_Radio_Books.csv.ser", "Nostalgia_Eclectic_Books.csv.ser",
				"Old_Time_Radio.csv.ser", "Sports_Sports_Memorabilia.csv.ser", "Trains_Planes_Automobiles.csv.ser"};
		
		// Create 2D array Books to store the deserialized objects
		Book[][] books = new Book[8][];
		
		// deserialize the objects in the binary files
		for (int i=0; i<8; i++) {
			ObjectInputStream ois = null;
			FileInputStream fis = null;
			int numBooks=0;
			
			try {
				fis = new FileInputStream(binFiles[i]);
				ois = new ObjectInputStream(fis);
				
				// read number of books
				while(ois.readObject()!=null) {
					ois.readObject();
					numBooks++;
				}
				ois.close();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch(EOFException e) {
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			// reinitialize them
			try {
				fis = new FileInputStream(binFiles[i]);
				ois = new ObjectInputStream(fis);
				
				books[i] = new Book[numBooks];
				// deserialize all books
				for (int j=0; j<books[i].length; j++) {
					books[i][j] = (Book) ois.readObject();
				}
				ois.close();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// String for the option that the user inputs
		String optionNav;
		Scanner keyboard = new Scanner(System.in);
		// file index that should be in display
		int fileDisplay = 0;
		
		do {
			// Display the main menu
			displayMainMenu(binFiles[fileDisplay], books[fileDisplay].length);
			optionNav = keyboard.next();
			
			switch(optionNav) {
			case "v": System.out.println("viewing: "+binFiles[fileDisplay]+"   ("+books[fileDisplay].length+" records)");
					int n;
					int currentObj=0;
					do {
						System.out.println("\n-----------------------------------------------------------------------------------------------------");
						System.out.print("Please specify the range of viewing by entering a positive or a negative integer (type 0 to end): ");
						n = keyboard.nextInt();
						System.out.println("-----------------------------------------------------------------------------------------------------");
						
						// if n>0
						if(n>0) {
							if (currentObj + (n-1) >= books[fileDisplay].length-1) {
								for(int i=currentObj; i<=books[fileDisplay].length-1;i++) {
									System.out.println("\n"+books[fileDisplay][i]);
								}
								System.out.println("\nEOF has been reached.");
								currentObj = books[fileDisplay].length-1;
							}
							else {
								for(int i=currentObj; i<=currentObj+(n-1);i++) {
								System.out.println("\n"+books[fileDisplay][i]);
								}
								currentObj += (n-1);
							}
						}
						// if n<0
						if(n<0) {
							if (currentObj - ((n*-1)-1) <= 0) {
								for(int i=currentObj; i>=0;i--) {
									System.out.println("\n"+books[fileDisplay][i]);
								}
								System.out.println("\nBOF has been reached.");
								currentObj = 0;
							}
							else {
								for(int i=currentObj; i>=currentObj-((n*-1)-1);i--) {
								System.out.println("\n"+books[fileDisplay][i]);
								}
								currentObj -= ((n*-1)-1);
							}
						}
						
					}while(n!=0);
					break;
				
			case "s": System.out.println("\n-------------------------------");
					System.out.println("         File Sub-Menu         ");
					System.out.println("-------------------------------");
					for (int i=0; i<8; i++) {
						System.out.print((i+1)+" "+binFiles[i]);
						
						if(binFiles[i].length()>29) System.out.print("\t");
						else if(binFiles[i].length()<=29 && binFiles[i].length()>21) System.out.print("\t\t");
						else System.out.print("\t\t\t");
							
						System.out.println("("+books[i].length+" records)");
					}
					System.out.println("9 Exit");
					System.out.println("-------------------------------\n");
					System.out.print("Enter Your Choice: ");
					int optionFile = keyboard.nextInt();
					
					// change the file option
					switch(optionFile) {
					case 1: fileDisplay = 0;
							break;
					case 2: fileDisplay = 1;
							break;
					case 3: fileDisplay = 2;
							break;
					case 4: fileDisplay = 3;
							break;
					case 5: fileDisplay = 4;
							break;
					case 6: fileDisplay = 5;
							break;
					case 7: fileDisplay = 6;
							break;
					case 8: fileDisplay = 7;
							break;
					case 9: break;
					}
					break;
			}
			
		}while(!optionNav.equals("x"));
		
		System.out.println("\nProgram exited. Thank you.");
		keyboard.close();
	}
	
	/**
	 * This static method displays the Main Menu
	 * @param fileName a String which corresponds to the name of the file to be read
	 * @param numRecords an integer which corresponds to the number of records that the file contains
	 */
	public static void displayMainMenu (String fileName, int numRecords) {
		// display Main Menu
		System.out.println("\n-------------------------------");
		System.out.println("           Main Menu           ");
		System.out.println("-------------------------------");
		System.out.println("v  View the selected file: "+fileName+" ("+numRecords+" records)");
		System.out.println("s  Select a file to view");
		System.out.println("x  Exit");
		System.out.println("-------------------------------");
		System.out.print("\nEnter Your Choice: ");
	}
	
}
