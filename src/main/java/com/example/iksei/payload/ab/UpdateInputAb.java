package com.example.iksei.payload.ab;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateInputAb {
//	objAb.getAbId(),objAb.getAbName(), objAb.getIsNotified(), objAb.getNotifTransaksi()
//	, objAb.getCorporateId(), objAb.getCorpIdRegDate(), objAb.getCompCode(), objAb.getCompCodeRegDate(), objAb.getAbInitial()
	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("ab_name")
	private String abName;
	
	@JsonProperty("is_notified")
	private String isNotified;
	
	@JsonProperty("notif_transaksi")
	private String notifTransaksi;
	
	@JsonProperty("corporate_id")
	private String corporateId;
	
	@JsonProperty("corp_id_reg_date")
	private Date corpIdRegDate;
	
	@JsonProperty("comp_code")
	private String compCode;
	
	@JsonProperty("comp_code_reg_date")
	private Date compCodeRegDate;
	
	@JsonProperty("ab_initial")
	private String abInitial;
	
	public UpdateInputAb() {
		super();
	}

	public UpdateInputAb(String abId, String abName, String isNotified, String notifTransaksi, String corporateId,
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
		return "UpdateInputAb [abId=" + abId + ", abName=" + abName + ", isNotified=" + isNotified + ", notifTransaksi="
				+ notifTransaksi + ", corporateId=" + corporateId + ", corpRegDate=" + corpIdRegDate + ", compCode="
				+ compCode + ", compCodeRegDate=" + compCodeRegDate + ", abInitial=" + abInitial + "]";
	}
	
	

}
