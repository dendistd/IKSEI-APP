package com.example.iksei.service.imple;

import java.text.DateFormat;
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
import org.springframework.stereotype.Service;

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


@Service
public class AbServiceImple implements AbService {
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
	//fungsi NOTIF TRANSAKSI 
	static String fungsiNotifTransaksi(String input) {
		int jumlahInput = input.split(";").length;
		int valueLoop = 6;
		String[] isiRow = input.split(";");
		String result ="";
		for(int i = 0; i < jumlahInput; i++) {
			for(int j = 1; j <= valueLoop; j++) {
				result += j+isiRow[i] + ";";
			}
		}

		return result; 
	}
	
	//GET ALL DATA 
	public List<Ab> showAllAb(){
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		List<Ab> listAb = new ArrayList<>();
		listAb = abRepository.findAll();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menampilkan Semua Data Ab");
		return listAb;
	}
	
	//INSERT DATA 
	public ResponseCreateAb insertAb(InputAb input) {	
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		Ab dataAb = new Ab(input.getAbId().toUpperCase(), input.getAbName().toUpperCase(), input.getIsNotified().toUpperCase()
				, fungsiNotifTransaksi(input.getNotifTransaksi().toUpperCase()), input.getCorporateId().toUpperCase()
				, input.getCompCode().toUpperCase(), input.getAbInitial().toUpperCase());
		ResponseCreateAb responseCAb = new ResponseCreateAb(dataAb.getAbId(), dataAb.getAbName(), dataAb.getIsNotified(),
				dataAb.getNotifTransaksi(), dataAb.getCorporateId(), dataAb.getCompCode(), dataAb.getAbInitial());
			
			//Bisa Create -> ab_id NILAINYA TIDAK STRING KOSONG ATAU HANYA BERISI SPASI
			if(!(input.getAbId().length() == 0 || input.getAbId().contains(" "))) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Key AB_ID Di Inputkan Di Input Paramnya");
					//AB_ID TIDAK ADA DALAM DB, SEHINGGA BISA CREATE AB 
					if(abRepository.existsByAbId(input.getAbId().toUpperCase()) == false  ) {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_id yang di input belum ada di db, sehingga bisa create");
						//BISA CREATE -> NILAI IS_NOTIFIED HANYA UNTUK "Y", "N"
						if( (input.getIsNotified().equalsIgnoreCase("y") || input.getIsNotified().equalsIgnoreCase("n")) ){
							System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Kondisi terpenuhi, value input param is_notified sama dengan Y or N or y or n, kemudian persiapan save ke db");
							Date date= new Date();
							//SET COMPCODEREGDATE & CORPIDREGDATE
							dataAb.setCompCodeRegDate(date);		
							dataAb.setCorpIdRegDate(date);	
							dataAb = abRepository.save(dataAb);
							System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save ke db selesai, isi dari dataAb = "+dataAb.toString());
					
						}
						//GA BISA CREATE -> VALUE IS_NOTIFIED SELAIN (Y, N, y, n)
						else {
							System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value dari Inputan is_notified selain dari Y,N,y,ataupun n ");
							responseCAb.setIsNotified("");
						}
					}//AKHIR BLOCK IF YANG AB_ID TIDAK ADA DALAM DB	
					
					//AB_ID ADA DALAM DB, SEHINGGA TIDAK BISA CREATE AB
					else {	
						System.out.println("["+valueLog + "]" +" - "+dateLog() +" - AB_ID sudah terdapat dalam db, sehingga gak bisa create");
						responseCAb.setAbId("Exist");
					}	

			}//AKHIR IF AB_ID TIDAK STRING KOSONG /SPASI
			
			//JIKA AB_ID TIDAK DI INPUT KE INPUT PARAMNYA, MAKA TIDAK BISA PROSES INSERT DATA
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nilai Ab_Id nya String Kosong atau Hanya Spasi");
				responseCAb.setAbId("");
			}
			
		responseCAb.setCorpIdRegDate(dataAb.getCorpIdRegDate());
		responseCAb.setCompCodeRegDate(dataAb.getCompCodeRegDate());
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi dari responseCAb = " + responseCAb.toString());
		return responseCAb;
		
	}
	
	//FIND BY ID 
	public Ab findAbById (String id) {
		String valueLog = logNumber();
		Ab result = abRepository.findById(id).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data");
		return result;
	}
	
	//UPDATE AB 
	public ResponseUpdateAb updateAb (UpdateInputAb input){
		String valueLog = logNumber();
		ResponseUpdateAb responseGagal = new ResponseUpdateAb();
		
		// TIDAK BISA UPDATE -> AB ID HANYA BERISI STRING KOSONG ATAU MENGANDUNG SPASI
		if(input.getAbId().equals("")|| input.getAbId().contains(" ")) {
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - abId Yang DI Input mengandung string kosong atau spasi");
			responseGagal.setAbId("");
			return responseGagal;
		}
		
		//TIDAK BISA UPDATE -> AB_ID TIDAK ADA DALAM DB
		if (abRepository.existsByAbId(input.getAbId().toUpperCase()) == false) {
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - abId Yang DI Input Tidak Ada Dalam DB");
			responseGagal.setAbId("Not Exist");
			return responseGagal;
		}
		
		Ab ab = abRepository.findById(input.getAbId().toUpperCase()).get();  	
//		//Tampung Data hasil dari Find By Ab ID
		ResponseUpdateAb responseUpdate = new ResponseUpdateAb(ab.getAbId().toUpperCase(), ab.getAbName().toUpperCase(), ab.getIsNotified().toUpperCase()
				, ab.getNotifTransaksi().toUpperCase(), ab.getCorporateId().toUpperCase(), ab.getCompCode().toUpperCase(), ab.getAbInitial().toUpperCase());
		String[] penanda = new String[6]; 
	
		// Bisa Update -> AB ID Ada Dalam DB
		if(abRepository.existsByAbId(input.getAbId().toUpperCase())) {
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - abId Yang Di Input Ada Dalam DB");
			
			//Bisa Update-> AB NAME yang DI Input BEda Dengan yang DI DB
			if(!(input.getAbName().equalsIgnoreCase(ab.getAbName())) ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - ab_name Yang DI Input Beda Dengan DB");
				ab.setAbName(input.getAbName().toUpperCase());
				penanda[0] = "Terupdate";
			
			}
			else {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - No Update Untuk Ab_Name");
				penanda[0] = "No Update";
			}
			
			
			//Value IS NOTIFIED HARUS BERNILAI "Y" ATAU "N"
			if(input.getIsNotified().equalsIgnoreCase("Y") || input.getIsNotified().equalsIgnoreCase("N") ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Value Is Notified Sesuai -> Y atau N");
				//Bisa Update-> IS NOTIFIED yang DI Input BEda Dengan yang DI DB
				if( !( input.getIsNotified().equalsIgnoreCase(ab.getIsNotified()) ) ) {
					System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Value Is_Notified BEDA dengan DB");
					ab.setIsNotified(input.getIsNotified().toUpperCase());
					penanda[1] = "Terupdate";
				} 
				//TIDAK BISA UPDATE , NILAI IS_NOTIFIED SAMA DENGAN DB 
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update Untuk Is_Notified");
					penanda[1] = "No Update";
				}
			}
			//Tidak Bisa Update -> Nilai Is NOTIFIED BUKAN Y ATAU N 
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value dari is_notified selain dari Y,N,y,ataupun n ");
				responseUpdate.setIsNotifiedOld("");
				return responseUpdate;
				
			}
			
			//Bisa Update-> NOTIF TRANSAKSI yang DI Input BEda Dengan yang DI DB
			if(!(fungsiNotifTransaksi(input.getNotifTransaksi()).equalsIgnoreCase(ab.getNotifTransaksi())) ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - notif_transaksi Yang DI Input Beda Dengan DB");
				ab.setNotifTransaksi(fungsiNotifTransaksi(input.getNotifTransaksi().toUpperCase()));
				penanda[2] = "Terupdate";
			
			}
			else {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - No Update Untuk Notif_Transaksi");
				penanda[2] = "No Update";
			}
			
			//Bisa Update-> CORPORATE ID yang DI Input BEda Dengan yang DI DB
			if(!(input.getCorporateId().equalsIgnoreCase(ab.getCorporateId())) ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - corporate_id Yang DI Input Beda Dengan DB");
				ab.setCorporateId(input.getCorporateId().toUpperCase());
				penanda[3] = "Terupdate";
			
			}
			else {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - No Update Untuk Coorporate_Id");
				penanda[3] = "No Update";
			}
			
			//Bisa Update-> COMP CODE yang DI Input BEda Dengan yang DI DB
			if(!(input.getCompCode().equalsIgnoreCase(ab.getCompCode())) ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - comp_code Yang DI Input Beda Dengan DB");
				ab.setCompCode(input.getCompCode().toUpperCase());
				penanda[4] = "Terupdate";
			
			}
			else {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - No Update Untuk comp_code");
				penanda[4] = "No Update";
			}

			//Bisa Update-> AB INITIAL yang DI Input BEda Dengan yang DI DB
			if(!(input.getAbInitial().equalsIgnoreCase(ab.getAbInitial())) ) {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - ab_initial Yang DI Input Beda Dengan DB");
				ab.setAbInitial(input.getAbInitial().toUpperCase());
				penanda[5] = "Terupdate";
			
			}
			else {
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - No Update Untuk Ab_Initial");
				penanda[5] = "No Update";
			}
			
		}//AKHIR IF AB_ID ADA DALAM DB
				
		//SAVE KE DB 
		System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Proses Akan Save Ke DB");
		ab = abRepository.save(ab);
		System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Save Ke Db Selesai");

		//KONDISI JIKA TIDAK ADA UPDATE DATA SAMA SEKALI => SEMUA penanda => "Gagal"
		if(penanda[0].equals("No Update") && penanda[1].equals("No Update") && penanda[2].equals("No Update") && penanda[3].equals("No Update") && penanda[4].equals("No Update") && penanda[5].equals("No Update") ) {
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Tidak Ada Update Untuk Semua Field");
			responseUpdate.setAbNameOld("No Update");
			responseUpdate.setAbInitialOld("No Update");
			responseUpdate.setCompCodeOld("No Update");
			responseUpdate.setCorporateIdOld("No Update");
			responseUpdate.setIsNotifiedOld("No Update");
			responseUpdate.setNotifTransaksiOld("No Update");
			
		}
		
		//ATUR ISIAN RESPONSE API 
	
		//isian ab_name
		if(penanda[0].equals("Terupdate")) {
			responseUpdate.getAbNameOld();
			responseUpdate.setAbNameNew(ab.getAbName().toUpperCase());;
		}
		
		//isian isNotified
		if(penanda[1].equals("Terupdate")) {
			responseUpdate.getIsNotifiedOld();
			responseUpdate.setIsNotifiedNew(ab.getIsNotified().toUpperCase());
		}
		
		//isian notifTransaksi
		if(penanda[2].equals("Terupdate")) {
			responseUpdate.getNotifTransaksiOld();
			responseUpdate.setNotifTransaksiNew(ab.getNotifTransaksi().toUpperCase());

		}
		
		//isian corporateId
		if(penanda[3].equals("Terupdate")) {
			responseUpdate.getCorporateIdOld();
			responseUpdate.setCorporateIdNew(ab.getCorporateId().toUpperCase());

		}
		//isian comp_code
		if(penanda[4].equals("Terupdate")) {
			responseUpdate.getCompCodeOld();
			responseUpdate.setCompCodeNew(ab.getCompCode().toUpperCase());

		}
		//isian ab_initial
		if(penanda[5].equals("Terupdate")) {
			responseUpdate.getAbInitialOld();
			responseUpdate.setAbInitialNew(ab.getAbInitial().toUpperCase());

		}
		
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi dari responseUpdate =  "+responseUpdate.toString());
		return responseUpdate;
	
	}
	
	//DELETE 
	public Ab deleteAb (String id) {
		String valueLog = logNumber();
		Ab findData = abRepository.findById(id).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil ditemukan");
		//Delete Data
		abRepository.delete(findData);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil dihapus");
		Ab result = new Ab(findData.getAbId(), findData.getAbName(), findData.getIsNotified(), findData.getNotifTransaksi(), findData.getCorporateId(), findData.getCorpIdRegDate(), findData.getCompCode(), findData.getCompCodeRegDate(), findData.getAbInitial());
		return result;
	}
	

}
