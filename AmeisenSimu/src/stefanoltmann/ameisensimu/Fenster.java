package stefanoltmann.ameisensimu;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Fenster extends JFrame {
	
	private Welt welt = new Welt();
	private Grafik grafik = new Grafik(welt);
	
	public Fenster() {
		super("Die Welt");
		
		setLayout(null);
		getContentPane().add(grafik);
		setSize(Welt.GROESSE_X+10, Welt.GROESSE_Y+30);
//		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MyThread thread = new MyThread();
		thread.run();
	}
	
	private class MyThread implements Runnable {

		public void run() {

			try {
				
				while (true) {
					
					if (welt.getAmeisen().size() < 100)
						welt.getAmeisen().add(AmeisenFactory.createAmeise());
					
					for (Ameise ameise : welt.getAmeisen())
						ameise.bewegung();
					
					// Kollisionsabfrage
					for (Ameise ameiseErste : welt.getAmeisen())
						if (ameiseErste.getRechteckObjekt().intersects(welt.getBau().getRechteckObjekt())) {
							ameiseErste.angekommenBei(welt.getBau());
						}
					
					grafik.repaint();
					
					Thread.sleep(10);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
}
