package webCrawler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import fileCreation.WriteTextFiles;

/** Web crawling class for performing web crawling and storing the HTML and text files in a directory
 * @author Harkirat Singh
 */
public class WebCrawler {

	//Regex pattern for matching the URL
	static String urlRegex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
	
	/** Method for performing the web crawling and calling method writeHtmlFiles() for storing the HTML files in a directory
	 * @param getUrl, storing the URL from user input
	 * @object urlList, object of class ArrayList
	 * @object patternUrl, object of class Pattern
	 * @object getPage, object of class Document
	 * @object getLinks, object of class Elements
	 * @param getAbsUrl, storing the link
	 * @object matchUrl, object of class Matcher
	 * @method urlValidation(getAbsUrl, urlList), calling method for validating URL
	 * @method writeHtmlFiles(urlList), calling method for storing each URL as HTML file in a directory
	 */
	public static HashSet<String> webCrawling(String getUrl, int linkIndex) {
		
		HashSet<String> urlList = new HashSet<String>();
		
		try {
			Pattern patternUrl = Pattern.compile(urlRegex);
			
			Document getPage = Jsoup.connect(getUrl).get();
			Elements getLinks = getPage.select("a[href]");		
			
			int index = 1;
			for(Element eachLink : getLinks) {
				if(index == linkIndex + 1) break;
				String getAbsUrl = eachLink.attr("abs:href");
				Matcher matchUrl = patternUrl.matcher(getAbsUrl);
				
				System.out.println("Link " + index + ":: " + getAbsUrl);
				while(matchUrl.find() && urlValidation(getAbsUrl, urlList)) {
					urlList.add(getAbsUrl);
				}
				index++;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlList;
	}
	
	/** Method for validating the URL (Method overloaded)
	 * @param valUrl, URL to validate
	 * @object urlList, object of class ArrayList
	 * @param valStatus, boolean value initializing to true
	 * @return valStatus, return the boolean value
	 */
	public static boolean urlValidation(String valUrl, HashSet<String> urlList) {
		
		boolean valStatus = true;
		
		if (urlList.contains(valUrl)) {
			valStatus = false;
		}
		
		if (valUrl.endsWith(".jpeg") || valUrl.endsWith(".jpg") || valUrl.endsWith(".png") || valUrl.endsWith(".pdf") || valUrl.contains("#") || valUrl.contains("?") || valUrl.endsWith(".gif") ||valUrl.endsWith(".xml")) {
			valStatus = false;
		}		
		return valStatus;
	}
	
	/** Method for validating the URL (Method overloaded)
	 * @param valUrl, URL to validate
	 * @object urlList, object of class ArrayList
	 * @param valStatus, boolean value initializing to true
	 * @return valStatus, return the boolean value
	 */
	public static boolean urlValidation(String valUrl) {
		
		boolean valStatus = true;
		
		try {
			URL urlObj = new URL(valUrl);
	        HttpURLConnection CON = (HttpURLConnection) urlObj.openConnection();
	        //Sending the request
	        CON.setRequestMethod("GET");
	        valStatus = (CON.getResponseCode() == 200) ? true : false;
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("=====> Entered URL " + valUrl + " is invalid\n");
			valStatus = false;
		}
		
		if(valStatus == true) {
			boolean visitValResult = WriteTextFiles.writeVisitedPage(valUrl);
			
			if(visitValResult == true) {
				System.out.println("=====> URL " + valUrl + " is already crawled and stored in directory\n");
				valStatus = false;
			}
		}
		return valStatus;
	}
}
