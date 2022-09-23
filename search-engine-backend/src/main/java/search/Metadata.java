package search;

public class Metadata {
	
	/* Private Members */
	
	private String title;
	private String description;
	
	/* Constructor */
	
	public Metadata(String title, String description) {

		this.title = title;
		this.description = description;
	
	}

	/* Public Properties */
	
	public String getTitle() {
	
		return title;
	
	}

	public void setTitle(String title) {
	
		this.title = title;
	
	}

	public String getDescription() {
	
		return description;
	
	}

	public void setDescription(String description) {
	
		this.description = description;
	
	}
}
