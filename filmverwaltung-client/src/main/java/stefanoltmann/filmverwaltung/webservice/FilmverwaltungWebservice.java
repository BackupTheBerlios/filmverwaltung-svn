/**
 * FilmverwaltungWebservice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package stefanoltmann.filmverwaltung.webservice;

public interface FilmverwaltungWebservice extends java.rmi.Remote {
    public stefanoltmann.filmverwaltung.dataaccess.Genre[] getAllGenres() throws java.rmi.RemoteException;
    public void sendBewertung(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Filmbewertung arg1) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Benutzer registriereNutzer(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Person[] getPersonenForFilm(java.lang.String arg0) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Filmbewertung getBewertung(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Film[] getAllFilme() throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Person[] getAllPersonen() throws java.rmi.RemoteException;
    public java.lang.String sendGenre(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Genre arg1) throws java.rmi.RemoteException;
    public boolean isFilmInDatabase(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String[] getFilmIDs(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Benutzer getBenutzer(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Genre[] getGenresForFilm(java.lang.String arg0) throws java.rmi.RemoteException;
    public stefanoltmann.filmverwaltung.dataaccess.Film getFilm(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.String sendFilm(stefanoltmann.filmverwaltung.dataaccess.Benutzer arg0, stefanoltmann.filmverwaltung.dataaccess.Film arg1, stefanoltmann.filmverwaltung.dataaccess.Genre[] arg2, stefanoltmann.filmverwaltung.dataaccess.Person[] arg3) throws java.rmi.RemoteException;
}
