package tatwaffe;

import java.util.ArrayList;
import java.util.List;

public class TatwaffenFactory {
	
	public static List<Tatwaffe> createTatwaffen() {
		
		List<Tatwaffe> listTatwaffen = new ArrayList<Tatwaffe>();
		
		listTatwaffen.add(new Tatwaffe("Rail Gun"));
		listTatwaffen.add(new Tatwaffe("Kryptonit"));
		listTatwaffen.add(new Tatwaffe("Gamma Strahlen"));
		listTatwaffen.add(new Tatwaffe("S�ure"));
		listTatwaffen.add(new Tatwaffe("Zahnb�rste"));
		listTatwaffen.add(new Tatwaffe("Akkustikkoppler"));
		
		return listTatwaffen;
	}
	
}
