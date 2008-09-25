package stefanoltmann.filmverwaltung.client.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.ApplicationLauncher;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class Main {

	private static final Log logger = LogFactory.getLog(Main.class);

	public static void main(String[] args) {
		
		logger.info("Programm startet...");
		
		/*
		 * Prüfen, ob eine Datenbank vorhanden ist.
		 */
		
		copyDatabase();
		
		/*
		 * Anwendung starten
		 */
		
		String richclientStartupContextPath 	= "/stefanoltmann/filmverwaltung/client/ctx/richclient-startup-context.xml";
		String richclientApplicationContextPath = "/stefanoltmann/filmverwaltung/client/ctx/richclient-application-context.xml";
		
		try {
			new ApplicationLauncher(richclientStartupContextPath, new String[] { richclientApplicationContextPath });
		} catch (Exception e) {
			
//			if (e.getCause() instanceof JDBCConnectionException) {
//				JDBCConnectionException conex = (JDBCConnectionException)e.getCause();
//				if (conex.getMessage().equals("Cannot open connection")) {
//					JOptionPane.showMessageDialog(null, "Die Anwendung kann nur einmal gleichzeitig laufen.", "Nur eine Instanz möglich!", JOptionPane.ERROR_MESSAGE);
//					System.exit(1);
//				}
//			}
			
			logger.error("Fehler während des Startvorgangs", e);
			JOptionPane.showMessageDialog(null, "Leider ist ein Fehler aufgetreten.\nDetails:\n" + e.getMessage());
			System.exit(1);
		}
	}
	
	private static void copyDatabase() {
		
		if (new File("db/filmverwaltung.script").exists())
			return;
		
		logger.info("Datenbank nicht gefunden! Kopiere von Template...");

		new File("db").mkdirs();
		
		File src = new File("src/main/resources/template/filmverwaltung.script");
		
		if (!src.exists())
			src = new File("template/filmverwaltung.script");
		
		if (!src.exists()) {
			logger.error("Template nicht gefunden! Bereche Programmausführung ab.");
			System.exit(1);
		}
		
		File dest = new File("db/filmverwaltung.script");
		
	    byte[] buffer = new byte[5000];
	    int read = 0;
	    InputStream in = null;
	    OutputStream out = null;
	    
	    try {
	        in = new FileInputStream(src);
	        out = new FileOutputStream(dest);
	        while(true) {
	            read = in.read(buffer);
	            if (read == -1)
	                break;
	            out.write(buffer, 0, read);
	        }
	    } catch (Exception e) {
	    	logger.error("Datenbank kopieren fehlgeschlagen." , e);
	    }
	}

}
