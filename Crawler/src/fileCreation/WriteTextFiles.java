package fileCreation;

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WriteTextFiles {

	/** Method for storing URL as Text files in directory "WebTextFiles"
	 * @param fileName, filename for the URL
	 * @param eachUrl, URL as string
	 * @param urlIndex, index value of each iteration
	 * @object textDirectory, object of class File
	 * @object getHtmlFile, object of class File
	 * @object parseHtml, object of class Document
	 * @param htmlStringData, storing the HTML file data
	 * @object writeTxt, object of class PrintWriter
	 */
	public static void writeTextFiles(String fileName, String eachUrl, int urlIndex) {
		
		if(urlIndex == 0)
			System.out.println("=====> Storing Text files in directory 'WebTextFiles'");
		
		try {
			File textDirectory = new File("WebTextFiles");
			if(!textDirectory.exists()) {
				textDirectory.mkdir();
			}
			
			
			File getHtmlFile = new File("WebPages/" + fileName + ".html");
			Document parseHtml = Jsoup.parse(getHtmlFile, "UTF-8");
			String htmlStringData = parseHtml.text().trim();
			htmlStringData = "|-------------------- " + eachUrl + " --------------------|\n\n" + htmlStringData;
			PrintWriter writeTxt = new PrintWriter("C:\\Users\\Noyal Sam Mathew\\Desktop\\Home\\Repos\\acc-nch-engine\\search-engine-backend\\src\\files\\W3C Web Pages\\ConvertedTextFiles\\" + fileName + ".txt");
			writeTxt.println(htmlStringData);
			writeTxt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Method to check already visited URL's and write the new URL's is any
	 * @param url, URL as string
	 * @param visitedUrlVal, boolean value initialized to false
	 * @param visitStoragePath, variable storing the path
	 * @object visitUrlDir, object of class File
	 * @object dataArrayList, object of class ArrayList
	 * @object fileObj, object of class File
	 * @object fileWriter, object of class FileWriter
	 * @object fileReader, object of class Scanner
	 * @object writerTxt, object of class BufferedWriter
	 * @return visitedUrlVal, returns boolean value
	 */
	public static boolean writeVisitedPage(String url) {
		
		boolean visitedUrlVal = false;
		
		String visitStoragePath =  "VisitedURLs.txt";
		File visitUrlDir = new File("Visited URLs");
		if(!visitUrlDir.exists()) visitUrlDir.mkdir();
		
		ArrayList<String> dataArrayList = new ArrayList<String>();
		try {
			File fileObj = new File(visitUrlDir + "/" +visitStoragePath);
			
			if(!fileObj.exists() || !fileObj.isFile()) {
				FileWriter fileWriter = new FileWriter(visitUrlDir + "/" +visitStoragePath);
				fileWriter.close();
			}
				
			Scanner fileReader = new Scanner(fileObj);
			while (fileReader.hasNextLine()) {
				String data = fileReader.nextLine();
//		        System.out.println(data);
				dataArrayList.add(data.trim());
			}
			fileReader.close();
	    } catch (IOException e) {
	      System.out.println("Error occurred");
	      e.printStackTrace();
	    }
		
//		System.out.println("dataArrayList:: " + dataArrayList);
		
		// Checking if existing data has URL
		if(!dataArrayList.contains(url)) {
			try {
				BufferedWriter writerTxt = new BufferedWriter(new FileWriter(visitUrlDir + "/" +visitStoragePath));
				if(dataArrayList.size() != 0) {
					for(String eachURL : dataArrayList) {
						writerTxt.write(eachURL + "\n");
					}
				}
				writerTxt.write(url);
				writerTxt.close();
			} catch (Exception e) {
			     e.printStackTrace();
			}
		} else {
			visitedUrlVal = true;
		}
		return visitedUrlVal;
	}
}
