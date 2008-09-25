package stefanoltmann.filmverwaltung.client.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Ein globales Objekt, dass alle Einstellungen des Benutzers
 * zum Verhalten und Darstellung der Anwendung enthält.
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class Einstellungen {

	private static final Log logger = LogFactory.getLog(Einstellungen.class);

	private boolean showFilmeTablePunkteColumn = true;
	private boolean showFilmeTableImBesitzColumn = true;
	private boolean showFilmeTableErscheinungsJahrColumn = false;
	
	private boolean grafischePunkteZeigen = true;

	public boolean isGrafischePunkteZeigen() {
		return grafischePunkteZeigen;
	}

	public void setGrafischePunkteZeigen(boolean grafischePunkteZeigen) {
		this.grafischePunkteZeigen = grafischePunkteZeigen;
		
		logger.info("Einstellung geändert: Grafische Punkte -> " + grafischePunkteZeigen);
	}

	public boolean isShowFilmeTableErscheinungsJahrColumn() {
		return showFilmeTableErscheinungsJahrColumn;
	}

	public void setShowFilmeTableErscheinungsJahrColumn(boolean filmeTableErscheinungsJahrColumn) {
		this.showFilmeTableErscheinungsJahrColumn = filmeTableErscheinungsJahrColumn;
		
		logger.info("Einstellung geändert: Spalte Erscheinungsjahr -> " + filmeTableErscheinungsJahrColumn);
	}

	public boolean isShowFilmeTableImBesitzColumn() {
		return showFilmeTableImBesitzColumn;
	}

	public void setShowFilmeTableImBesitzColumn(boolean filmeTableImBesitzColumn) {
		this.showFilmeTableImBesitzColumn = filmeTableImBesitzColumn;
		
		logger.info("Einstellung geändert: Spalte 'Im Besitz' -> " + filmeTableImBesitzColumn);
	}

	public boolean isShowFilmeTablePunkteColumn() {
		return showFilmeTablePunkteColumn;
	}

	public void setShowFilmeTablePunkteColumn(boolean filmeTablePunkteColumn) {
		this.showFilmeTablePunkteColumn = filmeTablePunkteColumn;
		
		logger.info("Einstellung geändert: Spalte Punkte -> " + filmeTablePunkteColumn);
	}
	
}
