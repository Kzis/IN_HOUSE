/**
 * LineBotWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.cct.linebot.web.linebot.ws;

public interface LineBotWebService extends java.rmi.Remote {
    public boolean sendMessage(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String message) throws java.rmi.RemoteException;
    public boolean sendMessages(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String[] messages) throws java.rmi.RemoteException;
    public boolean broadcastMessage(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String message) throws java.rmi.RemoteException;
    public boolean broadcastMessages(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String[] messages) throws java.rmi.RemoteException;
    public boolean sendSticker(java.lang.String channelAccessToken, java.lang.String userId, java.lang.String packageId, java.lang.String stickerId) throws java.rmi.RemoteException;
    public boolean broadcastSticker(java.lang.String channelAccessToken, java.lang.String[] userIds, java.lang.String packageId, java.lang.String stickerId) throws java.rmi.RemoteException;
    public boolean sendByteMessage(java.lang.String channelAccessToken, java.lang.String userId, byte[] message) throws java.rmi.RemoteException;
}
