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
        int ret = insertLocationHst(jsonData);
        
        // 요청 데이터 처리 (여기서는 단순히 로그 출력)
        System.out.println("Received JSON data: " + jsonData);

        // 응답 준비
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"message\":\"Data received successfully\", " + ret + " }");
        out.flush();
    }
	
	private List<LocationHstDAO> getLocationHst() {
		List<LocationHstDAO> list = new ArrayList<LocationHstDAO>();
		CommonDBMSService commonDBMSService = new CommonDBMSService();
		
		try {			
			commonDBMSService.open();
			ResultSet rs = commonDBMSService.seleteTB("SELECT * FROM TB_LOCATION_HST");
			
			while( rs.next() ) {
				LocationHstDAO locationHstDAO = new LocationHstDAO();
				locationHstDAO.setSeq(rs.getInt("seq"));
				locationHstDAO.setLat(rs.getString("lat"));
				locationHstDAO.setLnt(rs.getString("lnt"));
				locationHstDAO.setInqDate(rs.getString("inq_date"));
				list.add(locationHstDAO);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return list;
		
	}
	
	private int insertLocationHst(String jsonData) {
		
		int ret = 0;
		
		CommonDBMSService commonDBMSService = new CommonDBMSService();
		JsonElement jsonElement = JsonParser.parseString(jsonData);
	    JsonObject jsonObject = jsonElement.getAsJsonObject();
	    
		
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
								
				ret = commonDBMSService.insertLocationHst(jsonObject.get("LAT").getAsString(), jsonObject.get("LNT").getAsString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(commonDBMSService.open()) commonDBMSService.close();
		}
		
		return ret;
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