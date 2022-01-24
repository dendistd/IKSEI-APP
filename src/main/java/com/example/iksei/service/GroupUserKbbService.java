package com.example.iksei.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ResponseSchema;

public interface GroupUserKbbService {
	
	public List<GroupUserKbb> getAllData ();
	
	public GroupUserKbb findGroupUserKbbById(String id);
	
	public ResponseSchema<?> createGroupUserKbb(GroupUserKbb input);
	
	public ResponseSchema<?> updateGroupUserKbb(Map<String, Object> map) throws ParseException;
	
	public GroupUserKbb deleteGroupUserKbb(String id);
	

}
