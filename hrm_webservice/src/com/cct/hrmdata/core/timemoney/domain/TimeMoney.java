package com.cct.hrmdata.core.timemoney.domain;

import java.io.Serializable;

public class TimeMoney implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4862892292166296111L;
	private Integer secUserId;
	private Integer employeeId;
	private String positionId;
	private String departmentId;
	private String departmentName;
	private String cardId;
	private String firstName;
	private String lastName;
	private String nickName;
	private Integer checkOnsite;
	private Integer checkLeave;
	private Integer empGroupId;
	private String workDate;
	private Long lateTime;
	private String workTimeIn;
	private String workTimeOut;
	private String scanTimeIn;
	private String scanTimeOut;
	private String scanTimeInDisplay;
	private String imageAvartar;
	
	private boolean chkLate;
	
	private String days;
	private String months; 	//DEPARTMENT
	private String years; 	//DEPARTMENT
	
	
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getCheckOnsite() {
		return checkOnsite;
	}
	public void setCheckOnsite(Integer checkOnsite) {
		this.checkOnsite = checkOnsite;
	}
	public Integer getCheckLeave() {
		return checkLeave;
	}
	public void setCheckLeave(Integer checkLeave) {
		this.checkLeave = checkLeave;
	}
	public Integer getEmpGroupId() {
		return empGroupId;
	}
	public void setEmpGroupId(Integer empGroupId) {
		this.empGroupId = empGroupId;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getWorkTimeIn() {
		return workTimeIn;
	}
	public void setWorkTimeIn(String workTimeIn) {
		this.workTimeIn = workTimeIn;
	}
	public String getWorkTimeOut() {
		return workTimeOut;
	}
	public void setWorkTimeOut(String workTimeOut) {
		this.workTimeOut = workTimeOut;
	}
	public String getScanTimeIn() {
		return scanTimeIn;
	}
	public void setScanTimeIn(String scanTimeIn) {
		this.scanTimeIn = scanTimeIn;
	}
	public String getScanTimeOut() {
		return scanTimeOut;
	}
	public void setScanTimeOut(String scanTimeOut) {
		this.scanTimeOut = scanTimeOut;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public boolean isChkLate() {
		return chkLate;
	}
	public void setChkLate(boolean chkLate) {
		this.chkLate = chkLate;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public Long getLateTime() {
		return lateTime;
	}
	public void setLateTime(Long lateTime) {
		this.lateTime = lateTime;
	}
	public String getScanTimeInDisplay() {
		return scanTimeInDisplay;
	}
	public void setScanTimeInDisplay(String scanTimeInDisplay) {
		this.scanTimeInDisplay = scanTimeInDisplay;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getImageAvartar() {
		return imageAvartar;
	}
	public void setImageAvartar(String imageAvartar) {
		this.imageAvartar = imageAvartar;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public Integer getSecUserId() {
		return secUserId;
	}
	public void setSecUserId(Integer secUserId) {
		this.secUserId = secUserId;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}

}
