package stefanoltmann.filmverwaltung.client.ui.filmbewertung;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.FormModelHelper;
import org.springframework.richclient.form.binding.BindingFactory;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;
import org.springframework.richclient.layout.TableLayoutBuilder;

import stefanoltmann.filmverwaltung.client.ui.CustomSlider;
import stefanoltmann.filmverwaltung.client.ui.CustomSliderBinding;
import stefanoltmann.filmverwaltung.dataaccess.Filmbewertung;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmbewertungForm extends AbstractForm {

//	private FilmverwaltungService filmverwaltungService;
	
	public FilmbewertungForm(FilmverwaltungService filmverwaltungService, Filmbewertung formObject) {
		super(FormModelHelper.createFormModel(formObject, true));
		setId("filmbewertung");
		
//		this.filmverwaltungService = filmverwaltungService;
	}
	
	@Override
	protected JComponent createFormControl() {

		SwingBindingFactory bindingFactory = (SwingBindingFactory) getBindingFactory();
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
		
		formBuilder.addSeparator("Bewertung");
		formBuilder.row();

		formBuilder.add("imBesitz");
		formBuilder.row();
		
		formBuilder.add("eintragsDatum");
		formBuilder.row();
		
		addTextArea(formBuilder, bindingFactory, "beschreibung");
		formBuilder.row();
		
		formBuilder.add(new CustomSliderBinding(new CustomSlider(), getFormModel(), "punkte"));
		formBuilder.row();
		
		addTextArea(formBuilder, bindingFactory, "kommentar");
		
		return formBuilder.getForm();
	}
	
	/**
	 * Die Original-Methode formBuilder.addTextArea ist offenbar irgendwie buggy... deshalb dieser Weg.
	 * Das hier sollte nicht mit gutem Code verwechselt werden.
	 */
    public void addTextArea(TableFormBuilder tableFormBuilder, BindingFactory bindingFactory, String fieldName) {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(4);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        tableFormBuilder.addBinding(bindingFactory.bindControl(textArea, fieldName), new JScrollPane(textArea), "", TableLayoutBuilder.DEFAULT_LABEL_ATTRIBUTES);
    }

}
