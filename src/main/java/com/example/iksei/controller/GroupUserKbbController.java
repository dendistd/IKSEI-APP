package com.example.iksei.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.example.iksei.payload.groupuser.ResponseCreateGroupUserKbb;
import com.example.iksei.payload.groupuser.ResponseUpdateGroupUserKbb;
import com.example.iksei.payload.groupuser.UpdateInputGroupUserKbb;
import com.example.iksei.service.GroupUserKbbService;

@RestController
public class GroupUserKbbController {
	@Autowired
	GroupUserKbbService groupUserKbbService;
	
	//FUNGSI UNTUK NUMBER LOG
	public String logNumber() {
		StringBuilder builder = new StringBuilder(6);
		Random random = new Random();		
		for(int i = 0; i < 6; i++) {
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}
			
	//FUNGSI DATE UNTUK LOG
	public String dateLog() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
	}
	
	//GET ALL GROUP USER KBB 
		@GetMapping("/iksei/group")
		public ResponseEntity<?> getAll(){
			String valueLog = logNumber();
			List<GroupUserKbb> result = new ArrayList<>();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
			ResponseSchema<List<GroupUserKbb>> responseSchema = new ResponseSchema<>(errorSchema);
			try {
				result = groupUserKbbService.getAllData();
				
			} catch (Exception e) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller-> Gagal Menampilkan Semua Data");
				ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_GET_ALL);
				ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
				responseSchema2.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
			}
		
			responseSchema.setOutputSchema(result);
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
			
		}
		
		//FIND GROUP USER KBB BY ID 
		@GetMapping("/iksei/group/{id}")
		public ResponseEntity<?> findGroupUserKbbById(@PathVariable String id){
			String valueLog = logNumber();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.FIND_ONE);
			ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
			GroupUserKbb result = new GroupUserKbb();
			try {
				result = groupUserKbbService.findGroupUserKbbById(id);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data = "+ result.toString());
			} catch (Exception e ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Dengan ID Yang Diinput Tidak Ada Dalam DB");
				ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_FIND_ONE);
				ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
				responseSchema2.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
			}
			responseSchema.setOutputSchema(result);
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
			
		}
		
		//CREATE GROUP USER KBB 
		@PostMapping("iksei/group")
		public ResponseEntity<?> createGroupUserKbb(@RequestBody InputUserGroupKbb input){
			String valueLog = logNumber();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
			ResponseCreateGroupUserKbb result = new ResponseCreateGroupUserKbb();
			ResponseSchema<ResponseCreateGroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
			try {
				result = groupUserKbbService.createGroupUserKbb(input);
				if(result.getAbId().equals("") ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> Ab_ID Yang DI Input Berisi String Kosong Atau Spasi");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Ab_ID That Imputed Doesn't Exist In DB"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
				
				if(result.getIsNew().equals("") ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> Nilai Is_New BUKAN Y atau N");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Value for Is_New Is Incorrect!"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
			
			} catch (Exception e) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			responseSchema.setOutputSchema(result);
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		}
		
		//UPDATE  
		@PutMapping("iksei/group")
		public ResponseEntity<?> updateGroupUserKbb(@RequestBody UpdateInputGroupUserKbb input) {
			String valueLog = logNumber();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
			ResponseUpdateGroupUserKbb result = new ResponseUpdateGroupUserKbb();
			ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
			Map<String, String> map = new LinkedHashMap<>();
			
			try {
				result = groupUserKbbService.updateGroupUserKbb(input);
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller Isi dari result =  "+result.toString());	
//				 if( !result.getId().equals("") && !result.getId().equals("Not Exist") ) {
//					 System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> ID Ada Dalam Db ");
				if(result.getId().equals("")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Id String Kosong");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Value Id Is Incorrect !"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
			
				if(result.getId().equals("Not Exist")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Id Tidak Ada Dalam DB");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Id Doesn't Exist On Database"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
				
				if(result.getAbIdOld().equals("Not Exist") ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> Ab Id Tidak Ada Dalam DB ");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("AB_Id Doesn't Exist On Database"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
			
				//Nilai Is New BUKAN Y atau N
				if(result.getIsNewOld().equals("")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Is_New Bukan Y atau N");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Is_New Value That Imputed is Incorrect !"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}	 
					
				if(result.getAbIdOld().equals("No Update") && result.getGroupUserCodeOld().equals("No Update") && result.getIsNewOld().equals("No Update")) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Tidak Ada Perubahan Data");
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Data Is Not Updated !"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}

				map.put("id", result.getId());
				
				if(result.getAbIdNew()!= null) {
					map.put("ab_id_old", result.getAbIdOld());
					map.put("ab_id_new", result.getAbIdNew().toUpperCase());
				}
				
				if(result.getGroupUserCodeNew()!= null) {
					map.put("group_user_code_old", result.getGroupUserCodeOld());
					map.put("group_user_code_new", result.getGroupUserCodeNew().toUpperCase());
				}
				
				if(result.getIsNewNew()!= null) {
					map.put("is_new_old", result.getIsNewOld());
					map.put("is_new_new", result.getIsNewNew().toUpperCase());
				}
//			}
			
			} catch (Exception e) {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			responseSchema.setOutputSchema(map);
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller, selesai set OutputSchema ");
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		}
			
		//DELETE DATA 
		@DeleteMapping("iksei/group/{id}")
		public ResponseEntity<?> deleteGroupUserKbbById (@PathVariable("id") String id){
			String valueLog = logNumber();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
			GroupUserKbb result = new GroupUserKbb();
			ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
			
			try {
				result = groupUserKbbService.deleteGroupUserKbb(id);
			} catch(Exception e) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Tidak Ada Dalam DB");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			responseSchema.setOutputSchema(result);
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		}
}
