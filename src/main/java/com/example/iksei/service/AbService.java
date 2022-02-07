package com.example.iksei.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.iksei.model.Ab;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.ab.InputAb;
import com.example.iksei.payload.ab.ResponseCreateAb;
import com.example.iksei.payload.ab.ResponseUpdateAb;
import com.example.iksei.payload.ab.UpdateInputAb;

public interface AbService {
	//GET ALL DATA AB
	public List<Ab> showAllAb();
	
	//INSERT DATA AB 
	public ResponseCreateAb insertAb (InputAb input);
	
	//FIND BY ID 
	public Ab findAbById (String id);
	
	//UPDATE 
	public ResponseUpdateAb updateAb (UpdateInputAb input);
	
	//DELETE DATA 
	public Ab deleteAb (String id);
	
	

}
