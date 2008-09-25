package stefanoltmann.filmverwaltung.client.ui.personen;

import java.util.ArrayList;
import java.util.List;

import org.springframework.richclient.table.support.AbstractObjectTable;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class PersonenTable extends AbstractObjectTable {

	private FilmverwaltungService filmverwaltungService;
	
	public PersonenTable(FilmverwaltungService filmverwaltungService) {
		super("person", new String[]{ "name" });
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected Object[] getDefaultInitialData() {
		return filmverwaltungService.findAllPersonen().toArray();
	}
	
	public List<Person> getSelectedPersonen() {
		int[] selektierteIDs = getTable().getSelectedRows();
		List<Person> personen = new ArrayList<Person>();
		for (int i = 0; i < selektierteIDs.length; i++) {
			personen.add( (Person)getTableModel().getElementAt(selektierteIDs[i]) );
		}
		return personen;
	}
	
	public Person getSelectedPerson() {
		return getSelectedPersonen().get(0);
	}

}
