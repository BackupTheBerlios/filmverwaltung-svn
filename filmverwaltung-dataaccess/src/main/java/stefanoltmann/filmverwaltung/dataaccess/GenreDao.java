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
public class GenreDao extends HibernateDao {
	
	private static final Log logger = LogFactory.getLog(GenreDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Genre> findAll() {

		logger.info("findAll Genre");
		
		List<Genre> genres = new ArrayList<Genre>();

		try {
			openSession();
			genres = (List<Genre>) getSession().createQuery("FROM Genre").list();
		} catch (HibernateException e) {
			logger.error("findAll Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
		// Alphabetisch sortiere
		Collections.sort(genres, new Comparator<Genre>() {
			public int compare(Genre o1, Genre o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return genres;
	}
	
	public Genre find(int id) {
		logger.info("find Genre by id " + id);
		try {
			openSession();
			Genre genre = (Genre)getSession().get(Genre.class, id);
			return genre;
		} catch (HibernateException e) {
			logger.error("find Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public Genre find(String name) {
		logger.info("find Genre by name " + name);
		try {
			openSession();
			return (Genre)getSession().createQuery("FROM Genre AS genre WHERE genre.name=:name")
										.setParameter("name", name)
										.uniqueResult();
		} catch (HibernateException e) {
			logger.error("find Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public void delete(Genre genre) {
		logger.info("delete Genre " + genre.getName());
		try {
			openSession();
			getSession().delete(genre);
		} catch (HibernateException e) {
			logger.error("delete Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public void changeID(Genre genre, String neueId) {
		
		if (genre == null)
			throw new IllegalArgumentException("Parameter 'genre' darf nicht NULL sein.");
		if (genre.getName().length() == 0)
			throw new IllegalArgumentException("Parameter 'genre' darf nicht leer sein.");
		
		if (neueId == null)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht NULL sein.");
		if (neueId.length() == 0)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht leer sein.");
		
		logger.info("change ID for Genre " + genre.getName());
		try {
		
			openSession();

			getSession().createQuery("UPDATE Genre as genre SET id = :neueId WHERE name = :name")
						.setParameter("neueId", neueId)
						.setParameter("name", genre.getName())
						.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error("change ID for Genre failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
	}
	
	public void saveOrUpdate(Genre genre) {
		
		if (genre == null)
			throw new IllegalArgumentException("Parameter 'genre' must not be null.");
		if (genre.getName().length() == 0)
			throw new IllegalArgumentException("Parameter 'genre' must not have zero size.");
		
		logger.info("saveOrUpdate Genre " + genre.getName());
		try {
			openSession();
			
			if (genre.getId() == null)
				genre.setId(UUID.randomUUID().toString());
			
			getSession().saveOrUpdate(genre);
		} catch (HibernateException e) {
			logger.error("saveOrUpdate Genre " + genre.getId() + " failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
}
