package net.gwanghwa.study.servlet.dao;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class LoadWifiInfoDAO {
	@Builder.Default
	@SerializedName("X_SWIFI_MGR_NO")
	String xSwifiMgrNo = " "; 		//관리번호

	@Builder.Default
	@SerializedName("X_SWIFI_WRDOFC")
	String xSwifiWrdofc = " "; 		//자치구

	@Builder.Default
	@SerializedName("X_SWIFI_MAIN_NM")
	String xSwifiMainNm = " "; 	//와이파이명

	@Builder.Default
	@SerializedName("X_SWIFI_ADRES1")
	String xSwifiAdres1 = " "; 		//도로명주소

	@Builder.Default
	@SerializedName("X_SWIFI_ADRES2")
	String xSwifiAdres2 = " "; 		//상세주소

	@Builder.Default
	@SerializedName("X_SWIFI_INSTL_FLOOR")
	String xSwifiInstlFloor = " "; //설치위치(층)

	@Builder.Default
	@SerializedName("X_SWIFI_INSTL_TY")
	String xSwifiInstlTy = " "; 	//설치유형

	@Builder.Default
	@SerializedName("X_SWIFI_INSTL_MBY")
	String xSwifiInstlMby = " "; 	//설치기관

	@Builder.Default
	@SerializedName("X_SWIFI_SVC_SE")
	String xSwifiSvcSe = " "; 		//서비스구분

	@Builder.Default
	@SerializedName("X_SWIFI_CMCWR")
	String xSwifiCmcwr = " "; 		//망종류

	@Builder.Default
	@SerializedName("X_SWIFI_CNSTC_YEAR")
	String xSwifiCnstcYear = " "; 	//설치년도

	@Builder.Default
	@SerializedName("X_SWIFI_INOUT_DOOR")
	String xSwifiInoutDoor = " "; 	//실내외구분

	@Builder.Default
	@SerializedName("X_SWIFI_REMARS3")
	String xSwifiRemars3 = " "; 	//wifi접속환경

	@Builder.Default
	@SerializedName("LAT")
	String lat = " "; 				//Y좌표

	@Builder.Default
	@SerializedName("LNT")
	String lnt = " "; 				//X좌표

	@Builder.Default
	@SerializedName("WORK_DTTM")
	String workDttm = " "; 			//작업일자
}