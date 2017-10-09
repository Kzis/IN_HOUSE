/**
 * LineBotWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cct.linebot.web.linebot.ws;

public class LineBotWebServiceServiceLocator extends org.apache.axis.client.Service implements com.cct.linebot.web.linebot.ws.LineBotWebServiceService {

    public LineBotWebServiceServiceLocator() {
    }


    public LineBotWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LineBotWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LineBotWebServicePort
    private java.lang.String LineBotWebServicePort_address = "http://10.100.70.68:80/linebot_webservice_dev/LineBotWebService";

    public java.lang.String getLineBotWebServicePortAddress() {
        return LineBotWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LineBotWebServicePortWSDDServiceName = "LineBotWebServicePort";

    public java.lang.String getLineBotWebServicePortWSDDServiceName() {
        return LineBotWebServicePortWSDDServiceName;
    }

    public void setLineBotWebServicePortWSDDServiceName(java.lang.String name) {
        LineBotWebServicePortWSDDServiceName = name;
    }

    public com.cct.linebot.web.linebot.ws.LineBotWebService getLineBotWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LineBotWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLineBotWebServicePort(endpoint);
    }

    public com.cct.linebot.web.linebot.ws.LineBotWebService getLineBotWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cct.linebot.web.linebot.ws.LineBotWebServicePortBindingStub _stub = new com.cct.linebot.web.linebot.ws.LineBotWebServicePortBindingStub(portAddress, this);
            _stub.setPortName(getLineBotWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLineBotWebServicePortEndpointAddress(java.lang.String address) {
        LineBotWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cct.linebot.web.linebot.ws.LineBotWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cct.linebot.web.linebot.ws.LineBotWebServicePortBindingStub _stub = new com.cct.linebot.web.linebot.ws.LineBotWebServicePortBindingStub(new java.net.URL(LineBotWebServicePort_address), this);
                _stub.setPortName(getLineBotWebServicePortWSDDServiceName());
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
        if ("LineBotWebServicePort".equals(inputPortName)) {
            return getLineBotWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.linebot.web.linebot.cct.com/", "LineBotWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.linebot.web.linebot.cct.com/", "LineBotWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LineBotWebServicePort".equals(portName)) {
            setLineBotWebServicePortEndpointAddress(address);
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
