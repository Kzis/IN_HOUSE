package com.cct.linebot.web.linebot.ws;

public class LineBotWebServiceProxy implements com.cct.linebot.web.linebot.ws.LineBotWebService {
  private String _endpoint = null;
  private com.cct.linebot.web.linebot.ws.LineBotWebService lineBotWebService = null;
  
  public LineBotWebServiceProxy() {
    _initLineBotWebServiceProxy();
  }
  
  public LineBotWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initLineBotWebServiceProxy();
  }
  
  private void _initLineBotWebServiceProxy() {
    try {
      lineBotWebService = (new com.cct.linebot.web.linebot.ws.LineBotWebServiceServiceLocator()).getLineBotWebServicePort();
      if (lineBotWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)lineBotWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)lineBotWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (lineBotWebService != null)
      ((javax.xml.rpc.Stub)lineBotWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.cct.linebot.web.linebot.ws.LineBotWebService getLineBotWebService() {
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService;
  }
  
  public boolean sendMessage(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String message) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.sendMessage(channelAccessToken, userId, message);
  }
  
  public boolean sendMessages(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String[] messages) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.sendMessages(channelAccessToken, userId, messages);
  }
  
  public boolean broadcastMessage(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String message) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.broadcastMessage(channelAccessToken, userIds, message);
  }
  
  public boolean broadcastMessages(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String[] messages) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.broadcastMessages(channelAccessToken, userIds, messages);
  }
  
  public boolean sendSticker(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String packageId, java.lang.String stickerId) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.sendSticker(channelAccessToken, userId, packageId, stickerId);
  }
  
  public boolean broadcastSticker(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String packageId, java.lang.String stickerId) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.broadcastSticker(channelAccessToken, userIds, packageId, stickerId);
  }
  
  public boolean sendByteMessage(java.lang.String channelAccessToken, java.lang.String userId, byte[] message) throws java.rmi.RemoteException{
    if (lineBotWebService == null)
      _initLineBotWebServiceProxy();
    return lineBotWebService.sendByteMessage(channelAccessToken, userId, message);
  }
  
  
}