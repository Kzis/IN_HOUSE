package com.cct.inhouse.timeoffset.core.approve.domain;

import com.cct.inhouse.common.InhouseDomain;

public class Detail extends InhouseDomain {

	private static final long serialVersionUID = -5762241584573492634L;

	private String timeOffsetId;
	private String timeOffsetDetId;
	private String workDetail;
	private String startDateTime;
	private String endDateTime;
	private String day;
	private String hour;
	private String minute;
	private String remark;
	private String approveStatus;
	private String approveStatusDesc;
	private String processStatus;

	public String getWorkDetail() {
		return workDetail;
	}

	public void setWorkDetail(String workDetail) {
		this.workDetail = workDetail;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinute() {
		return minute;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTimeOffsetId() {
		return timeOffsetId;
	}

	public void setTimeOffsetId(String timeOffsetId) {
		this.timeOffsetId = timeOffsetId;
	}

	public String getTimeOffsetDetId() {
		return timeOffsetDetId;
	}

	public void setTimeOffsetDetId(String timeOffsetDetId) {
		this.timeOffsetDetId = timeOffsetDetId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApproveStatusDesc() {
		return approveStatusDesc;
	}

	public void setApproveStatusDesc(String approveStatusDesc) {
		this.approveStatusDesc = approveStatusDesc;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

}
