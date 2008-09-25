package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ToggleCommand;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class ShowFilmTableErscheinungsJahrColumnToggle extends ToggleCommand {

	private Einstellungen einstellungen;
	
	public ShowFilmTableErscheinungsJahrColumnToggle(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}
	
	@Override
	protected void onSelection() {
		einstellungen.setShowFilmeTableErscheinungsJahrColumn(true);
		FilmeViewDescriptor.filmeViewReOpen();
	}

	@Override
	protected void onDeselection() {
		einstellungen.setShowFilmeTableErscheinungsJahrColumn(false);
		FilmeViewDescriptor.filmeViewReOpen();
	}
	
}
