package stefanoltmann.filmverwaltung.webservice;

import javax.jws.WebService;

import stefanoltmann.filmverwaltung.dataaccess.Benutzer;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
@WebService
public interface FilmverwaltungWebservice {

	public Benutzer registriereNutzer(String anmeldename, String passwort);
	
	public Benutzer getBenutzer(String anmeldename, String passwort);

	public boolean isFilmInDatabase(String filmId);
	
	/**
	 * Ruft ein Array aller Genres ab
	 * Bei einem Fehler ist die Liste leer.
	 */
	public Genre[] getAllGenres();
	
	/**
	 * Übermittelt ein Genre und gibt die vom Server zugewiesene GUID zurück
	 * Bei einem Fehler ist der String NULL
	 */
	public String sendGenre(Benutzer benutzer, Genre genre);
	
	/**
	 * Ruft ein Array aller Personen ab
	 * Bei einem Fehler ist die Liste leer.
	 */
	public Person[] getAllPersonen();
	
	/**
	 * Extrem verkürzte Informationen über den Film um Bandbreite zu sparen.
	 * - ID
	 * - Name
	 * - Erscheinungsjahr
	 * - ofdbId
	 * Alle anderen Felder werden NULL gesetzt.
	 */
	public Film[] getAllFilme();
	
	public String[] getFilmIDs(Benutzer benutzer);
	
	public Film getFilm(String filmId);
	
	public Genre[] getGenresForFilm(String filmId);
	
	public Person[] getPersonenForFilm(String filmId);
	
	public String sendFilm(Benutzer benutzerLogin, Film film, Genre[] genres, Person[] cast);
	
	public void sendBewertung(Benutzer benutzerLogin, Filmbewertung bewertung);
	
	public Filmbewertung getBewertung(Benutzer benutzer, String filmId);
	
//	public void reportError(String error);
	
//	public String getCurrentSoftwareVersionNumber();
	
//	public String getSoftwareDownloadLink();

}
