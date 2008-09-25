package stefanoltmann.filmverwaltung.client.ui;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public enum ExportFormat {

	TEXT, POWERPOINT;
	
	@Override
	public String toString() {
		switch (this) {
			case TEXT: 
				return "Text";
			case POWERPOINT:
				return "PowerPoint";
			default:
				return "ungültig";
		}
	}
	
}
