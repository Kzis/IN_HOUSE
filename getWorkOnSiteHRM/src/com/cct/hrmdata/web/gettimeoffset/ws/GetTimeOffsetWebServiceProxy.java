package com.cct.hrmdata.web.gettimeoffset.ws;

public class GetTimeOffsetWebServiceProxy implements com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService {
  private String _endpoint = null;
  private com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService getTimeOffsetWebService = null;
  
  public GetTimeOffsetWebServiceProxy() {
    _initGetTimeOffsetWebServiceProxy();
  }
  
  public GetTimeOffsetWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initGetTimeOffsetWebServiceProxy();
  }
  
  private void _initGetTimeOffsetWebServiceProxy() {
    try {
      getTimeOffsetWebService = (new com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebServiceServiceLocator()).getGetTimeOffsetWebServicePort();
      if (getTimeOffsetWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)getTimeOffsetWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)getTimeOffsetWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (getTimeOffsetWebService != null)
      ((javax.xml.rpc.Stub)getTimeOffsetWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cct.hrmdata.web.gettimeoffset.ws.GetTimeOffsetWebService getGetTimeOffsetWebService() {
    if (getTimeOffsetWebService == null)
      _initGetTimeOffsetWebServiceProxy();
    return getTimeOffsetWebService;
  }
  
  public com.cct.hrmdata.web.gettimeoffset.ws.WorkOnsite[] getTimeOffset(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (getTimeOffsetWebService == null)
      _initGetTimeOffsetWebServiceProxy();
    return getTimeOffsetWebService.getTimeOffset(arg0, arg1);
  }
  
  
}