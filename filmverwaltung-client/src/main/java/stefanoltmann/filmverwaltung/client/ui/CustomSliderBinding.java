package stefanoltmann.filmverwaltung.client.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.binding.form.FormModel;
import org.springframework.binding.value.ValueModel;
import org.springframework.richclient.form.binding.support.AbstractBinding;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class CustomSliderBinding extends AbstractBinding {

	private final CustomSlider slider;
	
	public CustomSliderBinding(CustomSlider slider, FormModel formModel, String formPropertyPath) {
		super(formModel, formPropertyPath, Integer.class);
		this.slider = slider;
	}

	@Override
	protected JComponent doBindControl() {
		
		final ValueModel valueModel = getValueModel();
		
		if (valueModel.getValue() != null)
			slider.setValue((Integer)valueModel.getValue());
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				valueModel.setValue(slider.getValue());
			}
		});
		
		valueModel.addValueChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				slider.setValue((Integer)valueModel.getValue());
			}
		});
		
		return slider;
	}

    protected void readOnlyChanged() {
        slider.setEnabled(! isReadOnly());
    }

    protected void enabledChanged() {
        slider.setEnabled(isEnabled());
    }

}
