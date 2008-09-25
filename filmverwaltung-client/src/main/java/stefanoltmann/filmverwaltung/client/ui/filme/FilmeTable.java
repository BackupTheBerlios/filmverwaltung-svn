package stefanoltmann.filmverwaltung.client.ui.filme;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.springframework.richclient.table.support.AbstractObjectTable;

import stefanoltmann.filmverwaltung.client.app.Einstellungen;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmeTable extends AbstractObjectTable {

	private FilmverwaltungService 	filmverwaltungService;
	private Einstellungen			einstellungen;
	
	public FilmeTable(FilmverwaltungService filmverwaltungService, Einstellungen einstellungen, String[] columnPropertyNames) {
		super("film", columnPropertyNames);
		
		this.filmverwaltungService = filmverwaltungService;
		this.einstellungen = einstellungen;
	}

	@Override
	protected Object[] getDefaultInitialData() {
		return filmverwaltungService.findAllFilme().toArray();
	}
	
	public List<Film> getSelectedFilme() {
		int[] selektierteIDs = getTable().getSelectedRows();
		List<Film> filme = new ArrayList<Film>();
		for (int i = 0; i < selektierteIDs.length; i++) {
			filme.add( (Film)getTableModel().getElementAt(selektierteIDs[i]) );
		}
		return filme;
	}
	
	public Film getSelectedFilm() {
		return getSelectedFilme().get(0);
	}

	@Override
	protected void configureTable(JTable table) {
	
		assert einstellungen != null;
		if (einstellungen.isGrafischePunkteZeigen() && einstellungen.isShowFilmeTablePunkteColumn())
			table.getColumnModel().getColumn(1).setCellRenderer(new PunkteCellRenderer());
		
	}
	
	private class PunkteCellRenderer implements TableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			int punkte = (Integer)value;
			
			JProgressBar progressBar = new JProgressBar(0, 10);
			progressBar.setValue(punkte);
			progressBar.setStringPainted(true);
			progressBar.setString(Integer.toString(punkte));

			// Balken einfärben
			if (punkte <= 1)
				progressBar.setForeground(new Color(230, 70, 70)); // rot
			else if (punkte <= 5)
				progressBar.setForeground(new Color(230, 160, 30)); // orange
			else if (punkte <= 8)
				progressBar.setForeground(new Color(220, 230, 24)); // gelb
			else if (punkte <= 10)
				progressBar.setForeground(new Color(110, 200, 110)); // grün
			
			return progressBar;		
			
		}
		
	}

}
