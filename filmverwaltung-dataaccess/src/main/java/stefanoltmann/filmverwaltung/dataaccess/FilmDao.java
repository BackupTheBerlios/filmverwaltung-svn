package stefanoltmann.filmverwaltung.dataaccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmDao extends HibernateDao {
	
	private static final Log logger = LogFactory.getLog(FilmDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Film> findAll() {

		logger.info("findAll Film");
		
		List<Film> filme = new ArrayList<Film>();

		try {
			openSession();
			filme = (List<Film>) getSession().createQuery("FROM Film").list();
		} catch (HibernateException e) {
			logger.error("findAll Film failed", e);
			throw e;
		} finally {
			closeSession();
		}

		// Alphabetisch sortiere
		Collections.sort(filme, new Comparator<Film>() {
			public int compare(Film o1, Film o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		return filme;
	}
	
	public Film find(String id) {
		logger.info("find Film " + id);
		try {
			openSession();
			Film film = (Film)getSession().get(Film.class, id);
			return film;
		} catch (HibernateException e) {
			logger.error("find Film failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public Film findWithOfdbId(int ofdbId) {
		logger.info("find Film with ofdbId " + ofdbId);
		try {
			openSession();
			Film film = (Film)  getSession().createQuery("FROM Film WHERE ofdbId = :ofdbId")
											.setParameter("ofdbId", ofdbId)							
											.uniqueResult();
			return film;
		} catch (HibernateException e) {
			logger.error("find Film failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public boolean filmWithOfdbIdExists(int ofdbId) {
		if (findWithOfdbId(ofdbId) == null)
			return false;
		else
			return true;
	}
	
	public void delete(Film film) {
		logger.info("delete Film " + film.getName());
		try {
			openSession();
			getSession().delete(film);
		} catch (HibernateException e) {
			logger.error("delete Film failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public void saveOrUpdate(Film film) {
		logger.info("saveOrUpdate Film " + film.getName());
		try {
			openSession();
			
			// Neue ID assignen
			if (film.getId() == null)
				film.setId(UUID.randomUUID().toString());
			
			getSession().saveOrUpdate(film);
		} catch (HibernateException e) {
			logger.error("saveOrUpdate Film failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	/**
	 * Findet alle Filme zugehörig zu einem gegebenen Genre.
	 * Diese Methode ist ein Workaround, weil many-to-many von 
	 * Hibernate irgendwie nicht richtig funktioniert.
	 */
	@SuppressWarnings("unchecked")
	public List<Film> findAllFilme(Genre genre) {

		logger.info("findAll Filme for Genre " + genre);
				
		List<Film> 		filme 	= new ArrayList<Film>();
		List<String> 	filmIds;
		
		if (genre == null)
			return filme;
		
		try {
			openSession();
			
			filmIds =	getSession().createSQLQuery("SELECT film_id FROM film_genre JOIN genre ON film_genre.genre_id = genre.id WHERE id = :genreId")
									.setParameter("genreId", genre.getId())
									.list();

		} catch (HibernateException e) {
			logger.error("findAll Filme for Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
		for (String filmId : filmIds)
			filme.add(find(filmId));
		
		// Alphabetisch sortiere
		Collections.sort(filme, new Comparator<Film>() {
			public int compare(Film o1, Film o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return filme;
	}
	
	public void changeID(Film film, String neueId) {
		
		if (film == null)
			throw new IllegalArgumentException("Parameter 'film' darf nicht NULL sein.");
		if (film.getName().length() == 0)
			throw new IllegalArgumentException("Parameter 'film' darf nicht leer sein.");
		
		if (neueId == null)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht NULL sein.");
		if (neueId.length() == 0)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht leer sein.");
		
		logger.info("change ID for Film " + film.getName());
		try {
			
			String alteId = film.getId();
		
			openSession();

			getSession().createQuery("UPDATE Film as film SET id = :neueId WHERE id = :alteId")
						.setParameter("neueId", neueId)
						.setParameter("alteId", alteId)
						.executeUpdate();
			
			// FIXME Alles mega uncool hier
			getSession().createQuery("UPDATE Filmbewertung as filmbewertung SET film_id = :neueId WHERE film_id = :alteId")
						.setParameter("neueId", neueId)
						.setParameter("alteId", alteId)
						.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error("change ID for Film " + film.getId() + " failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
	}
	
	/**
	 * Findet alle Filme zugehörig zu einem gegebenen Person.
	 * Diese Methode ist ein Workaround, weil many-to-many von 
	 * Hibernate irgendwie nicht richtig funktioniert.
	 */
	@SuppressWarnings("unchecked")
	public List<Film> findAllFilme(Person person) {

		logger.info("findAll Filme for Person " + person);
		
		List<Film> 		filme 	= new ArrayList<Film>();
		List<String> 	filmIds;
		
		if (person == null)
			return filme;
		
		try {
			openSession();
			
			filmIds =	getSession().createSQLQuery("SELECT film_id FROM film_person JOIN person ON film_person.person_id = person.id WHERE id = :personId")
									.setParameter("personId", person.getId())
									.list();

		} catch (HibernateException e) {
			logger.error("findAll Filme for Person failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
		for (String filmId : filmIds)
			filme.add(find(filmId));
		
		// Alphabetisch sortiere
		Collections.sort(filme, new Comparator<Film>() {
			public int compare(Film o1, Film o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return filme;
	}
	
}
