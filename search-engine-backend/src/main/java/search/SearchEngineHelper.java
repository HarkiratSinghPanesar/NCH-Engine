package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import search.service.Result;

public class SearchEngineHelper {
	
	private static String FILE_EXTENSION = ".txt";
	
	public static SearchResultResponse Trigger(Result searchResult, String[] requestParams) {
	
		SearchResultResponse responseResult = new SearchResultResponse();

		List<Hit> hits = new ArrayList<>();

		Map<String, Integer> occurrences = GetOccurrences(searchResult, requestParams);
		
		if (occurrences.size() <= Integer.valueOf(requestParams[2])) {			// less than filterCount, no pagination required
			
			occurrences.forEach((k, v) -> hits.add(new Hit(new Metadata(k, "" + v))));	// add all occurrences
		
		} else {																// pagination required
		
			int[] indexes = setPageIndexes(requestParams, occurrences.size());
			
			int i = 0;
			for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
				if (indexes[0] <= i && i < indexes[1]) {
					hits.add(new Hit(new Metadata(entry.getKey().substring(0, entry.getKey().lastIndexOf('.')) + FILE_EXTENSION, "" + entry.getValue())));
				}
				i++;
			}
		
		}
		
		responseResult.setTotal(occurrences.size());
		responseResult.setSuggestedSearch(searchResult.getSearchKeys());
		responseResult.setHits(hits);

		return responseResult;
		
	}
	
	public static SearchResultResponse EmptySearchTrigger() {
		
		SearchResultResponse emptyResponse = new SearchResultResponse();
		emptyResponse.setTotal(0);
		return emptyResponse;
		
	}
	
	private static Map<String, Integer> GetOccurrences(Result searchResult, String[] requestParams) {
		
		if(String.valueOf(requestParams[3]).equals("title"))
		{
			if(String.valueOf(requestParams[4]).equals("desc"))
			{
				return searchResult.getSortedOccurrencesByKeyDesc();
			}
			else
			{
				return searchResult.getSortedOccurrencesByKeyAsc();
			}	
		}
		else
		{
			if(String.valueOf(requestParams[4]).equals("desc"))
			{
				return searchResult.getSortedOccurrencesByValueDesc();
			}
			else
			{
				return searchResult.getSortedOccurrencesByValueAsc();
			}	
		}
		
	}
	
	private static int[] setPageIndexes(String[] requestParams, int resultSize) {
		
		int resultRange = Integer.valueOf(requestParams[2]);
		int resultPage = Integer.valueOf(requestParams[1]);
		
		int[] indexes = new int[2];
		
		indexes[0] = (resultPage - 1) * resultRange;
		indexes[1] = ((resultPage * resultRange) <= resultSize) ? resultPage * resultRange : resultSize;
		
		return indexes;
	}

}
