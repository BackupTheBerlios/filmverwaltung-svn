package stefanoltmann.filmverwaltung.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import stefanoltmann.filmverwaltung.dataaccess.Benutzer;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
@WebService(endpointInterface = "stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice")
public class FilmverwaltungWebserviceImpl implements FilmverwaltungWebservice {
	
	private static Logger logger = Logger.getLogger(FilmverwaltungWebserviceImpl.class);
	
	private FilmverwaltungService service;
	
	public FilmverwaltungWebserviceImpl(FilmverwaltungService filmverwaltungService) {
		this.service = filmverwaltungService;
	}
	
	public Benutzer registriereNutzer(String anmeldename, String passwort) {
		
		logger.info("registriereNutzer() für: " + anmeldename);
		
		try {
			
			// Daten prüfen
			if (anmeldename == null || passwort == null || anmeldename.equals("") || passwort.equals("")) {
				logger.warn("registriereNutzer() mit ungültigen Daten aufgerufen: " + anmeldename + " - " + passwort);
				return null;
			}
			
			if (service.findBenutzer(anmeldename) != null) {
				logger.warn("registriereNutzer() - Nutzername existiert bereits: " + anmeldename);
				return null;
			}
			
			Benutzer benutzer = new Benutzer(anmeldename, passwort);
			
			service.saveBenutzer(benutzer);
			
			// keine internen IDs rausgeben
			benutzer.setId(null);
			
			return benutzer;
			
		} catch (Exception e) {
			logger.error("registriereNutzer()", e);
			return null;
		}
	}
	
	public Benutzer getBenutzer(String anmeldename, String passwort) {
		
		logger.info("getBenutzer() für: " + anmeldename);
		
		try {
			
			if (anmeldename == null || passwort == null || anmeldename.equals("") || passwort.equals("")) {
				logger.warn("getBenutzer() mit ungültigen Daten aufgerufen: " + anmeldename + " - " + passwort);
				return null;
			}
				
			Benutzer benutzer = service.findBenutzer(anmeldename);
			
			if (benutzer == null) {
				logger.warn("getBenutzer() findet keinen Nutzer für: " + anmeldename);
				return null;
			}
			
			if (benutzer.getPasswort().equals(passwort)) {
				return benutzer;
			} else {
				logger.warn("getBenutzer() mit falschem Passwort aufgerufen für: " + anmeldename);
				return null;
			}
		
		} catch (Exception e) {
			logger.error("getBenutzer()", e);
			return null;
		}
	}
	
	public boolean isFilmInDatabase(String filmId) {
		if (service.findFilm(filmId) != null)
			return true;
		else
			return false;
	}

	public Genre[] getAllGenres() {

		logger.info("WebService getAllGenres() aufgerufen");
		
		try {
			
			/*
			 * Genres senden
			 */
			
			List<Genre> genres = service.findAllGenres();

			if (genres == null)
				throw new IllegalStateException("service.findAllGenres() returned NULL.");
			
			// In Array umwandeln
			Genre[] genresArray = new Genre[genres.size()];
			for (int i = 0; i <= genres.size() - 1; i++)
				genresArray[i] = genres.get(i);
			
			return genresArray;
	
		} catch (Exception e) {
			logger.error("getAllGenres()", e);
			return null;
		}

	}

	public String sendGenre(Benutzer benutzer, Genre genre) {

		logger.info("WebService sendGenre() aufgerufen");
		
		/*
		 * Benutzerdaten prüfen
		 * Ohne gültigen Benutzerlogin gehts nicht.
		 */
		
		if (benutzer == null || genre == null) {
			logger.warn("sendGenre() mit ungültigen Daten aufgerufen - Benutzer: " + benutzer + ", Genre: " + genre);
			return null;
		}
		
		Benutzer dbBenutzer = getBenutzer(benutzer.getAnmeldename(), benutzer.getPasswort());
		benutzer = null;
		
		if (dbBenutzer == null)
			return null;
		
		/*
		 * Genre speichern und GUID zurückgeben
		 */
		
		try  {
		
			service.saveGenre(genre);
			return genre.getId().toString();
		
		} catch (Exception e) {
			logger.error("sendGenre()", e);
			return null;
		}
		
	}
	
	public Person[] getAllPersonen() {

		logger.info("WebService getAllPersonen() aufgerufen");
		
		try {
			
			/*
			 * Person senden
			 */
			
			List<Person> personen = service.findAllPersonen();
			
			if (personen == null)
				throw new IllegalStateException("service.findAllPersonen() returned NULL.");

			// In Array umwandeln
			Person[] personenArray = new Person[personen.size()];
			for (int i = 0; i <= personen.size() - 1; i++)
				personenArray[i] = personen.get(i);
			
			return personenArray;
	
		} catch (Exception e) {
			logger.error("getAllGenres()", e);
			return null;
		}

	}
	
	/**
	 * Extrem verkürzte Informationen über den Film um Bandbreite zu sparen.
	 * - ID
	 * - Name
	 * - Erscheinungsjahr
	 * - ofdbId
	 * Alle anderen Felder werden NULL gesetzt.
	 */
	public Film[] getAllFilme() {
		
		logger.info("WebService getAllFilme() aufgerufen");
		
		try {
			
			/*
			 * Filme senden
			 */
			
			List<Film> filme = service.findAllFilme();

			if (filme == null)
				throw new IllegalStateException("service.findAllFilme() returned NULL.");
			
			// Kastrieren
			for (Film film : filme) {
				film.setUploader(null);
				film.setBewertung(null);
				film.setGenres(null);
				film.setCast(null);
				film.setBewertung(null);
				film.setOfdbName(null);
				film.setOfdbBeschreibung(null);
			}
			
			// In Array umwandeln
			Film[] filmeArray = new Film[filme.size()];
			for (int i = 0; i <= filme.size() - 1; i++)
				filmeArray[i] = filme.get(i);
			
			return filmeArray;
	
		} catch (Exception e) {
			logger.error("getAllFilme()", e);
			return null;
		}
		
	}
	
	public String[] getFilmIDs(Benutzer benutzer) {
		
		logger.info("WebService getFilmIDs() aufgerufen");
		
		try {
			
			// Aufruf validieren

			if (benutzer == null) {
				logger.warn("getFilme() mit ungültigen Daten aufgerufen: " + benutzer);
				return new String[0];
			}

			Benutzer dbBenutzer = getBenutzer(benutzer.getAnmeldename(), benutzer.getPasswort());
			benutzer = null;
			
			if (dbBenutzer == null)
				return null;
			
			/*
			 * Hier gehts los
			 */
			
			List<String> filmIds = new ArrayList<String>();
			
			// Überall, wo der Nutzer eine Bewertung hat, wird er auch einen Film zu brauchen
			List<Filmbewertung> filmBewertungen = service.findAllFilmbewertungen(dbBenutzer);
			if (filmBewertungen == null)
				throw new IllegalStateException("service.findAllFilmbewertungen() returned NULL.");
			for (Filmbewertung filmbewertung : service.findAllFilmbewertungen(dbBenutzer))
				filmIds.add(filmbewertung.getFilmId());
			
			String[] filmeArray = new String[filmIds.size()];
			for (int i = 0; i <= filmIds.size() - 1; i++)
				filmeArray[i] = filmIds.get(i);
			
			return filmeArray;
			
		} catch (Exception e) {
			logger.error("getFilme() für Benutzer " + benutzer.getAnmeldename() + " failed", e);
			return null;
		}
		
	}
	
	public Film getFilm(String filmId) {

		logger.info("WebService getFilm() aufgerufen: " + filmId);
		
		try {

			/*
			 * Film senden
			 */
			
			Film film = service.findFilm(filmId);
			
			// Wenn es denn Film nicht gibt, dann nicht drauf zugreifen
			if (film == null)
				return null;
			
			// nicht alle informationen übermitteln
			// sonst ist das teil zu komplex für apache axis
			film.setUploader(null);
			film.setBewertung(null);
			film.setGenres(null);
			film.setCast(null);
			
			return film;
	
		} catch (Exception e) {
			logger.error("getFilm() " + filmId + " failed", e);
			return null;
		}

	}
	
	public Genre[] getGenresForFilm(String filmId) {

		logger.info("WebService getAllGenresForFilm() aufgerufen: " + filmId);
		
		try {
			
			/*
			 * Genres senden
			 */
			
			Film film = service.findFilm(filmId);
			
			if (film == null || film.getGenres() == null)
				return new Genre[0];
			
			List<Genre> genres = film.getGenres();

			// In Array umwandeln
			Genre[] genresArray = new Genre[genres.size()];
			for (int i = 0; i <= genres.size() - 1; i++)
				genresArray[i] = genres.get(i);
			
			return genresArray;
	
		} catch (Exception e) {
			logger.error("getAllGenres()", e);
			return null;
		}

	}
	
	public Person[] getPersonenForFilm(String filmId) {

		logger.info("WebService getAllPersonenForFilm() aufgerufen: " + filmId);
		
		try {
			
			/*
			 * Personen senden
			 */
			
			Film film = service.findFilm(filmId);
			
			if (film == null || film.getCast() == null)
				return null;
			
			List<Person> personen = film.getCast();

			// In Array umwandeln
			Person[] personenArray = new Person[personen.size()];
			for (int i = 0; i <= personen.size() - 1; i++)
				personenArray[i] = personen.get(i);
			
			return personenArray;
	
		} catch (Exception e) {
			logger.error("getAllGenres()", e);
			return null;
		}

	}
	
	/**
	 * Um Filme zu übermitteln
	 * @param <Genre>
	 */
	public String sendFilm(Benutzer benutzerLogin, Film film, Genre[] genres, Person[] cast) {
		
		logger.info("sendFilm() aufgerufen");
		
		try {

			// Aufruf validieren
			if (benutzerLogin == null || film == null || genres == null) {
				logger.warn("sendFilm() mit ungültigen Daten aufgerufen: " + benutzerLogin + " - " + film + " - " + genres);
				return null;
			}
			
			Benutzer benutzer = getBenutzer(benutzerLogin.getAnmeldename(), benutzerLogin.getPasswort());
				
			if (benutzer == null)
				return null;	
				
			// Nur Filme mit OFDB ID sind möglich
			
			if (film.getOfdbId() == null || film.getOfdbId() == 0) {
				logger.warn("sendFilm() - Film hat keine OFDB ID");
				return null;
			}
			
			if (service.filmWithOfdbIdExists(film.getOfdbId()))
				return null; // hamm wa schon
			
			film.setBewertung(null);
			film.setUploader(null);
			film.setGenres(null);
			film.setCast(null);

			// Prüfen, welche Genres schon in der Datenbank existieren und diese nicht neu speichern 
			List<Genre> filmGenres = new ArrayList<Genre>();
			if (genres != null) { // warum auch immer...
				for (Genre genre : genres) {
					Genre dbGenre = service.findGenre(genre.getName());
					// nur nach dem namen zu fragen, welcher ehe unique ist, ist sicherer
					if (dbGenre == null) {
						service.saveGenre(genre);
						dbGenre = genre;
					}
					filmGenres.add(dbGenre);
				}
			}
				
			// Prüfen, welche Genres schon in der Datenbank existieren und diese nicht neu speichern 
			List<Person> filmCast = new ArrayList<Person>();
			if (cast != null) { // Trickfilme haben keine Schauspieler
				for (Person person : cast) {
					Person dbPerson = service.findPerson(person.getName());
					// nur nach dem namen zu fragen, welcher ehe unique ist, ist sicherer
					if (dbPerson == null) {
						service.savePerson(person);
						dbPerson = person;
					}
					filmCast.add(dbPerson);
				}
			}

			// Listen zum Film setzen
			film.setGenres(filmGenres);
			film.setCast(filmCast);

			// Wer hats hochgeladen?
			film.setUploader(benutzer);

			// Speichern
			service.saveFilm(film); 
			
			return film.getId();
		
		} catch (Exception e) {
			logger.error("sendFilm()", e);
			return null;
		}
		
	}

	public void sendBewertung(Benutzer benutzerLogin, Filmbewertung bewertung) {

		logger.info("sendBewertung() aufgerufen");

		try {

			// Aufruf validieren

			if (benutzerLogin == null || bewertung == null) {
				logger.warn("sendFilm() mit ungültigen Daten aufgerufen: " + benutzerLogin + " - " + bewertung);
				return;
			}

			Benutzer benutzer = getBenutzer(benutzerLogin.getAnmeldename(), benutzerLogin.getPasswort());

			if (benutzer == null)
				return;
		
			Film film = service.findFilm(bewertung.getFilmId());
			
			if (film == null) {
				logger.warn("sendBewertung() - Bewertung mit filmId " + bewertung.getFilmId() + " hat keinen Film.");
				return;
			}
			
			bewertung.setFilmId(film.getId());			
			
			// Alle Bewertungen dieses Users zu dieser OfdbID löschen
			// Sollte eigentlich immer nur eine sein.
			List<Filmbewertung> dbFilmbewertungen = service.findAllFilmbewertungen(bewertung.getFilmId(), benutzer);
			for (Filmbewertung filmbewertung : dbFilmbewertungen)
				service.deleteFilmbewertung(filmbewertung);

			// Wer hats hochgeladen?
			bewertung.setBenutzer(benutzer);
			
			// Speichern
			service.saveFilmbewertung(bewertung);

		} catch (Exception e) {
			logger.error("sendBewertung()", e);
		}

	}
	
	public Filmbewertung getBewertung(Benutzer benutzer, String filmId) {

		logger.info("getBewertung() aufgerufen mit filmId " + filmId);
		
		try {

			if (benutzer == null) {
				logger.warn("getBewertung() mit ungültigen Daten aufgerufen: " + benutzer);
				return null;
			}
			
			Benutzer dbBenutzer = getBenutzer(benutzer.getAnmeldename(), benutzer.getPasswort());
			benutzer = null;
			
			if (dbBenutzer == null)
				return null;
			
			List<Filmbewertung> filmbewertungen = service.findAllFilmbewertungen(filmId, dbBenutzer);
			
			if (filmbewertungen == null)
				throw new IllegalStateException("service.findAllFilmbewertungen() returned NULL.");
			
			if (filmbewertungen.size() == 0)
				return null;
			
			Filmbewertung filmbewertung = filmbewertungen.get(0);
			filmbewertung.setBenutzer(null);
			
			return filmbewertung;

		} catch (Exception e) {
			logger.error("getBewertung()", e);
			return null;
		}
		
	}
	
}
