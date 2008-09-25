package stefanoltmann.filmverwaltung.dataaccess;

import java.util.Date;

public class Filmbewertung {

	private String id = null; // hibernate
	
	private Integer ofdbId; // OFDB.de Film ID
	
	private String			filmId;
	
	private Boolean			imBesitz			= true;
	private Integer			punkte				= 0;
	private String			beschreibung		= "";
	private String			kommentar			= "";
	private Date			eintragsDatum		= new Date();

	private Benutzer		benutzer;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public Integer getOfdbId() {
		return ofdbId;
	}

	public void setOfdbId(Integer ofdbId) {
		this.ofdbId = ofdbId;
	}

	public Boolean getImBesitz() {
		return imBesitz;
	}

	public void setImBesitz(Boolean imBesitz) {
		this.imBesitz = imBesitz;
	}

	public Integer getPunkte() {
		return punkte;
	}

	public void setPunkte(Integer punkte) {
		this.punkte = punkte;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Date getEintragsDatum() {
		return eintragsDatum;
	}

	@SuppressWarnings("unused")
	private void setEintragsDatum(Date eintragsDatum) {
		this.eintragsDatum = eintragsDatum;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Filmbewertung) {
			String andereId = ((Filmbewertung)obj).id;
			if (andereId != null)
				if (this.id.equals(andereId))
					return true;
		}
		return super.equals(obj);
	}

}
