package stefanoltmann.filmverwaltung.dataaccess;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmbewertungDao extends HibernateDao {
	
	private static final Log logger = LogFactory.getLog(FilmbewertungDao.class);
	
	public List<Filmbewertung> findAll(String filmId) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(Restrictions.eq("filmId", filmId));
		return findAll(criteria);		
	}
	
	public List<Filmbewertung> findAll(Benutzer benutzer) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(Restrictions.eq("benutzer", benutzer));
		return findAll(criteria);		
	}
	
	public List<Filmbewertung> findAll(String filmId, Benutzer benutzer) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		criteria.add(Restrictions.eq("filmId", filmId));
		criteria.add(Restrictions.eq("benutzer", benutzer));
		return findAll(criteria);		
	}
	
	public List<Filmbewertung> findAll() {
		return findAll(new ArrayList<Criterion>());		
	}
	
	@SuppressWarnings("unchecked")
	private List<Filmbewertung> findAll(List<Criterion> criteria) {
		
		List<Filmbewertung> bewertungen = new ArrayList<Filmbewertung>();

		DetachedCriteria crit = DetachedCriteria.forClass(Filmbewertung.class);
		
		for (Criterion criterion : criteria) 
			crit.add(criterion);
		
		try {
			openSession();
			bewertungen = (List<Filmbewertung>) crit.getExecutableCriteria(getSession()).list();
		} catch (HibernateException e) {
			logger.error("findAll criteria Filmbewertung failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
		return bewertungen;
	}
	
	public void updateBenutzerWhereNULL(Benutzer benutzer) {
		
		if (benutzer == null)
			throw new IllegalArgumentException("Parameter 'benutzer' darf nicht NULL sein.");
		
		if (benutzer.getId() == null)
			throw new IllegalArgumentException("Parameter 'benutzer.ID' darf nicht NULL sein.");
		
		if (benutzer.getId().length() == 0)
			throw new IllegalArgumentException("Parameter 'benutzer' muss eine ID haben.");
		
		if (!benutzer.isLokal())
			throw new IllegalStateException("Diese Zuordnung darf nur mit lokalen Benutzern erfolgen.");
		
		logger.info("update Filmbewertung.benutzer Where NULL " + benutzer.getId());
		try {
		
			openSession();

			getSession().createQuery("UPDATE Filmbewertung as filmbewertung SET benutzer = :benutzer WHERE benutzer = NULL")
						.setParameter("benutzer", benutzer)
						.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error("update Filmbewertung.benutzer Where NULL failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
	}
	
	public void saveOrUpdate(Filmbewertung filmbewertung) {
		
		if (filmbewertung == null)
			throw new IllegalArgumentException("Parameter 'filmbewertung' darf nicht NULL sein.");
		if (filmbewertung.getFilmId() == null)
			throw new IllegalArgumentException("Parameter 'filmbewertung.filmId' darf nicht NULL sein.");
		
		logger.info("saveOrUpdate Filmbewertung for filmId " + filmbewertung.getFilmId());
		try {
			openSession();
			
			// Neue ID assignen
			if (filmbewertung.getId() == null)
				filmbewertung.setId(UUID.randomUUID().toString());
			
			getSession().saveOrUpdate(filmbewertung);
		} catch (HibernateException e) {
			logger.error("saveOrUpdate Filmbewertung failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
	public void delete(Filmbewertung filmbewertung) {
		logger.info("delete Filmbewertung " + filmbewertung.getId());
		try {
			openSession();
			getSession().delete(filmbewertung);
		} catch (HibernateException e) {
			logger.error("delete Filmbewertung failed", e);
			throw e;
		} finally {
			closeSession();
		}
	}
	
}
