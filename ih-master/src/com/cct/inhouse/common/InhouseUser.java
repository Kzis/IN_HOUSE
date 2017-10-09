package com.cct.inhouse.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.cct.common.CommonUser;
import com.cct.domain.Operator;

public class InhouseUser extends CommonUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6989869542535028616L;
	
	private String titleName;
	private String name;
	private String surName;
	private String nickName;
	private String fullName;

	private String departmentId;
	private String departmentName;
	private String positionId;
	private String positionName;
	
	private String email;
	private String gmail;
	private String colorCode;
	private String lineUserId;
	private String tableNo;
	private String insidePhoneNumber;
	private String cardId;
	
	private String displayName;
	private String updateDate;
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	private Map<String, Operator> mapOperator = new LinkedHashMap<String, Operator>();

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getLineUserId() {
		return lineUserId;
	}

	public void setLineUserId(String lineUserId) {
		this.lineUserId = lineUserId;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getInsidePhoneNumber() {
		return insidePhoneNumber;
	}

	public void setInsidePhoneNumber(String insidePhoneNumber) {
		this.insidePhoneNumber = insidePhoneNumber;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public Map<String, Operator> getMapOperator() {
		return mapOperator;
	}

	public void setMapOperator(Map<String, Operator> mapOperator) {
		this.mapOperator = mapOperator;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
