package search;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import search.service.Result;
import search.service.NCHSearchEngine;

@RestController
public class SearchEngineController {

	@Autowired
	public NCHSearchEngine ses;

	@ModelAttribute
	public void setResponseHeader(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	@RequestMapping("/search")
	public SearchResultResponse search(@RequestParam Map<String, String> requestParams) {
		
		String[] queryStrings = new String[10];
		
		// all possible query strings
		queryStrings[0] = requestParams.get("query");
		queryStrings[1] = requestParams.get("page");
		queryStrings[2] = requestParams.get("size");
		queryStrings[3] = requestParams.get("sort");
		queryStrings[4] = requestParams.get("order");
		
		if (!queryStrings[0].isEmpty()) {		// not empty, search
			Result searchResult = ses.findByQuery(queryStrings[0]);

			return SearchEngineHelper.Trigger(searchResult, queryStrings);

		} else {								// empty, return 0 rows
			return SearchEngineHelper.EmptySearchTrigger();
		}

	}
}
