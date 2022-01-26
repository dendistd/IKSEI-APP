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
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Akan Memulai Service Get All Data");
		List<Ab> listAb = new ArrayList<>();
		listAb = abRepository.findAll();
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Isi dari listAb = "+listAb.toString());
		return listAb;
	}
	
	//Insert Data
	public ResponseEntity<?> insertAb(InputAb input) {	
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		Ab dataAb = new Ab();
		
	try {
		//Bisa Create -> ab_id NILAINYA TIDAK STRING KOSONG ATAU HANYA BERISI SPASI
		if(!(input.getAbId().length() == 0 || input.getAbId().contains(" "))) {
			
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Key AB_ID Di Inputkan Di Input Paramnya");
			//BISA CREATE-> PANJANG INPUT PARAM TIDAK MELEBIHI BATAS MAXIMAL VALUE DARI MASING-MASING KOLOM
			if(!(input.getAbName().length() >=41 || input.getAbId().length() >= 6 || input.getIsNotified().length() >=2 || input.getNotifTransaksi().length() >= 43 || input.getCorporateId().length() >= 11 || input.getCompCode().length() >= 11 || input.getAbInitial().length() >= 21)) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Length Value Dari Input Param Tidak Melebihi Max Length Dari Setiap Kolom");
			
				//AB_ID TIDAK ADA DALAM DB, SEHINGGA BISA CREATE AB 
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Cek apakah ab_id sudah ada dalam db atau tidak");
				if(abRepository.cekAbId(input.getAbId().toUpperCase()).size() < 1  ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_id yang di input belum ada di db, sehingga bisa create");
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Akan memeriksa apakah value dari input param is_notified adalah Y,N,n atau y");
				
					//BISA CREATE -> NILAI IS_NOTIFIED HANYA UNTUK "Y", "N"
					if((input.getIsNotified().equals("y") || input.getIsNotified().equals("n") || input.getIsNotified().equals("Y") || input.getIsNotified().equals("N") )){
						System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Kondisi terpenuhi, value input param is_notified sama dengan Y or N or y or n, kemudian persiapan save ke db");

						//ATUR SEMUA SETTER DARI INPUT PARAM, SETELAH ITU SAVE KE DB
						dataAb.setAbId(input.getAbId().toUpperCase());
						dataAb.setAbInitial(input.getAbInitial().toUpperCase());
						dataAb.setAbName(input.getAbName().toUpperCase());
						dataAb.setCompCode(input.getCompCode().toUpperCase());
						//DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
						Date date= new Date();
						//SET COMPCODEREGDATE & CORPIDREGDATE
						dataAb.setCompCodeRegDate(date);		
						dataAb.setCorpIdRegDate(date);	
						
						dataAb.setCorporateId(input.getCorporateId().toUpperCase());
						dataAb.setIsNotified(input.getIsNotified().toUpperCase());
						dataAb.setNotifTransaksi(fungsiNotifTransaksi(input.getNotifTransaksi().toUpperCase()));
						dataAb = abRepository.save(dataAb);
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Save ke db selesai, isi dari dataAb = "+dataAb.toString());
				
					}
					//GA BISA CREATE -> VALUE IS_NOTIFIED SELAIN (Y, N, y, n)
					else {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value dari Inputan is_notified selain dari Y,N,y,ataupun n ");
						ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
						ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
						responseSchema2.setOutputSchema(new GagalOutputSchema("VALUE FOR IS_NOTIFIED IS INCORECT, THE IS_NOTIFIED VALUE MUST BE 'Y' or 'N' or 'y', or 'n' "));
						return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseSchema2);
					}
				}//AKHIR BLOCK IF YANG AB_ID TIDAK ADA DALAM DB	
				
				//AB_ID ADA DALAM DB, SEHINGGA TIDAK BISA CREATE AB
				else {	
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - AB_ID sudah terdapat dalam db, sehingga gak bisa create");
					ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
					responseSchema2.setOutputSchema(new GagalOutputSchema("AB_ID THAT INPUTED IS ALREADY EXIST"));
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
				}	
			} //AKHIR IF TIDAK MELEBIHI PANJANG KOLOM
			
			//JIKA DATA YANG DIINPUT DI INPUT PARAM(BODY) ADA YANG MELEBIHI BATAS MAX DARI SETIAP KOLOM, MAKA TIDAK BISA CREATE AB
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Ada Input value yang melebihi batas maximum value dari setiap kolom");
				ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Persiapan set Output schema ");
				responseSchema2.setOutputSchema(new GagalOutputSchema("There's Value That Passed Exceeds The Maximum Length "));
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - OutputSchema telah diatur " + responseSchema2.getOutputSchema().toString());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
			}
		}//AKHIR IF AB_ID TIDAK STRING KOSONG /SPASI
		
		//JIKA AB_ID TIDAK DI INPUT KE INPUT PARAMNYA, MAKA TIDAK BISA PROSES INSERT DATA
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nilai Ab_Id nya String Kosong atau Hanya Spasi");
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
			responseSchema2.setOutputSchema(new GagalOutputSchema("AB_ID can't be null or contains 'Space' "));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}
		
	} catch (Exception e) {
		ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
		ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
		responseSchema2.setOutputSchema(new GagalOutputSchema("Field yang Di Input Ke Dalam Request Body Tidak Lengkap "));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}
		
	System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Persiapan set outputschema setelah save db");	
	responseSchema.setOutputSchema(dataAb);
	System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Set outputSchema selesai, isi dari outputSchema adalah" + responseSchema.getOutputSchema().toString());
	return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		
}
	
	//Find By Id
	public ResponseEntity<?> findAbById(String id) {
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.FIND_ONE);
		Ab result = new Ab();
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		try {
			result = abRepository.findById(id).get();
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Berhasil Menemukan Data = "+ result.toString());
		} catch (Exception e) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Dengan Id Yang Di Input Tidak Ada Dalam DB");
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_FIND_ONE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
			String reason = "Data Dengan Id Yang Di Input Tidak Ada Dalam DB";
			responseSchema2.setOutputSchema(new GagalOutputSchema(reason));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}
		responseSchema.setOutputSchema(result);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	
	}

	//Update  pakai inputan param nya MAP
		public ResponseEntity<?> updateAb (Map<String, String> map)  {
			/* Alur proses update : 
			 * 1. kita input ab_id di input param(body) untuk update datav(DONE)
			 * 2. ab_id yang di input (SI map nya) harus sama dengan ab_id yang ada di db (DONE)
			 * 3. Jika di input paramnya(map) tidak di inputkan AB_ID (null), maka tidak boleh/tidak bisa untuk update data (done) 
			 * 4. Jika map (input paramnya) kosong/null, artinya tidak ada inputan field apa aja yang mau di update (done)
			 * MAKA tidak akan bisa/boleh update data
			 * 5. kita boleh update data dari beberapa/semua field yang ingin diubah => (FLEKSIBEL) (DONE)
			 * 6. Jika length value di input paramnya MELEBIHI AMX VALUE SETIAP FILED, MAKA-> UPDATE GAGAL*/
			//VALUELOG UNTUK 1 SERVICE 
			String valueLog = logNumber();
			ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.UPDATE);
			ResponseSchema<Map<String, String>> responseSchema = new ResponseSchema<>(errorSchema);
		
			// ALUR PROSES UPDATE NO 4 => Cek Jika field Map nya/input paramn updatenya kosong, MAKA UPDATE GAGAL
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Cek size dari map (input paramnya) = "+map.size());
			if(map.size() == 0) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Request Parameter Update");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "No request Parameter Update";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			//ALUR PROSES UPDATE NO 3 => Cek Jika AB_ID nya kosong/null, MAKA TIDAK BISA UPDATE/GAGAL
			if(map.get("ab_id") == null) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_Id null");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "Ab_Id is required for updating data";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			//ISI DARI AB_ID NYA STRING KOSONG, ATAU HANYA SPASI, MAKA TIDAK BISA DILAKUKAN UPDATE
			if(map.get("ab_id").toString().contains(" ") || map.get("ab_id").toString().equals("")) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_Id berisi STring Kosong ATAU Hanya SPASI");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "Value Format For Ab_Id is Incorect";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
			
			
			//AB_ID YANG INPUTKAN TIDAK ADA DALAM DATABASE
			if(abRepository.cekAbId(map.get("ab_id").toString().toUpperCase()).size() < 1) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_ID Yang Di Inputkan Tidak Ada Dalam DB");
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ab_id yang di input = " + map.get("ab_id"));
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "Ab_Id Is Not Found In Database";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
	
			//Convert Object(map.get("ab_id") ->  ke String (id)
			//FIND DATA DARI DB menggunakan SERVICE findAbById, id nya dari map(input param), ada gak di db ?
			Ab objAb = abRepository.findById(map.get("ab_id").toUpperCase()).get(); 
					
			/*Buat instance dari UpdateInputAb untuk menampung data hasil findAbById/objAb
			 * karna kalo pakai instance Ab akan nge hit ke db, karna Ab adalah entity */
			UpdateInputAb uia = new UpdateInputAb(objAb.getAbId(),objAb.getAbName(), objAb.getIsNotified(), objAb.getNotifTransaksi()
					, objAb.getCorporateId(), objAb.getCorpIdRegDate(), objAb.getCompCode(), objAb.getCompCodeRegDate(), objAb.getAbInitial());
			
			//Buat penanda dari setiap pengecekan , fungsinya untuk menandai suatu proses apakah Gagal Update, atau Terupdate
			String[] penanda = new String[7];
		
			
//			//JIKA PANJANG VALUE INPUT PARAMNYA MELEBIHI MAX VALUE DARI SETIAP FIELD, MAKA GAK BISA LAKUKAN UPDATE
//			if(map.get("ab_id").toString().length() >=6 || map.get("ab_name").toString().length() >= 41 || map.get("is_notified").toString().length() >=2 || map.get("notif_transaksi").toString().length() >= 261 || map.get("corporate_id").toString().length() >= 11 || map.get("comp_code").toString().length() >= 11 || map.get("ab_initial").toString().length() >= 21) {
//				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ada value yang melebihi batas maximum value dari setiap kolom");
//				ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
//				ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
//				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Persiapan set Output schema ");
//				responseSchema2.setOutputSchema(new GagalOutputSchema(" There's Value That Passed Exceeds The Maximum Length "));
//				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - OutputSchema telah diatur " + responseSchema2.getOutputSchema().toString());
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
//			}
//			
//			//ELSE -> BISA UPDATE DATA KARNA PANJANG INPUT PARAM NYA TIDAK MELEBIHI MAX VALUE DARI TIAP KOLOM.
//			else {
	try {		
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Length Input Param Tidak Melebihi Max Value Dari Tiap Kolom");
			//ALUR PROSES UPDATE NO 2 => Jika Ab_id dari map(input param) nya beda dengan AB_id dari db , maka gak bisa Update
			if(!(map.get("ab_id").toUpperCase().equals(objAb.getAbId()) ) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update -> Inputan Ab_Id BEDA dengan Ab_ID dari DB");
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ab_id yang di input = " + map.get("ab_id"));
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ab_id yang di input = " + uia.getAbId());
				penanda[0] = "Gagal";	
			}
		
			//Cek apakah ab_name yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("ab_name") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_Name NOT NULL");
				if( !(map.get("ab_name").toString().toUpperCase().equals(objAb.getAbName())) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Ab_Name Berbeda Dengan Ab_Name dari DB");
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Inputan Ab_Name = "+ map.get("ab_name").toString());
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_Name Database = "+ objAb.getAbName().toString());
					objAb.setAbName(map.get("ab_name").toString().toUpperCase());
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Proses set ab_name dengan value baru = " + objAb.getAbName().toString());
					penanda[1] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update Untuk ab_name");
					penanda[1] = "Gagal";
				}
			}
			else {
				penanda[1] = "Kosong";
			}
				
			//Cek apakah is_notified yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("is_notified") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - is_notified NOT NULL");
				//CEK APAKAH NILAI IS_NOTIFIED dari Input Param (Baik itu huruf Capital = Y atau lowercase = y) sama dengan value dari db
				if( !(map.get("is_notified").toString().toUpperCase().equals(objAb.getIsNotified())  ) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Data is_notified dari input param beda dengan db");
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Input param is_notified = " + map.get("is_notified") + " & nilai is_notified dari db " + objAb.getIsNotified());
					if( map.get("is_notified").toString().equals("Y") || map.get("is_notified").toString().equals("N") || map.get("is_notified").toString().equals("y") || map.get("is_notified").toString().equals("n") ) {
						System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Input Is_Notified Sesuai Kondisi yaitu Y atau N, Proses Akan Update is_notified");
						objAb.setIsNotified(map.get("is_notified").toString().toUpperCase());
						System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Proses set is_notified dengan value baru = "+ objAb.getIsNotified());
						penanda[2] = "Terupdate";
					}
					//TIDAK BISA UPDATE KARNA VALUE IS_NOTIFIED NILAINYA BUKAN Y, N , n, y
					else {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value dari is_notified selain dari Y,N,y,ataupun n ");
						ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
						ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
						responseSchema2.setOutputSchema(new GagalOutputSchema("VALUE FOR IS_NOTIFIED IS INCORECT, THE IS_NOTIFIED VALUE MUST BE 'Y' or 'N' "));
						return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseSchema2);
					}

				}
				
				//JIKA VALUE DARI INPUT PARAM & DB SAMA , TIDAK ADA YANG DI UPDATE
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - No Update Untuk Is_Notified");
					penanda[2] = "Gagal";
				}
			}
			else {
				penanda[2] = "Kosong";
			}
				
			//Cek apakah notif_transaksi yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("notif_transaksi") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - notif_transaksi NOT NULL");
				if( !(fungsiNotifTransaksi(map.get("notif_transaksi").toString().toUpperCase()).equals(objAb.getNotifTransaksi())) ) {	
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - Input notif_transaksi BEDA dengan DB ");
					System.out.println("Sebelum cetak updateNotifTransaksi");
					objAb.setNotifTransaksi(fungsiNotifTransaksi(map.get("notif_transaksi").toString().toUpperCase()));
					System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Setelah set Notif Transaksi dari fungsiNotifTransaksi = " + objAb.getNotifTransaksi());
					penanda[3] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - No Updated for notif_transaksi");
					penanda[3] = "Gagal";
				}
			}
			else {
				penanda[3] = "Kosong";
			}
				
			//Cek apakah corporate_id yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("corporate_id") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - corporate_id NOT NULL");
				if( !(map.get("corporate_id").toString().toUpperCase().equals(objAb.getCorporateId())) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - corporate_id Input Param BEDA dengan yang di DB");
					objAb.setCorporateId(map.get("corporate_id").toString().toUpperCase());
					System.out.println("["+valueLog + "]" +" - "+dateLog() +  " - Value corporate_id telah di update dengan value = "+ objAb.getCorporateId());
					penanda[4] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - No Updated for corporate_id");
					penanda[4] = "Gagal";
				}
			}	
			else {
				penanda[4] = "Kosong";
			}
				
			//Cek apakah comp_code yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("comp_code") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - comp_code NOT NULL");
				if( !(map.get("comp_code").toString().toUpperCase().equals(objAb.getCompCode())) ) {
					System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Input comp_code Beda Dengan DB");
					objAb.setCompCode(map.get("comp_code").toString().toUpperCase());
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - value comp_code telah di update dengan value = "+ objAb.getCompCode());
					penanda[5] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - No Updated for comp_code");
					penanda[5] = "Gagal";
				}
			}
			else {
				penanda[5] = "Kosong";
			}
			//Cek apakah ab_initial yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("ab_initial") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ab_initial NOT NULL");
				if( !(map.get("ab_initial").toString().toUpperCase().equals(objAb.getAbInitial())) ) {
					System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Inputan ab_initial BEDA dengan DB");
					objAb.setAbInitial(map.get("ab_initial").toString().toUpperCase());
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - value ab_initial telah di update dengan value = "+ objAb.getAbInitial());
					penanda[6] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" + " - "+dateLog() + " - No Updated for ab_initial");
					penanda[6] = "Gagal";
				}
			}	
			else {
				penanda[6] = "Kosong";
			}
			
//			}//AKHIR BLOK CODE ELSE YANG PANJANG INPUT PARAM TIDAK MELEBIHI MAX VALUE DARI SETIAP KOLOM	
			
			//SAVE KE DB 
			objAb = abRepository.save(objAb);
			System.out.println("["+valueLog + "]" + " - " +dateLog()+ " - Berhasil Save Ke DB");
	} catch (Exception e) {
		ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
		ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
		responseSchema2.setOutputSchema(new GagalOutputSchema("Length Input Param Yang Di Input Bernilai 0 Atau Melebihi Max Length Kolom"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		
	}
			
			//BUAT MAP2 UNTUK ATUR OUTPUT_SCHEMA DI RESPONSESCHEMA 
			Map<String, String> map2 = new LinkedHashMap<>();
			System.out.println("["+valueLog + "]" + " - "+dateLog() +  " - Persiapan Untuk Atur Output Response API");
			//atur isian ab_id 
			map2.put("ab_id", uia.getAbId());
			
			//atur isian ab_name 
			if(!(map.get("ab_name") == null) && penanda[1].equals("Terupdate")) {
				map2.put("ab_name_old", uia.getAbName());
				map2.put("ab_name_new", objAb.getAbName().toUpperCase());
			}
			//atur isian is_notified 
			if(!(map.get("is_notified") == null) && penanda[2].equals("Terupdate")) {
				map2.put("is_notified_old", uia.getIsNotified());
				map2.put("is_notified_new", objAb.getIsNotified().toUpperCase());
			}
			//atur isian notif_transaksi 
			if(!(map.get("notif_transaksi") == null) && penanda[3].equals("Terupdate")) {
				map2.put("notif_transaksi_old", uia.getNotifTransaksi());
				map2.put("notif_transaksi_new", objAb.getNotifTransaksi().toUpperCase());
			}
			
			//atur isian corporate_id 
			if(!(map.get("corporate_id") == null) && penanda[4].equals("Terupdate")) {
				map2.put("corporate_id_old", uia.getCorporateId());
				map2.put("corporate_id_new", objAb.getCorporateId().toUpperCase());
			}
			
			//atur isian comp_code 
			if(!(map.get("comp_code") == null) && penanda[5].equals("Terupdate")) {
				map2.put("comp_code_old", uia.getCompCode());
				map2.put("comp_code_new", objAb.getCompCode().toUpperCase());
			}
			
			//atur isian ab_initial 
			if(!(map.get("ab_initial") == null) && penanda[6].equals("Terupdate")) {
				map2.put("ab_initial_old", uia.getAbInitial());
				map2.put("ab_initial_new", objAb.getAbInitial().toUpperCase());
			}
			
			//KONDISI UNTUK SEMUA PENANDA GAGAL UDPADTE
			if( ("Gagal".equals(penanda[1]) || penanda[1].equals("Kosong") ) && ("Gagal".equals(penanda[2]) || penanda[2].equals("Kosong") ) && ("Gagal".equals(penanda[3]) || penanda[3].equals("Kosong") ) && ("Gagal".equals(penanda[4]) || penanda[4].equals("Kosong") ) && ("Gagal".equals(penanda[5]) || penanda[5].equals("Kosong") ) && ("Gagal".equals(penanda[6]) || penanda[6].equals("Kosong") ) ){
				System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Tidak Ada Update Untuk Semua Field");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "TIDAK ADA UPDATE DATA";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
				
			}

			System.out.println("["+valueLog + "]" + " - "+dateLog() + " - Persiapan sebelum set OutputSchema dari data yang sudah diupdate");
			responseSchema.setOutputSchema(map2);
			System.out.println("["+valueLog + "]" + " - "+dateLog() +" - Setelah set Output Schema, isi dari OututSchema adalah "+ responseSchema.getOutputSchema().toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
			
	}
	
	//Delete
	public ResponseEntity<?> deleteAb(String id) {
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseSchema<Ab> responseSchema = new ResponseSchema<>(errorSchema);
		Ab objAb = new Ab();
		try {
			objAb = abRepository.findById(id).get();
			System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Data berhasil ditemukan dengan id : " + objAb.toString());
			abRepository.delete(objAb);
			System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Data berhasil dihapus: " + objAb.toString());
		} catch (Exception e) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Tidak Bisa Menghapus Data Dengan ID Yang Di Input Tidak Ada Dalam DB" );
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_DELETE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
			responseSchema2.setOutputSchema(new GagalOutputSchema("Tidak Bisa Menghapus Data Yang Tidak Ada Dalam DB"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		}
		
		responseSchema.setOutputSchema(objAb);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema); 
		
	}

}
