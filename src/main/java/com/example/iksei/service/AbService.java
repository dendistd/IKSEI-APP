package com.example.iksei.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.example.iksei.dto.UpdateInputAb;
import com.example.iksei.dto.UpdateResponseAb;
import com.example.iksei.model.Ab;
import com.example.iksei.payload.ResponseSchema;

public interface AbService {
	//GET ALL DATA AB
	public List<Ab> showAllAb();
	
	//INSERT DATA AB
	public ResponseSchema<?> insertAb(Ab input);
	
	//FIND BY ID
	public Ab findAbById(String id);
	
	//SERVICE UPDATE YANG DIPAKE
	public ResponseSchema<?> updateAb (Map<String, Object> map) throws ParseException;
	
	//DELETE DATA 
	public Ab deleteAb(String id);

}
