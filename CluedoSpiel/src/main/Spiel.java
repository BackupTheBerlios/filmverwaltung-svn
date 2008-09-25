package main;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import spieler.Spieler;
import spieler.SpielerFactory;
import taeter.Taeter;
import taeter.TaeterFactory;
import tatort.Tatort;
import tatort.TatortFactory;
import tatwaffe.Tatwaffe;
import tatwaffe.TatwaffenFactory;

public class Spiel {

	private Taeter gesuchterTaeter;
	private Tatwaffe gesuchteTatwaffe;
	private Tatort gesuchterTatort;

	private List<Tatort> listTatorte = TatortFactory.createTatorte();
	private List<Taeter> listTaeter = TaeterFactory.createTaeter();
	private List<Tatwaffe> listTatwaffen = TatwaffenFactory.createTatwaffen();
	
	private List<Spieler> listSpieler = SpielerFactory.createSpieler();

	private GUI gui;
	private KartenUebersicht kartenUebersicht;
	
	public Spiel() {

		gui = new GUI();
		kartenUebersicht = new KartenUebersicht();
		
		kartenVerteilen();
		
		gui.appendText("Das Spiel beginnt...");
		
		Thread spielTread = new SpielThread();
		spielTread.start();
		
	}
	
	@SuppressWarnings("serial")
	private class GUI extends JFrame {
		
		private JTextArea textArea;
		
		public GUI() {
			setTitle("Cluedo Spiel");
			
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
	
	@SuppressWarnings("serial")
	private class KartenUebersicht extends JFrame {
		
		private List<KartenUebersichtCheckbox> checkboxRegistry = new ArrayList<KartenUebersichtCheckbox>();
		
		public KartenUebersicht() {
			setTitle("Karten-Übersicht");
			
			JPanel rootPane = new JPanel(new BorderLayout());

			int anzahlAllerItems = 3 + listTaeter.size() + listTatwaffen.size() + listTatorte.size();
			
			JPanel kartenPanel = new JPanel(new GridLayout(anzahlAllerItems, 1 + listSpieler.size()) );
			
			// Täter
			
			kartenPanel.add(new JLabel("[ Täter ]"));
			
			for (Spieler spieler : listSpieler)
				kartenPanel.add(new JLabel("[ " + spieler.getName() + " ]"));
			
			for (Taeter taeter : listTaeter) {
				kartenPanel.add(new JLabel(taeter.getName()));
				for (Spieler spieler : listSpieler) {
					KartenUebersichtCheckbox checkbox = new KartenUebersichtCheckbox(spieler, taeter);
					checkboxRegistry.add(checkbox);
					kartenPanel.add(checkbox);
				}
			}
			
			// Täter
			
			kartenPanel.add(new JLabel("[ Tatwaffe ]"));
			
			for (Spieler spieler : listSpieler)
				kartenPanel.add(new JLabel("[ " + spieler.getName() + " ]"));
			
			for (Tatwaffe tatwaffe : listTatwaffen) {
				kartenPanel.add(new JLabel(tatwaffe.getName()));
				for (Spieler spieler : listSpieler) {
					KartenUebersichtCheckbox checkbox = new KartenUebersichtCheckbox(spieler, tatwaffe);
					checkboxRegistry.add(checkbox);
					kartenPanel.add(checkbox);
				}
			}
			
			// Tatort
			
			kartenPanel.add(new JLabel("[ Tatort ]"));
			
			for (Spieler spieler : listSpieler)
				kartenPanel.add(new JLabel("[ " + spieler.getName() + " ]"));
			
			for (Tatort tatort : listTatorte) {
				kartenPanel.add(new JLabel(tatort.getName()));
				for (Spieler spieler : listSpieler) {
					KartenUebersichtCheckbox checkbox = new KartenUebersichtCheckbox(spieler, tatort);
					checkboxRegistry.add(checkbox);
					kartenPanel.add(checkbox);
				}
			}
			
			JTabbedPane tabbedPane = new JTabbedPane();
			tabbedPane.add(kartenPanel);
			tabbedPane.setTitleAt(0, "Sicht: Jacky Cola");
			rootPane.add(tabbedPane, BorderLayout.CENTER);
			
			setContentPane(rootPane);
			setSize(400, 500);
			setVisible(true); // TODO
		}

		public List<KartenUebersichtCheckbox> getCheckboxRegistry() {
			return checkboxRegistry;
		}
		
	}
	
	/**
	 * Eine Checkbox die weiß, welche karte sie für welchen Spieler anzeigt.
	 */
	@SuppressWarnings({ "serial" })
	private class KartenUebersichtCheckbox extends Checkbox {
		
		private Spieler spieler;
		private Karte karte;
		
		public KartenUebersichtCheckbox(Spieler spieler, Karte karte) {
			super();
			this.spieler = spieler;
			this.karte = karte;
		}

		public Karte getKarte() {
			return karte;
		}

		public Spieler getSpieler() {
			return spieler;
		}
		
	}
	
	private class SpielThread extends Thread {
		
		@Override
		public void run() {
		
			// Spieler 1 fängt an 
			Spieler spieler = listSpieler.get(0);

			int rundenZaehler = 0;
			
			while (true) {
				
				rundenZaehler += 1;
				
				spieler = spieler.getLinkerNachbar();
				gui.appendText(" - - - - - - - - - - - - - - - - - - - - - - - - -");
				gui.appendText(" Runde " + rundenZaehler + " : " + spieler + " ist jetzt an der Reihe.");

				List<Karte> verdachtsKarten = verdaechtigen(spieler);
				
				gui.appendText(spieler + " verdächtigt " + verdachtsKarten);
				
				Spieler nachbar = spieler.getLinkerNachbar();
				
				boolean hatFremdeKartenGesehen = false;
				
				while (nachbar != spieler) {
					
					List<Karte> kartenDesNachbarn = nachbar.kartenZeigen(verdachtsKarten);
					if (kartenDesNachbarn.size() == 0) {
						gui.appendText("Nachbar " + nachbar + " hat keine solchen Karten.");
						nachbar = nachbar.getLinkerNachbar();
					} else {
						gui.appendText("Nachbar " + nachbar + " zeigt Spieler " + spieler + " Karten: " + kartenDesNachbarn);
						spieler.kartenSehen(nachbar, kartenDesNachbarn);

						// Nur die Sicht des ersten Spielers
						if (spieler.equals(listSpieler.get(0))) {
							
							// Checkbox anhaken, wenn was dazukommt
							for (Karte karte : kartenDesNachbarn) 
								for (KartenUebersichtCheckbox kartenUebersichtCheckbox : kartenUebersicht.getCheckboxRegistry())
									if (kartenUebersichtCheckbox.getSpieler().equals(nachbar) && kartenUebersichtCheckbox.getKarte().equals(karte))
										kartenUebersichtCheckbox.setState(true);
						
						}
							
						hatFremdeKartenGesehen = true;
						break;
					}
				
				}
				
				// Wenn seine Verdachtskarten bei den anderen nicht dabei waren, wird das richtig sein.
				if (! hatFremdeKartenGesehen) {
					boolean anklageErfolgreich = anklagen(spieler, verdachtsKarten);
					if (anklageErfolgreich)
						break;
				}
				
				// 3 Sekunden warten
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			
		}
		
	}
	
	private List<Karte> verdaechtigen(Spieler spieler) {
		
		List<Karte> verdachtsKarten = new ArrayList<Karte>();
		
		for (Taeter taeter : listTaeter)
			if (!spieler.hatKarteGesehen(taeter)) {
				verdachtsKarten.add(taeter);
				break;
			}
		
		for (Tatwaffe tatwaffe : listTatwaffen)
			if (!spieler.hatKarteGesehen(tatwaffe)) {
				verdachtsKarten.add(tatwaffe);
				break;
			}
		
		for (Tatort tatort : listTatorte)
			if (!spieler.hatKarteGesehen(tatort)) {
				verdachtsKarten.add(tatort);
				break;
			}	
		
		return verdachtsKarten;			
	}
	
	private boolean anklagen(Spieler spieler, List<Karte> verdachtsKarten) {
		
		// Für ihn ist es bereits vorbei.
		if (spieler.isHatVerloren())
			return false;

		gui.appendText(spieler + " erhebt Anklage gegen: " + verdachtsKarten);

		if (		verdachtsKarten.size() == 3 
				&& 	verdachtsKarten.contains(gesuchterTaeter)
				&& 	verdachtsKarten.contains(gesuchterTatort)
				&&  verdachtsKarten.contains(gesuchteTatwaffe) ) {

			gui.appendText(spieler + " lag richtig! Er hat das Spiel gewonnen.");

			return true;

		} else {

			gui.appendText(spieler + " hat sich geirrt.");
			spieler.setHatVerloren(true);

			return false;

		}
		
	}
	
	private void kartenVerteilen() {
		
		// Karten zum verteilen
		List<Tatort> listVerteilKartenTatorte = TatortFactory.createTatorte();
		List<Taeter> listVerteilKartenTaeter = TaeterFactory.createTaeter();
		List<Tatwaffe> listVerteilKartenTatwaffen = TatwaffenFactory.createTatwaffen();

		// Drei Karten herauslegen
		gesuchterTatort = listVerteilKartenTatorte.get((int)(Math.random()*(listVerteilKartenTatorte.size()-1)));		
		gesuchterTaeter = listVerteilKartenTaeter.get((int)(Math.random()*(listVerteilKartenTaeter.size()-1)));
		gesuchteTatwaffe = listVerteilKartenTatwaffen.get((int)(Math.random()*(listVerteilKartenTatwaffen.size()-1)));

		listVerteilKartenTatorte.remove(gesuchterTatort);
		listVerteilKartenTaeter.remove(gesuchterTaeter);
		listVerteilKartenTatwaffen.remove(gesuchteTatwaffe);
		
		// Übrige Karten zusammenfassen
		List<Karte> karten = new ArrayList<Karte>();
		karten.addAll(listVerteilKartenTaeter);
		karten.addAll(listVerteilKartenTatwaffen);
		karten.addAll(listVerteilKartenTatorte);
		
		// Wieviele Karten soll jeder Spieler bekommen?
		int kartenProSpieler = karten.size() / listSpieler.size();
		
		// Karten gleichmäßig verteilen
		for (Spieler spieler : listSpieler) {
			while (spieler.getKarten().size() < kartenProSpieler) {
				Karte zufaelligeKarte = karten.get( (int)(Math.random()*(karten.size()-1)) );
				
				karten.remove(zufaelligeKarte);
	
				spieler.addKarte(zufaelligeKarte);
			}
			
			// Nur die Sicht des ersten Spielers
			if (spieler.equals(listSpieler.get(0))) {
				
				// Checkbox anhaken, wenn was dazukommt
				for (Karte karte : spieler.getKarten())  
					for (KartenUebersichtCheckbox kartenUebersichtCheckbox : kartenUebersicht.getCheckboxRegistry())
						if (kartenUebersichtCheckbox.getSpieler().equals(spieler) && kartenUebersichtCheckbox.getKarte().equals(karte))
							kartenUebersichtCheckbox.setState(true);

			}
				
		}
		
		
		if (karten.size() != 0)
			throw new IllegalStateException("Es sind Karten über, was nicht sein sollte!");
		
		bestimmeSitznachbarn(listSpieler);	
		
	}
	
	private void bestimmeSitznachbarn(List<Spieler> listSpieler) {
		
		Spieler vorigerSpieler = null;
		for (Spieler spieler : listSpieler) {
			spieler.setLinkerNachbar(vorigerSpieler);
			vorigerSpieler = spieler;
		}
		
		// den kreis schließen
		Spieler letzterSpieler = listSpieler.get(listSpieler.size()-1);
		Spieler ersterSpieler = listSpieler.get(0);
		ersterSpieler.setLinkerNachbar(letzterSpieler);
		
	}
	
	public static void main(String[] args) {
		
		new Spiel();
		
	}

}
