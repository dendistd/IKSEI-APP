package com.example.iksei.payload.groupuser;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateGroupUserKbb {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("ab_id_new")
	private String abIdNew;
	
	@JsonProperty("ab_id_old")
	private String abIdOld;
	
	@JsonProperty("group_user_code_new")
	private String groupUserCodeNew;
	
	@JsonProperty("group_user_code_old")
	private String groupUserCodeOld;
	
	@JsonProperty("is_new_new")
	private String isNewNew;
	
	@JsonProperty("is_new_old")
	private String isNewOld;

	public ResponseUpdateGroupUserKbb() {
		super();
	}

	public ResponseUpdateGroupUserKbb(String id, String abIdNew, String abIdOld, String groupUserCodeNew,
			String groupUserCodeOld, String isNewNew, String isNewOld) {
		super();
		this.id = id;
		this.abIdNew = abIdNew;
		this.abIdOld = abIdOld;
		this.groupUserCodeNew = groupUserCodeNew;
		this.groupUserCodeOld = groupUserCodeOld;
		this.isNewNew = isNewNew;
		this.isNewOld = isNewOld;
	}
	
	public ResponseUpdateGroupUserKbb(String id, String abIdOld, 
			String groupUserCodeOld, String isNewOld) {
		super();
		this.id = id;
		this.abIdOld = abIdOld;
		this.groupUserCodeOld = groupUserCodeOld;
		this.isNewOld = isNewOld;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbIdNew() {
		return abIdNew;
	}

	public void setAbIdNew(String abIdNew) {
		this.abIdNew = abIdNew;
	}

	public String getAbIdOld() {
		return abIdOld;
	}

	public void setAbIdOld(String abIdOld) {
		this.abIdOld = abIdOld;
	}

	public String getGroupUserCodeNew() {
		return groupUserCodeNew;
	}

	public void setGroupUserCodeNew(String groupUserCodeNew) {
		this.groupUserCodeNew = groupUserCodeNew;
	}

	public String getGroupUserCodeOld() {
		return groupUserCodeOld;
	}

	public void setGroupUserCodeOld(String groupUserCodeOld) {
		this.groupUserCodeOld = groupUserCodeOld;
	}

	public String getIsNewNew() {
		return isNewNew;
	}

	public void setIsNewNew(String isNewNew) {
		this.isNewNew = isNewNew;
	}

	public String getIsNewOld() {
		return isNewOld;
	}

	public void setIsNewOld(String isNewOld) {
		this.isNewOld = isNewOld;
	}

	@Override
	public String toString() {
		return "ResponseUpdateGroupUserKbb [id=" + id + ", abIdNew=" + abIdNew + ", abIdOld=" + abIdOld
				+ ", groupUserCodeNew=" + groupUserCodeNew + ", groupUserCodeOld=" + groupUserCodeOld + ", isNewNew=" + isNewNew + ", isNewOld=" + isNewOld + "]";
	}
	
	

}
