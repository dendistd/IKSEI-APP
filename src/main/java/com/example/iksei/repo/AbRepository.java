package com.example.iksei.repo;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.iksei.model.Ab;
@Repository
public interface AbRepository extends JpaRepository<Ab, String> {
	//CEK AB ID
	@Query(value = "SELECT ab_id FROM DENDI_AB WHERE ab_id = :ab_id", nativeQuery = true)
	public Map<String, String> cekAbId(@PathVariable("ab_id") String ab_id);
	
	//NILAI VALUE UNTUK IS_NOTIFIED HARUS 'Y' ATAU 'N'
	@Query(value = "SELECT is_notified FROM DENDI_AB Where is_notified IN('Y', 'N')", nativeQuery = true)
	public Map<String, String> cekIsNotified();
	
}
