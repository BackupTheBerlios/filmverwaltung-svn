package stefanoltmann.filmverwaltung.client.ui.genres;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.springframework.binding.value.ValueModel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.GuardedActionCommandExecutor;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.list.ListSelectionValueModelAdapter;
import org.springframework.richclient.list.ListSingleSelectionGuard;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class GenresView extends AbstractView implements ApplicationListener {

	private GenresTable 			genresTable;
	private FilmverwaltungService 	filmverwaltungService;
	
	private                ActionCommand newGenreCommand			= (NewGenreCommand)Application.instance().getActiveWindow().getCommandManager().getCommand("newGenreCommand");;
	private GuardedActionCommandExecutor propertiesCommandExecutor 	= new PropertiesCommandExecutor();
	private GuardedActionCommandExecutor deleteCommandExecutor 		= new DeleteCommandExecutor();
	
	// Das Textfeld, nach dessen Inhalt gefiltert wird
	private JTextField filterField;
	
	public void setFilmverwaltungService(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}
	
	public void setNewGenreCommand(ActionCommand newGenreCommand) {
		this.newGenreCommand = newGenreCommand;
	}

	@Override
	protected JComponent createControl() {
		JPanel filterPanel = new JPanel(new BorderLayout());
		JLabel filterLabel = getComponentFactory().createLabel("genreNameFilter.label");
		filterPanel.add(filterLabel, BorderLayout.WEST);

		String tip = getMessage("genreNameFilter.caption");
		filterField = getComponentFactory().createTextField();
		filterField.setToolTipText(tip);
		filterPanel.add(filterField, BorderLayout.CENTER);
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		genresTable = new GenreTableFactory().erstelleGenreTable();

		JPanel view = new JPanel(new BorderLayout());
		JScrollPane sp = getComponentFactory().createScrollPane(genresTable.getControl());
		view.add(filterPanel, BorderLayout.NORTH);
		view.add(sp, BorderLayout.CENTER);
		return view;
	}
	
	protected void registerLocalCommandExecutors(PageComponentContext context) {
		context.register("newCommand", newGenreCommand);
		context.register(GlobalCommandIds.PROPERTIES, propertiesCommandExecutor);
		context.register(GlobalCommandIds.DELETE, deleteCommandExecutor);
	}
	
	private class GenreTableFactory {
		@SuppressWarnings("unchecked")
		public GenresTable erstelleGenreTable() {
			
			genresTable = new GenresTable(filmverwaltungService);
			
			// Festlegen, was beim Doppelklick ausgeführt werden soll
			genresTable.setDoubleClickHandler(propertiesCommandExecutor);

			// Dieses wird zum Filtern der Tabelle über ein Textfeld benötigt
			EventList baseEventList = genresTable.getBaseEventList();
			TextFilterator textFilterator = GlazedLists.textFilterator(new String[] { "name" });
			FilterList gefilterteListe = new FilterList(baseEventList, new TextComponentMatcherEditor(filterField, textFilterator));
			genresTable.setFinalEventList(gefilterteListe);
			
			// Context-Menü konfigurieren
			CommandGroup popup = new CommandGroup();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("deleteCommand", ActionCommand.class));
			popup.addSeparator();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("propertiesCommand", ActionCommand.class));
			genresTable.setPopupCommandGroup(popup);

			// Änderungen an der Statusbar weiterreichen
			genresTable.setStatusBar(getStatusBar());

			// Executoren sind nur aktiv, wenn etwas in der Tabelle selektiert wurde
			ValueModel selectionHolder = new ListSelectionValueModelAdapter(genresTable.getSelectionModel());
			new ListSingleSelectionGuard(selectionHolder, deleteCommandExecutor);
			new ListSingleSelectionGuard(selectionHolder, propertiesCommandExecutor);
			
			return genresTable;
		}
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event.getSource() instanceof Genre)
			genresTable.onApplicationEvent(event);
	}
	
	/*
	 * ActionCommandExecutors
	 */
	
	private class PropertiesCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			new GenrePropertiesDialog(filmverwaltungService, genresTable.getSelectedGenre()).showDialog();			
		}
	}

	private class DeleteCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			
			final Genre genre = genresTable.getSelectedGenre();
			
			String message = getMessage("genresView.deleteMessage");
			String title = getMessage("genresView.deleteTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
			
					filmverwaltungService.deleteGenre(genre);
					
					// Löschen publizieren
					getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.DELETED, genre));
				}
			};
			dlg.showDialog();
			
		}
	}

}
