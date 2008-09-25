package stefanoltmann.filmverwaltung.client.ui.benutzer;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.webclient.FilmverwaltungWebclient;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class EditBenutzerCommand extends ActionCommand {

	private FilmverwaltungService 	filmverwaltungService;
	private FilmverwaltungWebclient	filmverwaltungWebclient;
	
	public EditBenutzerCommand(FilmverwaltungService filmverwaltungService, FilmverwaltungWebclient filmverwaltungWebclient) {
		this.filmverwaltungService = filmverwaltungService;
		this.filmverwaltungWebclient = filmverwaltungWebclient;
	}

	@Override
	protected void doExecuteCommand() {
		new BenutzerPropertiesDialog(filmverwaltungService, filmverwaltungWebclient).showDialog();
	}
	
}
