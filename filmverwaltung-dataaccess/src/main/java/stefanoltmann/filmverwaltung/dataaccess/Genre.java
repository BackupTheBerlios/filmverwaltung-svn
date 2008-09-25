package stefanoltmann.filmverwaltung.dataaccess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class Genre {

	private String id = null; // hibernate
	
	private String name = "";
	private List<Film> filme = new ArrayList<Film>();
	
	public Genre() {}
	
	public Genre(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilme() {
		return filme;
	}

	public void setFilme(List<Film> filme) {
		this.filme = filme;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Genre) {
			String andereId = ((Genre)obj).id;
			if (andereId != null)
				if (this.id.equals(andereId))
					return true;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
