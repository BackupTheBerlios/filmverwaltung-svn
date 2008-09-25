package stefanoltmann.filmverwaltung.client.ui.filme;

import java.util.Set;

import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.View;
import org.springframework.richclient.application.support.DefaultViewDescriptor;
import org.springframework.richclient.application.tabbed.TabbedApplicationPage;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmeViewDescriptor extends DefaultViewDescriptor {

	private FilmverwaltungService 	filmverwaltungService;
	private Einstellungen			einstellungen;

	public FilmeViewDescriptor(FilmverwaltungService filmverwaltungService, Einstellungen einstellungen) {
		setId("filmeView");
		this.filmverwaltungService = filmverwaltungService;
		this.einstellungen = einstellungen;
	}
	
	public FilmverwaltungService getFilmverwaltungService() {
		assert einstellungen != null;
		return filmverwaltungService;
	}
	
	public Einstellungen getEinstellungen() {
		assert einstellungen != null;
		return einstellungen;
	}

	@Override
	protected View createView() {
		return new FilmeView(this);
	}
	
	@SuppressWarnings("unchecked")
	public static void filmeViewReOpen() {
		
		ApplicationWindow window = Application.instance().getActiveWindow();
		
		// FIXME Das hier ist wirklich nicht schön. Der View sollte woanders neu geöffnet werden.
		if (window != null) {
			TabbedApplicationPage tabbedApplicationPage = (TabbedApplicationPage) window.getPage();
			
			FilmeView filmeView = null;
			for (PageComponent pageComponent : (Set<PageComponent>)tabbedApplicationPage.getPageComponents()) {
				if (pageComponent.getClass() == FilmeView.class)
					filmeView = (FilmeView)pageComponent;
			}

			if (filmeView != null) {			
				filmeView.close();
				window.getPage().showView("filmeView");
			}
			
		}
		
	}
	
}
