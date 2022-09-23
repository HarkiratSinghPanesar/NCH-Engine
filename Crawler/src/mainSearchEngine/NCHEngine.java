package mainSearchEngine;

import java.util.*;

import fileCreation.WriteHTMLFiles;
import webCrawler.WebCrawler;


/** 
	 * NOT THE MAIN CLASS ANYMORE, MOVED TO SPRING FRAMEWORK TO HAVE A UI BASED APPLICATION, CHECK THAT OUT
	 * 
	 */


/** Main search engine class having the main function
 * @author Harkirat Singh, Noyal Sam Mathew, Cecil Joseph
 */
public class NCHEngine {
	
	/** Main method calling the webCrawling() method from WebCrawler class
	 * @object sc, object of class Scanner
	 * @param setUrl, storing input from the user
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("======================================================================");
		System.out.println("||                     *****  NCH Engine *****                      ||");
		System.out.println("======================================================================\n");
		
		//----------------------- More features implementation starts here -----------------------//
		String validation = "proceed";						//This is a part of validation
		boolean valResult = true;
		long startTime, endTime, getAverageTime = 0;
		while(validation.equalsIgnoreCase("proceed")) {
			int getChoice = 0;
			if(valResult == true) {
				System.out.println("Enter the feature you want to explore?\n\tEnter 1 for::\n\t\t1. Crawling and storing HTML files to directory\n\t\t2. Converting HTML files to text files\n\tEnter 4 to exit");
				System.out.print("Enter your choice:: ");
				getChoice = sc.nextInt();
			}
			
			if(getChoice == 1 || valResult == false) {
				//Harkirat's feature
				System.out.print("Enter a valid URL (e.g. https://www.uwindsor.ca/ or www.uwindsor.ca):: ");
				String setUrl = sc.next();
				
				if(!setUrl.startsWith("https://")) {
					setUrl = "https://" + setUrl + "/";
				}
				
				//----------------------- URL validation -----------------------//
				
				System.out.println("\n=====> Validating URL " + setUrl + "");
				valResult = WebCrawler.urlValidation(setUrl);
				
				if(valResult == true) {
					
					valResult = true;
					
					System.out.println("=====> Entered URL " + setUrl + " is valid");
					System.out.print("\nEnter the limit for crawling:: ");
					int linkIndex = sc.nextInt();
					//----------------------- Web crawling starts here -----------------------//
					System.out.println("\n======================================================================");
					System.out.println("||               ***** Starting with web crawling *****             ||");
					System.out.println("======================================================================");
					startTime = System.currentTimeMillis();
					HashSet<String> getHashSet = WebCrawler.webCrawling(setUrl, linkIndex);
					endTime = System.currentTimeMillis();
					getAverageTime = endTime - startTime;
					System.out.println("======================================================================");
					System.out.println("||                  ***** Webcrawling completed *****               ||");
					System.out.println("||                    Time taken:: " + getAverageTime + " milli seconds                ||");
					System.out.println("======================================================================");
					
					//----------------------- Web crawling ends here -----------------------//
					
					//----------------------- Writing HTML files start here -----------------------//
					
					if(getHashSet.size() != 0) {
						System.out.println("======================================================================");
						System.out.println("||          ***** Starting storing HTML and text files *****        ||");
						System.out.println("======================================================================");
						System.out.println("=====> Storing HTML files in directory 'WebPages'");
						startTime = System.currentTimeMillis();
						WriteHTMLFiles.writeHtmlFiles(getHashSet);
						endTime = System.currentTimeMillis();
						getAverageTime = endTime - startTime;
						System.out.println("======================================================================");
						System.out.println("||            ***** Done storing HTML and text files *****          ||");
						System.out.println("||                   Time taken:: " + getAverageTime + " milli seconds                ||");
						System.out.println("======================================================================\n");
					}
					//----------------------- Writing HTML files ends here -----------------------//
				} else {
					valResult = false;
				}
				
			} else if(getChoice == 2) {
				//Noyal's feature
			} else if(getChoice == 3) {
				//Cecil's feature
			} else if(getChoice == 4) {
				//exit
				System.out.println("You have successfully exited!");
				break;
			}else {
				System.out.println("You entered wrong choice. Try again.....");
//				break;
			}
		}
		sc.close();
	}

}
