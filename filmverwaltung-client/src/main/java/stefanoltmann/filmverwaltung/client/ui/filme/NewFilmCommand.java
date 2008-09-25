package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class NewFilmCommand extends ActionCommand {

	private FilmverwaltungService 	filmverwaltungService;
	
	public NewFilmCommand(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected void doExecuteCommand() {
		new FilmPropertiesDialog(filmverwaltungService).showDialog();		
	}

}
