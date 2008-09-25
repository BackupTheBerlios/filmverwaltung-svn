package stefanoltmann.filmverwaltung.client.ui.filme;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.client.ui.DiagrammArt;
import stefanoltmann.filmverwaltung.dataaccess.Film;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class ExportPunkteVerteilungsDiagrammCommand extends ActionCommand {

	private List<Film> filme;
	
	public void setFilme(List<Film> filme) {
		this.filme = filme;
	}

	@Override
	protected void doExecuteCommand() {
		
		// Ohne Filme geht nichts...
		if (filme == null)
			return;
		
		DiagrammArt diagrammArt = (DiagrammArt)JOptionPane.showInputDialog(
				null, 								// parent
				"Wählen sie eine Diagramm-Art aus", // message
				"Diagramm-Art wählen",				// title 
				JOptionPane.QUESTION_MESSAGE,		// frage-art
				null,								// icon
				DiagrammArt.values(),				// values
				DiagrammArt.KREIS					// initialValue
				);
		
		if (diagrammArt != null) {
		
			// Die Map soll nach Punkten sortiert sein
			Map<Integer, Integer> punkteVerteilung = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
				public int compare(Integer o1, Integer o2) {
					
					if (o1 == o2)
						return 0;
					
					if (o1 > o2)
						return 1;
					else
						return -1;
				}
			});
			
			for (Film film : filme) {
				
				int punkte = film.getBewertung().getPunkte();
				
				Integer punkteZaehler = punkteVerteilung.get(punkte);
				
				if (punkteZaehler == null)
					punkteZaehler = 0;
				
				punkteVerteilung.put(punkte, punkteZaehler + 1);
			}
			
			new PunkteVerteilungsDiagramm(diagrammArt, punkteVerteilung).setVisible(true);
		
		}

	}

}
