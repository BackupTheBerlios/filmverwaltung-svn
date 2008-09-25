package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ToggleCommand;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;

/**
 * Wenn die Darstellung der Punkte geändert wird, dann wird diese
 * Änderung in das globale Einstellungs-Objekt eingetragen.
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class ShowPunkteProgressBarToggleCommand extends ToggleCommand {
	
	private Einstellungen einstellungen;
	
	public ShowPunkteProgressBarToggleCommand(Einstellungen einstellungen) {
		this.einstellungen = einstellungen;
	}
	
	@Override
	protected void onSelection() {
		einstellungen.setGrafischePunkteZeigen(true);
		FilmeViewDescriptor.filmeViewReOpen();
	}

	@Override
	protected void onDeselection() {
		einstellungen.setGrafischePunkteZeigen(false);
		FilmeViewDescriptor.filmeViewReOpen();
	}
	
}
