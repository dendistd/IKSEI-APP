package com.example.iksei.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.iksei.model.Ab;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.ab.InputAb;

public interface AbService {
	//GET ALL DATA AB
	public List<Ab> showAllAb();
	
	//INSERT DATA AB
	public ResponseEntity<?> insertAb(InputAb input);
	
	//FIND BY ID
	public ResponseEntity<?> findAbById(String id);
	
	//SERVICE UPDATE YANG DIPAKE
	public ResponseEntity<?> updateAb (Map<String, String> map) ;
	
	//DELETE DATA 
	public ResponseEntity<?> deleteAb(String id);

}
