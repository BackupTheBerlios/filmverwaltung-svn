package tatort;

import main.Karte;

public class Tatort extends Karte {

	private String name;
	
	public Tatort(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((Karte)obj).getName());
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
