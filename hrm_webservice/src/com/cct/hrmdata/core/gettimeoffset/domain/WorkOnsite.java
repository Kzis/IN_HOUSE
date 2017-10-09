package com.cct.hrmdata.core.gettimeoffset.domain;

import java.io.Serializable;

public class WorkOnsite implements Serializable {

	private static final long serialVersionUID = -3480425551975160209L;

	private String onsiteid;
	private String userid;
	private String employeeid;
	private String firstnametha;
	private String lastnametha;
	private String nickname;
	private String workonsiteid;
	private String onsitedatefrom1;
	private String onsitetimefrom1;
	private String onsitedateto2;
	private String onsitetimeto2;
	private Integer totalOnsiteday;
	private Integer totalOnsitetime;
	private String siteservice;
	private String onsitestatus;
	private String approvets;
	private String siteserviceRemark;
	private String onsitestatusRemark;
	private String approverid;

	public String getOnsiteid() {
		return onsiteid;
	}

	public void setOnsiteid(String onsiteid) {
		this.onsiteid = onsiteid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public String getFirstnametha() {
		return firstnametha;
	}

	public void setFirstnametha(String firstnametha) {
		this.firstnametha = firstnametha;
	}

	public String getLastnametha() {
		return lastnametha;
	}

	public void setLastnametha(String lastnametha) {
		this.lastnametha = lastnametha;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getWorkonsiteid() {
		return workonsiteid;
	}

	public void setWorkonsiteid(String workonsiteid) {
		this.workonsiteid = workonsiteid;
	}

	public String getOnsitedatefrom1() {
		return onsitedatefrom1;
	}

	public void setOnsitedatefrom1(String onsitedatefrom1) {
		this.onsitedatefrom1 = onsitedatefrom1;
	}

	public String getOnsitetimefrom1() {
		return onsitetimefrom1;
	}

	public void setOnsitetimefrom1(String onsitetimefrom1) {
		this.onsitetimefrom1 = onsitetimefrom1;
	}

	public String getOnsitedateto2() {
		return onsitedateto2;
	}

	public void setOnsitedateto2(String onsitedateto2) {
		this.onsitedateto2 = onsitedateto2;
	}

	public String getOnsitetimeto2() {
		return onsitetimeto2;
	}

	public void setOnsitetimeto2(String onsitetimeto2) {
		this.onsitetimeto2 = onsitetimeto2;
	}

	public Integer getTotalOnsiteday() {
		return totalOnsiteday;
	}

	public void setTotalOnsiteday(Integer totalOnsiteday) {
		this.totalOnsiteday = totalOnsiteday;
	}

	public Integer getTotalOnsitetime() {
		return totalOnsitetime;
	}

	public void setTotalOnsitetime(Integer totalOnsitetime) {
		this.totalOnsitetime = totalOnsitetime;
	}

	public String getSiteservice() {
		return siteservice;
	}

	public void setSiteservice(String siteservice) {
		this.siteservice = siteservice;
	}

	public String getOnsitestatus() {
		return onsitestatus;
	}

	public void setOnsitestatus(String onsitestatus) {
		this.onsitestatus = onsitestatus;
	}

	public String getApprovets() {
		return approvets;
	}

	public void setApprovets(String approvets) {
		this.approvets = approvets;
	}

	public String getSiteserviceRemark() {
		return siteserviceRemark;
	}

	public void setSiteserviceRemark(String siteserviceRemark) {
		this.siteserviceRemark = siteserviceRemark;
	}

	public String getOnsitestatusRemark() {
		return onsitestatusRemark;
	}

	public void setOnsitestatusRemark(String onsitestatusRemark) {
		this.onsitestatusRemark = onsitestatusRemark;
	}

	public String getApproverid() {
		return approverid;
	}

	public void setApproverid(String approverid) {
		this.approverid = approverid;
	}

}
