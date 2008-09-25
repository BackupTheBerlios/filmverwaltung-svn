package spieler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Karte;

public class Spieler {
	
	private String name;
	
	private Spieler linkerNachbar;
	
	// Die eigenen Karten
	private List<Karte> karten = new ArrayList<Karte>();
	
	// Hält fest, bei welchem Spieler welche Karten gesehen wurden
	private Map<Spieler, List<Karte>> geseheneKarten = new HashMap<Spieler, List<Karte>>();
	
	private boolean hatVerloren = false;
	
	public Spieler(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addKarte(Karte karte) {
		karten.add(karte);
	}
	
	/**
	 * Gibt zurück ob diese Karte schon einmal gesehen wurde.
	 * Dies kann bei einem Mitspieler gewesen sein oder in den
	 * eigenen Beständen.
	 */
	public boolean hatKarteGesehen(Karte karte) {
		
		// Hat der Spieler sie selber?
		if (karten.contains(karte))
			return true;
		
		for (List<Karte> kartenMitspieler : geseheneKarten.values()) {
			if (kartenMitspieler.contains(karte))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Gucke die Karten eines Mitspielers an.
	 */
	public void kartenSehen(Spieler spieler, List<Karte> verdachtsKarten) {
		
		// Dieser Mitspieler ist uns noch unbkannt
		if (!geseheneKarten.containsKey(spieler)) {
			geseheneKarten.put(spieler, verdachtsKarten);
			return; // erledigt ;D
		}
		
		List<Karte> kartenDesMitspielers = geseheneKarten.get(spieler);
		kartenDesMitspielers.addAll(verdachtsKarten);		
	}
	
	/**
	 * Zeigt die Karten vom "Verdacht", die dieser Spieler hat.
	 */
	public List<Karte> kartenZeigen(List<Karte> verdachtsKarten) {
		
		List<Karte> eigeneKarten = new ArrayList<Karte>();
		
		for (Karte karte : verdachtsKarten) 
			if (karten.contains(karte)) 
				eigeneKarten.add(karte);
		
		return eigeneKarten;
	}
	
	public List<Karte> getKarten() {
		return Collections.unmodifiableList(karten);
	}
	
	public Map<Spieler, List<Karte>> getGeseheneKarten() {
		return Collections.unmodifiableMap(geseheneKarten);
	}

	public Spieler getLinkerNachbar() {
		return linkerNachbar;
	}

	public void setLinkerNachbar(Spieler linkerNachbar) {
		this.linkerNachbar = linkerNachbar;
	}

	public boolean isHatVerloren() {
		return hatVerloren;
	}

	public void setHatVerloren(boolean hatVerloren) {
		this.hatVerloren = hatVerloren;
	}

	@Override
	public String toString() {
		return "'" + getName() + "'";
	}
	
}
