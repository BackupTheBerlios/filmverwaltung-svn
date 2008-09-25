package stefanoltmann.filmverwaltung.webclient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import stefanoltmann.filmverwaltung.client.app.Main;
import stefanoltmann.filmverwaltung.dataaccess.Benutzer;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;
import stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceProxy;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmverwaltungWebclient {

	private FilmverwaltungService service;

	private Thread thread;

	private static final Log logger = LogFactory.getLog(Main.class);

	public FilmverwaltungWebclient(FilmverwaltungService filmverwaltungService) {
		this.service = filmverwaltungService;
	}

	public Benutzer getBenutzer(String anmeldeName, String passwort) {

		try {

			if (anmeldeName == null || passwort == null || anmeldeName.length() == 0 || passwort.length() == 0)
				throw new IllegalArgumentException("getBenutzer() mit ungültigen Daten aufgerufen: " + anmeldeName + " - " + passwort);

			FilmverwaltungWebserviceProxy proxy = new FilmverwaltungWebserviceProxy();
			
			if (proxy == null) {
				logger.error("FilmverwaltungWebserviceProxy konnte nicht instantiiert werden");
				return null;
			}
			
			return proxy.getBenutzer(anmeldeName, passwort);

		} catch (RemoteException e) {
			if (e.getCause() != null && e.getCause().getMessage() != null
					&& e.getCause().getMessage().equals("Connection refused")) {
				logger.info("getBenutzer() failed: Connection refused");
				JOptionPane.showMessageDialog(null, "Der Server ist nicht erreichbar.", "Verbindungs-Problm", JOptionPane.INFORMATION_MESSAGE);
			} else {
				logger.error("getBenutzer() failed", e);
				JOptionPane.showMessageDialog(null, "Beim Abfragen der Benutzer-Daten ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}

	}

	public Benutzer registriereBenutzer(String anmeldeName, String passwort) {

		try {
			
			if (anmeldeName == null || passwort == null || anmeldeName.length() == 0 || passwort.length() == 0)
				throw new IllegalArgumentException("registriereBenutzer() mit ungültigen Daten aufgerufen: " + anmeldeName + " - " + passwort);

			FilmverwaltungWebserviceProxy proxy = new FilmverwaltungWebserviceProxy();
			
			if (proxy == null) {
				logger.error("FilmverwaltungWebserviceProxy konnte nicht instantiiert werden");
				return null;
			}
			
			return proxy.registriereNutzer(anmeldeName, passwort);

		} catch (RemoteException e) {
			if (e.getCause() != null && e.getCause().getMessage() != null
					&& e.getCause().getMessage().equals("Connection refused")) {
				logger.info("registriereBenutzer() failed: Connection refused");
			} else {
				logger.error("registriereBenutzer() failed", e);
				JOptionPane.showMessageDialog(null, "Beim Registrieren des Benutzers ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}

	}
	
	public void sendFilme() {

		if (thread == null || !thread.isAlive()) {

			thread = new Thread (new Runnable() {
				public void run() {

					try {

						List<Film> filmeFehlschlag = new ArrayList<Film>();
						
						FilmverwaltungWebserviceProxy proxy = new FilmverwaltungWebserviceProxy();

						if (proxy == null) {
							logger.error("FilmverwaltungWebserviceProxy konnte nicht instantiiert werden");
							return;
						}
						
						Benutzer dbBenutzer = service.findBenutzer();

						if (dbBenutzer == null) {
							JOptionPane.showMessageDialog(null, "Sie haben noch keinen Benutzer für den WebService.");
							return;
						}

						Benutzer soapBenutzer = proxy.getBenutzer(dbBenutzer.getAnmeldename(), dbBenutzer.getPasswort());

						if (soapBenutzer == null) {
							JOptionPane.showMessageDialog(null, "Ihre WebService Login-Daten sind ungültig.");
							return;
						}

						/*
						 * Ab hier gehts los
						 */
						
						// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
						ProgressMonitor pm = new ProgressMonitor(null, "Uploade Filme an Server", "", 0, 0);
						pm.setMillisToDecideToPopup(10);
						pm.setMillisToPopup(10);
						pm.setNote("Verbinde mit WebService...");
						
						logger.info("Synchronisiere Genres");
						pm.setNote("Sychnronisiere Genres");
						synchronisiereGenres(proxy);

						logger.info("Sychnronisiere Personen");
						pm.setNote("Sychnronisiere Personen");
						synchronisierePersonen(proxy);
						
						logger.info("Sychnronisiere Filme");
						pm.setNote("Sychnronisiere Filme");
						synchronisiereFilme(proxy);
						
						int counter = 0;
						
						logger.info("Starte Film-Upload...");
						pm.setNote("Starte Film-Upload...");
						
						// Refreshing all Filme
						List<Film> filme = service.findAllFilme();
						
						if (filme != null) {
						
							pm.setMaximum(filme.size());
							for (Film film : filme) {
								
								// Notwendige Daten prüfen
								if (film == null || film.getId() == null) {
									filmeFehlschlag.add(film);
									continue;
								}
								
								// Hochzählen
								counter = counter + 1;
								pm.setProgress(counter);
								pm.setNote("Film: " + film.getName());
								
								// Film bereits vorhanden?
								if (proxy.isFilmInDatabase(film.getId()))
									continue;
								
								/*
								 * Prüfen, ob Informationen zum Eintrag eines neuen Films ausreichen
								 */
								
								// Filme ohne ausreichend Informationen können nicht eingetragen werden
								if (		film.getOfdbId() == null 			|| film.getOfdbId() == 0
										||	film.getName()	== null 			|| film.getName().length() == 0
										||	film.getErscheinungsJahr() == null	|| film.getErscheinungsJahr() == 0 
										||	film.getOfdbName() == null			|| film.getOfdbName().length() == 0
										||  film.getOfdbBeschreibung() == null	|| film.getOfdbBeschreibung().length() == 0) {
									filmeFehlschlag.add(film);
									continue;
								}
	
								Genre[] genres = new Genre[film.getGenres().size()];
								if (film.getGenres() != null)
									for (int i = 0; i <= film.getGenres().size() - 1; i++)
										genres[i] = film.getGenres().get(i);
								film.setGenres(null);
								
								Person[] cast = new Person[film.getCast().size()];
								if (film.getCast() != null)
									for (int i = 0; i <= film.getCast().size() - 1; i++)
										cast[i] = film.getCast().get(i);
								film.setCast(null);
								
								film.setBewertung(null);
	
								proxy.sendFilm(soapBenutzer, film, genres, cast);
								
								// Wenn der Nutzer nicht mehr will, is auch Schluss...
								if (pm.isCanceled())
									break;
							}
						
						} else {
							logger.error("service.findAllFilme() returned NULL");
							JOptionPane.showMessageDialog(null, "Filme wurden nicht gefunden.\nKonnten nicht übermittelt werden.");
						}
							
						// dann alle Bewertungen hinterher
						List<Filmbewertung> bewertungen = service.findAllFilmbewertungen();
						
						if (bewertungen != null) {
							
							counter = 0;
							pm.setProgress(0);
							pm.setMaximum(bewertungen.size());
							
							for (Filmbewertung bewertung : bewertungen) {
								
								// Bewertungen ohne FilmID werden vom Webservice abgelehnt
								if (bewertung.getFilmId() == null || bewertung.getFilmId().length() == 0)
									continue;
								
								pm.setNote("Bewertung ID: " + bewertung.getId());
	
								// Film bereits vorhanden?
								if (!proxy.isFilmInDatabase(bewertung.getFilmId())) {
									logger.error("Bewertung " + bewertung.getId() + " kann nicht hochgeladen werden, da Film " + bewertung.getFilmId() + " nicht in WebService-Datenbank existiert.");
									continue;
								}
								
								proxy.sendBewertung(soapBenutzer, bewertung);
	
								// Wenn der Nutzer nicht mehr will, is auch Schluss...
								if (pm.isCanceled())
									break;
								
								counter = counter + 1;
								pm.setProgress(counter);
							}
							
						} else {
							logger.error("service.findAllFilmbewertungen() returned NULL");
							JOptionPane.showMessageDialog(null, "Bewertungen wurden nicht gefunden.\nKonnten nicht übermittelt werden.");
						}

						pm.close();
						
						// Im Dialog anzeigen, was nicht geklappt hat
						// TODO: Schönere Darstellung
						if (filmeFehlschlag.size() > 0) {

							JList liste = new JList(filmeFehlschlag.toArray());
							JDialog dialog = new JDialog();
							dialog.setTitle("Filme mit zuwenig Informationen für Upload");
							dialog.getContentPane().add(new JScrollPane(liste));
							dialog.setSize(400, 200);
							dialog.setVisible(true);

						}
						
					} catch (RemoteException e) {
						if (e.getCause() != null && e.getCause().getMessage() != null
								&& e.getCause().getMessage().equals("Connection refused")) {
							logger.info("sendFilme() failed: Connection refused");
							JOptionPane.showMessageDialog(null, "Der Server ist nicht erreichbar.", "Verbindungs-Problm", JOptionPane.INFORMATION_MESSAGE);
						} else {
							logger.error("sendFilme() failed", e);
							JOptionPane.showMessageDialog(null, "Beim Upload ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					}

				}

			});
			thread.start ();

		} else
			System.err.println("Thread läuft noch...");

	}
	
	/**
	 * Alle lokalen Genres aktualisieren
	 * Anpassung an die GUID vom Server
	 * @throws RemoteException 
	 */
	private void synchronisiereGenres(FilmverwaltungWebserviceProxy proxy) throws RemoteException {
		
		List<Genre> lokalGenres = service.findAllGenres();
		
		if (lokalGenres.size() > 0) {
			Genre[] serverGenres = proxy.getAllGenres();

			if (serverGenres == null)
				logger.info("proxy.getAllGenres() returned NULL: Auf dem Server existieren keine Genres.");
			else
				for (Genre lokalGenre : lokalGenres)
					for (Genre serverGenre : serverGenres)
						if (	lokalGenre.getName().equals(serverGenre.getName())
							&&	!lokalGenre.getId().equals(serverGenre.getId()))
								service.changeGenreId(lokalGenre, serverGenre.getId());
		}
		
	}

	/**
	 * Alle lokalen Personen aktualisieren
	 * Anpassung an die GUID vom Server
	 */
	private void synchronisierePersonen(FilmverwaltungWebserviceProxy proxy) throws RemoteException {
		
		List<Person> lokalPersonen = service.findAllPersonen();
		
		if (lokalPersonen.size() > 0) {
			Person[] serverPersonen = proxy.getAllPersonen();
			if (serverPersonen == null)
				logger.info("proxy.getAllPersonen() returned NULL: Auf dem Server existieren keine Personen.");
			else
				for (Person lokalPerson : lokalPersonen)
					for (Person serverPerson : serverPersonen)
						if (	lokalPerson.getName().equals(serverPerson.getName())
							&&	!lokalPerson.getId().equals(serverPerson.getId()))
								service.changePersonId(lokalPerson, serverPerson.getId());
		}
		
	}
	
	private void synchronisiereFilme(FilmverwaltungWebserviceProxy proxy) throws RemoteException {
		
		List<Film> filme = service.findAllFilme();
		
		if (filme.size() > 0) {
			Film[] serverFilme = proxy.getAllFilme();
			if (serverFilme == null)
				logger.info("proxy.getAllFilme() returned NULL: Auf dem Server existieren keine Filme.");
			else
				for (Film lokalFilm : filme)
					for (Film serverFilm : serverFilme)
						// Wenn zwei Filme 
						//     identische OfdbID
						// oder
						//     gleicher Name + Erscheinungsjahr
						// haben, wird von Gleichheit ausgegangen und die ID angepasst.
						// TODO: Für alle anderen müsste man später wohl nochmal nachfragen.
						if (	(	lokalFilm.getOfdbId() != null && serverFilm.getOfdbId() != null && lokalFilm.getOfdbId().equals(serverFilm.getOfdbId())
								 ||	(lokalFilm.getName() != null && lokalFilm.getErscheinungsJahr() != null && serverFilm.getName() != null && serverFilm.getErscheinungsJahr() != null && lokalFilm.getName().equals(serverFilm.getName()) && lokalFilm.getErscheinungsJahr().equals(serverFilm.getErscheinungsJahr())) )
								&&	!lokalFilm.getId().equals(serverFilm.getId()) )
									service.changeFilmId(lokalFilm, serverFilm.getId());
		}
		
	}
	
	public void downloadFilme() {

		if (thread == null || !thread.isAlive()) {

			thread = new Thread (new Runnable() {
				public void run() {

					try {
						
						FilmverwaltungWebserviceProxy proxy = new FilmverwaltungWebserviceProxy();

						Benutzer dbBenutzer = service.findBenutzer();

						if (dbBenutzer == null) {
							JOptionPane.showMessageDialog(null, "Sie haben noch keinen Benutzer für den WebService.");
							return;
						}

						Benutzer soapBenutzer = proxy.getBenutzer(dbBenutzer.getAnmeldename(), dbBenutzer.getPasswort());

						if (soapBenutzer == null) {
							JOptionPane.showMessageDialog(null, "Ihre WebService Login-Daten sind ungültig.");
							return;
						}

						/*
						 * Ab hier gehts los
						 */
						
						// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
						ProgressMonitor pm = new ProgressMonitor(null, "Synchronisiere mit WebService", "", 0, 0);
						pm.setMillisToDecideToPopup(10);
						pm.setMillisToPopup(10);
						pm.setNote("Verbinde mit WebService...");
												
						/*
						 * Liste von allen FilmIDs auf dem Server
						 */
						
						String[] filmIds = proxy.getFilmIDs(soapBenutzer);
						
						if (filmIds == null || filmIds.length == 0) {
							JOptionPane.showMessageDialog(null, "Dieser Benutzer hat keine Filme hochgeladen.");
							return;
						}
						
						pm.setMaximum(filmIds.length);
						
						int counter = 0;
						for (String filmId : filmIds) {

							if (service.findFilm(filmId) != null)
								continue; // Den haben wir schon
							
							Film film = proxy.getFilm(filmId);
							
							pm.setNote("Download: " + film.getName());
							
							/*
							 * Genres
							 */
							
							Genre[] genres = proxy.getGenresForFilm(filmId);
							
							List<Genre> genresList = new ArrayList<Genre>();
							if (genres != null)
								for (Genre genre : genres) {
									service.saveGenre(genre);
									genresList.add(genre);
								}
							
							film.setGenres(genresList);
							
							/*
							 * Personen
							 */
							
							Person[] personen = proxy.getPersonenForFilm(filmId);
							
							List<Person> personenList = new ArrayList<Person>();
							if (personen != null)
								for (Person person : personen) {
									service.savePerson(person);
									personenList.add(person);
									
								}
							
							film.setCast(personenList);
							
							/*
							 * Speichern
							 */
							
							service.saveFilm(film);
							
							Filmbewertung filmbewertung = proxy.getBewertung(soapBenutzer, filmId);
							
							filmbewertung.setFilmId(film.getId());
							
							service.saveFilmbewertung(filmbewertung);
							
							// Events
//							Application.instance().getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.CREATED, film));
							
							// Wenn der Nutzer nicht mehr will, is auch Schluss...
							if (pm.isCanceled())
								break;
							
							counter = counter + 1;
							pm.setProgress(counter);
						}
						
						pm.close();
						
						JOptionPane.showMessageDialog(null, "Download abgeschlossen.\nBitte starten Sie das Programm einmal neu.");
						
					} catch (RemoteException e) {
						if (e.getCause() != null && e.getCause().getMessage() != null
								&& e.getCause().getMessage().equals("Connection refused")) {
							logger.info("downloadFilme() failed: Connection refused");
							JOptionPane.showMessageDialog(null, "Der Server ist nicht erreichbar.", "Verbindungs-Problm", JOptionPane.INFORMATION_MESSAGE);
						} else {
							logger.error("downloadFilme() failed", e);
							JOptionPane.showMessageDialog(null, "Beim Download ist ein Fehler aufgetreten.", "Fehler", JOptionPane.ERROR_MESSAGE);
						}
					}

				}

			});
			thread.start ();

		} else
			System.err.println("Thread läuft noch...");
		
	}

}
