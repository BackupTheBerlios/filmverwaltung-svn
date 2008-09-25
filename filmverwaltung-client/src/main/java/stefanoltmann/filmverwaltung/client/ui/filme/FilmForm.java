package stefanoltmann.filmverwaltung.client.ui.filme;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.springframework.binding.value.support.ListListModel;
import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.FormModelHelper;
import org.springframework.richclient.form.binding.BindingFactory;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;
import org.springframework.richclient.layout.TableLayoutBuilder;

import stefanoltmann.filmverwaltung.client.ui.CustomSlider;
import stefanoltmann.filmverwaltung.client.ui.CustomSliderBinding;
import stefanoltmann.filmverwaltung.dataaccess.Film;
import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;
import stefanoltmann.filmverwaltung.dataaccess.Person;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class FilmForm extends AbstractForm {

	private FilmverwaltungService filmverwaltungService;
	
	public FilmForm(FilmverwaltungService filmverwaltungService, Film formObject) {
		super(FormModelHelper.createFormModel(formObject, true));
		setId("film");
		
		this.filmverwaltungService = filmverwaltungService;
	}
	
	@Override
	protected JComponent createFormControl() {

		Film film = (Film)getFormObject();
		
		SwingBindingFactory bindingFactory = (SwingBindingFactory) getBindingFactory();
		TableFormBuilder formBuilder = new TableFormBuilder(bindingFactory);
		
		formBuilder.addSeparator("Filmdaten");
		formBuilder.row();

		if (new File("cover/" + film.getOfdbId() + ".jpg").exists())
			formBuilder.getLayoutBuilder().cell(new JLabel(new ImageIcon("cover/" + film.getOfdbId() + ".jpg")), "rowSpan=6");
		else
			formBuilder.getLayoutBuilder().cell(new JLabel("                               "), "rowSpan=6");
		
		formBuilder.add("name");
		formBuilder.row();
		
		formBuilder.add("ofdbName");
		formBuilder.row();
		
		JComponent ofdbIdFeld = formBuilder.add("ofdbId")[1];
		ofdbIdFeld.setToolTipText("Wenn hier eine falsche OFDB.de ID erkannt wurde, diese bitte hier selbst korrigieren. OFDB ID unter 0 löscht die Daten.");
		formBuilder.row();
		
		formBuilder.add("erscheinungsJahr");
		formBuilder.row();
		
		formBuilder.add("bewertung.imBesitz");
		formBuilder.row();
		
		formBuilder.add("bewertung.eintragsDatum");
		formBuilder.row();
		
		formBuilder.addSeparator("Genres", "colSpan=2");
		formBuilder.addSeparator("Schauspieler", "colSpan=2");
		formBuilder.row();
		
		formBuilder.getLayoutBuilder().cell(createAddGenreControl(filmverwaltungService.findAllGenres(), film.getGenres()), "colSpan=2");
		formBuilder.getLayoutBuilder().cell(createAddPersonControl(filmverwaltungService.findAllPersonen(), film.getCast()), "colSpan=2");
		formBuilder.row();
		
		formBuilder.addSeparator("Beschreibungen");
		formBuilder.row();
		
		addTextArea(formBuilder, bindingFactory, "ofdbBeschreibung");
		formBuilder.row();
		
		addTextArea(formBuilder, bindingFactory, "bewertung.beschreibung");
		formBuilder.row();
		
		formBuilder.addSeparator("Bewertung");
		formBuilder.row();		
		
		formBuilder.add(new CustomSliderBinding(new CustomSlider(), getFormModel(), "bewertung.punkte"));
		formBuilder.row();
		
		addTextArea(formBuilder, bindingFactory, "bewertung.kommentar");
		
		// Felder die nicht editierbar sein sollen
		// getFormModel().getFieldMetadata("ofdbId").setReadOnly(true);
		getFormModel().getFieldMetadata("ofdbName").setReadOnly(true);
		getFormModel().getFieldMetadata("ofdbBeschreibung").setReadOnly(true);
		
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
    
    private JPanel createAddGenreControl(List<Genre> allGenres, final List<Genre> zugeordneteGenres) {
    	
    	final JComboBox cboAllGenres = new JComboBox(allGenres.toArray());
    	    			cboAllGenres.setPreferredSize(new Dimension(200, 22));

    	final ListListModel zugeordneteGenresModel = new ListListModel(zugeordneteGenres);
    	final JList jlistZugeordneteGenres = new JList(zugeordneteGenresModel);
    	
    	JButton btnAdd = new JButton("+");
    	btnAdd.setPreferredSize(new Dimension(25, 25));
    	btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Genre selektiertesGenre = (Genre)cboAllGenres.getSelectedItem();
				if (!zugeordneteGenresModel.contains(selektiertesGenre)) {
					zugeordneteGenresModel.add(selektiertesGenre);
					zugeordneteGenres.add(selektiertesGenre);
				}
			}
    	});
    	
    	JButton btnDel = new JButton("-");
    	btnDel.setPreferredSize(new Dimension(25, 25));
    	btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Object object : jlistZugeordneteGenres.getSelectedValues()) {
					zugeordneteGenresModel.remove((Genre)object);
					zugeordneteGenres.remove((Genre)object);
				}
			}
    	});
    	
    	// JList in JScrollPane packen
    	JScrollPane spJlistZugeordneteGenres = new JScrollPane(jlistZugeordneteGenres);
    	spJlistZugeordneteGenres.setPreferredSize(new Dimension(200, 60));
    	
    	// Komplizierter Kram, damit das Layout halbwegs vernünftig aussieht.
    	JPanel panel = new JPanel(new BorderLayout());
    	
    	JPanel oberesPanel = new JPanel(new BorderLayout());
    	oberesPanel.add(cboAllGenres, BorderLayout.CENTER);
    	oberesPanel.add(btnAdd, BorderLayout.EAST);
    	
    	panel.add(oberesPanel, BorderLayout.NORTH);
    	panel.add(spJlistZugeordneteGenres, BorderLayout.CENTER);
    	panel.add(btnDel, BorderLayout.EAST);
    	
    	panel.setPreferredSize(new Dimension(200, 100));
    	
    	return panel;
    }
    
    private JPanel createAddPersonControl(List<Person> allPersonen, final List<Person> zugeordnetePersonen) {

    	final JComboBox cboAllPersonen = new JComboBox(allPersonen.toArray());
    					cboAllPersonen.setPreferredSize(new Dimension(200, 22));

    	final ListListModel zugeordnetePersonenModel = new ListListModel(zugeordnetePersonen);
    	final JList jlistZugeordnetePersonen = new JList(zugeordnetePersonenModel);
    					
    	JButton btnAdd = new JButton("+");
    	btnAdd.setPreferredSize(new Dimension(25, 25));
    	btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Person neuePerson = (Person)cboAllPersonen.getSelectedItem();
				if (!zugeordnetePersonenModel.contains(neuePerson)) {
					zugeordnetePersonenModel.add(neuePerson);
					zugeordnetePersonen.add(neuePerson);
				}
			}
    	});
    	
    	JButton btnDel = new JButton("-");
    	btnDel.setPreferredSize(new Dimension(25, 25));
    	btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Object object : jlistZugeordnetePersonen.getSelectedValues()) {
					zugeordnetePersonenModel.remove((Person)object);
					zugeordnetePersonen.remove((Person)object);
				}
			}
    	});
    	
    	// JList in JScrollPane packen
    	JScrollPane spJlistZugeordnetePersonen = new JScrollPane(jlistZugeordnetePersonen);
    	spJlistZugeordnetePersonen.setPreferredSize(new Dimension(200, 60));
    	
    	// Komplizierter Kram, damit das Layout halbwegs vernünftig aussieht.
    	JPanel panel = new JPanel(new BorderLayout());
    	
    	JPanel oberesPanel = new JPanel(new BorderLayout());
    	oberesPanel.add(cboAllPersonen, BorderLayout.CENTER);
    	oberesPanel.add(btnAdd, BorderLayout.EAST);
    	
    	panel.add(oberesPanel, BorderLayout.NORTH);
    	panel.add(spJlistZugeordnetePersonen, BorderLayout.CENTER);
    	panel.add(btnDel, BorderLayout.EAST);
    	
    	panel.setPreferredSize(new Dimension(200, 100));
    	
    	return panel;
    }

}
