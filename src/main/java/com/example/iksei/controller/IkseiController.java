package com.example.iksei.controller;

import java.text.ParseException;
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

import com.example.iksei.dto.UpdateInputAb;
import com.example.iksei.dto.UpdateResponseAb;
import com.example.iksei.enumeration.ErrorEnum;
import com.example.iksei.model.Ab;
import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.model.repo.AbRepository;
import com.example.iksei.payload.ErrorSchema;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.service.AbService;
import com.example.iksei.service.GroupUserKbbService;
import com.example.iksei.service.imple.AbServiceImple;

@RestController
public class IkseiController {
	@Autowired
	AbService abService;
	
	@Autowired
	GroupUserKbbService groupUserKbbService;
	
	//GET ALL DATA 
	@GetMapping("/iksei/ab")
	public ResponseEntity<?> getAllAb(){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		List<Ab> result = abService.showAllAb();
		ResponseSchema<List<Ab>> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//FIND AB BY ID
	@GetMapping("/iksei/ab/{id}")
	public ResponseEntity<?> getAbById(@PathVariable("id") String id){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.SUCCESS);
		Ab result = abService.findAbById(id);
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//INSERT/CREATE DATA
	@PostMapping("/iksei/ab")
	public ResponseEntity<?> createAb(@RequestBody Ab input){
		ResponseSchema<?> responseSchema = abService.insertAb(input);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//UPDATE DATA  PAKAI MAP
	@PutMapping("/iksei/ab")
	public ResponseEntity<?> updateAb(@RequestBody Map<String, Object> map) throws ParseException{
		ResponseSchema<?> result = abService.updateAb(map);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	//DELETE DATA
	@DeleteMapping("iksei/ab/{id}")
	public ResponseEntity<?> deleteData(@PathVariable("id") String id){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		Ab result = abService.deleteAb(id);
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	
	//GET ALL GROUP USER KBB 
	@GetMapping("/iksei/group")
	public ResponseEntity<?> getAll(){
		List<GroupUserKbb> result = groupUserKbbService.getAllData();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		ResponseSchema<List<GroupUserKbb>> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		
	}
	
	//FIND GROUP USER KBB BY ID
	@GetMapping("/iksei/group/{id}")
	public ResponseEntity<?> findGroupUserKbbById(@PathVariable String id){
		GroupUserKbb result = groupUserKbbService.findGroupUserKbbById(id);
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.SUCCESS);
		ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		
	}
	
	//CREATE GROUP USER KBB
	@PostMapping("iksei/group")
	public ResponseEntity<?> createGroupUserKbb(@RequestBody GroupUserKbb input){
		ResponseSchema<?> result = groupUserKbbService.createGroupUserKbb(input);
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	//UPDATE 
	@PutMapping("/iksei/group")
	public ResponseEntity<?> updateGroupUserKbb(@RequestBody Map<String, Object> map) throws ParseException{
		ResponseSchema<?> result = groupUserKbbService.updateGroupUserKbb(map);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	//DELETE DATA
	@DeleteMapping("iksei/group/{id}")
	public ResponseEntity<?> deleteGroupUserKbbById(@PathVariable String id){
		GroupUserKbb result = groupUserKbbService.deleteGroupUserKbb(id);
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		
	}
}
