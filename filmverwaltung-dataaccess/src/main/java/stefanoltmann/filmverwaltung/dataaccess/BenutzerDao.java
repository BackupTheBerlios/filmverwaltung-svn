package stefanoltmann.filmverwaltung.dataaccess;

import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class BenutzerDao extends HibernateDao {

	private static final Log logger = LogFactory.getLog(BenutzerDao.class);
	
	/**
	 * Wird von der Anwendung aufgerufen um die einzigen Anmeldedaten zu finden
	 */
	@SuppressWarnings("unchecked")
	public Benutzer find() {
		logger.info("find einzigen Benutzer");
		try {
			openSession();
			
			List<Benutzer> benutzer = (List<Benutzer>)getSession().createQuery("FROM Benutzer").list();
			if (benutzer.size() > 0)
				return benutzer.get(0);
			else
				return null;
		} catch (HibernateException e) {
			logger.error("find Benutzer failed", e);
			return null;
		} finally {
			closeSession();
		}
	}
	
	public Benutzer find(String anmeldename) {
		logger.info("find Benutzer by anmeldename " + anmeldename);
		try {
			openSession();
			return (Benutzer)getSession().createQuery("FROM Benutzer AS Benutzer WHERE Benutzer.anmeldename=:anmeldename")
										.setParameter("anmeldename", anmeldename)
										.uniqueResult();
		} catch (HibernateException e) {
			logger.error("find Benutzer failed", e);
			return null;
		} finally {
			closeSession();
		}
	}
	
	public void saveOrUpdate(Benutzer benutzer) {
		
		if (benutzer == null)
			throw new IllegalArgumentException("Parameter 'benutzer' must not be null.");
		if (benutzer.getAnmeldename().length() == 0)
			throw new IllegalArgumentException("Parameter 'benutzer.anmeldename' must not have zero size.");
		if (benutzer.getPasswort().length() == 0)
			throw new IllegalArgumentException("Parameter 'benutzer.passwort' must not have zero size.");
		
		logger.info("saveOrUpdate Benutzer " + benutzer.getAnmeldename());
		try {
			openSession();
			
			// Neue ID assignen
			if (benutzer.getId() == null)
				benutzer.setId(UUID.randomUUID().toString());
			
			getSession().saveOrUpdate(benutzer);
		} catch (HibernateException e) {
			logger.error("saveOrUpdate Benutzer failed", e);
		} finally {
			closeSession();
		}
	}
	
}
