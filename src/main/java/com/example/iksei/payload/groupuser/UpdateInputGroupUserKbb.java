package com.example.iksei.payload.groupuser;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateInputGroupUserKbb {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("group_user_code")
	private String groupUserCode;
	
	@JsonProperty("reg_date")
	private Date regDate;
	
	@JsonProperty("is_new")
	private String isNew;
	
	public UpdateInputGroupUserKbb() {
		super();
	}
	
	public UpdateInputGroupUserKbb(String id, String abId, String groupUserCode, String isNew) {
		super();
		this.id = id;
		this.abId = abId;
		this.groupUserCode = groupUserCode;
		this.isNew = isNew;
	}

	public UpdateInputGroupUserKbb(String id, String abId, String groupUserCode, Date regDate, String isNew) {
		super();
		this.id = id;
		this.abId = abId;
		this.groupUserCode = groupUserCode;
		this.regDate = regDate;
		this.isNew = isNew;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "UpdateInputGroupUserKbb [id=" + id + ", abId=" + abId + ", groupUserCode=" + groupUserCode
				+ ", regDate=" + regDate + ", isNew=" + isNew + "]";
	}
	
	

}
