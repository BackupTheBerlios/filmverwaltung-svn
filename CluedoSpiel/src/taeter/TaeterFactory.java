package taeter;

import java.util.ArrayList;
import java.util.List;

public class TaeterFactory {
	
	public static List<Taeter> createTaeter() {
		
		List<Taeter> listTaeter = new ArrayList<Taeter>();
		
		listTaeter.add(new Taeter("Stefan"));
		listTaeter.add(new Taeter("Gina"));
		listTaeter.add(new Taeter("Astrid"));
		listTaeter.add(new Taeter("Matthias"));
		listTaeter.add(new Taeter("Hannibal"));
		listTaeter.add(new Taeter("Jigsaw"));
		
		return listTaeter;
	}

}
