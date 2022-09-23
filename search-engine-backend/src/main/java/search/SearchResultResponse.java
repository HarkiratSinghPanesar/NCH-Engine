package search;

import java.util.List;

public class SearchResultResponse {

	/* Private Members */
	
	private long total;
	private String suggestedSearch;
    private List<Hit> hits;

    /* Public Properties */
    
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
	
	public String getSuggestedSearch() {
		return suggestedSearch;
	}

	public void setSuggestedSearch(String suggestedSearch) {
		this.suggestedSearch = suggestedSearch;
	}

	public List<Hit> getHits() {
		return hits;
	}

	public void setHits(List<Hit> hits) {
		this.hits = hits;
	}
}
