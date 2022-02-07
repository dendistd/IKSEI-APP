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
import com.example.iksei.model.Ab;
import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ErrorSchema;
import com.example.iksei.payload.GagalOutputSchema;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.ab.InputAb;
import com.example.iksei.payload.ab.ResponseCreateAb;
import com.example.iksei.payload.ab.ResponseUpdateAb;
import com.example.iksei.payload.ab.UpdateInputAb;
import com.example.iksei.repo.AbRepository;
import com.example.iksei.service.AbService;


@RestController
public class AbController {
	@Autowired
	AbService abService;
	
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
	
	//GET ALL DATA 
	@GetMapping("/iksei/ab")
	public ResponseEntity<?> getAllAb(){
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.GET_ALL);
		ResponseSchema<List<Ab>> responseSchema = new ResponseSchema<>(errorSchema);
		List<Ab> result = new ArrayList<>();
		try {
			result = abService.showAllAb();
		} 
		catch (Exception e) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> Gagal Menampilkan Semua Data");
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_FIND_ONE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
			responseSchema2.setOutputSchema(new GagalOutputSchema("Show All Data Failed"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}

		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//FIND AB BY ID 
	@GetMapping("/iksei/ab/{id}")
	public ResponseEntity<?> getAbById(@PathVariable("id") String id){
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.FIND_ONE);
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		Ab result = new Ab();
		try {
			result = abService.findAbById(id);
		} catch (Exception e) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller -> Data Dengan ID Yang Diinput Tidak Ada Dalam DB");
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_FIND_ONE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
			responseSchema2.setOutputSchema(new GagalOutputSchema("Data Dengan Id Yang Di Input Tidak Ada Dalam DB"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//INSERT DATA AB 
	@PostMapping("iksei/ab")
	public ResponseEntity<?> createAb (@RequestBody InputAb input){
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseCreateAb result = new ResponseCreateAb();
		ResponseSchema<ResponseCreateAb> responseSchema = new ResponseSchema<>(errorSchema);
		try {
			result = abService.insertAb(input);
			//VALUE AB_ID BUKAN STRING KOSONG ATAU MENGANDUNG SPASI
			if(!(result.getAbId().equals(""))) {
				//AB_ID SUDAH ADA DALAM DB 
				if(result.getAbId().equals("Exist")) {
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Can't Create Cause Ab_id that Imputed Is Already Exist!"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
				//Value Is_Notified nya BUKAN Y atau N
				if(result.getIsNotified().equals("")) {
					ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
					responseFail.setOutputSchema(new GagalOutputSchema("Is_Notified Value That Imputed Is Incorrect!"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				}
				
			} 
			//AB_ID TIDAK SAMA DENGAN "" Atau mengandung Spasi
			else {
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Ab_ID Value is Inccorrect !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
		} catch(Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_CREATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
		
	//UPDATE DATA 
	@PutMapping("iksei/ab")
	public ResponseEntity<?> updateAb(@RequestBody UpdateInputAb input){
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
		ResponseUpdateAb result = new ResponseUpdateAb();
		ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
		Map<String, String> map = new LinkedHashMap<>();
		try {
			result = abService.updateAb(input);
			if(result.getAbId().equals("")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Ab_ID String Kosong");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Ab_Id Is Incorrect !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			if(result.getAbId().equals("Not Exist")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Ab_ID Tidak Ada Dalam DB");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Ab_Id Doesn't Exist On Database"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			if(result.getIsNotifiedOld().equals("")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Value Is_Notified BUKAN Y atau N");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				responseFail.setOutputSchema(new GagalOutputSchema("Value Is_Notified Is Incorrect !"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			if(result.getAbNameOld().equals("No Update") && result.getAbInitialOld().equals("No Update") && result.getCompCodeOld().equals("No Update")
			&& result.getCorporateIdOld().equals("No Update") && result.getIsNotifiedOld().equals("No Update") && result.getNotifTransaksiOld().equals("No Update")) {
				 System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Controller => Tidak Ada Perubahan Data");
				 ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				 ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				 responseFail.setOutputSchema(new GagalOutputSchema("Data Is Not Updated !"));
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				
			}
			
			map.put("ab_id", result.getAbId());
			
			if(result.getAbNameNew()!= null) {
				map.put("ab_name_old", result.getAbNameOld());
				map.put("ab_name", result.getAbNameNew().toUpperCase());
			}
			
			if(result.getIsNotifiedNew()!= null) {
				map.put("is_notified_old", result.getIsNotifiedOld());
				map.put("is_notified_new", result.getIsNotifiedNew().toUpperCase());
			}
			
			if(result.getNotifTransaksiNew()!= null) {
				map.put("notif_transaksi_old", result.getNotifTransaksiOld());
				map.put("notif_transaksi_new", result.getNotifTransaksiNew().toUpperCase());
			}
			
			if(result.getCorporateIdNew()!= null) {
				map.put("corporate_id_old", result.getCorporateIdOld());
				map.put("corporate_id_new", result.getCorporateIdNew().toUpperCase());
			}
			
			if(result.getCompCodeNew()!= null) {
				map.put("comp_code_old", result.getCompCodeOld());
				map.put("comp_code_new", result.getCompCodeNew().toUpperCase());
			}
			
			if(result.getAbInitialNew()!= null) {
				map.put("ab_initial_old", result.getAbInitialOld());
				map.put("ab_initial_new", result.getAbInitialNew().toUpperCase());
			}
			
		} catch (Exception e) {
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema(e.getLocalizedMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}

		responseSchema.setOutputSchema(map);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	//DELETE DATA 
	@DeleteMapping("iksei/ab/{id}")
	public ResponseEntity<?> deleteData(@PathVariable("id") String id){
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		Ab result = new Ab();
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		try {
			result = abService.deleteAb(id);
			
		} catch(Exception e) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Bisa Menghapus Data Yang Tidak Ada Dalam DB");
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_DELETE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			responseFail.setOutputSchema(new GagalOutputSchema("Data Yang Akan Dihapus Tidak Ada Dalam DB"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
	
	
}
