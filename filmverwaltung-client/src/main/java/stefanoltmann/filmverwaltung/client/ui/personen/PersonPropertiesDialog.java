package stefanoltmann.filmverwaltung.client.ui.personen;

import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class PersonPropertiesDialog extends TitledPageApplicationDialog {

	private Form form;
	
	private FilmverwaltungService filmverwaltungService;
	
	private boolean neuerEintrag = false;
	
	public PersonPropertiesDialog(FilmverwaltungService filmverwaltungService) {
		this(filmverwaltungService, null);
	}
	
	public PersonPropertiesDialog(FilmverwaltungService filmverwaltungService, Person person) {
		
		if (person == null) {
			neuerEintrag = true;
			person = new Person();
		} else
			person = filmverwaltungService.findPerson(person.getId());
		
		form = new PersonForm(filmverwaltungService, person);
		setDialogPage(new FormBackedDialogPage(form));
		
		this.filmverwaltungService = filmverwaltungService;		
	}
	
	public Person getPerson() {
		return (Person)form.getFormModel().getFormObject();
	}
	
	@Override
	protected void onAboutToShow() {
		if (neuerEintrag)
			setTitle(getMessage("personProperties.new.title"));
		else
			setTitle(getMessage("personProperties.edit.title", new Object[]{ getPerson().getName() }));
		setImage(getImageSource().getImage("genreProperties.image"));
	}

	@Override
	protected boolean onFinish() {

		// commit any buffered edits to the model
		form.getFormModel().commit();
		
		filmverwaltungService.savePerson(getPerson());
		
		if (neuerEintrag)
			getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.CREATED, getPerson()));
		else
			getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.MODIFIED, getPerson()));
		
		return true;
	
	}

	@Override
	protected void onCancel() {
	
		if (form.getFormModel().isDirty()) {
			String message = getMessage("personProperties.dirtyCancelMessage");
			String title = getMessage("personProperties.dirtyCancelTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
					PersonPropertiesDialog.super.onCancel();
				}
			};
			dlg.showDialog();
		}
		else
			super.onCancel();
	}

}
