package stefanoltmann.filmverwaltung.webservice;

public class FilmverwaltungWebserviceProxy implements stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice {
  private String _endpoint = null;
  private stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice filmverwaltungWebservice = null;
  
  public FilmverwaltungWebserviceProxy() {
    _initFilmverwaltungWebserviceProxy();
  }
  
  public FilmverwaltungWebserviceProxy(String endpoint) {
    _endpoint = endpoint;
    _initFilmverwaltungWebserviceProxy();
  }
  
  private void _initFilmverwaltungWebserviceProxy() {
    try {
      filmverwaltungWebservice = (new stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplServiceLocator()).getFilmverwaltungWebserviceImplPort();
      if (filmverwaltungWebservice != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)filmverwaltungWebservice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)filmverwaltungWebservice)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (filmverwaltungWebservice != null)
      ((javax.xml.rpc.Stub)filmverwaltungWebservice)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice getFilmverwaltungWebservice() {
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice;
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Genre[] getAllGenres() throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getAllGenres();
  }
  
  public void sendBewertung(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Filmbewertung arg1) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    filmverwaltungWebservice.sendBewertung(arg0, arg1);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Benutzer registriereNutzer(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.registriereNutzer(arg0, arg1);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Person[] getPersonenForFilm(java.lang.String arg0) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getPersonenForFilm(arg0);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Filmbewertung getBewertung(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getBewertung(arg0, arg1);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Film[] getAllFilme() throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getAllFilme();
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Person[] getAllPersonen() throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getAllPersonen();
  }
  
  public java.lang.String sendGenre(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Genre arg1) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.sendGenre(arg0, arg1);
  }
  
  public boolean isFilmInDatabase(java.lang.String arg0) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.isFilmInDatabase(arg0);
  }
  
  public java.lang.String[] getFilmIDs(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getFilmIDs(arg0);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Benutzer getBenutzer(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getBenutzer(arg0, arg1);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Genre[] getGenresForFilm(java.lang.String arg0) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getGenresForFilm(arg0);
  }
  
  public stefanoltmann.filmverwaltung.dataaccess.Film getFilm(java.lang.String arg0) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.getFilm(arg0);
  }
  
  public java.lang.String sendFilm(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Film arg1, stefanoltmann.filmverwaltung.dataaccess.Genre[] arg2, stefanoltmann.filmverwaltung.dataaccess.Person[] arg3) throws java.rmi.RemoteException{
    if (filmverwaltungWebservice == null)
      _initFilmverwaltungWebserviceProxy();
    return filmverwaltungWebservice.sendFilm(arg0, arg1, arg2, arg3);
  }
  
  
}