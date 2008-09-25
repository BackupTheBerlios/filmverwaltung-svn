package stefanoltmann.filmverwaltung.client.ofdbgwconnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class OFDBGWConnector {

	private static final Log logger = LogFactory.getLog(OFDBGWConnector.class);
	
	@SuppressWarnings("unchecked")
	public static int receiveOfdbId(String filmName) throws MalformedURLException, DocumentException {

		if (filmName == null || filmName.length() == 0)
			return 0;

		URL url = new URL( "http", "xml.n4rf.net" , 80 , "/ofdbgw/search/" + filmName);

		SAXReader reader = new SAXReader();
		Document document = reader.read(url);

		List list = document.selectNodes( "//resultat/eintrag" );

		if (list.size() == 0)
			return 0; // Kein Eintrag gefunden

		// Nimm den ersten Treffer
		Node node = (Node)list.get(0);

		int id = Integer.parseInt(node.valueOf( "id" ));

		logger.info("Determined ofdbId " + id + " for " + filmName);

		return id;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Genre> receiveGenres(int ofdbId) throws MalformedURLException, DocumentException {

		List<Genre> genres = new ArrayList<Genre>();

		URL url = new URL( "http", "xml.n4rf.net" , 80 , "/ofdbgw/movie/" + ofdbId);

		SAXReader reader = new SAXReader();
		Document document = reader.read(url);

		List list = document.selectNodes( "//resultat/genre/titel" );

		for (Object node : list)
			genres.add( new Genre( ((Node)node).getText() ) );

		return genres;
	}

	@SuppressWarnings("unchecked")
	public static List<Person> receiveCast(int ofdbId) throws MalformedURLException, DocumentException {

		List<Person> personen = new ArrayList<Person>();

		URL url = new URL( "http", "xml.n4rf.net" , 80 , "/ofdbgw/movie/" + ofdbId);

		SAXReader reader = new SAXReader();
		Document document = reader.read(url);

		List list = document.selectNodes( "//resultat/besetzung/person" );

		for (Object node : list)
			personen.add( new Person( ((Node)node).getText() ) );

		return personen;
	}

	@SuppressWarnings("unchecked")
	public static Film receiveFilmInformation(int ofdbId) throws MalformedURLException, DocumentException {
		
		Film film = new Film();

		URL url = new URL("http", "xml.n4rf.net", 80, "/ofdbgw/movie/" + ofdbId);

		SAXReader reader = new SAXReader();
		Document document = reader.read(url);

		List list = document.selectNodes("//resultat");

		// Vom Resultat gibt es nur eine Node
		Node node = (Node)list.get(0);

		film.setOfdbId(				ofdbId									);
		film.setOfdbName(			node.valueOf("titel")					);
		film.setOfdbBeschreibung( 	node.valueOf("beschreibung")			);
		film.setErscheinungsJahr(	Integer.parseInt(node.valueOf("jahr"))	);

		// Coverbild downloaden

		try {
			new File("cover").mkdirs();
			
			if (!new File("cover/" + ofdbId + ".jpg").exists()) { // nur wenn das bild noch nich da ist.
				URL bildUrl = new URL("http", "img.ofdb.de", 80, node.valueOf("bild").substring(18));
				InputStream in = bildUrl.openConnection().getInputStream();
				copyStreamIntoFile(in, "cover/" + ofdbId + ".jpg");
			}
		} catch (IOException e) {
			// Wegen nem Cover machen wir uns nicht weiter Stress
			logger.error("Download von Cover-Bild für OFDB ID " + ofdbId + " fehlgeschlagen.",e);
		}

		return film;
	}
	
	private static void copyStreamIntoFile(InputStream src, String filename) throws IOException {
		
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		
		byte[] buffer = new byte[16384];
		int len;
		while ((len = src.read(buffer)) > 0) {
			fileOutputStream.write(buffer, 0, len);
		}
		src.close();
		fileOutputStream.close();
	}

}
