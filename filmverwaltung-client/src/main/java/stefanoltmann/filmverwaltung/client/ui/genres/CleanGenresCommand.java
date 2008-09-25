package stefanoltmann.filmverwaltung.client.ui.genres;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;

import org.springframework.richclient.application.Application;
import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class CleanGenresCommand extends ActionCommand {

	private Thread thread;

	private FilmverwaltungService filmverwaltungService;

	public CleanGenresCommand(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected void doExecuteCommand() {
		clean();
	}
	
	public void clean() {

		if (thread == null || !thread.isAlive()) {

			thread = new Thread (new Runnable() {
				public void run() {

					int counter = 0;
					
					List<Genre> allGenres = filmverwaltungService.findAllGenres();
					List<Genre> selGenres = new ArrayList<Genre>();
					
					// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
					ProgressMonitor pm = new ProgressMonitor(null, getMessage("cleanGenresCommand.pm.title"), "", 0, allGenres.size());
					pm.setMillisToDecideToPopup(10);
					pm.setMillisToPopup(10);
					pm.setNote(getMessage("cleanGenresCommand.pm.note"));
						
					for (Genre genre : allGenres) {
						genre.setFilme( filmverwaltungService.findAllFilme(genre) );
						
						if (pm.isCanceled())
							return;
						
						counter = counter + 1;
						pm.setProgress( counter );
					}
					
					for (Genre genre : allGenres)
						if (genre.getFilme().size() == 0)
							selGenres.add(genre);
					
					if (selGenres.size() > 0) {

						// Dialog anzeigen
						JList liste = new JList(selGenres.toArray());
						JDialog dialog = new JDialog();
						dialog.setTitle(getMessage("cleanGenresCommand.dialog.title"));
						dialog.getContentPane().add(new JScrollPane(liste));
						dialog.setSize(400, 200);
						dialog.setVisible(true);

						for (Genre genre : selGenres)
							filmverwaltungService.deleteGenre(genre);

					}
					
					pm.close();

				}

			});
			thread.start ();

		} else
			System.err.println("Thread läuft noch...");
		
		
	}
	
	public String getMessage(String code) {
		return Application.instance().getApplicationContext().getMessage(code, new Object[0], Locale.getDefault());
	}

}
