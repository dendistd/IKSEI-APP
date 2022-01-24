package com.example.iksei.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputCreateGroupUserKbb {
	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("group_user_code")
	private String groupUserCode;
	
	@JsonProperty("reg_date")
	private Date regDate;
	
	@JsonProperty("is_new")
	private String isNew;

	public InputCreateGroupUserKbb() {
		super();
	}

	public InputCreateGroupUserKbb(String abId, String groupUserCode, Date regDate, String isNew) {
		super();
		this.abId = abId;
		this.groupUserCode = groupUserCode;
		this.regDate = regDate;
		this.isNew = isNew;
	}

	public String getAbId() {
		return abId;
	}

	public void setAbId(String abId) {
		this.abId = abId;
	}

	public String getGroupUserCode() {
		return groupUserCode;
	}

	public void setGroupUserCode(String groupUserCode) {
		this.groupUserCode = groupUserCode;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Override
	public String toString() {
		return "InputCreateGroupUserKbb [abId=" + abId + ", groupUserCode=" + groupUserCode + ", regDate=" + regDate
				+ ", isNew=" + isNew + "]";
	}
	
	

}
