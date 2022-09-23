package search;

public class Hit {

	/* Private Members */
	
	private Metadata metadata;
	
	/* Constructor */
	
	public Hit(Metadata metadata) {

		this.metadata = metadata;
	
	}

	/* Public Properties */
	
	public Metadata getMetadata() {

		return metadata;

	}

	public void setMetadata(Metadata metadata) {
	
		this.metadata = metadata;
	
	}
	
}
