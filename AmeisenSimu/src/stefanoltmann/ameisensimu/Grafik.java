package stefanoltmann.ameisensimu;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Grafik extends Canvas {

	private Welt welt;
	
	public Grafik(Welt welt) {
		this.welt = welt;
		
		setBounds(0,0,Welt.GROESSE_X, Welt.GROESSE_Y);
		setBackground(Color.YELLOW);
	}
	
	@Override
	public void paint(Graphics g) {
	
		try {
			
			welt.getBau().drawOn(g);
			
			for (Ameise ameise : welt.getAmeisen())
				ameise.drawOn(g);
			
			for (Zucker zucker : welt.getZuckerHaufen())
				zucker.drawOn(g);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
}
