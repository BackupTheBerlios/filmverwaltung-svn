package stefanoltmann.ameisensimu;
import java.util.ArrayList;
import java.util.List;

public class Welt {

	public static final int GROESSE_X = 500;
	public static final int GROESSE_Y = 500;
	
	private Bau bau = new Bau();
	private List<Ameise> ameisen = new ArrayList<Ameise>();
	private List<Zucker> zuckerHaufen = new ArrayList<Zucker>();
	
	public Bau getBau() {
		return bau;
	}
	
	public List<Ameise> getAmeisen() {
		return ameisen;
	}
	
	public List<Zucker> getZuckerHaufen() {
		return zuckerHaufen;
	}
	
	
	
	
	
	
}
