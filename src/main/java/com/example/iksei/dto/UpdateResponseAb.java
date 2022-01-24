package com.example.iksei.dto;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateResponseAb {

	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("ab_name_new")
	private String abNameNew;
	
	@JsonProperty("ab_name_old")
	private String abNameOld;

	@JsonProperty("is_notified_new")
	private String isNotifiedNew;
	
	@JsonProperty("is_notified_old")
	private String isNotifiedOld;

	@JsonProperty("notif_transaksi_new")
	private String notifTransaksiNew;
	
	@JsonProperty("notif_transaksi_old")
	private String notifTransaksiOld;
	
	@JsonProperty("corporate_id_new")
	private String corporateIdNew;
	
	@JsonProperty("corporate_id_old")
	private String corporateIdOld;

	@JsonProperty("corp_id_reg_date_new")
	private Date corpIdRegDateNew;
	
	@JsonProperty("corp_id_reg_date_old")
	private Date corpIdRegDateOld;

	@JsonProperty("comp_code_new")
	private String compCodeNew;
	
	@JsonProperty("comp_code_old")
	private String compCodeOld;
	
	@JsonProperty("comp_code_reg_date_new")
	private Date compCodeRegDateNew;
	
	@JsonProperty("comp_code_reg_date_old")
	private Date compCodeRegDateOld;

	@JsonProperty("ab_initial_new")
	private String abInitialNew;

	@JsonProperty("ab_initial_old")
	private String abInitialOld;
	
	public UpdateResponseAb() {
		super();
	}
	
	//CONSTRUCTOR NEW
	public UpdateResponseAb(String abId, String abNameNew, String isNotifiedNew, String notifTransaksiNew,
			String corporateIdNew, Date corpIdRegDateNew, String compCodeNew, Date compCodeRegDateNew,
			String abInitialNew) {
		super();
		this.abId = abId;
		this.abNameNew = abNameNew;
		this.isNotifiedNew = isNotifiedNew;
		this.notifTransaksiNew = notifTransaksiNew;
		this.corporateIdNew = corporateIdNew;
		this.corpIdRegDateNew = corpIdRegDateNew;
		this.compCodeNew = compCodeNew;
		this.compCodeRegDateNew = compCodeRegDateNew;
		this.abInitialNew = abInitialNew;
	}
	
	public UpdateResponseAb(String abId, String abNameNew, String abNameOld, String isNotifiedNew, String isNotifiedOld,
			String notifTransaksiNew, String notifTransaksiOld, String corporateIdNew, String corporateIdOld,
			Date corpIdRegDateNew, Date corpIdRegDateOld, String compCodeNew, String compCodeOld,
			Date compCodeRegDateNew, Date compCodeRegDateOld, String abInitialNew, String abInitialOld) {
		super();
		this.abId = abId;
		this.abNameNew = abNameNew;
		this.abNameOld = abNameOld;
		this.isNotifiedNew = isNotifiedNew;
		this.isNotifiedOld = isNotifiedOld;
		this.notifTransaksiNew = notifTransaksiNew;
		this.notifTransaksiOld = notifTransaksiOld;
		this.corporateIdNew = corporateIdNew;
		this.corporateIdOld = corporateIdOld;
		this.corpIdRegDateNew = corpIdRegDateNew;
		this.corpIdRegDateOld = corpIdRegDateOld;
		this.compCodeNew = compCodeNew;
		this.compCodeOld = compCodeOld;
		this.compCodeRegDateNew = compCodeRegDateNew;
		this.compCodeRegDateOld = compCodeRegDateOld;
		this.abInitialNew = abInitialNew;
		this.abInitialOld = abInitialOld;
	}



	public String getAbId() {
		return abId;
	}

	public void setAbId(String abId) {
		this.abId = abId;
	}

	public String getAbNameNew() {
		return abNameNew;
	}

	public void setAbNameNew(String abNameNew) {
		this.abNameNew = abNameNew;
	}

	public String getAbNameOld() {
		return abNameOld;
	}

	public void setAbNameOld(String abNameOld) {
		this.abNameOld = abNameOld;
	}

	public String getIsNotifiedNew() {
		return isNotifiedNew;
	}

	public void setIsNotifiedNew(String isNotifiedNew) {
		this.isNotifiedNew = isNotifiedNew;
	}

	public String getIsNotifiedOld() {
		return isNotifiedOld;
	}

	public void setIsNotifiedOld(String isNotifiedOld) {
		this.isNotifiedOld = isNotifiedOld;
	}

	public String getNotifTransaksiNew() {
		return notifTransaksiNew;
	}

	public void setNotifTransaksiNew(String notifTransaksiNew) {
		this.notifTransaksiNew = notifTransaksiNew;
	}

	public String getNotifTransaksiOld() {
		return notifTransaksiOld;
	}

	public void setNotifTransaksiOld(String notifTransaksiOld) {
		this.notifTransaksiOld = notifTransaksiOld;
	}

	public String getCorporateIdNew() {
		return corporateIdNew;
	}

	public void setCorporateIdNew(String corporateIdNew) {
		this.corporateIdNew = corporateIdNew;
	}

	public String getCorporateIdOld() {
		return corporateIdOld;
	}

	public void setCorporateIdOld(String corporateIdOld) {
		this.corporateIdOld = corporateIdOld;
	}

	public Date getCorpIdRegDateNew() {
		return corpIdRegDateNew;
	}

	public void setCorpIdRegDateNew(Date corpIdRegDateNew) {
		this.corpIdRegDateNew = corpIdRegDateNew;
	}

	public Date getCorpIdRegDateOld() {
		return corpIdRegDateOld;
	}

	public void setCorpIdRegDateOld(Date corpIdRegDateOld) {
		this.corpIdRegDateOld = corpIdRegDateOld;
	}

	public String getCompCodeNew() {
		return compCodeNew;
	}

	public void setCompCodeNew(String compCodeNew) {
		this.compCodeNew = compCodeNew;
	}

	public String getCompCodeOld() {
		return compCodeOld;
	}

	public void setCompCodeOld(String compCodeOld) {
		this.compCodeOld = compCodeOld;
	}

	public Date getCompCodeRegDateNew() {
		return compCodeRegDateNew;
	}

	public void setCompCodeRegDateNew(Date compCodeRegDateNew) {
		this.compCodeRegDateNew = compCodeRegDateNew;
	}

	public Date getCompCodeRegDateOld() {
		return compCodeRegDateOld;
	}

	public void setCompCodeRegDateOld(Date compCodeRegDateOld) {
		this.compCodeRegDateOld = compCodeRegDateOld;
	}

	public String getAbInitialNew() {
		return abInitialNew;
	}

	public void setAbInitialNew(String abInitialNew) {
		this.abInitialNew = abInitialNew;
	}

	public String getAbInitialOld() {
		return abInitialOld;
	}

	public void setAbInitialOld(String abInitialOld) {
		this.abInitialOld = abInitialOld;
	}

	@Override
	public String toString() {
		return "UpdateInputAb [abId=" + abId + ", abNameNew=" + abNameNew + ", abNameOld=" + abNameOld
				+ ", isNotifiedNew=" + isNotifiedNew + ", isNotifiedOld=" + isNotifiedOld + ", notifTransaksiNew="
				+ notifTransaksiNew + ", notifTransaksiOld=" + notifTransaksiOld + ", corporateIdNew=" + corporateIdNew
				+ ", corporateIdOld=" + corporateIdOld + ", corpIdRegDateNew=" + corpIdRegDateNew
				+ ", corpIdRegDateOld=" + corpIdRegDateOld + ", compCodeNew=" + compCodeNew + ", compCodeOld="
				+ compCodeOld + ", compCodeRegDateNew=" + compCodeRegDateNew + ", compCodeRegDateOld="
				+ compCodeRegDateOld + ", abInitialNew=" + abInitialNew + ", abInitialOld=" + abInitialOld + "]";
	}
	
	
	

	
	
}
