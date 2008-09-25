package stefanoltmann.filmverwaltung.dataaccess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class Film {

	private String id = null; // hibernate
	
	private Integer ofdbId; // OFDB.de Film ID
	
	private String 			name				= "";
	private String			ofdbName			= "";
	private List<Genre> 	genres				= new ArrayList<Genre>();
	private List<Person>	cast				= new ArrayList<Person>();
	private String			ofdbBeschreibung	= "";
	private Integer			erscheinungsJahr	= 0;
	
	private Benutzer		uploader;
	
	private Filmbewertung	bewertung;
	
	/*
	 * Konstruktor
	 */
	
	public Film() {}
	
	public Film(String name) {
		this.name = name;
	}
	
	/*
	 * Getter + Setter
	 */

	public Filmbewertung getBewertung() {
		return bewertung;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBewertung(Filmbewertung bewertung) {
		this.bewertung = bewertung;
	}
	
	public Integer getOfdbId() {
		return ofdbId;
	}

	public void setOfdbId(Integer ofdbId) {
		this.ofdbId = ofdbId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getErscheinungsJahr() {
		return erscheinungsJahr;
	}

	public void setErscheinungsJahr(Integer erscheinungsJahr) {
		this.erscheinungsJahr = erscheinungsJahr;
	}

	public String getOfdbBeschreibung() {
		return ofdbBeschreibung;
	}

	public void setOfdbBeschreibung(String ofdbBeschreibung) {
		this.ofdbBeschreibung = ofdbBeschreibung;
	}

	public List<Person> getCast() {
		return cast;
	}

	public void setCast(List<Person> cast) {
		this.cast = cast;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public String getOfdbName() {
		return ofdbName;
	}

	public void setOfdbName(String ofdbName) {
		this.ofdbName = ofdbName;
	}

	public Benutzer getUploader() {
		return uploader;
	}

	public void setUploader(Benutzer uploader) {
		this.uploader = uploader;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Film) {
			String andereId = ((Film)obj).id;
			if (andereId != null)
				if (this.id.equals(andereId))
					return true;
		}
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		if (getName() != null && getName().length() > 0)
			return getName();
		if (getOfdbName() != null && getOfdbName().length() > 0)
			return getOfdbName();
		if (getId() != null && getId().length() == 0)
			return getId();
		return super.toString();
	}
	
}
