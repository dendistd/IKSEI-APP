package com.example.iksei.repo;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.iksei.model.Ab;
@Repository
public interface AbRepository extends JpaRepository<Ab, String> {
	//CEK AB_ID ADA DALAM DB
	public Boolean existsByAbId(String abId);

	
}
