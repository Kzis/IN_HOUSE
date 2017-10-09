/**
 * GetTimeOffsetWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cct.hrmdata.web.gettimeoffset.ws;

import com.cct.inhouse.timeoffset.core.config.parameter.domain.ParameterExtendedConfig;

public class GetTimeOffsetWebServiceServiceLocator extends org.apache.axis.client.Service implements com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceService {

    public GetTimeOffsetWebServiceServiceLocator() {
    }


    public GetTimeOffsetWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GetTimeOffsetWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GetTimeOffsetWebServicePort
    private java.lang.String GetTimeOffsetWebServicePort_address = ParameterExtendedConfig.getParameterExtended().getTimeOffsetConf().getWebServiceConf().getUrl();

    public java.lang.String getGetTimeOffsetWebServicePortAddress() {
        return GetTimeOffsetWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GetTimeOffsetWebServicePortWSDDServiceName = "GetTimeOffsetWebServicePort";

    public java.lang.String getGetTimeOffsetWebServicePortWSDDServiceName() {
        return GetTimeOffsetWebServicePortWSDDServiceName;
    }

    public void setGetTimeOffsetWebServicePortWSDDServiceName(java.lang.String name) {
        GetTimeOffsetWebServicePortWSDDServiceName = name;
    }

    public com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService getGetTimeOffsetWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GetTimeOffsetWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGetTimeOffsetWebServicePort(endpoint);
    }

    public com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService getGetTimeOffsetWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServicePortBindingStub _stub = new com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServicePortBindingStub(portAddress, this);
            _stub.setPortName(getGetTimeOffsetWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGetTimeOffsetWebServicePortEndpointAddress(java.lang.String address) {
        GetTimeOffsetWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServicePortBindingStub _stub = new com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServicePortBindingStub(new java.net.URL(GetTimeOffsetWebServicePort_address), this);
                _stub.setPortName(getGetTimeOffsetWebServicePortWSDDServiceName());
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
        if ("GetTimeOffsetWebServicePort".equals(inputPortName)) {
            return getGetTimeOffsetWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.gettimeoffset.web.hrmdata.cct.com/", "GetTimeOffsetWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.gettimeoffset.web.hrmdata.cct.com/", "GetTimeOffsetWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GetTimeOffsetWebServicePort".equals(portName)) {
            setGetTimeOffsetWebServicePortEndpointAddress(address);
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
