package stefanoltmann.filmverwaltung.client.domain;

import org.springframework.rules.Rules;
import org.springframework.rules.support.DefaultRulesSource;

import stefanoltmann.filmverwaltung.dataaccess.Film;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class SimpleValidationRulesSource extends DefaultRulesSource {

//	private final Constraint ALPHABETIC_CONSTRAINT = all(new Constraint[] { required(), minLength(2), regexp("[-'.a-zA-Z ]*", "alphabeticConstraint") });
//	private final Constraint POSTLEITZAHL_CONSTRAINT = all(new Constraint[] { required(), minLength(5), maxLength(10), regexp("[0-9]{5}(-[0-9]{4})?", "postleitzahlConstraint") });

	public SimpleValidationRulesSource() {
		super();
		addRules(createFilmRules());
	}

	private Rules createFilmRules() {
		return new Rules(Film.class) {
			protected void initRules() {
				add("name", required());
//				add("genre", required());
//				add("beschreibung", required());
				add("kommentar", required());
			}
		};
	}
	
}
