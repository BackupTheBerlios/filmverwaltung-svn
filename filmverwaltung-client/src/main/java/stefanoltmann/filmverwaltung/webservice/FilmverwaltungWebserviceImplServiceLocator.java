/**
 * FilmverwaltungWebserviceImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package stefanoltmann.filmverwaltung.webservice;

public class FilmverwaltungWebserviceImplServiceLocator extends org.apache.axis.client.Service implements stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplService {

    public FilmverwaltungWebserviceImplServiceLocator() {
    }


    public FilmverwaltungWebserviceImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FilmverwaltungWebserviceImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FilmverwaltungWebserviceImplPort
    private java.lang.String FilmverwaltungWebserviceImplPort_address = "http://localhost:8080/filmverwaltung-webservice/FilmverwaltungWebservice";

    public java.lang.String getFilmverwaltungWebserviceImplPortAddress() {
        return FilmverwaltungWebserviceImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FilmverwaltungWebserviceImplPortWSDDServiceName = "FilmverwaltungWebserviceImplPort";

    public java.lang.String getFilmverwaltungWebserviceImplPortWSDDServiceName() {
        return FilmverwaltungWebserviceImplPortWSDDServiceName;
    }

    public void setFilmverwaltungWebserviceImplPortWSDDServiceName(java.lang.String name) {
        FilmverwaltungWebserviceImplPortWSDDServiceName = name;
    }

    public stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice getFilmverwaltungWebserviceImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FilmverwaltungWebserviceImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFilmverwaltungWebserviceImplPort(endpoint);
    }

    public stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice getFilmverwaltungWebserviceImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplServiceSoapBindingStub _stub = new stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getFilmverwaltungWebserviceImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFilmverwaltungWebserviceImplPortEndpointAddress(java.lang.String address) {
        FilmverwaltungWebserviceImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebservice.class.isAssignableFrom(serviceEndpointInterface)) {
                stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplServiceSoapBindingStub _stub = new stefanoltmann.filmverwaltung.webservice.FilmverwaltungWebserviceImplServiceSoapBindingStub(new java.net.URL(FilmverwaltungWebserviceImplPort_address), this);
                _stub.setPortName(getFilmverwaltungWebserviceImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("FilmverwaltungWebserviceImplPort".equals(inputPortName)) {
            return getFilmverwaltungWebserviceImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.filmverwaltung.stefanoltmann/", "FilmverwaltungWebserviceImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.filmverwaltung.stefanoltmann/", "FilmverwaltungWebserviceImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FilmverwaltungWebserviceImplPort".equals(portName)) {
            setFilmverwaltungWebserviceImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
