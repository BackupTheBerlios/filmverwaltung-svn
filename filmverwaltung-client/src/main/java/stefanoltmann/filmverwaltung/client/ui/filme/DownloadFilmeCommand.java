package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.webclient.FilmverwaltungWebclient;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class DownloadFilmeCommand extends ActionCommand {

	private FilmverwaltungWebclient filmverwaltungWebclient;
	
	public DownloadFilmeCommand(FilmverwaltungWebclient filmverwaltungWebclient) {
		this.filmverwaltungWebclient = filmverwaltungWebclient;
	}
	
	@Override
	protected void doExecuteCommand() {

		filmverwaltungWebclient.downloadFilme();
		
	}

}
