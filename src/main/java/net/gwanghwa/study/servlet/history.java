package net.gwanghwa.study.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;
import lombok.Setter;
import net.gwanghwa.study.SQLite.CommonDBMSService;
import net.gwanghwa.study.servlet.dao.LoadWifiInfoDAO;

/**
 * Servlet implementation class LoadWifi
 */
@WebServlet("/history.do")
public class history extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public history() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        

		List<LocationHstDAO> list = getLocationHst();
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonArray = gson.toJson(list);
		
        // 응답 출력
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        // 요청 본문에서 JSON 데이터 읽기
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        String jsonData = stringBuilder.toString();
        JsonElement jsonElement = JsonParser.parseString(jsonData);
	    JsonObject jsonObject = jsonElement.getAsJsonObject();
	    
	    String type = jsonObject.get("TYPE").getAsString();
        

        // 응답 준비
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
	    if("insert".compareTo(type) == 0) {
	    	String lat = jsonObject.get("LAT").getAsString();
	    	String lnt = jsonObject.get("LNT").getAsString();
	    	
	    	int ret = insertLocationHst(lat, lnt);
	    	if( ret > 0 ) {
	    		List<LoadWifiInfoDAO> list = selectWifiInfoByNear(lat, lnt);
	    		
	    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            String jsonArray = gson.toJson(list);
	    		
	            // 응답 출력
	            out.print(jsonArray);
	    	}
	    } else if("delete".compareTo(type) == 0) {
	    	deleteLocationHst(jsonObject.get("SEQ").getAsInt());
	        out.print("{\"message\":\"Data received successfully\"}");
	    }
	    
        out.flush();
    }

	private List<LoadWifiInfoDAO> selectWifiInfoByNear(String lat, String lnt) {
		
		List<LoadWifiInfoDAO> list = new ArrayList<LoadWifiInfoDAO>();
		CommonDBMSService commonDBMSService = new CommonDBMSService();
		try {
			
			if( commonDBMSService.open() ) {
				ResultSet rs = commonDBMSService.selectWifiInfoByNear(lat, lnt);
				
				while(rs.next()) {
					LoadWifiInfoDAO loadWifiInfoDAO = new LoadWifiInfoDAO();
					loadWifiInfoDAO.setXSwifiDistances(rs.getString("x_swifi_distances"));
					loadWifiInfoDAO.setXSwifiMgrNo(rs.getString("x_swifi_mgr_no"));
					loadWifiInfoDAO.setXSwifiWrdofc(rs.getString("x_swifi_wrdofc"));
					loadWifiInfoDAO.setXSwifiMainNm(rs.getString("x_swifi_main_nm"));
					loadWifiInfoDAO.setXSwifiAdres1(rs.getString("x_swifi_adres1"));
					loadWifiInfoDAO.setXSwifiAdres2(rs.getString("x_swifi_adres2"));
					loadWifiInfoDAO.setXSwifiInstlFloor(rs.getString("x_swifi_instl_floor"));
					loadWifiInfoDAO.setXSwifiInstlTy(rs.getString("x_swifi_instl_ty"));
					loadWifiInfoDAO.setXSwifiInstlMby(rs.getString("x_swifi_instl_mby"));
					loadWifiInfoDAO.setXSwifiSvcSe(rs.getString("x_swifi_svc_se"));
					loadWifiInfoDAO.setXSwifiCmcwr(rs.getString("x_swifi_cmcwr"));
					loadWifiInfoDAO.setXSwifiCnstcYear(rs.getString("x_swifi_cnstc_year"));
					loadWifiInfoDAO.setXSwifiInoutDoor(rs.getString("x_swifi_inout_door"));
					loadWifiInfoDAO.setXSwifiRemars3(rs.getString("x_swifi_remars3"));
					loadWifiInfoDAO.setLat(rs.getString("lat"));
					loadWifiInfoDAO.setLnt(rs.getString("lnt"));
					loadWifiInfoDAO.setWorkDttm(rs.getString("work_dttm"));
					
					list.add(loadWifiInfoDAO);
				}

				if(rs != null) rs.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(commonDBMSService.open()) commonDBMSService.close();
		}
		
		return list;
	}
		
	private int insertLocationHst(String lat, String lnt) {
		
		int ret = 0;
		
		CommonDBMSService commonDBMSService = new CommonDBMSService();
		
		try {
			
			if( commonDBMSService.open() ) {
				try {
	        		commonDBMSService.seleteTB("SELECT * FROM TB_LOCATION_HST LIMIT 1");
	        	} catch (Exception e) {
	        		System.out.println("[MainServlet::doGet] Not Table Select " + e.getMessage());
	        		try {
						commonDBMSService.createLocationHst();
					} catch (Exception e1) {
						System.out.println("[MainServlet::doGet] Not Table Create " + e.getMessage());
					}
	        	}	
								
				ret = commonDBMSService.insertLocationHst(lat, lnt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(commonDBMSService.open()) commonDBMSService.close();
		}
		
		return ret;
	}
	
	private int deleteLocationHst(int seq) {
		
		int ret = 0;

		CommonDBMSService commonDBMSService = new CommonDBMSService();
		
		try {
			if( commonDBMSService.open() ) {
				ResultSet rs = commonDBMSService.seleteTB("SELECT * FROM TB_LOCATION_HST WHERE SEQ = " + seq + ";");
				if(rs.next()) {
					if(rs != null) rs.close();
					ret = commonDBMSService.deleteLocationHst(seq);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(commonDBMSService.open()) commonDBMSService.close();
		}
		
		return ret;
	}
	
	private List<LocationHstDAO> getLocationHst() {
		List<LocationHstDAO> list = new ArrayList<LocationHstDAO>();
		CommonDBMSService commonDBMSService = new CommonDBMSService();
		
		try {			
			commonDBMSService.open();
			ResultSet rs = commonDBMSService.seleteTB("SELECT * FROM TB_LOCATION_HST ORDER BY SEQ DESC;");
			
			while( rs.next() ) {
				LocationHstDAO locationHstDAO = new LocationHstDAO();
				locationHstDAO.setSeq(rs.getInt("seq"));
				locationHstDAO.setLat(rs.getString("lat"));
				locationHstDAO.setLnt(rs.getString("lnt"));
				locationHstDAO.setInqDate(rs.getString("inq_date"));
				list.add(locationHstDAO);
			}
			
			if(rs != null) rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return list;
		
	}
}

@Setter
@Getter
class LocationHstDAO {
	int seq;
	String lat;
	String lnt;
	String inqDate;
}