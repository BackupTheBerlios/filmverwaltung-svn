package stefanoltmann.filmverwaltung.client.ui.personen;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class NewPersonCommand extends ActionCommand {

	private FilmverwaltungService 	filmverwaltungService;
	
	public NewPersonCommand(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected void doExecuteCommand() {
		new PersonPropertiesDialog(filmverwaltungService).showDialog();		
	}

}
