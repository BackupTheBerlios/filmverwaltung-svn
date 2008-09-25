package stefanoltmann.filmverwaltung.client.app;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class SimpleLifecycleAdvisor extends DefaultApplicationLifecycleAdvisor {

//	private final Log logger = LogFactory.getLog(getClass());

//	public void onPreWindowOpen(ApplicationWindowConfigurer configurer) {
//		super.onPreWindowOpen(configurer);
//		
//		// startup wizards?
//		
//		// configurer.setShowMenuBar(false);
//		// configurer.setShowToolBar(false);
//		// configurer.setInitialSize(new Dimension(640, 480));
//	}

//	public void onCommandsCreated(ApplicationWindow window) {
//		// Die ActionCommands sind fertig geladen. Muss irgendeiner sofort ausgeführt werden? Login Command z.B. ?
//	}

	public boolean onPreWindowClose(ApplicationWindow window) {
		// Wenn man hier "false" gibt, kann man das Fenster nicht schließen
		return true;
	}

}
