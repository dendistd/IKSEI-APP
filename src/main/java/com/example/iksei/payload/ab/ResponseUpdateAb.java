package com.example.iksei.payload.ab;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseUpdateAb {
	
	@JsonProperty("ab_id")
	private String abId;
	
	@JsonProperty("ab_name_old")
	private String abNameOld;
	
	@JsonProperty("ab_name_new")
	private String abNameNew;
	
	@JsonProperty("is_notified_old")
	private String isNotifiedOld;
	
	@JsonProperty("is_notified_new")
	private String isNotifiedNew;
	
	@JsonProperty("notif_transaksi_old")
	private String notifTransaksiOld;
	
	@JsonProperty("notif_transaksi_new")
	private String notifTransaksiNew;
	
	@JsonProperty("corporate_id_old")
	private String corporateIdOld;
	
	@JsonProperty("corporate_id_new")
	private String corporateIdNew;
	
	@JsonProperty("comp_code_old")
	private String compCodeOld;
	
	@JsonProperty("comp_code_new")
	private String compCodeNew;

	@JsonProperty("ab_initial_old")
	private String abInitialOld;
	
	@JsonProperty("ab_initial_new")
	private String abInitialNew;

	public ResponseUpdateAb() {
		super();
	}
	
	public ResponseUpdateAb(String abId, String abNameOld, String isNotifiedOld, String notifTransaksiOld,
			String corporateIdOld, String compCodeOld, String abInitialOld) {
		super();
		this.abId = abId;
		this.abNameOld = abNameOld;
		this.isNotifiedOld = isNotifiedOld;
		this.notifTransaksiOld = notifTransaksiOld;
		this.corporateIdOld = corporateIdOld;
		this.compCodeOld = compCodeOld;
		this.abInitialOld = abInitialOld;
	}

	public ResponseUpdateAb(String abId, String abNameOld, String abNameNew, String isNotifiedOld, String isNotifiedNew,
			String notifTransaksiOld, String notifTransaksiNew, String corporateIdOld, String corporateIdNew,
			String compCodeOld, String compCodeNew, String abInitialOld, String abInitialNew) {
		super();
		this.abId = abId;
		this.abNameOld = abNameOld;
		this.abNameNew = abNameNew;
		this.isNotifiedOld = isNotifiedOld;
		this.isNotifiedNew = isNotifiedNew;
		this.notifTransaksiOld = notifTransaksiOld;
		this.notifTransaksiNew = notifTransaksiNew;
		this.corporateIdOld = corporateIdOld;
		this.corporateIdNew = corporateIdNew;
		this.compCodeOld = compCodeOld;
		this.compCodeNew = compCodeNew;
		this.abInitialOld = abInitialOld;
		this.abInitialNew = abInitialNew;
	}
	
	public String getAbId() {
		return abId;
	}

	public void setAbId(String abId) {
		this.abId = abId;
	}

	public String getAbNameOld() {
		return abNameOld;
	}

	public void setAbNameOld(String abNameOld) {
		this.abNameOld = abNameOld;
	}

	public String getAbNameNew() {
		return abNameNew;
	}

	public void setAbNameNew(String abNameNew) {
		this.abNameNew = abNameNew;
	}

	public String getIsNotifiedOld() {
		return isNotifiedOld;
	}

	public void setIsNotifiedOld(String isNotifiedOld) {
		this.isNotifiedOld = isNotifiedOld;
	}

	public String getIsNotifiedNew() {
		return isNotifiedNew;
	}

	public void setIsNotifiedNew(String isNotifiedNew) {
		this.isNotifiedNew = isNotifiedNew;
	}

	public String getNotifTransaksiOld() {
		return notifTransaksiOld;
	}

	public void setNotifTransaksiOld(String notifTransaksiOld) {
		this.notifTransaksiOld = notifTransaksiOld;
	}

	public String getNotifTransaksiNew() {
		return notifTransaksiNew;
	}

	public void setNotifTransaksiNew(String notifTransaksiNew) {
		this.notifTransaksiNew = notifTransaksiNew;
	}

	public String getCorporateIdOld() {
		return corporateIdOld;
	}

	public void setCorporateIdOld(String corporateIdOld) {
		this.corporateIdOld = corporateIdOld;
	}

	public String getCorporateIdNew() {
		return corporateIdNew;
	}

	public void setCorporateIdNew(String corporateIdNew) {
		this.corporateIdNew = corporateIdNew;
	}

	public String getCompCodeOld() {
		return compCodeOld;
	}

	public void setCompCodeOld(String compCodeOld) {
		this.compCodeOld = compCodeOld;
	}

	public String getCompCodeNew() {
		return compCodeNew;
	}

	public void setCompCodeNew(String compCodeNew) {
		this.compCodeNew = compCodeNew;
	}

	public String getAbInitialOld() {
		return abInitialOld;
	}

	public void setAbInitialOld(String abInitialOld) {
		this.abInitialOld = abInitialOld;
	}

	public String getAbInitialNew() {
		return abInitialNew;
	}

	public void setAbInitialNew(String abInitialNew) {
		this.abInitialNew = abInitialNew;
	}

	@Override
	public String toString() {
		return "ResponseUpdateAb [abId=" + abId + ", abNameOld=" + abNameOld + ", abNameNew=" + abNameNew
				+ ", isNotifiedOld=" + isNotifiedOld + ", isNotifiedNew=" + isNotifiedNew + ", notifTransaksiOld="
				+ notifTransaksiOld + ", notifTransaksiNew=" + notifTransaksiNew + ", corporateIdOld=" + corporateIdOld
				+ ", corporateIdNew=" + corporateIdNew + ", compCodeOld=" + compCodeOld + ", compCodeNew=" + compCodeNew
				+ ", abInitialOld=" + abInitialOld + ", abInitialNew=" + abInitialNew + "]";
	}
	
	

}
