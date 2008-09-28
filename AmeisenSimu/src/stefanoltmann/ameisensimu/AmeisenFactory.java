package stefanoltmann.ameisensimu;

public class AmeisenFactory {

	public static Ameise createAmeise() {
		
		Richtung richtung = Richtung.NORDEN;
		
		int random = (int)(Math.random()*8)+1;
		
		switch(random) {
			case 1: richtung = Richtung.NORDEN;
				break;
			case 2:	richtung = Richtung.NORDOST;
				break;
			case 3: richtung = Richtung.OSTEN;
				break;
			case 4: richtung = Richtung.SUEDOST;
				break;
			case 5: richtung = Richtung.SUEDEN;
				break;
			case 6: richtung = Richtung.SUEDWEST;
				break;
			case 7: richtung = Richtung.WESTEN;
				break;
			case 8: richtung = Richtung.NORDWEST;
				break;
		}
		
		Ameise ameise = new Ameise(250, 250, richtung);
		
		return ameise; 
	}
	
}
