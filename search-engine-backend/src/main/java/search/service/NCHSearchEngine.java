package search.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import search.Sequences;
import search.TST;

@Service
public class NCHSearchEngine {

	private TST<Word> tst;
	private String CONVERTED_TEXT_FILES = "src/files/W3C Web Pages/ConvertedTextFiles/";
	
	public NCHSearchEngine() {
		
		this.init();
		
	}

	public void init() {
		
		String source = CONVERTED_TEXT_FILES;
		String[] filenames = Utils.loadFilenamesFromPath(source);

		// Create a tries using TST
		tst = new TST<Word>();

		for (String filename : filenames) {
			String content = Utils.readFileToString(source + filename);

			String regEx = "[^a-zA-Z_.!#$0-9]";	// allow alphanumeric with underscore
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(content);
			String tmp = m.replaceAll("\n").trim();	// Replace all non-word character in file content by \n for tokenizer

			StringTokenizer st = new StringTokenizer(tmp.toString(), "\n");	// create tokens out of file content

			String words[] = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {	// loop through the tokens and store them as words
				words[i] = st.nextToken();
				i++;
			}

			for (int l = 0; l < words.length; l++) {
				String word = words[l].toLowerCase();
				if (tst.contains(word)) {
					Word w = tst.get(word);
					Map<String, Integer> occur = w.getOccurences();
					if (occur.containsKey(filename)) {
						occur.put(filename, occur.get(filename) + 1);
					} else {
						occur.put(filename, 1);
					}
					tst.put(word, w);
				} else {
					Map<String, Integer> occur = new HashMap<String, Integer>();
					occur.put(filename, 1);
					Word w = new Word(word, occur);
					tst.put(word, w);
				}
			}
		}
	}
	
	public Result findByQuery(String query) {
		Result rs = new Result();
		
		String[] keys = query.split(" ");
		for (String key : keys) {
			key = key.toLowerCase();
			if (tst.get(key) != null) {
				rs.setSearchKeys(key);
				rs.setOccurencesOfEachKey(tst.get(key).getSortedOccurences());
				
			} else {							// if no match found, suggest a similar word using edit distance
				Iterator<String> tstKeysIter = tst.keys().iterator();
				
				int min_distance = 1_000_000;
				String min_distance_key = "";
				while (tstKeysIter.hasNext()) {
					String tstKey = tstKeysIter.next();
					int ed = Sequences.editDistance(key, tstKey);
					if (ed <= min_distance) {
						min_distance = ed;
						min_distance_key = tstKey;
					}
				}
				
				rs.setSearchKeys(min_distance_key);
				
				if (tst.get(min_distance_key) != null) {
					rs.setOccurencesOfEachKey(tst.get(min_distance_key).getSortedOccurences());
				}
			}
		}
		
		return rs;
	}
	
	public TST<Word> getTst() {
		
		return this.tst;
		
	}
}