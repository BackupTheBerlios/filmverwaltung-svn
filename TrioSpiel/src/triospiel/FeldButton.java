package triospiel;
import java.awt.Color;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class FeldButton extends JButton {

	private boolean aktiviert = false;
	
	private int positionX;
	private int positionY;
	
	public FeldButton(int positionX, int positionY, String text) {
		super(text);
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public void aktivieren() {
		aktiviert = true;
		setBackground(Color.RED);
	}
	
	public void deaktivieren() {
		aktiviert = false;
		setBackground(null);
	}
	
	public boolean isAkiviert() {
		return aktiviert;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

}
