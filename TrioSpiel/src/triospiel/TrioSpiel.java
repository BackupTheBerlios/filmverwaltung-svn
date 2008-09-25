package triospiel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TrioSpiel extends JFrame {

	public final int FELD_GROESSE = 9;

	private ButtonController buttonController = new ButtonController();
	
	private List<Formel> formeln = FormelFactory.erstelleFormeln();
	
	int zuFindeneZahl = -1;

	public TrioSpiel() {
		super("Trio Spiel");
		
		// Zu findene Zahl bestimmen
		zuFindeneZahl = (int)Math.round(Math.random()*50);
		
		JPanel rootPane = new JPanel(new BorderLayout());
		
		JLabel label = new JLabel("Die gesuchte Zahl ist: " + zuFindeneZahl);
		
		JPanel panelFeld = new JPanel(new GridLayout(FELD_GROESSE, FELD_GROESSE));
		
		rootPane.add(label, BorderLayout.NORTH);
		rootPane.add(panelFeld, BorderLayout.CENTER);
		
		int[][] arrayDing = erstelleFeld();
		
		for (int i = 0; i <= FELD_GROESSE-1; i++)
			for (int j = 0; j <= FELD_GROESSE-1; j++) {
				FeldButton feldButton = new FeldButton(i, j, String.valueOf(arrayDing[i][j]));
				feldButton.addActionListener(buttonController);
				panelFeld.add(feldButton);
			}
		
		this.setContentPane(rootPane);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	private int[][] erstelleFeld() {
		
		int[][] arrayDing = new int[FELD_GROESSE][FELD_GROESSE];
		
		int letzteZufallsZahl = -1;
		
		Map<Integer, Integer> zahlenCounter = new HashMap<Integer, Integer>();
		for (int i = 1; i <= FELD_GROESSE; i++)
			zahlenCounter.put(i, 0);
		
		for (int i = 0; i <= FELD_GROESSE-1; i++) {
			for (int j = 0; j <= FELD_GROESSE-1; j++) {
				
				while (true) {
				
					int zufallsZahl = (int)Math.round(Math.random()*FELD_GROESSE);
					
					if (zufallsZahl == 0)
						continue;
					
					int anzahlZufallszahl = zahlenCounter.get(zufallsZahl);
					
					if (anzahlZufallszahl == FELD_GROESSE+1)
						continue;
					
					if (zufallsZahl == letzteZufallsZahl)
						continue;
					
					arrayDing[i][j] = zufallsZahl;
					zahlenCounter.remove(zufallsZahl);
					zahlenCounter.put(zufallsZahl, anzahlZufallszahl + 1);
					letzteZufallsZahl = zufallsZahl;
					
					break;
				}

			}	
		}
		
		durchprobieren(arrayDing, zuFindeneZahl);
		
		return arrayDing;
		
	}
	
	private class LoesungenGUI extends JFrame {
		
		private JTextArea textArea;
		
		public LoesungenGUI() {
			setTitle("Trio Lösungen");
			
			textArea = new JTextArea();
			
			JPanel rootPane = new JPanel(new BorderLayout());
			JScrollPane scrollPane = new JScrollPane(textArea);
			rootPane.add(scrollPane, BorderLayout.CENTER);
			
			setContentPane(rootPane);
			
			setSize(600, 300);
			setVisible(true);
		}
		
		public void appendText(String text) {
			textArea.append(text + "\n");
		}
		
	}
	
	private void durchprobieren(int[][] arrayDing, int zuFindeneZahl) {
		
		LoesungenGUI loesungenGUI = new LoesungenGUI();
		
		// Horizontal
		for (int i = 0; i <= FELD_GROESSE-1; i++)
			for (int j = 0; j <= FELD_GROESSE-3; j++) {
				
				int a = arrayDing[i][j];
				int b = arrayDing[i][j+1];
				int c = arrayDing[i][j+2];
				
				Formel formel = berechne(a, b, c, zuFindeneZahl);
				if (formel != null) {
					loesungenGUI.appendText("Formel für horizontal " + i + "," + j + " -> " + formel.showExample(a, b, c) + " -> Formel: " + formel.showFormel());
				}
			}
		
		// Vertikal
		for (int i = 0; i <= FELD_GROESSE-3; i++)
			for (int j = 0; j <= FELD_GROESSE-1; j++) {
				
				int a = arrayDing[i][j];
				int b = arrayDing[i+1][j];
				int c = arrayDing[i+2][j];
				
				Formel formel = berechne(a, b, c, zuFindeneZahl);
				if (formel != null) {
					loesungenGUI.appendText("Formel für vertikal " + i + "," + j + " -> " + formel.showExample(a, b, c) + " -> Formel: " + formel.showFormel());
				}
			}
		
		// Diagonal rechts
		for (int i = 0; i <= FELD_GROESSE-3; i++)
			for (int j = 0; j <= FELD_GROESSE-3; j++) {
				
				int a = arrayDing[i][j];
				int b = arrayDing[i+1][j+1];
				int c = arrayDing[i+2][j+2];
				
				Formel formel = berechne(a, b, c, zuFindeneZahl);
				if (formel != null) {
					loesungenGUI.appendText("Formel für diagonal links " + i + "," + j + " -> " + formel.showExample(a, b, c) + " -> Formel: " + formel.showFormel());
				}
			}
				
		// TODO: Diagonal links

	}
	
	/**
	 * Gibt die Formel zurück, mit welcher die Berechnung erfolgreich war
	 * oder NULL, wenn gar keine Formel gepasst hat.
	 */
	private Formel berechne(int a, int b, int c, int ergebnis) {
		
		for (Formel formel : formeln) {
			
			if (formel.berechne(a, b, c) == ergebnis) {
				return formel;				
			}
			
		}
		
		return null;
	}
	
	private class ButtonController implements ActionListener {

		private List<FeldButton> aktivierteButtons = new ArrayList<FeldButton>();
		
		public void actionPerformed(ActionEvent e) {
			
			FeldButton feldButton = (FeldButton)e.getSource();
			
			if (feldButton.isAkiviert()) {
				
				feldButton.deaktivieren();
				aktivierteButtons.remove(feldButton);
				
			} else if (aktivierteButtons.size() < 3) {
				
				feldButton.aktivieren();
				aktivierteButtons.add(feldButton);
				
			}

			if (aktivierteButtons.size() == 3) {
				
				int a = Integer.valueOf(aktivierteButtons.get(0).getText());
				int b = Integer.valueOf(aktivierteButtons.get(1).getText());
				int c = Integer.valueOf(aktivierteButtons.get(2).getText());
				
				Formel formel = berechne(a, b, c, zuFindeneZahl);
				
				if (formel == null)
					JOptionPane.showMessageDialog(TrioSpiel.this, "Auswahl unsinnig.");
				else
					JOptionPane.showMessageDialog(TrioSpiel.this, "Korrekt.\nFormel: " + formel.showFormel() + "\nRechnung: " + formel.showExample(a, b, c));
			
			}
			
			
			
		}

	}
	
	public static void main(String[] args) {
		
		new TrioSpiel();
		
	}
	
	
}
