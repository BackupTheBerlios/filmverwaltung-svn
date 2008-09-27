package stefanoltmann.filmverwaltung.client.ui.filme;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.richclient.application.ViewDescriptor;
import org.springframework.richclient.application.event.LifecycleApplicationEvent;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.GuardedActionCommandExecutor;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.ConfirmationDialog;
import org.springframework.richclient.list.ListMultipleSelectionGuard;
import org.springframework.richclient.list.ListSelectionValueModelAdapter;
import org.springframework.richclient.list.ListSingleSelectionGuard;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;
import stefanoltmann.filmverwaltung.client.ofdbgwconnector.OFDBExportCommand;
import stefanoltmann.filmverwaltung.client.ofdbgwconnector.OFDBGWConnectorCommand;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmeView extends AbstractView implements ApplicationListener {
	
	private FilmeTable 				filmeTable;
	
	private                ActionCommand newFilmCommand						= (NewFilmCommand)Application.instance().getActiveWindow().getCommandManager().getCommand("newFilmCommand");;
	private GuardedActionCommandExecutor propertiesCommandExecutor 			= new PropertiesCommandExecutor();
	private GuardedActionCommandExecutor deleteCommandExecutor 				= new DeleteCommandExecutor();
	private GuardedActionCommandExecutor exportCommandExecutor				= new ExportCommandExecutor();
	private          MyOFDBExportCommand myOfdbExportCommand				= new MyOFDBExportCommand();
	private	    MyOfdbgwConnectorCommand myOfdbgwConnectorCommand;
	private GuardedActionCommandExecutor myExportPunkteVerteilungsDiagrammCommand = new MyExportPunkteVerteilungChartCommand();
	
	// Das Textfeld, nach dessen Inhalt gefiltert wird
	private JTextField filterField;
	
	public FilmverwaltungService getFilmverwaltungService() {
		return ((FilmeViewDescriptor)getDescriptor()).getFilmverwaltungService();
	}
	
	public Einstellungen getEinstellungen() {
		return ((FilmeViewDescriptor)getDescriptor()).getEinstellungen();
	}
	
	public FilmeView(ViewDescriptor viewDescriptor) {
		setDescriptor(viewDescriptor);
		this.myOfdbgwConnectorCommand = new MyOfdbgwConnectorCommand( getFilmverwaltungService() );
	}

	@Override
	protected JComponent createControl() {
		JPanel filterPanel = new JPanel(new BorderLayout());
		JLabel filterLabel = getComponentFactory().createLabel("filmNameFilter.label");
		filterPanel.add(filterLabel, BorderLayout.WEST);

		String tip = getMessage("filmNameFilter.caption");
		filterField = getComponentFactory().createTextField();
		filterField.setToolTipText(tip);
		filterPanel.add(filterField, BorderLayout.CENTER);
		filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		filmeTable = new FilmTableFactory().erstelleFilmTable();

		JPanel view = new JPanel(new BorderLayout());
		JScrollPane sp = new JScrollPane(filmeTable.getControl());
		view.add(filterPanel, BorderLayout.NORTH);
		view.add(sp, BorderLayout.CENTER);
		return view;
	}
	
	protected void registerLocalCommandExecutors(PageComponentContext context) {
		context.register("newCommand", newFilmCommand);
		context.register(GlobalCommandIds.PROPERTIES, propertiesCommandExecutor);
		context.register(GlobalCommandIds.DELETE, deleteCommandExecutor);
		context.register("exportFilmeCommand", exportCommandExecutor);
		context.register("ofdbExportCommand", myOfdbExportCommand);
		context.register("syncCommand", myOfdbgwConnectorCommand);
		context.register("exportPunkteVerteilungsDiagrammCommand", myExportPunkteVerteilungsDiagrammCommand);
	}
	
	private class FilmTableFactory {
		@SuppressWarnings("unchecked")
		public FilmeTable erstelleFilmTable() {
			
			filmeTable = new FilmeTable(getFilmverwaltungService(), getEinstellungen(), getColumnPropertyNames());
			
			// Festlegen, was beim Doppelklick ausgeführt werden soll
			filmeTable.setDoubleClickHandler(propertiesCommandExecutor);

			// Dieses wird zum Filtern der Tabelle über ein Textfeld benötigt
			EventList baseEventList = filmeTable.getBaseEventList();
			TextFilterator textFilterator = GlazedLists.textFilterator(new String[] { "name" });
			FilterList gefilterteListe = new FilterList(baseEventList, new TextComponentMatcherEditor(filterField, textFilterator));
			filmeTable.setFinalEventList(gefilterteListe);
			
			// Context-Menü konfigurieren
			CommandGroup popup = new CommandGroup();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("deleteCommand", ActionCommand.class));
			popup.addSeparator();
			popup.add((ActionCommand) getWindowCommandManager().getCommand("propertiesCommand", ActionCommand.class));
			filmeTable.setPopupCommandGroup(popup);

			// Änderungen an der Statusbar weiterreichen
			filmeTable.setStatusBar(getStatusBar());

			// Executoren sind nur aktiv, wenn etwas in der Tabelle selektiert wurde
			ValueModel selectionHolder = new ListSelectionValueModelAdapter(filmeTable.getSelectionModel());
			new ListSingleSelectionGuard(selectionHolder, deleteCommandExecutor);
			new ListSingleSelectionGuard(selectionHolder, propertiesCommandExecutor);
			new ListMultipleSelectionGuard(selectionHolder, exportCommandExecutor);
			new ListMultipleSelectionGuard(selectionHolder, myOfdbExportCommand);
			new ListMultipleSelectionGuard(selectionHolder, myOfdbgwConnectorCommand);
			new ListMultipleSelectionGuard(selectionHolder, myExportPunkteVerteilungsDiagrammCommand);
			
			return filmeTable;
		}
	}
	
	private String[] getColumnPropertyNames() {
		
		List<String> listColumnPropertyNames = new ArrayList<String>();
		
		listColumnPropertyNames.add("name");
		
		if (getEinstellungen().isShowFilmeTablePunkteColumn())
			listColumnPropertyNames.add("bewertung.punkte");

		if (getEinstellungen().isShowFilmeTableImBesitzColumn())
			listColumnPropertyNames.add("bewertung.imBesitz");
		
		if (getEinstellungen().isShowFilmeTableErscheinungsJahrColumn())
			listColumnPropertyNames.add("erscheinungsJahr");
		
		String[] columnPropertyNames = new String[listColumnPropertyNames.size()];
		for (int i = 0; i < listColumnPropertyNames.size(); i++)
			columnPropertyNames[i] = listColumnPropertyNames.get(i);
		
		return columnPropertyNames;
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (event.getSource() instanceof Film)
			filmeTable.onApplicationEvent(event);
	}
	
	/*
	 * ActionCommandExecutors
	 */
	
	private class PropertiesCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			new FilmPropertiesDialog(getFilmverwaltungService(), filmeTable.getSelectedFilm()).showDialog();			
		}
	}

	private class DeleteCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			
			final Film film = filmeTable.getSelectedFilm();
			
			String message = getMessage("filmeView.deleteMessage");
			String title = getMessage("filmeView.deleteTitle");
			ConfirmationDialog dlg = new ConfirmationDialog(title, message) {
				protected void onConfirm() {
			
					getFilmverwaltungService().deleteFilm(film);
					
					// Löschen publizieren
					getApplicationContext().publishEvent(new LifecycleApplicationEvent(LifecycleApplicationEvent.DELETED, film));
				}
			};
			dlg.showDialog();
			
		}
	}
	
	private class ExportCommandExecutor extends AbstractActionCommandExecutor {
		public void execute() {
			new ExportFilmDialog(filmeTable.getSelectedFilme()).showDialog();
		}
	}
	
	private class MyOfdbgwConnectorCommand extends OFDBGWConnectorCommand {

		public MyOfdbgwConnectorCommand(FilmverwaltungService filmverwaltungService) {
			super(filmverwaltungService);
		}

		@Override
		protected void doExecuteCommand() {
			setFilme(filmeTable.getSelectedFilme());
			super.doExecuteCommand();
		}
		
	}
	
	private class MyOFDBExportCommand extends OFDBExportCommand {
		
		@Override
		protected void doExecuteCommand() {
			setFilme(filmeTable.getSelectedFilme());
			
			super.doExecuteCommand();
		}
		
	}
	
	private class MyExportPunkteVerteilungChartCommand extends ExportPunkteVerteilungsDiagrammCommand {
		
		@Override
		protected void doExecuteCommand() {
			setFilme(filmeTable.getSelectedFilme());
			
			super.doExecuteCommand();
		}
		
	}

}
