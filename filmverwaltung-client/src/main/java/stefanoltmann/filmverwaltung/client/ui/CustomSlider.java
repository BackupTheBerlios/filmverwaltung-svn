package stefanoltmann.filmverwaltung.client.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class CustomSlider extends JPanel {

	private static final long serialVersionUID = 402817166376445071L;
	
	private JSlider slider;
	private JLabel label;
	
	public CustomSlider() {
	
		slider = new JSlider(0, 10);
		label = new JLabel("5");
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label.setText(String.valueOf(slider.getValue()));
			}
		});
		
		this.add(slider);
		this.add(label);
	}
	
	public int getValue() {
		return slider.getValue();
	}
	
	public void setValue(int value) {
		slider.setValue(value);
	}
	
	public void addChangeListener(ChangeListener changeListener) {
		slider.addChangeListener(changeListener);
	}

}
