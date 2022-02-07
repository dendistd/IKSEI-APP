package com.example.iksei.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.groupuser.InputUserGroupKbb;
import com.example.iksei.payload.groupuser.ResponseCreateGroupUserKbb;
import com.example.iksei.payload.groupuser.ResponseUpdateGroupUserKbb;
import com.example.iksei.payload.groupuser.UpdateInputGroupUserKbb;

public interface GroupUserKbbService {
	
	public List<GroupUserKbb> getAllData ();
	
	//FIND BY ID 
	public GroupUserKbb  findGroupUserKbbById(String id);
	
	//CREATE 
	public ResponseCreateGroupUserKbb createGroupUserKbb(InputUserGroupKbb input);
	
	//UPDATE 
	public ResponseUpdateGroupUserKbb updateGroupUserKbb(UpdateInputGroupUserKbb input);
	
	//DELETE 
	public GroupUserKbb deleteGroupUserKbb (String id);

}
