package stefanoltmann.filmverwaltung.client.ui.personen;

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
import stefanoltmann.filmverwaltung.dataaccess.Person;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class PersonenView extends AbstractView implements ApplicationListener {

	private PersonenTable 			personenTable;
	private FilmverwaltungService 	filmverwaltungService;
	
	private                ActionCommand newPersonCommand			= (NewPersonCommand)Application.instance().getActiveWindow().getCommandManager().getCommand("newPersonCommand");
	private GuardedActionCommandExecutor propertiesCommandExecutor 	= new PropertiesCommandExecutor();
	private GuardedActionCommandExecutor deleteCommandExecutor 		= new DeleteCommandExecutor();
	
	// Das Textfeld, nach dessen Inhalt gefiltert wird
	private JTextField filterField;

	public void setFilmverwaltungService(FilmverwaltungService filmverwaltungService) {
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected JComponent createControl() {
		JPanel filterPanel = new JPanel(new BorderLayout());
		JLabel filterLabel = getComponentFactory().createLabel("personNameFilter.label");
		filterPanel.add(filterLabel, BorderLayout.WEST);

		String tip = getMessage("personNameFilter.caption");
		filterField = getComponentFactory().createTextField();
		filterField.setToolTipText(tip);
		filterPanel.add(filterField, BorderLayout.CENTER);
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		personenTable = new PersonTableFactory().erstellePersonTable();

		JPanel view = new JPanel(new BorderLayout());
		JScrollPane sp = getComponentFactory().createScrollPane(personenTable.getControl());
		view.add(filterPanel, BorderLayout.NORTH);
		view.add(sp, BorderLayout.CENTER);
		return view;
	}
	
	protected void registerLocalCommandExecutors(PageComponentContext context) {
		context.register("newCommand", newPersonCommand);
		context.register(GlobalCommandIds.PROPERTIES, propertiesCommandExecutor);
		context.register(GlobalCommandIds.DELETE, deleteCommandExecutor);
	}
	
	private class PersonTableFactory {
		@SuppressWarnings("unchecked")
		public PersonenTable erstellePersonTable() {
			
			personenTable = new PersonenTable(filmverwaltungService);
			
			// Festlegen, was beim Doppelklick ausgeführt werden soll
			personenTable.setDoubleClickHandler(propertiesCommandExecutor);

			// Dieses wird zum Filtern der Tabelle über ein Textfeld benötigt
			EventList baseEventList = personenTable.getBaseEventList();
			TextFilterator textFilterator = GlazedLists.textFilterator(new String[] { "name" });
			FilterList gefilterteListe = new FilterList(baseEventList, new TextComponentMatcherEditor(filterField, textFilterator));
			personenTable.setFinalEventList(gefilterteListe);
			
			// Context-Menü konfigurieren
			CommandGroup popup = new CommandGroup();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("deleteCommand", ActionCommand.class));
			popup.addSeparator();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("propertiesCommand", ActionCommand.class));
			personenTable.setPopupCommandGroup(popup);

			// Änderungen an der Statusbar weiterreichen
			personenTable.setStatusBar(getStatusBar());

			// Executoren sind nur aktiv, wenn etwas in der Tabelle selektiert wurde
			ValueModel selectionHolder = new ListSelectionValueModelAdapter(personenTable.getSelectionModel());
			new ListSingleSelectionGuard(selectionHolder, deleteCommandExecutor);
			new ListSingleSelectionGuard(selectionHolder, propertiesCommandExecutor);
			
			return personenTable;
		}
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event.getSource() instanceof Person)
			personenTable.onApplicationEvent(event);
	}
	
	/*
	 * ActionCommandExecutors
	 */
	
	private class PropertiesCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			new PersonPropertiesDialog(filmverwaltungService, personenTable.getSelectedPerson()).showDialog();			
		}
	}

	private class DeleteCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			
			final Person person = personenTable.getSelectedPerson();
			
			String message = getMessage("personenView.deleteMessage");
			String title = getMessage("personenView.deleteTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
			
					filmverwaltungService.deletePerson(person);
					
					// Löschen publizieren
					getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.DELETED, person));
				}
			};
			dlg.showDialog();
			
		}
	}

}
