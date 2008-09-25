package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ToggleCommand;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class ShowFilmTablePunkteColumnToggle extends ToggleCommand {

	private Einstellungen einstellungen;
	
	public ShowFilmTablePunkteColumnToggle(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}
	
	@Override
	protected void onSelection() {
		einstellungen.setShowFilmeTablePunkteColumn(true);
		FilmeViewDescriptor.filmeViewReOpen();
	}

	@Override
	protected void onDeselection() {
		einstellungen.setShowFilmeTablePunkteColumn(false);
		FilmeViewDescriptor.filmeViewReOpen();
	}
	
}
