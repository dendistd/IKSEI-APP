package com.example.iksei.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.iksei.enumeration.ErrorEnum;
import com.example.iksei.model.Ab;
import com.example.iksei.payload.ErrorSchema;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.ab.InputAb;
import com.example.iksei.service.AbService;


@RestController
public class AbController {
	@Autowired
	AbService abService;
	
	//GET ALL DATA 
	@GetMapping("/iksei/ab")
	public ResponseEntity<?> getAllAb(){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		ResponseSchema<List<Ab>> responseSchema = new ResponseSchema<>(errorSchema);
		List<Ab> result = new ArrayList<>();
		try {
			result = abService.showAllAb();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//FIND AB BY ID
	@GetMapping("/iksei/ab/{id}")
	public ResponseEntity<?> getAbById(@PathVariable("id") String id){
		ResponseEntity<?> result = abService.findAbById(id);
		return result;
	}
	
	//INSERT/CREATE DATA
	@PostMapping("/iksei/ab")
	public ResponseEntity<?> createAb(@RequestBody InputAb input){
		ResponseEntity<?> result = abService.insertAb(input);
		return result;
	}
	
	//UPDATE DATA  PAKAI MAP
	@PutMapping("/iksei/ab")
	public ResponseEntity<?> updateAb(@RequestBody Map<String, String> map) {
		ResponseEntity<?> result = abService.updateAb(map);
		return result;
	}
	
	//DELETE DATA
	@DeleteMapping("iksei/ab/{id}")
	public ResponseEntity<?> deleteData(@PathVariable("id") String id){
		ResponseEntity<?> result = abService.deleteAb(id);
		return result;
	}
	
}
