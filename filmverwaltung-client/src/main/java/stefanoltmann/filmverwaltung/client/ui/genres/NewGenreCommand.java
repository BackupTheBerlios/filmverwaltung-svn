package stefanoltmann.filmverwaltung.client.ui.genres;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class NewGenreCommand extends ActionCommand {

	private FilmverwaltungService 	filmverwaltungService;
	
	public NewGenreCommand(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected void doExecuteCommand() {
		new GenrePropertiesDialog(filmverwaltungService).showDialog();		
	}

}
