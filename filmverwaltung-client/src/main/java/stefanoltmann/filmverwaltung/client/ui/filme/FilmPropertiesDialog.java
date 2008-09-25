package stefanoltmann.filmverwaltung.client.ui.filme;

import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmPropertiesDialog extends TitledPageApplicationDialog {

	private Form form;
	
	private FilmverwaltungService filmverwaltungService;
	
	private boolean neuerEintrag = false;
	
	public FilmPropertiesDialog(FilmverwaltungService filmverwaltungService) {
		this(filmverwaltungService, null);
	}
	
	public FilmPropertiesDialog(FilmverwaltungService filmverwaltungService, Film film) {
		
		if (film == null) {
			neuerEintrag = true;
			film = new Film();
			film.setBewertung(new Filmbewertung());
		}
		
		form = new FilmForm(filmverwaltungService, film);
		setDialogPage(new FormBackedDialogPage(form));
		
		this.filmverwaltungService = filmverwaltungService;		
	}
	
	public Film getFilm() {
		return (Film)form.getFormModel().getFormObject();
	}
	
	@Override
	protected void onAboutToShow() {
		if (neuerEintrag)
			setTitle(getMessage("filmProperties.new.title"));
		else
			setTitle(getMessage("filmProperties.edit.title", new Object[]{ getFilm().getName() }));
		setImage(getImageSource().getImage("filmProperties.image"));
	}

	@Override
	protected boolean onFinish() {

		if (form.getFormModel().isDirty()) {
			
			// commit any buffered edits to the model
			form.getFormModel().commit();

			filmverwaltungService.saveFilm(getFilm());
			
			if (neuerEintrag)
				getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.CREATED, getFilm()));
			else
				getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.MODIFIED, getFilm()));
			
		}
				
		return true;
	}

	@Override
	protected void onCancel() {
	
		if (form.getFormModel().isDirty()) {
			String message = getMessage("filmProperties.dirtyCancelMessage");
			String title = getMessage("filmProperties.dirtyCancelTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
					FilmPropertiesDialog.super.onCancel();
				}
			};
			dlg.showDialog();
		}
		else
			super.onCancel();
	}

}
