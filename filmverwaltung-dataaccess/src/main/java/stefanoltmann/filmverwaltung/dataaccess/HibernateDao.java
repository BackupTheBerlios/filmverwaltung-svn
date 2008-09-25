package stefanoltmann.filmverwaltung.dataaccess;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class HibernateDao {

	private SessionFactory 	sessionFactory;
	private Session 		session;
	private Transaction 	transaction;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected void openSession() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	protected void closeSession() {
		try {
			if (transaction != null)
				transaction.commit();
		} catch (HibernateException exception) {
			transaction.rollback();
			throw exception;
		} finally {
			session.close();
		}
	}
	
	public Session getSession() {
		return session;
	}

}