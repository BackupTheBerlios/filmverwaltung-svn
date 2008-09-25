package stefanoltmann.filmverwaltung.client.ofdbgwconnector;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;

import org.dom4j.DocumentException;
import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class OFDBGWConnectorCommand extends ActionCommand {
	
	private Thread thread;
	
	private FilmverwaltungService filmverwaltungService;

	private List<Film> filme = new ArrayList<Film>();
	
	public List<Film> getFilme() {
		return filme;
	}

	public void setFilme(List<Film> filme) {
		if (filme == null)
			throw new IllegalArgumentException("Paramter 'filme' must not be null.");
		this.filme = filme;
	}

	public OFDBGWConnectorCommand(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected void doExecuteCommand() {
		abgleich();
	}
	
	public void abgleich() {

		if (thread == null || !thread.isAlive()) {

			thread = new Thread (new Runnable() {
				public void run() {

					List<Film> filmeFehlschlag = new ArrayList<Film>();

					// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
					ProgressMonitor pm = new ProgressMonitor(null, "Gleiche Daten mit OFDB GW ab...", "", 0, filme.size());
					pm.setMillisToDecideToPopup(10);
					pm.setMillisToPopup(10);
					pm.setNote("Frage Datenbank ab ...");

					int counter = 0;

					try {

						for (Film film : filme) {

							pm.setNote(film.getName());

							try {
							
								if (film.getOfdbId() == null || film.getOfdbId() == 0)
									film.setOfdbId( OFDBGWConnector.receiveOfdbId(film.getName()) );

								// OFDB daten löschen
								if (film.getOfdbId() < 0) {
									film.setOfdbName("");
									film.setOfdbBeschreibung("");
									filmverwaltungService.saveFilm(film);
									continue;
								}
								
								if (film.getOfdbId() != null && film.getOfdbId() != 0) {
	
									Film filmInfo = OFDBGWConnector.receiveFilmInformation(film.getOfdbId());
	
									film.setOfdbName(			filmInfo.getOfdbName()			);
									film.setErscheinungsJahr(	filmInfo.getErscheinungsJahr()	);
									film.setOfdbBeschreibung(	filmInfo.getOfdbBeschreibung()	);
	
									List<Genre> gwGenres = OFDBGWConnector.receiveGenres(film.getOfdbId());
									film.setGenres( convert2DBGenres(gwGenres) );
	
									List<Person> gwCast = OFDBGWConnector.receiveCast(film.getOfdbId());
									film.setCast( convert2DBPersonen(gwCast) );
	
									filmverwaltungService.saveFilm(film);
	
								} else
									filmeFehlschlag.add(film);
							
							} catch (DocumentException e) {
								
								if (e.getNestedException() instanceof UnknownHostException || e.getNestedException() instanceof SocketException) {
									
									logger.error("Es besteht keine Internetverbindung.", e);
									JOptionPane.showMessageDialog(null, "Das OFDB Gateway kann zum Laden von Zusatzinformationen nicht kontaktiert werden.\n Besteht eine Internetverbindung?", "Keine Verbindung", JOptionPane.ERROR_MESSAGE);
									break;
									
								} else {
									logger.error("Die XML Datei konnte nicht gelesen werden?", e);
									// Vielleicht klappts beim nächsten Film.
									filmeFehlschlag.add(film);
								}
							}

							// Wenn der Nutzer nicht mehr will, is auch Schluss...
							if (pm.isCanceled())
								break;

							counter = counter + 1;
							pm.setProgress(counter);
						}

					} catch (MalformedURLException e) {
						logger.error("URL stimmt nicht!?", e);
						JOptionPane.showMessageDialog(null, "Beim Abgleich mit OFDB GW schwerer Fehler aufgetreten.");
					}

					pm.close();

					// Im Dialog anzeigen, was nicht geklappt hat
					// TODO: Schönere Darstellung
					if (filmeFehlschlag.size() > 0) {

						JList liste = new JList(filmeFehlschlag.toArray());
						JDialog dialog = new JDialog();
						dialog.setTitle("Abgleich Fehlgeschlagen mit...");
						dialog.getContentPane().add(new JScrollPane(liste));
						dialog.setSize(400, 200);
						dialog.setVisible(true);

					}

				}
			});
			thread.start ();

		} else
			System.err.println("Thread läuft noch...");

	}
	
	/**
	 * Wandelt ein gegebene Liste von Genres in mit der Datenbank verknüpft Genres um
	 */
	public List<Genre> convert2DBGenres(List<Genre> genres) {
		
		List<Genre> datenbankGenres = new ArrayList<Genre>();
		
		for (Genre genre : genres) {
			
			// Wenn der Name leer ist, ist das hier wertlos
			if (genre == null || genre.getName() == null || genre.getName().length() == 0)
				continue;
			
			// Wer id nicht null hat kommt aus der Datenbank
			if (genre.getId() != null) {
				datenbankGenres.add(genre);
				continue;
			}
			
			Genre datenbankGenre = filmverwaltungService.findGenre(genre.getName());
			if (datenbankGenre != null) {
				datenbankGenres.add(datenbankGenre); // So eines haben wir bereits in der DB
			} else {
				filmverwaltungService.saveGenre(genre);
				datenbankGenres.add(genre); // Ist damit jetzt auch ein DB Genre
			}	

		}
		
		return datenbankGenres;
	}
	
	/**
	 * Wandelt ein gegebene Liste von Personen in mit der Datenbank verknüpft Genres um
	 */
	public List<Person> convert2DBPersonen(List<Person> personen) {
		
		List<Person> datenbankPersonen = new ArrayList<Person>();
		
		for (Person person : personen) {
			
			// Wenn der Name leer ist, ist das hier wertlos
			if (person == null || person.getName() == null || person.getName().length() == 0)
				continue;
			
			// Wer id nicht null hat kommt aus der Datenbank
			if (person.getId() != null) {
				datenbankPersonen.add(person);
				continue;	
			}
			
			Person datenbankPerson = filmverwaltungService.findPerson(person.getName());
			if (datenbankPerson != null) {
				datenbankPersonen.add(datenbankPerson); // So eine haben wir bereits in der DB
			} else {
				filmverwaltungService.savePerson(person);
				datenbankPersonen.add(person); // Ist damit jetzt auch eine DB Person
			}

		}
		
		return datenbankPersonen;
	}

}
