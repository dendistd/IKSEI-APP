package com.example.iksei.model.repo;

import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.iksei.model.GroupUserKbb;

@Repository
public interface GroupUserKbbRepository extends JpaRepository<GroupUserKbb, String> {
	
	//CEK ID 
	@Query(value = "SELECT id FROM dendi_user_group_kbb WHERE id = :id", nativeQuery = true)
	public Map<String, String> cekId(@PathVariable("id") String id);
	
	//NILAI VALUE UNTUK IS_NEW HARUS 'Y' ATAU 'N'
	@Query(value = "SELECT is_new FROM dendi_user_group_kbb Where is_new IN('Y', 'N')", nativeQuery = true)
	public Map<String, String> cekIsNew();
	
	//CEK AB_ID YANG DIINPUT PARAM ADA DITABEL DENDI_AB
	@Query(value = "SELECT ab_id FROM DENDI_AB WHERE ab_id = :ab_id", nativeQuery = true)
	public Map<String, String> cekAbId(@PathParam("ab_id") String ab_id);
	
	// (BELOM DIPAKE) CREATE GROUP USER KBB DENGAN VALUE AB_ID NYA ADA/TERDAPAT DI TABEL DENDI_AB
	@Query(value = "INSERT INTO DENDI_USER_GROUP_KBB (ID, AB_ID, GROUP_USER_CODE, REG_DATE, IS_NEW) VALUES ('TEST2349',\r\n" + 
			"(SELECT ab_id FROM DENDI_AB WHERE ab_id = 'IK008' ), 'ECM03', TO_DATE('2022-01-19 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Y')", nativeQuery = true)
	public Map<String, String> insertGroupUserKbb();

}
