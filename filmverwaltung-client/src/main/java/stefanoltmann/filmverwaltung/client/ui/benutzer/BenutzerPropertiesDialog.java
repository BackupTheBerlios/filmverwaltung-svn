package stefanoltmann.filmverwaltung.client.ui.benutzer;

import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

import stefanoltmann.filmverwaltung.dataaccess.Benutzer;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.webclient.FilmverwaltungWebclient;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class BenutzerPropertiesDialog extends TitledPageApplicationDialog {

	private Form form;
	
	private FilmverwaltungService 	filmverwaltungService;
	private FilmverwaltungWebclient	filmverwaltungWebclient;
	
	public BenutzerPropertiesDialog(FilmverwaltungService filmverwaltungService, FilmverwaltungWebclient filmverwaltungWebclient) {
		
		Benutzer benutzer = filmverwaltungService.findBenutzer();

		boolean neuerBenutzer = false;
		if (benutzer == null) {
			benutzer = new Benutzer();
			benutzer.setLokal(true);
			neuerBenutzer = true;
		}
		
		form = new BenutzerForm(benutzer, neuerBenutzer);
		setDialogPage(new FormBackedDialogPage(form));
		setTitle(getMessage("benutzerProperties.title"));
		
		this.filmverwaltungService = filmverwaltungService;
		this.filmverwaltungWebclient = filmverwaltungWebclient;
	}
	
	@Override
	protected void onAboutToShow() {
		setTitle(getMessage("benutzerProperties.title"));
//		setImage(getImageSource().getImage("benutzerPropertiesDialog.image"));
	}

	@Override
	protected boolean onFinish() {

		if (form.getFormModel().isDirty()) {
			
			// commit any buffered edits to the model
			form.getFormModel().commit();

			Benutzer benutzer = (Benutzer)form.getFormObject();
			
			// Nutzer existiert online bereits und Daten sind gültig
			if (filmverwaltungWebclient.getBenutzer(benutzer.getAnmeldename(), benutzer.getPasswort()) != null) {
				filmverwaltungService.saveBenutzer(benutzer);
				filmverwaltungService.updateBenutzerWhereNULL(benutzer);
				return true;
			} else {
				
				Benutzer neuerBenutzer = filmverwaltungWebclient.registriereBenutzer(benutzer.getAnmeldename(), benutzer.getPasswort());
				
				if (neuerBenutzer != null) {
					filmverwaltungService.saveBenutzer(neuerBenutzer);
					filmverwaltungService.updateBenutzerWhereNULL(neuerBenutzer);
					return true;
				} else {
					setDescription(getMessage("benutzerProperties.message.error.cannotRegister"));
					return false;
				}
	
			}

		}
				
		return false;
	}

	@Override
	protected void onCancel() {
	
		if (form.getFormModel().isDirty()) {
			
			String message = getMessage("benutzerProperties.dirtyCancelMessage");
			String title = getMessage("benutzerProperties.dirtyCancelTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
					BenutzerPropertiesDialog.super.onCancel();
				}
			};
			dlg.showDialog();
		}
		else
			super.onCancel();
	}

}
