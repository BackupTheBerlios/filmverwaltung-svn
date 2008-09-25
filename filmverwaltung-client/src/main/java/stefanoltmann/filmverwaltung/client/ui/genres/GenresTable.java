package stefanoltmann.filmverwaltung.client.ui.genres;

import java.util.ArrayList;
import java.util.List;

import org.springframework.richclient.table.support.AbstractObjectTable;

import stefanoltmann.filmverwaltung.dataaccess.FilmverwaltungService;
import stefanoltmann.filmverwaltung.dataaccess.Genre;

/**
 * @author Stefan Oltmann (stefan.oltmann@gmail.com)
 */
public class GenresTable extends AbstractObjectTable {

	private FilmverwaltungService filmverwaltungService;
	
	public GenresTable(FilmverwaltungService filmverwaltungService) {
		super("genre", new String[]{ "name" });
		this.filmverwaltungService = filmverwaltungService;
	}

	@Override
	protected Object[] getDefaultInitialData() {
		return filmverwaltungService.findAllGenres().toArray();
	}
	
	public List<Genre> getSelectedGenres() {
		int[] selektierteIDs = getTable().getSelectedRows();
		List<Genre> genres = new ArrayList<Genre>();
		for (int i = 0; i < selektierteIDs.length; i++) {
			genres.add( (Genre)getTableModel().getElementAt(selektierteIDs[i]) );
		}
		return genres;
	}
	
	public Genre getSelectedGenre() {
		return getSelectedGenres().get(0);
	}

}
