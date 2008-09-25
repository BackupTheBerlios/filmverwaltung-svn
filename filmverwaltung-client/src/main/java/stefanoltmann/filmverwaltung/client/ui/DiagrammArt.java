package stefanoltmann.filmverwaltung.client.ui;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public enum DiagrammArt {
	
	BALKEN, KREIS;

	@Override
	public String toString() {
		switch (this) {
			case BALKEN: 
				return "Balken";
			case KREIS:
				return "Kreis";
			default:
				return "ungültig";
		}
	}

}
