package com.example.iksei.payload.ab;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputAb {
	
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
	
	@JsonProperty("comp_code")
	private String compCode;
	
	@JsonProperty("ab_initial")
	private String abInitial;

	public InputAb() {
		super();
	}

	public InputAb(String abId, String abName, String isNotified, String notifTransaksi, String corporateId,
			String compCode, String abInitial) {
		super();
		this.abId = abId;
		this.abName = abName;
		this.isNotified = isNotified;
		this.notifTransaksi = notifTransaksi;
		this.corporateId = corporateId;
		this.compCode = compCode;
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

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getAbInitial() {
		return abInitial;
	}

	public void setAbInitial(String abInitial) {
		this.abInitial = abInitial;
	}

	@Override
	public String toString() {
		return "InputAb [abId=" + abId + ", abName=" + abName + ", isNotified=" + isNotified + ", notifTransaksi="
				+ notifTransaksi + ", corporateId=" + corporateId + ", compCode=" + compCode + ", abInitial="
				+ abInitial + "]";
	}
	
	

}
