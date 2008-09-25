package tatort;

import java.util.ArrayList;
import java.util.List;

public class TatortFactory {
	
	public static List<Tatort> createTatorte() {
		
		List<Tatort> listTatorte = new ArrayList<Tatort>();
		
		listTatorte.add(new Tatort("Damenklo"));
		listTatorte.add(new Tatort("Event Horizon"));
		listTatorte.add(new Tatort("Puff"));
		listTatorte.add(new Tatort("Mars"));
		listTatorte.add(new Tatort("Hölle"));
		listTatorte.add(new Tatort("Im Arsch"));
		
		return listTatorte;
	}

}
