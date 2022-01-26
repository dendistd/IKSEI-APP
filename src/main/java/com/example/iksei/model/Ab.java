package com.example.iksei.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dendi_ab")
public class Ab {
	
	@Id
	@Column(name = "ab_id" )
	private String abId;
	
	@Column(name = "ab_name")
	private String abName;
	
	@Column(name = "is_notified")
	private String isNotified;
	
	@Column(name = "notif_transaksi")
	private String notifTransaksi;
	
	@Column(name = "corporate_id")
	private String corporateId;
	
	@Column(name = "corp_id_reg_date")
	private Date corpIdRegDate;
	
	@Column(name = "comp_code")
	private String compCode;
	
	@Column(name = "comp_code_reg_date")
	private Date compCodeRegDate;
	
	@Column(name = "ab_initial")
	private String abInitial;
	
	public Ab() {
		super();
	}
	public Ab(String abId, String abName, String isNotified, String notifTransaksi, String corporateId,
			Date corpIdRegDate, String compCode, Date compCodeRegDate, String abInitial) {
		super();
		this.abId = abId;
		this.abName = abName;
		this.isNotified = isNotified;
		this.notifTransaksi = notifTransaksi;
		this.corporateId = corporateId;
		this.corpIdRegDate = corpIdRegDate;
		this.compCode = compCode;
		this.compCodeRegDate = compCodeRegDate;
		this.abInitial = abInitial;
	}
	public String getAbId() {
		return abId;
	}
	public void setAbId(String abId) {
		this.abId = abId;
	}
	public String getAbName() {
		return abName;
	}
	public void setAbName(String abName) {
		this.abName = abName;
	}
	public String getIsNotified() {
		return isNotified;
	}
	public void setIsNotified(String isNotified) {
		this.isNotified = isNotified;
	}
	public String getNotifTransaksi() {
		return notifTransaksi;
	}
	public void setNotifTransaksi(String notifTransaksi) {
		this.notifTransaksi = notifTransaksi;
	}
	public String getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(String corporateId) {
		this.corporateId = corporateId;
	}
	public Date getCorpIdRegDate() {
		return corpIdRegDate;
	}
	public void setCorpIdRegDate(Date corpIdRegDate) {
		this.corpIdRegDate = corpIdRegDate;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public Date getCompCodeRegDate() {
		return compCodeRegDate;
	}
	public void setCompCodeRegDate(Date compCodeRegDate) {
		this.compCodeRegDate = compCodeRegDate;
	}
	public String getAbInitial() {
		return abInitial;
	}
	public void setAbInitial(String abInitial) {
		this.abInitial = abInitial;
	}
	@Override
	public String toString() {
		return "Ab [abId=" + abId + ", abName=" + abName + ", isNotified=" + isNotified + ", notifTransaksi="
				+ notifTransaksi + ", corporateId=" + corporateId + ", corpIdRegDate=" + corpIdRegDate + ", compCode="
				+ compCode + ", compCodeRegDate=" + compCodeRegDate + ", abInitial=" + abInitial + "]";
	}
	
	
}
