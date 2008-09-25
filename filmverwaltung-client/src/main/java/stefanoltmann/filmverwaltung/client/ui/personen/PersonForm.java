package stefanoltmann.filmverwaltung.client.ui.personen;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.springframework.binding.value.support.ListListModel;
import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

import stefanoltmann.filmverwaltung.client.ui.filme.FilmPropertiesDialog;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class PersonForm extends AbstractForm {

	private JComponent nameFeld;
	private FilmverwaltungService filmverwaltungService;
	
	public PersonForm(FilmverwaltungService filmverwaltungService, Person formObject) {
		super(formObject);
		setId("person");
		
		this.filmverwaltungService = filmverwaltungService;
	}
	
	public Person getPerson() {
		return (Person)getFormObject();
	}
	
	@Override
	protected JComponent createFormControl() {
		
		SwingBindingFactory bindingFactory = (SwingBindingFactory) getBindingFactory();
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
		
		formBuilder.addSeparator("Persondaten");
		formBuilder.row();
		
		nameFeld = formBuilder.add("name")[0];
		formBuilder.row();
		
		// JList mit allen Filmen dieses Schauspielers anzeigen
		final JList jlist = new JList(new ListListModel( getPerson().getFilme() ));
		jlist.setMinimumSize(new Dimension(200, 200));
		JScrollPane sp = new JScrollPane(jlist);
		formBuilder.getLayoutBuilder().cell(sp);
		
		jlist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Film film = (Film) jlist.getSelectedValue();
					if (film != null)
						new FilmPropertiesDialog(filmverwaltungService, film).showDialog();
				}
			}
		});
		
		return formBuilder.getForm();
	}
	
	// Focus auf das Name-Feld legen
	public boolean requestFocusInWindow() {
		return nameFeld.requestFocusInWindow();
	}
	
}
