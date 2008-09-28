package stefanoltmann.ameisensimu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bau {

	private int positionX = Welt.GROESSE_X/2;
	private int positionY = Welt.GROESSE_Y/2;
	private int radius = 20;
	
	public Rectangle getRechteckObjekt() {
		return new Rectangle(positionX-radius, positionY-radius, radius*2, radius*2);
	}
	
	public void drawOn(Graphics g) {
	
		Rectangle rechteck = getRechteckObjekt();
		
		g.setColor(Color.BLUE);
		g.fillRect(rechteck.x, rechteck.y, rechteck.width, rechteck.height);
		
	}
	
}
