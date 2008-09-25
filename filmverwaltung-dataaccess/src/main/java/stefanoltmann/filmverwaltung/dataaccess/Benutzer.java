package stefanoltmann.filmverwaltung.dataaccess;

import java.util.Date;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class Benutzer {
	
	private String id = null; // hibernate
	
	private String anmeldename;
	private String passwort;
	private Date anmeldeDatum = new Date();
	private Date letzterZugriff = new Date();
	private Integer programmVersion;

	public Benutzer() {}
	
	public Benutzer(String anmeldename, String passwort) {
		this.anmeldename = anmeldename;
		this.passwort = passwort;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnmeldename() {
		return anmeldename;
	}
	
	public void setAnmeldename(String anmeldename) {
		this.anmeldename = anmeldename;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public Date getAnmeldeDatum() {
		return anmeldeDatum;
	}

	public void setAnmeldeDatum(Date anmeldeDatum) {
		this.anmeldeDatum = anmeldeDatum;
	}

	public Date getLetzterZugriff() {
		return letzterZugriff;
	}

	public void setLetzterZugriff(Date letzterZugriff) {
		this.letzterZugriff = letzterZugriff;
	}

	public Integer getProgrammVersion() {
		return programmVersion;
	}

	public void setProgrammVersion(Integer programmVersion) {
		this.programmVersion = programmVersion;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Benutzer) {
			String andereId = ((Benutzer)obj).id;
			if (andereId != null)
				if (this.id.equals(andereId))
					return true;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "<" + id + ">" + anmeldename;
	}
	
	public boolean isValid() {
		
		if (anmeldename == null || passwort == null || anmeldename.equals("") || passwort.equals(""))
			return false;
		else
			return true;
		
	}
	
}
