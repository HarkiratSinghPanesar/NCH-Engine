package fileCreation;

import java.io.*;
import java.util.Set;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WriteHTMLFiles {

	/** Method for storing URL as HTML files in directory "WebPages" and calling method writeTextFiles()
	 * @object urlList, object of class ArrayList
	 * @object htmlDirectory, object of class File
	 * @object domainPattern, object of class Pattern
	 * @param urlIndex, initializing index value to 0
	 * @param eachUrl, storing each URL string in loop
	 * @object getUrl, object of class Document
	 * @object matchPattern, object of class Matcher
	 * @param getName, forming the domain name
	 * @param fileName, forming the filename
	 * @object writeHtml, object of class PrintWriter
	 * @method writeTextFiles(fileName, eachUrl, urlIndex), calling method for storing URL as text files in directory
	 */
	public static void writeHtmlFiles(Set<String> urlList) {
		try {
			
			File htmlDirectory = new File("WebPages");
			
			//Checking and creating directory if doesn't exist
			if(!htmlDirectory.exists()) {
				htmlDirectory.mkdir();
			}
			
			//Regex for getching the domain name
			String domainNameRegex = "https?:\\/\\/(www\\.)?([-a-zA-Z0-9@:%._\\+~#=]{1,256})\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&\\/\\/=]*)";
			Pattern domainPattern = Pattern.compile(domainNameRegex);
			
			int urlIndex = 0;
			for(String eachUrl : urlList) {
				
				//For including pages having restrictions e.g., LinkedIn
				Document getUrl = Jsoup.connect(eachUrl).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").ignoreHttpErrors(true).timeout(10*1000).get();
				Matcher matchPattern = domainPattern.matcher(eachUrl);
				
				while(matchPattern.find()) {
					
					String getName = matchPattern.group(2) + "_" +matchPattern.group(3);
					
					String fileName = getName.replace("/", "");
					fileName = (fileName.charAt(fileName.length()-1) == '_') ? fileName.substring(0, fileName.length()-1) : fileName;
					PrintWriter writeHtml = new PrintWriter(new FileWriter("WebPages/" + fileName  + ".html"));
					writeHtml.write(getUrl.toString());
					writeHtml.close();
					WriteTextFiles.writeTextFiles(fileName, eachUrl, urlIndex);
				}
				urlIndex++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
