package com.cct.inhouse.timeoffset.core.history.general.domain;

import com.cct.inhouse.common.InhouseDomain;

public class HistoryGeneralSearch extends InhouseDomain {

	private static final long serialVersionUID = 3512835088121336160L;

	private String onsiteDateFrom1;
	private String onsiteDateTo2;
	private String onsiteTimeFrom1;
	private String onsiteTimeTo2;
	private String onsiteDateTimeFrom1;
	private String onsiteDateTimeTo2;
	private String siteServiceRemark;
	private String totalOnsiteDay;
	private String totalOnsiteHour;
	private String totalOnsiteMinute;
	private String onsiteStatus;
	private String onsiteStatusDesc;

	public String getOnsiteDateFrom1() {
		return onsiteDateFrom1;
	}

	public void setOnsiteDateFrom1(String onsiteDateFrom1) {
		this.onsiteDateFrom1 = onsiteDateFrom1;
	}

	public String getOnsiteDateTo2() {
		return onsiteDateTo2;
	}

	public void setOnsiteDateTo2(String onsiteDateTo2) {
		this.onsiteDateTo2 = onsiteDateTo2;
	}

	public String getOnsiteTimeFrom1() {
		return onsiteTimeFrom1;
	}

	public void setOnsiteTimeFrom1(String onsiteTimeFrom1) {
		this.onsiteTimeFrom1 = onsiteTimeFrom1;
	}

	public String getOnsiteTimeTo2() {
		return onsiteTimeTo2;
	}

	public void setOnsiteTimeTo2(String onsiteTimeTo2) {
		this.onsiteTimeTo2 = onsiteTimeTo2;
	}

	public String getOnsiteDateTimeFrom1() {
		return onsiteDateTimeFrom1;
	}

	public void setOnsiteDateTimeFrom1(String onsiteDateTimeFrom1) {
		this.onsiteDateTimeFrom1 = onsiteDateTimeFrom1;
	}

	public String getOnsiteDateTimeTo2() {
		return onsiteDateTimeTo2;
	}

	public void setOnsiteDateTimeTo2(String onsiteDateTimeTo2) {
		this.onsiteDateTimeTo2 = onsiteDateTimeTo2;
	}

	public String getSiteServiceRemark() {
		return siteServiceRemark;
	}

	public void setSiteServiceRemark(String siteServiceRemark) {
		this.siteServiceRemark = siteServiceRemark;
	}

	public String getTotalOnsiteDay() {
		return totalOnsiteDay;
	}

	public void setTotalOnsiteDay(String totalOnsiteDay) {
		this.totalOnsiteDay = totalOnsiteDay;
	}

	public String getTotalOnsiteHour() {
		return totalOnsiteHour;
	}

	public void setTotalOnsiteHour(String totalOnsiteHour) {
		this.totalOnsiteHour = totalOnsiteHour;
	}

	public String getTotalOnsiteMinute() {
		return totalOnsiteMinute;
	}

	public void setTotalOnsiteMinute(String totalOnsiteMinute) {
		this.totalOnsiteMinute = totalOnsiteMinute;
	}

	public String getOnsiteStatus() {
		return onsiteStatus;
	}

	public void setOnsiteStatus(String onsiteStatus) {
		this.onsiteStatus = onsiteStatus;
	}

	public String getOnsiteStatusDesc() {
		return onsiteStatusDesc;
	}

	public void setOnsiteStatusDesc(String onsiteStatusDesc) {
		this.onsiteStatusDesc = onsiteStatusDesc;
	}

}
