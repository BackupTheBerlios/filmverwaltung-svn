package stefanoltmann.filmverwaltung.client.ofdbgwconnector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

import org.springframework.richclient.command.ActionCommand;

import stefanoltmann.filmverwaltung.dataaccess.Film;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class OFDBExportCommand extends ActionCommand {

	private Thread thread;

	private List<Film> filme = new ArrayList<Film>();
	
	public List<Film> getFilme() {
		return filme;
	}

	public void setFilme(List<Film> filme) {
		if (filme == null)
			throw new IllegalArgumentException("Paramter 'filme' must not be null.");
		this.filme = filme;
	}

	@Override
	protected void doExecuteCommand() {
		export();
	}
	
	public void export() {

		if (thread == null || !thread.isAlive()) {

			thread = new Thread (new Runnable() {
				public void run() {

					String blablabla = "Sie sind im Begriff ihre Filmliste an OFDB.de zu exportieren.\n" +
										"OFDB.de setzt zur Authentifizierung im Browser ein Cookie namens 'ofdb_user'.\n" +
										"Loggen Sie sich auf der Seite ein und gucken Sie, welchen Wert dieses " +
										"Cookie gesetzt hat. Pasten Sie den Wert in dieses Fenster.";

					String ofdbUser = JOptionPane.showInputDialog(blablabla);

					if (ofdbUser != null && ofdbUser.length() > 5) {

						try {

							// TODO: Statt ProgressMonitor den in der Statusleiste von Spring Rich verwenden
							ProgressMonitor pm = new ProgressMonitor(null, "Exportiere Filme nach OFDB ...", "", 0, filme.size());
							pm.setMillisToDecideToPopup(10);
							pm.setMillisToPopup(10);
							pm.setNote("Frage Datenbank ab ...");

							int counter = 0;

							for (Film film : filme) {

								if (film.getOfdbName() == null || film.getOfdbId() == null)
									continue;

								pm.setNote(film.getName());

								String ofdbName = film.getOfdbName();
								String ofdbId   = film.getOfdbId().toString();

								String wunsch;
								if (film.getBewertung().getImBesitz())
									wunsch = "0";
								else
									wunsch = "1";

								ofdbName = ofdbName.replaceAll(" ", "%20");

								logger.info("Exporting to OFDB.de: " + ofdbId + ", " + ofdbName);
								new URL("http", "www.ofdb.de", 80, "/_mf_neu.php?ofdb_user=" + ofdbUser + "&Fassung=0&Wo=Z&Menge=1&Wunsch=" + wunsch + "&vid=0&VTitel=" + ofdbName + "&fid=" + ofdbId).openConnection().getInputStream();

								// Wenn der Nutzer nicht mehr will, is auch Schluss...
								if (pm.isCanceled())
									break;

								counter = counter + 1;
								pm.setProgress(counter);
							}

							pm.close();

						} catch (UnknownHostException e) {
							logger.error("UnknownHostException", e);
							JOptionPane.showMessageDialog(null, "Der OFDB Server kann zum Exportieren der Filmliste nicht kontaktiert werden.\n Besteht eine Internetverbindung?", "Keine Verbindung", JOptionPane.ERROR_MESSAGE);
						} catch (SocketException e) {
							logger.error("SocketException", e);
							JOptionPane.showMessageDialog(null, "Der OFDB Server kann zum Exportieren der Filmliste nicht kontaktiert werden.\n Besteht eine Internetverbindung?", "Keine Verbindung", JOptionPane.ERROR_MESSAGE);
						} catch (MalformedURLException e) {
							logger.error("MalformedURLException", e);
							JOptionPane.showMessageDialog(null, "Beim Exportieren an die OFDB Filmliste ist ein schwerer Fehler aufgetreten.");
						} catch (IOException e) {
							logger.error("IOException", e);
							JOptionPane.showMessageDialog(null, "Beim Exportieren an die OFDB Filmliste ist ein schwerer Fehler aufgetreten.");
						}

					} else
						JOptionPane.showMessageDialog(null, "Passwort zu kurz. Exportvorgang abgebrochen.");

				}
			});
			thread.start ();

		} else
			System.err.println("Thread läuft noch...");

	}
	
}
