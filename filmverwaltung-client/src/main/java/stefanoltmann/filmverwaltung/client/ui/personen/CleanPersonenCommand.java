package stefanoltmann.filmverwaltung.client.ui.personen;

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
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class CleanPersonenCommand extends ActionCommand {

	private Thread thread;
	
	private FilmverwaltungService filmverwaltungService;
	
	public CleanPersonenCommand(FilmverwaltungService filmverwaltungService) {
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

					List<Person> allPersonen = filmverwaltungService.findAllPersonen();
					List<Person> selPersonen = new ArrayList<Person>();
					
					// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
					ProgressMonitor pm = new ProgressMonitor(null, getMessage("cleanPersonenCommand.pm.title"), "", 0, allPersonen.size());
					pm.setMillisToDecideToPopup(10);
					pm.setMillisToPopup(10);
					pm.setNote(getMessage("cleanPersonenCommand.pm.note"));
					
					for (Person person : allPersonen) {
						person.setFilme( filmverwaltungService.findAllFilme(person) );
						
						if (pm.isCanceled())
							return;
						
						counter = counter + 1;
						pm.setProgress( counter );
					}
					
					for (Person person : allPersonen)
						if (person.getFilme().size() == 0)
							selPersonen.add(person);
					
					if (selPersonen.size() > 0) {

						// Dialog anzeigen
						JList liste = new JList(selPersonen.toArray());
						JDialog dialog = new JDialog();
						dialog.setTitle(getMessage("cleanPersonenCommand.dialog.title"));
						dialog.getContentPane().add(new JScrollPane(liste));
						dialog.setSize(400, 200);
						dialog.setVisible(true);

						for (Person person : selPersonen)
							filmverwaltungService.deletePerson(person);

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
