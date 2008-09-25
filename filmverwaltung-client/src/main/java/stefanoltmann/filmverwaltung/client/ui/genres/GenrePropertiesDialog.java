package stefanoltmann.filmverwaltung.client.ui.genres;

import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class GenrePropertiesDialog extends TitledPageApplicationDialog {

	private Form form;
	
	private FilmverwaltungService filmverwaltungService;
	
	private boolean neuerEintrag = false;
	
	public GenrePropertiesDialog(FilmverwaltungService filmverwaltungService) {
		this(filmverwaltungService, null);
	}
	
	public GenrePropertiesDialog(FilmverwaltungService filmverwaltungService, Genre genre) {
		
		if (genre == null) {
			neuerEintrag = true;
			genre = new Genre();
		} else
			genre = filmverwaltungService.findGenre(genre.getId());
		
		form = new GenreForm(filmverwaltungService, genre);
		setDialogPage(new FormBackedDialogPage(form));
		
		this.filmverwaltungService = filmverwaltungService;		
	}
	
	public Genre getGenre() {
		return (Genre)form.getFormModel().getFormObject();
	}
	
	@Override
	protected void onAboutToShow() {
		if (neuerEintrag)
			setTitle(getMessage("genreProperties.new.title"));
		else
			setTitle(getMessage("genreProperties.edit.title", new Object[]{ getGenre().getName() }));
		setImage(getImageSource().getImage("genreProperties.image"));
	}

	@Override
	protected boolean onFinish() {

		// commit any buffered edits to the model
		form.getFormModel().commit();
		
		filmverwaltungService.saveGenre(getGenre());
		
		if (neuerEintrag)
			getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.CREATED, getGenre()));
		else
			getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.MODIFIED, getGenre()));
		
		return true;
	
	}

	@Override
	protected void onCancel() {
	
		if (form.getFormModel().isDirty()) {
			String message = getMessage("genreProperties.dirtyCancelMessage");
			String title = getMessage("genreProperties.dirtyCancelTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
					GenrePropertiesDialog.super.onCancel();
				}
			};
			dlg.showDialog();
		}
		else
			super.onCancel();
	}

}
