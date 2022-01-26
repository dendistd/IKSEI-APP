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
import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ErrorSchema;
import com.example.iksei.payload.GagalOutputSchema;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.groupuser.InputUserGroupKbb;
import com.example.iksei.service.GroupUserKbbService;

@RestController
public class GroupUserKbbController {
	@Autowired
	GroupUserKbbService groupUserKbbService;
	
	//GET ALL GROUP USER KBB 
		@GetMapping("/iksei/group")
		public ResponseEntity<?> getAll(){
			List<GroupUserKbb> result = new ArrayList<>();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
			ResponseSchema<List<GroupUserKbb>> responseSchema = new ResponseSchema<>(errorSchema);
			try {
				result = groupUserKbbService.getAllData();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			responseSchema.setOutputSchema(result);
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
			
		}
		
		//FIND GROUP USER KBB BY ID
		@GetMapping("/iksei/group/{id}")
		public ResponseEntity<?> findGroupUserKbbById(@PathVariable String id){
			ResponseEntity<?> result = groupUserKbbService.findGroupUserKbbById(id);
			return result;
			
		}
		
		//CREATE GROUP USER KBB
		@PostMapping("iksei/group")
		public ResponseEntity<?> createGroupUserKbb(@RequestBody InputUserGroupKbb input){
			ResponseEntity<?> result = groupUserKbbService.createGroupUserKbb(input);
			return result;
			
		}
		
		//UPDATE 
		@PutMapping("/iksei/group")
		public ResponseEntity<?> updateGroupUserKbb(@RequestBody Map<String, String> map) throws ParseException{
			ResponseEntity<?> result = groupUserKbbService.updateGroupUserKbb(map);
			return result;
		}
		//DELETE DATA
		@DeleteMapping("iksei/group/{id}")
		public ResponseEntity<?> deleteGroupUserKbbById(@PathVariable String id){
			ResponseEntity<?> result = groupUserKbbService.deleteGroupUserKbb(id);
			return result;

		}
}