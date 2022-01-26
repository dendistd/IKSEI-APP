package com.example.iksei.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.groupuser.InputUserGroupKbb;

public interface GroupUserKbbService {
	
	public List<GroupUserKbb> getAllData ();
	
	public ResponseEntity<?> findGroupUserKbbById(String id);
	
	public ResponseEntity<?> createGroupUserKbb(InputUserGroupKbb input);
	
	public ResponseEntity<?> updateGroupUserKbb(Map<String, String> map);
	
	public ResponseEntity<?> deleteGroupUserKbb(String id);
	

}
