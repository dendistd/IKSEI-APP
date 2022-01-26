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
import com.example.iksei.payload.groupuser.UpdateInputGroupUserKbb;
import com.example.iksei.repo.GroupUserKbbRepository;
import com.example.iksei.service.GroupUserKbbService;


@Service
public class GroupUserKbbServiceImple implements GroupUserKbbService {
	@Autowired
	GroupUserKbbRepository groupUserKbbRepository;
	
	//Buat variabel genID untuk men-generate field id di TABEL DENDI_USER_GROUP_KBB
		private static final String generateId = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		public String genId(int length) {
		    Random random = new Random();
		    StringBuilder builder = new StringBuilder(length);

		    for (int i = 0; i < length; i++) {
		        builder.append(generateId.charAt(random.nextInt(generateId.length())));
		    }

		    return builder.toString();
		}
		
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
	public ResponseEntity<?> findGroupUserKbbById(String id) {
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.FIND_ONE);
		GroupUserKbb result = new GroupUserKbb();
		ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
		try {
			result = groupUserKbbRepository.findById(id).get();
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
	
	//CREATE DATA
	public ResponseEntity<?> createGroupUserKbb(InputUserGroupKbb input){
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.CREATE);
		ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
		GroupUserKbb dataGroup = new GroupUserKbb();
		//Generate STring ID dengan fungsi dari Java -> RandomStringUtils
		String stringId = RandomStringUtils.randomAlphanumeric(32);
		
	try {
		// BISA CREATE KARNA  INPUT PARAM NYA PANJANG NYA TIDAK MELEBIHI BATAS MAX VALUE DARI SETIAP KOLOM
		if(!(input.getAbId().length() >= 6 || input.getGroupUserCode().length() >=6 || input.getIsNew().length() >= 2 )) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Akan Dilakukan Pengecekan AB_ID Ada atau Tidak Dalam DB");
			
			//  BISA CREATE KARNA AB_ID ADA DALAM TABEL DENDI_AB
			if(groupUserKbbRepository.cekAbId(input.getAbId().toUpperCase()).size() > 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - AB_ID ada dalam DB");	
				//NILAI IS_NEW HANYA UNTUK "Y", "N"
				if( (input.getIsNew().equals("y") || input.getIsNew().equals("n") || input.getIsNew().equals("Y") || input.getIsNew().equals("N")) ){
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - kondisi terpenuhi, value input param is_notified sama dengan Y or N or y or n, kemudian persiapan save ke db");
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - proses sebelum save ke db");
//					dataGroup.setId(genId(32).toUpperCase());
					dataGroup.setId(stringId.toUpperCase());
					dataGroup.setAbId(input.getAbId().toUpperCase());
					dataGroup.setGroupUserCode(input.getGroupUserCode().toUpperCase());
					Date date = new Date();
					dataGroup.setRegDate(date);
					dataGroup.setIsNew(input.getIsNew().toUpperCase());
					dataGroup = groupUserKbbRepository.save(dataGroup);
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - save ke db selesai, isi dari dataAb = "+dataGroup.toString());
							
				}

				//Else Untuk VALUE IS_NEW SELAIN (Y, N, y, n)
				else {
					System.out.println("["+valueLog + "]" +" - " +dateLog() + " - value dari is_new selain dari Y,N,y,ataupun n ");
					ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
					ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
					responseSchema2.setOutputSchema(new GagalOutputSchema("VALUE FOR IS_NEW IS INCORECT, THE IS_NOTIFIED VALUE MUST BE 'Y' or 'N' "));
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseSchema2);
							
				}

			} //AKHIR IF -> BISA CREATE KARNA AB_ID ADA DALAM TABEL DENDI_AB
						
			//GAK BISA CREATE KARNA AB_ID TIDAK ADA DALAM DENDI_AB
			else {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - CREATE AB GAGAL KARNA AB_ID YANG DIINPUT TIDAK ADA DALAM DB");
				ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
				ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);
				responseSchema2.setOutputSchema(new GagalOutputSchema("AB_ID That Inputed Doesnt Exist In Database, Insert Data Failed"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
			}
							
		}
	
		
		// TIDAK BISA CREATE KARNA  DATA YANG DIINPUT DI INPUT PARAM(BODY) ADA YANG MELEBIHI BATAS MAX DARI SETIAP KOLOM
		else {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - ada value yang melebihi batas maximum value dari setiap kolom");
			ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
			ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Persiapan set Output schema ");
			responseSchema2.setOutputSchema(new GagalOutputSchema("There's Value That Passed Exceeds The Maximum Length "));
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - OutputSchema telah diatur " + responseSchema2.getOutputSchema().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
			

		}//AKHIR BLOCK ELSE PANJANG VALUE INPUT PARAMNYA  MELEBIHI BATAS MAX VALUE DARI SETIAP KOLOM
	} catch (Exception e) {
		ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_CREATE);
		ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
		responseSchema2.setOutputSchema(new GagalOutputSchema("Field yang Di Input Ke Dalam Request Body Tidak Lengkap "));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
	}
		responseSchema.setOutputSchema(dataGroup);
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	
	}
	
	//UPDATE
	public ResponseEntity<?> updateGroupUserKbb(Map<String, String> map) {
		/* Alur proses update : 
		 * 1. kita input ab_id di input param(body) untuk update data (DONE)
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
		System.out.println("["+valueLog + "]" +" - "+dateLog() +" - CEK size dari map (input paramnya) = "+map.size());
		if(map.size() == 0) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Input Param");
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			String reason = "No request Parameter Update";
			responseFail.setOutputSchema(new GagalOutputSchema(reason));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
					
		//ALUR PROSES UPDATE NO 3 => Cek Jika AB_ID nya TIDAK DI INPUTKAN KEDALAM INPUT PARAM, MAKA TIDAK BISA UPDATE/GAGAL
		if(map.get("id") == null) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - id = null");
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			String reason = "ID is required for updating data";
			responseFail.setOutputSchema(new GagalOutputSchema(reason));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
			}
		//ISI DARI ID NYA STRING KOSONG, ATAU HANYA SPASI, MAKA TIDAK BISA DILAKUKAN UPDATE
		if(map.get("id").toString().contains(" ") || map.get("id").toString().equals("")) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Nilai Id kosong atau berisi spasi");
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			String reason = "Value Format For Id is Incorect";
			responseFail.setOutputSchema(new GagalOutputSchema(reason));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		//ID YANG INPUTKAN TIDAK ADA DALAM DATABASE
		if(groupUserKbbRepository.cekId(map.get("id").toString()).size() < 1) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Id Tidak Ada Dalam DB");
			ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
			ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
			String reason = "ID Is Not Found In Database";
			responseFail.setOutputSchema(new GagalOutputSchema(reason));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);
		}
		
		//Convert Object(map.get("id") ->  ke String (id)
		//FIND DATA DARI DB menggunakan SERVICE findGroupUserKbbById, id nya dari map(input param), ada gak di db ?
//		GroupUserKbb objGuk = findGroupUserKbbById(map.get("id").toString());
		GroupUserKbb objGuk = groupUserKbbRepository.findById(map.get("id").toUpperCase()).get();
		
		/*Buat instance dari UpdateInputGroupUserKbb untuk menampung data hasil findGroupUserKbbById/objGuk
		 * karna kalo pakai instance GroupUserKbb akan nge hit ke db, karna GroupUserKbb adalah entity */		
		UpdateInputGroupUserKbb tampungData = new UpdateInputGroupUserKbb(objGuk.getId(), objGuk.getAbId(), objGuk.getGroupUserCode(), objGuk.getRegDate(), objGuk.getIsNew());
		
		//Buat penanda dari setiap pengecekan , fungsinya untuk menandai suatu proses apakah Gagal Update, atau Terupdate
		String[] penanda = new String[4];
		try {
//		if(map.get("id").length() < 41 || (map.get("ab_id").length() < 6 || map.get("ab_id") == null ) || ( map.get("group_user_code").length() < 6 || map.get("group_user_code") == null) || ( map.get("is_new").length() < 2 || map.get("is_new") == null) ) {
//		if(map.get("id").length() < 41 && (map.get("ab_id").length() < 6 ) && ( map.get("group_user_code").length() < 6 ) && ( map.get("is_new").length() < 2 ) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Masuk Blok COde Try");
			//ALUR PROSES UPDATE NO 2 => Jika id dari map(input param) nya beda dengan id dari db , maka gak bisa Update
			if(!(map.get("id").equals(objGuk.getId()) ) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Id Yang Di Input Berbeda Dengan DB");
				penanda[0] = "Gagal";	
			}
	
			if(!(map.get("ab_id") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ada Value Yang Di Input Kedalam Id");
					//BISA UPDATE KARNA NILAI AB_ID YANG DI INPUT ADA DI DB
					if(groupUserKbbRepository.cekAbId(map.get("ab_id").toString().toUpperCase()).size() > 0 ) {
						System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Ab_Id Ada Dalam DB");
						//BISA DILAKUKAN UPDATE -> NILAI AB_ID YANG DI INPUT DENGAN YG ADA DI DB BEDA
						if( !(map.get("ab_id").toString().toUpperCase().equals(objGuk.getAbId())) ) {
							System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Bisa update karna ab_id yang diinput beda dengan yang di db");						
							objGuk.setAbId(map.get("ab_id").toString().toUpperCase());
							System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Proses set ab_id dengan value baru");
							penanda[1] = "Terupdate";
					}
						else {
							System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Value Untuk AB_ID");
							penanda[1] = "Gagal";
						}
					}//AKHIR IF AB_ID YANG DI INPUT ADA DALAM DB
		
					// TIDAK BISA DILAKUKAN UPDATE -> YANG DI INPUT UNTUK UPDATENYA VALUES AB_ID TIDAK ADA DI TABEL DENDI_AB 
					else {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Ab_ID Yang DiInput Tidak Ada Dalam DB");
						ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
						ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
						responseSchema2.setOutputSchema(new GagalOutputSchema("AB_ID VALUE THAT IMPUTED DOESNT EXIST IN DATABASE, UPDATE FAILED FOR AB_ID VALUE "));
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
					}
			}
			else {
				penanda[1] = "Kosong";
			}
			//Cek apakah groupUserCode yang diinput(dari map nya) sama dengan yang didatabase jika beda berarti ada  update
			if(!(map.get("group_user_code") == null) ) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value group_user_code dari input param = "+ map.get("group_user_code").toString());
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value group_user_code dari db = "+ objGuk.getGroupUserCode());
				if( !(map.get("group_user_code").toString().toUpperCase().equals(objGuk.getGroupUserCode())) ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - group_user_code yang di input berbeda dengan db");
					objGuk.setGroupUserCode(map.get("group_user_code").toString().toUpperCase());
					System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Proses set group_user_code dengan value baru "+ objGuk.getGroupUserCode().toString());
					penanda[2] = "Terupdate";
				}
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Value Untuk group_user_code");
					penanda[2] = "Gagal";
				}
			}
			else {
				penanda[2] = "Kosong";
			}
			
			//IS_NEW NYA DI TULISKAN DI INPUT PARAM NYA
			if(!(map.get("is_new") == null) ) {
				//BISA LAKUKAN UPDATE -> VALUE IS_NEW DARI INPUT PARAM BERBEDA DENGAN DB 
				
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - is_new dari map = "+map.get("is_new").toString());
				System.out.println("["+valueLog + "]" +" - "+dateLog() + " - is_new dari objGuk/Database = "+ objGuk.getIsNew());
				//CEK APAKAH NILAI IS_NEW dari Input Param (Baik itu huruf Capital = Y atau N maupun lowercase = y atau n) sama dengan value dari db
				if( !(map.get("is_new").toString().toUpperCase().equals(objGuk.getIsNew()) )  ) {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Is_New Yang Di Input Beda Dengan DB");
					//BISA UPDATE KARNA VALUE IS_NEW  NILAINYA Y ATAU N
					if( map.get("is_new").toString().equals("Y") || map.get("is_new").toString().equals("N") || map.get("is_new").toString().equals("y") || map.get("is_new").toString().equals("n") ) {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value Is_New Yang Di Input Sesuai Kondisi yaitu 'Y' atau 'N' ");
						objGuk.setIsNew(map.get("is_new").toString().toUpperCase());
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Proses set is_new dengan value baru");
						penanda[3] = "Terupdate";
					}
					//TIDAK BISA UPDATE KARNA VALUE IS_NEW NILAINYA BUKAN Y ATAU N
					else {
						System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Value dari is_new selain dari Y,N,y,ataupun n ");
						ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
						ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
						responseSchema2.setOutputSchema(new GagalOutputSchema("VALUE FOR IS_NEW IS INCORECT, THE IS_NEW VALUE MUST BE 'Y' or 'N' "));
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
					}
					
				} // AKHIR DARI IF YANG IS_NEW DARI INPUT PARAM DAN DB NYA BEDA
				//JIKA NILAI IS_NEW DARI INPUT PARAM DAN DB NYA SAMA , GAK BISA UPDATE JADINYA
				else {
					System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Tidak Ada Update Nilai Untuk Is_New");
					penanda[3] = "Gagal";
				}
				
			}	
			else {
				penanda[3] = "Kosong";
			}
			
			//KONDISI UNTUK SEMUA PENANDA GAGAL UDPADTE
			if( ("Gagal".equals(penanda[1]) || penanda[1].equals("Kosong") ) && ("Gagal".equals(penanda[2]) || penanda[2].equals("Kosong")  ) && ("Gagal".equals(penanda[3]) || penanda[3].equals("Kosong") )) {
				System.out.println("["+valueLog + "]" +" - "+dateLog() + "- Tidak Ada Perubahan Data");
				ErrorSchema errorFail = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
				ResponseSchema<GagalOutputSchema> responseFail = new ResponseSchema<>(errorFail);
				String reason = "TIDAK ADA PERUBAHAN DATA";
				responseFail.setOutputSchema(new GagalOutputSchema(reason));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseFail);	
			}

		//SAVE KE DB 
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Proses Akan Save Ke DB");
		objGuk = groupUserKbbRepository.save(objGuk);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Proses Save Ke DB Selesai");
		
	} catch (Exception e){
		ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_UPDATE);
		ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
		responseSchema2.setOutputSchema(new GagalOutputSchema("Length Input Param Yang Di Input Bernilai 0 Atau Melebihi Max Length Kolom"));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
		
	}
		//BUAT MAP2 UNTUK ATUR OUTPUT_SCHEMA DI RESPONSESCHEMA 
		Map<String, String> map2 = new LinkedHashMap<>();	
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Untuk Menampilkan Response Api");
		/* String id, String abId, String groupUserCode, Date regDate, String isNew */
		
		//atur isian id 
		map2.put("id", tampungData.getId());
		
		//atur isian ab_id 
		if(!(map.get("ab_id") == null) && "Terupdate".equals(penanda[1]) )  {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value ab_id_old = " + tampungData.getAbId().toString() );
			map2.put("ab_id_old", tampungData.getAbId());
//			map2.put("ab_name_new", map.get("ab_name").toString());
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value ab_id_new = " + objGuk.getAbId().toString() );
			map2.put("ab_id_new", objGuk.getAbId());
		}
		//atur isian groupUserCode 
		if(!(map.get("group_user_code") == null) && "Terupdate".equals(penanda[2]) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value group_user_code_old = " + tampungData.getGroupUserCode().toString() );
			map2.put("group_user_code_old", tampungData.getGroupUserCode());
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value group_user_code_new = " + objGuk.getGroupUserCode().toString() );
			map2.put("group_user_code_new", objGuk.getGroupUserCode());
		}

		//atur isian isNew 
		if(!(map.get("is_new") == null) && "Terupdate".equals(penanda[3]) ) {
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value is_new_old = " + tampungData.getIsNew().toString() );
			map2.put("is_new_old", tampungData.getIsNew());
			System.out.println("["+valueLog + "]" +" - "+dateLog() + " - value is_new_new = " + objGuk.getIsNew().toString() );
			map2.put("is_new_new", objGuk.getIsNew());
		}
		
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Persiapan sebelum set OutputSchema dari data yang sudah diupdate");
		responseSchema.setOutputSchema(map2);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Setelah set Output Schema, isi dari OututSchema adalah "+ responseSchema.getOutputSchema().toString());
		return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
		
	}

			
	
	//DELETE DATA 
	public ResponseEntity<?> deleteGroupUserKbb(String id) {
		//VALUELOG UNTUK 1 SERVICE 
		String valueLog = logNumber();
		ErrorSchema errorSchema = new ErrorSchema(ErrorEnum.DELETE);
		ResponseSchema<GroupUserKbb> responseSchema = new ResponseSchema<>(errorSchema);
		GroupUserKbb data = new GroupUserKbb();
	try {
		
		data = groupUserKbbRepository.findById(id).get();
		System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Data berhasil ditemukan  : " + data.toString());
		System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Persiapan menghapus data dari data yang barusan difind by id");
		groupUserKbbRepository.delete(data);
		System.out.println("["+valueLog + "]" +" - "+dateLog() + " - Data Berhasil dihapus");
		
	} catch (Exception e) {
		System.out.println("["+valueLog + "]" +" - "+dateLog() +" - Tidak Bisa Menghapus Data Dengan ID Yang Di Input Tidak Ada Dalam DB" );
		ErrorSchema errorSchema2 = new ErrorSchema(ErrorEnum.FAIL_DELETE);
		ResponseSchema<GagalOutputSchema> responseSchema2 = new ResponseSchema<>(errorSchema2);	
		responseSchema2.setOutputSchema(new GagalOutputSchema("Data Yang Akan DIhapus Tidak Ada Dalam DB"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseSchema2);
	}
	
	responseSchema.setOutputSchema(data);
	return ResponseEntity.status(HttpStatus.OK).body(responseSchema);
	}
}
