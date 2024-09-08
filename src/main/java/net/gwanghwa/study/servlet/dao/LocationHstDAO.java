package net.gwanghwa.study.servlet.dao;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LocationHstDAO {
	@SerializedName("X_SWIFI_MGR_NO")
	String xSwifiMgrNo = ""; 		//관리번호

	@SerializedName("LAT")
	String lat = ""; 				//Y좌표

	
	@SerializedName("LNT")
	String lnt = ""; 				//X좌표

	
	@SerializedName("WORK_DTTM")
	String workDttm = ""; 			//작업일자
}