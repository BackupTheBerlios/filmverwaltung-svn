package stefanoltmann.ameisensimu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Zucker {

	private int positionX = Welt.GROESSE_X/4;
	private int positionY = Welt.GROESSE_Y/4;
	private int radius = 20;
	
	private int menge = 1000;
	
	public Rectangle getRechteckObjekt() {
		return new Rectangle(positionX-radius, positionY-radius, radius*2, radius*2);
	}
	
	public int getMenge() {
		return menge;
	}

	public void drawOn(Graphics g) {
	
		Rectangle rechteck = getRechteckObjekt();
		
		g.setColor(Color.WHITE);
		g.drawRect(rechteck.x, rechteck.y, rechteck.width, rechteck.height);
		
	}
	
}
