package com.cct.inhouse.bkonline.core.config.parameter.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParameterExtended implements Serializable {

	private static final long serialVersionUID = -8780043203790200467L;

	private String lineBotWebServiceProxy;
	private String lineBotChannelAccessToken;

	private Integer bookingRoomBeforeMinutes;
	private Integer bookingRoomReserveDays;

	private Integer dataOverLimitCheckOutMinutes;

	private Integer dataBeforeCheckInMinutes;
	private Integer dataAfterCheckInMinutes;

	private Integer timeCheckedCheckIn;
	private Integer timeCheckedCheckOut;

	public String getLineBotWebServiceProxy() {
		return lineBotWebServiceProxy;
	}

	public void setLineBotWebServiceProxy(String lineBotWebServiceProxy) {
		this.lineBotWebServiceProxy = lineBotWebServiceProxy;
	}

	public String getLineBotChannelAccessToken() {
		return lineBotChannelAccessToken;
	}

	public void setLineBotChannelAccessToken(String lineBotChannelAccessToken) {
		this.lineBotChannelAccessToken = lineBotChannelAccessToken;
	}

	public Integer getBookingRoomBeforeMinutes() {
		return bookingRoomBeforeMinutes;
	}

	public void setBookingRoomBeforeMinutes(Integer bookingRoomBeforeMinutes) {
		this.bookingRoomBeforeMinutes = bookingRoomBeforeMinutes;
	}

	public Integer getBookingRoomReserveDays() {
		return bookingRoomReserveDays;
	}

	public void setBookingRoomReserveDays(Integer bookingRoomReserveDays) {
		this.bookingRoomReserveDays = bookingRoomReserveDays;
	}

	public Integer getDataOverLimitCheckOutMinutes() {
		return dataOverLimitCheckOutMinutes;
	}

	public void setDataOverLimitCheckOutMinutes(Integer dataOverLimitCheckOutMinutes) {
		this.dataOverLimitCheckOutMinutes = dataOverLimitCheckOutMinutes;
	}

	public Integer getTimeCheckedCheckIn() {
		return timeCheckedCheckIn;
	}

	public void setTimeCheckedCheckIn(Integer timeCheckedCheckIn) {
		this.timeCheckedCheckIn = timeCheckedCheckIn;
	}

	public Integer getTimeCheckedCheckOut() {
		return timeCheckedCheckOut;
	}

	public void setTimeCheckedCheckOut(Integer timeCheckedCheckOut) {
		this.timeCheckedCheckOut = timeCheckedCheckOut;
	}

	public Integer getDataBeforeCheckInMinutes() {
		return dataBeforeCheckInMinutes;
	}

	public void setDataBeforeCheckInMinutes(Integer dataBeforeCheckInMinutes) {
		this.dataBeforeCheckInMinutes = dataBeforeCheckInMinutes;
	}

	public Integer getDataAfterCheckInMinutes() {
		return dataAfterCheckInMinutes;
	}

	public void setDataAfterCheckInMinutes(Integer dataAfterCheckInMinutes) {
		this.dataAfterCheckInMinutes = dataAfterCheckInMinutes;
	}

}
