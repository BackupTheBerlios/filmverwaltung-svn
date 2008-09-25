package stefanoltmann.filmverwaltung.dataaccess;

import java.util.List;

/**
 * @author 	Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmverwaltungService {

	private FilmDao				filmDao;
	private FilmbewertungDao	filmbewertungDao;
	private GenreDao 			genreDao;
	private PersonDao			personDao;
	private BenutzerDao			benutzerDao;
	
	public FilmverwaltungService(FilmDao filmDao, FilmbewertungDao filmbewertungDao, GenreDao genreDao, PersonDao personDao, BenutzerDao benutzerDao) {
		this.filmDao 			= filmDao;
		this.filmbewertungDao 	= filmbewertungDao;
		this.genreDao 			= genreDao;
		this.personDao 			= personDao;
		this.benutzerDao 		= benutzerDao; 
	}
	
	// filmDao
	
	public Film findFilm(String id) {
		return filmDao.find(id);
	}
	
	public Film findFilmWithOfdbId(int ofdbId) {
		return filmDao.findWithOfdbId(ofdbId);
	}
	
	public boolean filmWithOfdbIdExists(int ofdbId) {
		return filmDao.filmWithOfdbIdExists(ofdbId);
	}
	
	public List<Film> findAllFilme() {
		
		List<Film> filme = filmDao.findAll();
		
		// Jeder Film braucht eine Filmbewertung Property
		for (Film film : filme) {
			List<Filmbewertung> bewertungen = findAllFilmbewertungen(film.getId());
			if (bewertungen.size() > 0)
				film.setBewertung(bewertungen.get(0));
		}
		
		return filme;
	}
	
	public void changeFilmId(Film film, String neueId) {
		filmDao.changeID(film, neueId);
	}
	
	public void saveFilm(Film film) {
		filmDao.saveOrUpdate(film);
		
		if (film.getBewertung() != null) {
			film.getBewertung().setFilmId(film.getId());
			saveFilmbewertung(film.getBewertung());
		}
	}
	
	public void deleteFilm(Film film) {
		filmDao.delete(film);
		
		if (film.getBewertung() != null)
			deleteFilmbewertung(film.getBewertung());
		else {
			for (Filmbewertung filmbewertung : findAllFilmbewertungen(film.getId()))
				deleteFilmbewertung(filmbewertung);
		}
	}
	
	public List<Film> findAllFilme(Genre genre) {
		return filmDao.findAllFilme(genre);
	}
	
	public List<Film> findAllFilme(Person person) {
		return filmDao.findAllFilme(person);
	}
	
	// filmbewertungDao
	
	public List<Filmbewertung> findAllFilmbewertungen() {
		return filmbewertungDao.findAll();
	}
	
	public List<Filmbewertung> findAllFilmbewertungen(String filmId) {
		return filmbewertungDao.findAll(filmId);
	}
	
	public List<Filmbewertung> findAllFilmbewertungen(Benutzer benutzer) {
		return filmbewertungDao.findAll(benutzer);
	}

	public List<Filmbewertung> findAllFilmbewertungen(String filmId, Benutzer benutzer) {
		return filmbewertungDao.findAll(filmId, benutzer);
	}
	
	public void saveFilmbewertung(Filmbewertung filmbewertung) {
		filmbewertungDao.saveOrUpdate(filmbewertung);
	}
	
	public void deleteFilmbewertung(Filmbewertung filmbewertung) {
		filmbewertungDao.delete(filmbewertung);
	}
	
	// genreDao
	
	public List<Genre> findAllGenres() {
		return findAllGenres(false);
	}
	
	public List<Genre> findAllGenres(boolean attachAdditionalData) {
		
		List<Genre> genres = genreDao.findAll();
		
		if (attachAdditionalData)
			for (Genre genre : genres)
				genre.setFilme( filmDao.findAllFilme(genre) );
			
		return genres;
	}
	
	public Genre findGenre(int id) {
		Genre genre = genreDao.find(id);
		if (genre != null)
			genre.setFilme( filmDao.findAllFilme(genre) );
		return genre; 
	}
	
	public Genre findGenre(String name) {
		return genreDao.find(name);
	}
	
	public void changeGenreId(Genre genre, String neueId) {
		genreDao.changeID(genre, neueId);
	}
	
	public void saveGenre(Genre genre) {
		genreDao.saveOrUpdate(genre);
	}
	
	public void deleteGenre(Genre genre) {
		genreDao.delete(genre);
	}
	
	// personDao
	
	public List<Person> findAllPersonen() {
		return personDao.findAll();
	}
	
	public List<Person> findAllPersonen(boolean attachAdditionalData) {
		
		List<Person> personen = personDao.findAll();
		
		if (attachAdditionalData)
			for (Person person : personen)
				person.setFilme( filmDao.findAllFilme(person) );
		
		return personen;
	}
	
	public Person findPerson(int id) {
		Person person = personDao.find(id);
		if (person != null)
			person.setFilme( filmDao.findAllFilme(person) );
		return person;
	}
	
	public Person findPerson(String name) {
		return personDao.find(name);
	}
	
	public void changePersonId(Person person, String neueId) {
		personDao.changeID(person, neueId);
	}
	
	public void savePerson(Person person) {
		personDao.saveOrUpdate(person);
	}
	
	public void deletePerson(Person person) {
		personDao.delete(person);
	}
	
	// BenutzerDao
	
	public Benutzer findBenutzer() {
		return benutzerDao.find();
	}
	
	public Benutzer findBenutzer(String anmeldename) {
		return benutzerDao.find(anmeldename);
	}
	
	public void saveBenutzer(Benutzer webServiceAnmeldedaten) {
		benutzerDao.saveOrUpdate(webServiceAnmeldedaten);
	}
	
}
