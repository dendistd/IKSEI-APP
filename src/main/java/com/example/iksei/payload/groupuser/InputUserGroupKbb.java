package com.example.iksei.payload.groupuser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputUserGroupKbb {
		
	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("group_user_code")
	private String groupUserCode;
	
	@JsonProperty("is_new")
	private String isNew;

	public InputUserGroupKbb() {
		super();
	}

	public InputUserGroupKbb( String abId, String groupUserCode, String isNew) {
		super();

		this.abId = abId;
		this.groupUserCode = groupUserCode;
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

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Override
	public String toString() {
		return "InputUserGroupKbb [abId=" + abId + ", groupUserCode=" + groupUserCode + ", isNew="
				+ isNew + "]";
	}
	
	

}
