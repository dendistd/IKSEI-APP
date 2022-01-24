package com.example.iksei.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_user_group_kbb")
public class GroupUserKbb {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "ab_id")
	private String abId;
	
	@Column(name = "group_user_code")
	private String groupUserCode;
	
	@Column(name = "reg_date")
	private Date regDate;
	
	@Column(name = "is_new")
	private String isNew;

	public GroupUserKbb() {
		super();
	}

	public GroupUserKbb(String id, String abId, String groupUserCode, Date regDate, String isNew) {
		super();
		this.id = id;
		this.abId = abId;
		this.groupUserCode = groupUserCode;
		this.regDate = regDate;
		this.isNew = isNew;
	}
	
	public GroupUserKbb(String abId, String groupUserCode, Date regDate, String isNew) {
		super();
		
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
		return "GroupUserKbb [id=" + id + ", abId=" + abId + ", groupUserCode=" + groupUserCode + ", regDate=" + regDate
				+ ", isNew=" + isNew + "]";
	}
	
	

}
