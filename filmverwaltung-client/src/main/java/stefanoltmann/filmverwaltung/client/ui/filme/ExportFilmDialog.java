package stefanoltmann.filmverwaltung.client.ui.filme;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.PictureData;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.springframework.richclient.dialog.ApplicationDialog;

import stefanoltmann.filmverwaltung.client.ui.DiagrammArt;
import stefanoltmann.filmverwaltung.client.ui.ExportFormat;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Genre;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
@SuppressWarnings("unused")
public class ExportFilmDialog extends ApplicationDialog {
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
	
	private List<Film> filme;
	
	protected ExportFilmDialog(List<Film> filme) {
		this.filme = filme;
		setTitle("Exportierte Filme");
		setPreferredSize(new Dimension(500, 30)); // reicht für ein JLabel
	}

	@Override
	protected JComponent createDialogContentPane() {
		
		// Filme sortieren
		Collections.sort(filme, new FilmNameComparator());
		
		ExportFormat exportFormat = (ExportFormat)JOptionPane.showInputDialog(
				null, 								// parent
				"Wählen sie ein Export-Format aus", // message
				"Export-Format wählen",				// title 
				JOptionPane.QUESTION_MESSAGE,		// frage-art
				null,								// icon
				ExportFormat.values(),				// values
				ExportFormat.POWERPOINT				// initialValue
				);		

		if (exportFormat == ExportFormat.TEXT) {
			setPreferredSize(new Dimension(700, 500));
			return erstelleText(filme);	
		}
		
		if (exportFormat == ExportFormat.POWERPOINT) {
			String returnString = erstellePowerPoint(filme);
			return new JLabel(returnString);
		}
		
		return null;
	}

	@Override
	protected boolean onFinish() {
		return true;
	}
	
	private JScrollPane erstelleText(List<Film> filme) {
		
		JTextPane textPane = new JTextPane();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("### Filme ###");

		sb.append(LINE_SEPARATOR);
		sb.append(LINE_SEPARATOR);
		
		for (Film film : filme) {
			
			sb.append("# ");
			sb.append(film.getName());
			sb.append(" (");
			sb.append(film.getErscheinungsJahr());
			sb.append(")");
			sb.append(LINE_SEPARATOR);
			sb.append(LINE_SEPARATOR);

			sb.append("Punkte       : [");
			for (int i = 1; i <= 10; i++)
				if (i <= film.getBewertung().getPunkte())
					sb.append("+");
				else
					sb.append(" ");
			sb.append("] (");
			sb.append(film.getBewertung().getPunkte());
			sb.append(")");
			sb.append(LINE_SEPARATOR);
			
			sb.append("Kommentar    : ");
			sb.append(film.getBewertung().getKommentar());
			sb.append(LINE_SEPARATOR);
			
			sb.append("Im Besitz    : ");
			if (film.getBewertung().getImBesitz())
				sb.append("[x]");
			else
				sb.append("[ ]");
			sb.append(LINE_SEPARATOR);
			
			sb.append("Genre        : ");
			for (Genre genre : film.getGenres()) {
				sb.append(genre.getName());
				sb.append(" ");
			}
			sb.append(LINE_SEPARATOR);
			
			if (film.getBewertung().getBeschreibung().length() != 0) {
				sb.append("Beschreibung : ");
				sb.append(film.getBewertung().getBeschreibung());
				sb.append(LINE_SEPARATOR);	
			}
			
			sb.append("Eintragsdatum: ");
			sb.append(df.format(film.getBewertung().getEintragsDatum()));
			sb.append(LINE_SEPARATOR);

			sb.append("OFDB ID      : ");
			sb.append(film.getOfdbId());
			sb.append(LINE_SEPARATOR);

			sb.append(LINE_SEPARATOR);
		}
		
		sb.append("Liste erstellt am ");
		sb.append(df.format(new Date())); 
		
		textPane.setFont( new Font( "Monospaced", Font.PLAIN, 13 ));
		textPane.setText(sb.toString());
		
		return new JScrollPane(textPane);
		
	}
	
	private String erstellePowerPoint(List<Film> filme) {
		
		try {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new PowerPointFileFilter());
			int returnVal = fileChooser.showOpenDialog(null);
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return "Abgebrochen!";
			
			// Korrigiere Name
			// FIXME Hier muss es auch einen schöneren Weg geben
			String fileName = fileChooser.getSelectedFile().getAbsolutePath();
			if (!fileName.endsWith(".ppt"))
				fileName = fileName + ".ppt";
			
			SlideShow ppt = new SlideShow();
			
			for (Film film : filme) {
			
				Slide folie = ppt.createSlide();
				
				// Titel
				TextBox txtName = new TextBox();
				txtName.setText(film.getName());
				txtName.setAnchor(new Rectangle(10, 10, 700, 60));

				RichTextRun txtNameRT = txtName.getTextRun().getRichTextRuns()[0];
				txtNameRT.setFontSize(32);
				txtNameRT.setBold(true);
				txtNameRT.setUnderlined(true);

				folie.addShape(txtName);
				
				// Cover-Bild
				if (new File("cover/" + film.getOfdbId() + ".jpg").exists()) {
					int coverIndex = ppt.addPicture(new File("cover/" + film.getOfdbId() + ".jpg"), Picture.JPEG);
					Picture cover = new Picture(coverIndex);
					cover.setAnchor(new Rectangle(20, 70, 120, 170));
					folie.addShape(cover);
				}

				addLabeledText(folie, 1, "OFDB Name", film.getOfdbName());
				if (film.getOfdbId() != null)
					addLabeledText(folie, 2, "OFDB ID", film.getOfdbId().toString());
				if (film.getErscheinungsJahr() != null)
					addLabeledText(folie, 3, "Erscheinungsjahr", film.getErscheinungsJahr().toString());
				addLabeledText(folie, 4, "Eintragsdatum", df.format(film.getBewertung().getEintragsDatum()));
				addLabeledText(folie, 5, "Punkte", film.getBewertung().getPunkte().toString());
				
				// Im Besitz Indikator
				if (film.getBewertung().getImBesitz()) {
					TextBox txtImBesitz = new TextBox();
					txtImBesitz.setText("IM BESITZ");
					txtImBesitz.setAnchor(new Rectangle(5, 240, 180, 30));
					folie.addShape(txtImBesitz);
				}
				
				addLabeledTextArea(folie, 1, "Beschreibung", film.getBewertung().getBeschreibung());
				addLabeledTextArea(folie, 2, "Kommentar", film.getBewertung().getKommentar());

			}

			FileOutputStream out = new FileOutputStream(fileName);
			ppt.write(out);
			out.close();

			return "PowerPoint Datei " + fileName + " erstelt.";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Fehler aufgetreten.";
	}
	
	private class FilmNameComparator implements Comparator<Film> {

		public int compare(Film o1, Film o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
		
	}
	
	private class PowerPointFileFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			if (f.getName().endsWith(".ppt"))
				return true;
			else
				return false;
		}

		@Override
		public String getDescription() {
			return "PowerPoint Datei";
		}
		
	}
	
	public void addLabeledText(Slide folie, int position, String label, String value) {
		
		// Zeilenumbrüche reseten die RichTextRun-Eigenschaften
		value = value.replace("\n", " ");
		
		// TextBoxen
		
		TextBox txtLabel = new TextBox();
		txtLabel.setText(label);
		txtLabel.setAnchor(new Rectangle(150, 30 + (position * 40), 200, 30));
		
		TextBox txtValue = new TextBox();
		txtValue.setText(value);
		txtValue.setAnchor(new Rectangle(360, 30 + (position * 40), 400, 30));

		// RichTextRun
		
		RichTextRun txtLabelRT = txtLabel.getTextRun().getRichTextRuns()[0];
		txtLabelRT.setFontSize(20);
		txtLabelRT.setUnderlined(true);
		txtLabelRT.setAlignment(TextBox.AlignRight);
		
		RichTextRun txtValueRT = txtValue.getTextRun().getRichTextRuns()[0];
		txtValueRT.setFontSize(20);

		// Folien hinzufügen
		
		folie.addShape(txtLabel);
		folie.addShape(txtValue);
	
	}
	
	public void addLabeledTextArea(Slide folie, int position, String label, String value) {
		
		// Zeilenumbrüche reseten die RichTextRun-Eigenschaften
		value = value.replace("\n", " ");
		
		// TextBoxen
		
		TextBox txtLabel = new TextBox();
		txtLabel.setText(label);
		txtLabel.setAnchor(new Rectangle(10, 150 + (position * 150), 750, 30));
		
		TextBox txtValue = new TextBox();
		txtValue.setText(value);
		txtValue.setAnchor(new Rectangle(10, 170 + (position * 150), 750, 130));

		// RichTextRun
		
		RichTextRun txtLabelRT = txtLabel.getTextRun().getRichTextRuns()[0];
		txtLabelRT.setFontSize(15);
		txtLabelRT.setUnderlined(true);
		
		RichTextRun txtValueRT = txtValue.getTextRun().getRichTextRuns()[0];
		txtValueRT.setFontSize(15);

		// Folien hinzufügen
		
		folie.addShape(txtLabel);
		folie.addShape(txtValue);
	
	}

}
