package com.example.iksei.service.imple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.iksei.enumeration.ErrorEnum;
import com.example.iksei.model.GroupUserKbb;
import com.example.iksei.payload.ErrorSchema;
import com.example.iksei.payload.GagalOutputSchema;
import com.example.iksei.payload.ResponseSchema;
import com.example.iksei.payload.groupuser.InputUserGroupKbb;
import com.example.iksei.payload.groupuser.ResponseCreateGroupUserKbb;
import com.example.iksei.payload.groupuser.ResponseUpdateGroupUserKbb;
import com.example.iksei.payload.groupuser.UpdateInputGroupUserKbb;
import com.example.iksei.repo.AbRepository;
import com.example.iksei.repo.GroupUserKbbRepository;
import com.example.iksei.service.GroupUserKbbService;


@Service
public class GroupUserKbbServiceImple implements GroupUserKbbService {
	@Autowired
	GroupUserKbbRepository groupUserKbbRepository;
	@Autowired
	AbRepository abRepository;
	
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
	public List<GroupUserKbb> getAllData (){
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		List<GroupUserKbb> result = new ArrayList<>();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - List<GroupUserKbb> telah dibuat ");
		result = groupUserKbbRepository.findAll();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menampilkan Semua Data Group User Kbb ");
		return result;
	}
	
	//FIND DATA BY ID 
	public GroupUserKbb findGroupUserKbbById (String id) {
		GroupUserKbb result = groupUserKbbRepository.findById(id).get();
		return result;
	}
	
	//INSERT DATA GROUP USER KBB 
	public ResponseCreateGroupUserKbb createGroupUserKbb(InputUserGroupKbb input){
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		//Generate STring ID dengan fungsi dari Java -> RandomStringUtils
		String stringId = RandomStringUtils.randomAlphanumeric(32);
		
		GroupUserKbb dataGroup = new GroupUserKbb(input.getAbId().toUpperCase(), input.getGroupUserCode().toUpperCase()
				, input.getIsNew().toUpperCase());
		ResponseCreateGroupUserKbb responseCGK = new ResponseCreateGroupUserKbb(dataGroup.getAbId(), dataGroup.getGroupUserCode(), dataGroup.getIsNew());

			//  BISA CREATE KARNA AB_ID ADA DALAM TABEL DENDI_AB
			if(abRepository.existsById(input.getAbId().toUpperCase())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - AB_ID ada dalam DB");	
				//NILAI IS_NEW HANYA UNTUK "Y", "N"
				if( (input.getIsNew().equalsIgnoreCase("Y") || input.getIsNew().equalsIgnoreCase("N")) ){
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - kondisi terpenuhi, value input param is_notified sama dengan Y or N or y or n, kemudian persiapan save ke db");
					dataGroup.setId(stringId.toUpperCase());
					Date date = new Date();
					dataGroup.setRegDate(date);
					dataGroup = groupUserKbbRepository.save(dataGroup);
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - save ke db selesai, isi dari dataAb = "+dataGroup.toString());		
					
				}
				
				//Else Untuk VALUE IS_NEW SELAIN (Y, N, y, n)
				else {
					System.out.println("["+valueLog + "]" +" - " +dateLog() + " - value dari is_new selain dari Y,N,y,ataupun n ");
					//SET IS_NEW =>  ""
					dataGroup.setIsNew("");
					responseCGK.setIsNew(dataGroup.getIsNew());
					
					dataGroup.setId(stringId.toUpperCase());
					Date date = new Date();
					responseCGK.setRegDate(date);
				}

			} //AKHIR IF -> BISA CREATE KARNA AB_ID ADA DALAM TABEL DENDI_AB
						
			//GAK BISA CREATE KARNA AB_ID TIDAK ADA DALAM DENDI_AB
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - CREATE AB GAGAL KARNA AB_ID YANG DIINPUT TIDAK ADA DALAM DB");
				//SET AB_ID => ""
				dataGroup.setAbId("");
				responseCGK.setAbId(dataGroup.getAbId());
				dataGroup.setId(stringId.toUpperCase());
				Date date = new Date();
				dataGroup.setRegDate(date);
			}
		
		responseCGK.setId(dataGroup.getId());
		responseCGK.setRegDate(dataGroup.getRegDate());
		System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Isi dari responseCGK = " + responseCGK.toString());
		return responseCGK;
	
	}
	
	//UPDATE 
	public ResponseUpdateGroupUserKbb updateGroupUserKbb(UpdateInputGroupUserKbb input){
		String valueLog = logNumber();
		ResponseUpdateGroupUserKbb responseGagal = new ResponseUpdateGroupUserKbb();

		// TIDAK BISA UPDATE -> ID HANYA BERISI STRING KOSONG ATAU MENGANDUNG SPASI
		if(input.getId().equals("") || input.getId().contains(" ")) {
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - ID Yang DI Input mengandung string kosong atau spasi");
			responseGagal.setId("");
			return responseGagal;
		}
		//TIDAK BISA UPDATE-> ID YANG DI INPUT TIDAK ADA DALAM DB
		if(groupUserKbbRepository.existsById(input.getId()) == false) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Id Tidak Ada Dalam DB");
			responseGagal.setId("Not Exist");
			return responseGagal;
		}
		
		GroupUserKbb guk = groupUserKbbRepository.findById(input.getId().toUpperCase()).get();
		//TAMPUNG DATA SEBELUM UPDATE
		ResponseUpdateGroupUserKbb responseUpdate = new ResponseUpdateGroupUserKbb(guk.getId(), guk.getAbId(), guk.getGroupUserCode(), guk.getIsNew());
		String[] penanda = new String[3];
		
		//Bisa Update Id -> Ada Dalam DB 
		if (groupUserKbbRepository.existsById(input.getId())) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Id  Ada Dalam DB");
		//BISA UPDATE-> AB_ID ADA DALAM TABEL DENDI_AB
			if(abRepository.existsByAbId(input.getAbId().toUpperCase())) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - AB_ID Ada Dalam DB");
				//BISA UPDATE -> AB ID BEDA DENGAN DB
				if(!(input.getAbId().equalsIgnoreCase(guk.getAbId()) ) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - AB_ID Yang Di Input Beda Dengan DB");
					guk.setAbId(input.getAbId().toUpperCase());
					penanda[0] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Untuk Ab_Id");
					penanda[0] = "NoUpdate";
				}
			
			}
			//AB_ID TIDAK ADA DALAM DB
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - AB_ID TIDAK Ada Dalam DB");
				responseUpdate.setAbIdOld("Not Exist");
				responseUpdate.setId(guk.getAbId());
				return responseUpdate;
				
			}
		
			//CEK ADAKAH UPDATE PADA GROUP USER CODE
			if(!(input.getGroupUserCode().equalsIgnoreCase(guk.getGroupUserCode()) ) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Group User Code Yang Di Input BERBEDA dengan Yang DB");
				guk.setGroupUserCode(input.getGroupUserCode().toUpperCase());
				penanda[1] = "Terupdate";
			}
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Untuk group_user_code");
				penanda[1] = "NoUpdate";
			}
		
			//CEK ADAKAH UPDATE PADA IS_NEW
			// BISA UPDATE-> NILAI IS_NEW HARUS Y ATAU N
			if(input.getIsNew().equalsIgnoreCase("y") || input.getIsNew().equalsIgnoreCase("n") ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nilai IS NEW Sesuai Kondisi -> Y or N");
				//BISA UPDATE -> IS_NEW YANG DI INPUT BERBEDA DENGAN YG DI DB
				if(!(input.getIsNew().equalsIgnoreCase(guk.getIsNew()) ) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - IS_NEW Yang Di Input BERBEDA dengan Yang DB");
					guk.setIsNew(input.getIsNew().toUpperCase());
					penanda[2] = "Terupdate";
				}
				// IS_NEW INPUT SAMA DENGAN DB
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Untuk IS_NEW");
					penanda[2] = "NoUpdate";
				}
			}
			// TIDAK BISA UPDATE -> NILAI IS_NEW TIDAK SAMA DENGAN Y ATAU N
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nilai IS NEW Bukan Y ataupun N");
				responseUpdate.setIsNewOld("");
				return responseUpdate;
			}
		
		}//AKHIR IF ID ADA DALAM DB
		
		//SAVE KE DB
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Proses Akan Save ke DB ");
		guk = groupUserKbbRepository.save(guk);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save Ke DB Selesai ");

		//KONDISI UNTUK SELURUH PENANDA -> "NOUPDATE"
		if(penanda[0].equals("NoUpdate") && penanda[1].equals("NoUpdate") && penanda[2].equals("NoUpdate")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - TIDAK ADA UPDATE DATA ");
			responseUpdate.setAbIdOld("No Update");
			responseUpdate.setGroupUserCodeOld("No Update");
			responseUpdate.setIsNewOld("No Update");
		}
		//ATUR ISIAN RESPONSE API 
		//id
		responseUpdate.getId();		
		//ab_id 
		if(penanda[0].equals("Terupdate")) {
			responseUpdate.getAbIdOld();
			responseUpdate.setAbIdNew(guk.getAbId().toUpperCase());
		}
		
		//group_user_code
		if(penanda[1].equals("Terupdate")) {
			responseUpdate.getGroupUserCodeOld();
			responseUpdate.setGroupUserCodeNew(guk.getGroupUserCode().toUpperCase());
		}
		
		//is_new
		if(penanda[2].equals("Terupdate")) {
			responseUpdate.getIsNewOld();
			responseUpdate.setIsNewNew(guk.getIsNew().toUpperCase());
		}
				
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi dari responseUpdate =  "+responseUpdate.toString());
		return responseUpdate;		
	}
	
	//DELETE DATA  
	public GroupUserKbb deleteGroupUserKbb (String id) {
		String valueLog = logNumber();
		GroupUserKbb findData = groupUserKbbRepository.findById(id).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil ditemukan");
		GroupUserKbb result = new GroupUserKbb(findData.getId(), findData.getAbId(), findData.getGroupUserCode(), findData.getRegDate(), findData.getIsNew());
		groupUserKbbRepository.delete(findData);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil dihapus");
		return result;
	}
}
