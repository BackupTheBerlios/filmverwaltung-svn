package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ToggleCommand;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class ShowFilmTableImBesitzColumnToggle extends ToggleCommand {

	private Einstellungen einstellungen;
	
	public ShowFilmTableImBesitzColumnToggle(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}
	
	@Override
	protected void onSelection() {
		einstellungen.setShowFilmeTableImBesitzColumn(true);
		FilmeViewDescriptor.filmeViewReOpen();
	}

	@Override
	protected void onDeselection() {
		einstellungen.setShowFilmeTableImBesitzColumn(false);
		FilmeViewDescriptor.filmeViewReOpen();
	}
	
}
