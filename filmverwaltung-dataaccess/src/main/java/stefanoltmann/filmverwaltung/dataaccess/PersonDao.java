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
public class PersonDao extends HibernateDao {
	
	private static final Log logger = LogFactory.getLog(PersonDao.class);
	
	@SuppressWarnings("unchecked")
	public List<Person> findAll() {

		logger.info("findAll Person");
		
		List<Person> persons = new ArrayList<Person>();

		try {
			openSession();
			persons = (List<Person>) getSession().createQuery("FROM Person").list();
		} catch (HibernateException e) {
			logger.error("findAll Person failed", e);
		} finally {
			closeSession();
		}

		// Alphabetisch sortiere
		Collections.sort(persons, new Comparator<Person>() {
			public int compare(Person o1, Person o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		return persons;
	}
	
	public Person find(int id) {
		logger.info("find Person by id " + id);
		try {
			openSession();
			Person person = (Person)getSession().get(Person.class, id);
//			Hibernate.initialize(person.getFilme());
			return person;
		} catch (HibernateException e) {
			logger.error("find Person failed", e);
			return null;
		} finally {
			closeSession();
		}
	}
	
	public Person find(String name) {
		logger.info("find Person by name " + name);
		try {
			openSession();
			return (Person)getSession().createQuery("FROM Person AS person WHERE person.name=:name")
										.setParameter("name", name)
										.uniqueResult();
		} catch (HibernateException e) {
			logger.error("find Person failed", e);
			return null;
		} finally {
			closeSession();
		}
	}
	
	public void delete(Person person) {
		logger.info("delete Person " + person.getName());
		try {
			openSession();
			getSession().delete(person);
		} catch (HibernateException e) {
			logger.error("delete Person failed", e);
		} finally {
			closeSession();
		}
	}
	
	public void changeID(Person person, String neueId) {
		
		if (person == null)
			throw new IllegalArgumentException("Parameter 'person' darf nicht NULL sein.");
		if (person.getName().length() == 0)
			throw new IllegalArgumentException("Parameter 'person' darf nicht leer sein.");
		
		if (neueId == null)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht NULL sein.");
		if (neueId.length() == 0)
			throw new IllegalArgumentException("Parameter 'neueId' darf nicht leer sein.");
		
		logger.info("change ID for Person " + person.getName());
		try {
		
			openSession();

			getSession().createQuery("UPDATE Person as person SET id = :neueId WHERE name = :name")
						.setParameter("neueId", neueId)
						.setParameter("name", person.getName())
						.executeUpdate();
			
		} catch (HibernateException e) {
			logger.error("change ID for Person " + person.getId() + " failed", e);
			throw e;
		} finally {
			closeSession();
		}
		
	}
	
	public void saveOrUpdate(Person person) {
		
		if (person == null)
			throw new IllegalArgumentException("Parameter 'person' must not be null.");
		if (person.getName().length() == 0)
			throw new IllegalArgumentException("Parameter 'person' must not have zero size.");
		
		logger.info("saveOrUpdate Person " + person.getName());
		try {
			openSession();
			
			if (person.getId() == null)
				person.setId(UUID.randomUUID().toString());
			
			getSession().saveOrUpdate(person);
		} catch (HibernateException e) {
			logger.error("saveOrUpdate Person failed", e);
		} finally {
			closeSession();
		}
	}
	
}
