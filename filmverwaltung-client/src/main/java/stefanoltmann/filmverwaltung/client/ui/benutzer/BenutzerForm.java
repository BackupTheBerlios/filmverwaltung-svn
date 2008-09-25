package stefanoltmann.filmverwaltung.client.ui.benutzer;

import javax.swing.JComponent;

import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

import stefanoltmann.filmverwaltung.dataaccess.Benutzer;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class BenutzerForm extends AbstractForm {

	public BenutzerForm(Benutzer formObject, boolean neuerBenutzer) {
		super(formObject);
		setId("benutzer");
		
		if (!neuerBenutzer)
			getFormModel().setEnabled(false);
	}
	
	@Override
	protected JComponent createFormControl() {
		
		SwingBindingFactory bindingFactory = (SwingBindingFactory) getBindingFactory();
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
		
		formBuilder.addSeparator("Benutzerdaten");
		formBuilder.row();
		
		formBuilder.add("anmeldename");
		formBuilder.row();
		
		formBuilder.addPasswordField("passwort");
		formBuilder.row();
		
		return formBuilder.getForm();
	}
	
}
