package stefanoltmann.ameisensimu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ameise {
	
	private static int instanzenZahler = 0;
	
	private int ID;
	private int positionX = 100;
	private int positionY = 100;
	private int radius = 5;
	private Richtung richtung = Richtung.NORDEN;
	private int geschwindigkeit = 1;
//	private Image bild;
	
	public Ameise(int x, int y, Richtung richtung) {
		this.positionX = x;
		this.positionY = y;
		this.richtung = richtung;
		
		instanzenZahler += 1;
		ID = instanzenZahler;
	}
	
	public void richtungsWechsel() {
//		System.out.println("Ameise " + ID + " wechselt die Richtung.");
		switch (richtung) {
			case NORDEN: richtung = Richtung.SUEDEN;
				break;
			case NORDOST: richtung = Richtung.SUEDWEST;
				break;
			case OSTEN: richtung = Richtung.WESTEN;
				break;
			case SUEDOST: richtung = Richtung.NORDWEST;
				break;
			case SUEDEN: richtung = Richtung.NORDEN;
				break;
			case SUEDWEST: richtung = Richtung.NORDOST;
				break;
			case WESTEN: richtung = Richtung.OSTEN;
				break;
			case NORDWEST: richtung = Richtung.SUEDOST;
				break;
		}
	}
	
	public void bewegung() {
		
		if (richtung == Richtung.NORDEN) {
			positionY += geschwindigkeit;
		}
		
		if (richtung == Richtung.NORDOST) {
			positionY += geschwindigkeit;
			positionX += geschwindigkeit;
		}
		
		if (richtung == Richtung.OSTEN) {
			positionX += geschwindigkeit;
		}
		
		if (richtung == Richtung.SUEDOST) {
			positionY -= geschwindigkeit;
			positionX += geschwindigkeit;
		}
		
		if (richtung == Richtung.SUEDEN) {
			positionY -= geschwindigkeit;
		}
		
		if (richtung == Richtung.SUEDWEST) {
			positionY -= geschwindigkeit;
			positionX -= geschwindigkeit;
		}
		
		if (richtung == Richtung.WESTEN) {
			positionX -= geschwindigkeit;
		}
		
		if (richtung == Richtung.NORDWEST) {
			positionY += geschwindigkeit;
			positionX -= geschwindigkeit;
		}
		
		if (   (positionX <= 0 || positionX+radius*2 >= Welt.GROESSE_X)
			|| (positionY <= 0 || positionY+radius*2 >= Welt.GROESSE_Y))
				richtungsWechsel();
		
	}
	
	public int getID() {
		return ID;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public Richtung getRichtung() {
		return richtung;
	}

	public void setRichtung(Richtung richtung) {
		this.richtung = richtung;
	}
	
	public int getGeschwindigkeit() {
		return geschwindigkeit;
	}

	public int getRadius() {
		return radius;
	}
	
	public Rectangle getRechteckObjekt() {
		return new Rectangle(positionX-radius, positionY-radius, radius*2, radius*2);
	}
	
	public void drawOn(Graphics g) {
	
		Rectangle rechteck = getRechteckObjekt();
		
		g.setColor(Color.BLACK);
		g.fillRect(rechteck.x, rechteck.y, rechteck.width, rechteck.height);
		
	}
	
	public void angekommenBei(Bau bau) {
		System.out.println("Ameise ID " + ID + " angekommen bei Bau");
	}
	
//	public Image getBild() {
//		try {
//			if (bild == null)
//				bild = ImageIO.read(new File("src/ameise.png"));
//			return bild;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

}
