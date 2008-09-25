package spieler;

import java.util.ArrayList;
import java.util.List;

public class SpielerFactory {
	
	public static List<Spieler> createSpieler() {
		
		List<Spieler> listSpieler = new ArrayList<Spieler>();
		
		listSpieler.add(new Spieler("Jacky Cola"));
		listSpieler.add(new Spieler("Uff Uff"));
		listSpieler.add(new Spieler("Himsalabim"));
		
		return listSpieler;
	}

}
