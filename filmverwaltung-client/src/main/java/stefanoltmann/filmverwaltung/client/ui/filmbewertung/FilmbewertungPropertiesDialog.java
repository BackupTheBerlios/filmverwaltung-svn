package stefanoltmann.filmverwaltung.client.ui.filmbewertung;

import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmbewertungPropertiesDialog extends TitledPageApplicationDialog {

	private Form form;
	
	private boolean neuerEintrag = false;
	
	private FilmverwaltungService filmverwaltungService;
	
	public FilmbewertungPropertiesDialog(FilmverwaltungService filmverwaltungService) {
		this(filmverwaltungService, null);
	}
	
	public FilmbewertungPropertiesDialog(FilmverwaltungService filmverwaltungService, Filmbewertung filmbewertung) {
		
		if (filmbewertung == null) {
			neuerEintrag = true;
			filmbewertung = new Filmbewertung();
		}
		
		form = new FilmbewertungForm(filmverwaltungService, filmbewertung);
		setDialogPage(new FormBackedDialogPage(form));
		
		this.filmverwaltungService = filmverwaltungService;		
	}
	
	public Filmbewertung getFilmbewertung() {
		return (Filmbewertung)form.getFormModel().getFormObject();
	}

	@Override
	protected void onAboutToShow() {
		if (neuerEintrag)
			setTitle(getMessage("filmbewertungProperties.new.title"));
		else
			setTitle(getMessage("filmbewertungProperties.edit.title"));
		setImage(getImageSource().getImage("filmProperties.image"));
	}

	@Override
	protected boolean onFinish() {

		if (form.getFormModel().isDirty()) {
			
			// commit any buffered edits to the model
			form.getFormModel().commit();

			filmverwaltungService.saveFilmbewertung(getFilmbewertung());
			
			if (neuerEintrag)
				getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.CREATED, getFilmbewertung()));
			else
				getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.MODIFIED, getFilmbewertung()));
			
		}
				
		return true;
	}

	@Override
	protected void onCancel() {
	
		if (form.getFormModel().isDirty()) {
			String message = getMessage("filmbewertungProperties.dirtyCancelMessage");
			String title = getMessage("filmbewertungProperties.dirtyCancelTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
					FilmbewertungPropertiesDialog.super.onCancel();
				}
			};
			dlg.showDialog();
		}
		else
			super.onCancel();
	}

}
