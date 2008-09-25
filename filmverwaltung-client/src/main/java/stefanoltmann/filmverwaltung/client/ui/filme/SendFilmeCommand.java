package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.webclient.FilmverwaltungWebclient;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class SendFilmeCommand extends ActionCommand {

	private FilmverwaltungWebclient filmverwaltungWebclient;
	
	public SendFilmeCommand(FilmverwaltungWebclient filmverwaltungWebclient) {
		this.filmverwaltungWebclient = filmverwaltungWebclient;
	}
	
	@Override
	protected void doExecuteCommand() {
		filmverwaltungWebclient.sendFilme();
	}

}
