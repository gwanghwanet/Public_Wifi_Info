package net.gwanghwa.study.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.gwanghwa.study.SQLite.CommonDBMSService;
import net.gwanghwa.study.servlet.dao.LoadWifiInfoDAO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Servlet implementation class LoadWifi
 */
@WebServlet("/loadWifi.do")
public class LoadWifi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadWifi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int totCnt = getSeoulPublicWifiApi();
		String html = "";
		html += "<h1 style=\"text-align: center\">";
		html += totCnt;
		html += "개의 WIFI 정보를 정상적으로 저장하였습니다.";
		html += "</h1> <br>";
		html += "<div style=\"display: block; text-align: center\"> <a href=\"/\">홈으로 가기</a> <div>";
		
		response.getWriter().append(html).append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private int getSeoulPublicWifiApi(){
		
        CommonDBMSService commonDBMSService = new CommonDBMSService();
        if( commonDBMSService.open() ) {
        	try {
        		ResultSet resultSet = commonDBMSService.seleteTB("SELECT * FROM TB_PUBLIC_WIFI_INFO;");
        		if (resultSet.getRow() > 0 ) commonDBMSService.deleteWifiInfoTB();
        	} catch (Exception e) {
        		System.out.println("[MainServlet::doGet] Not Table Select " + e.getMessage());
        		try {
					commonDBMSService.createWifiInfoTB();
				} catch (Exception e1) {
					System.out.println("[MainServlet::doGet] Not Table Create " + e.getMessage());
				}
        	} finally {
				if(commonDBMSService.open()) commonDBMSService.close();
			}	
        }
		
		String apiUrl = "http://openapi.seoul.go.kr:8088";
		String apiKey = "655a766e7567776136357166544d56";
		String apiTyp = "json";
		String apiService = "TbPublicWifiInfo";
		
		int unitCnt = 1000;
		int nStaIdx = 1;
		int nEndIdx = 2;
		int nTotCnt = 0;
		
		// OkHttpClient 객체 생성
        OkHttpClient client = new OkHttpClient();
        
        String requestUrl = apiUrl + "/" + apiKey + "/" + apiTyp + "/" + apiService + "/";

        // GET 요청을 생성
        Request request = new Request.Builder().url(requestUrl + nStaIdx + "/" + nEndIdx).build();
        try {
        	Response response = client.newCall(request).execute();
        	if(response.isSuccessful()) {
        		String resData = response.body().string();
        		JsonElement jsonElement = JsonParser.parseString(resData);
        	    JsonObject jsonObject = jsonElement.getAsJsonObject();
        		jsonElement = JsonParser.parseString(jsonObject.get("TbPublicWifiInfo").toString());
        		if(jsonElement.isJsonObject()) {
	        	    jsonObject = jsonElement.getAsJsonObject();
	        	    nTotCnt = Integer.parseInt(jsonObject.get("list_total_count").toString());
	        	    String strResult = jsonObject.get("RESULT").toString();
	        	    
	        	    // Gson 인스턴스 생성
	                Gson gson = new Gson();
	        	    
	                // JSON 배열을 List로 변환
	        	    Type apiRsultListType = new TypeToken<ApiRsult>(){}.getType();
	        	    ApiRsult apiRsultList = gson.fromJson(strResult, apiRsultListType);
	        	    
	        		if("INFO-000".compareTo(apiRsultList.code) == 0) {        			
	        			int arrayCnt = (int)Math.ceil(nTotCnt/(double)unitCnt);
	        			for(int i = 0 ; i < arrayCnt ; i++) {
	        				nStaIdx = i + 1;
	        				nEndIdx = nStaIdx + unitCnt -1 ;
	        				if(nEndIdx > nTotCnt) nEndIdx = nTotCnt;
	        				
	        				request = new Request.Builder().url(requestUrl + nStaIdx + "/" + nEndIdx).build();
	        		        try {
	        		        	response = client.newCall(request).execute();
								if (response.isSuccessful()) {
									JsonArray jsonArray = (JsonArray) jsonObject.get("row");
					        	    
					        	    // JSON 배열을 List로 변환
					        	    for (JsonElement jsonArrayElement : jsonArray) {
					        	    	JsonObject Obj = (JsonObject) jsonArrayElement;
					        	    	
					        	    	gson = new Gson();
					        	    	LoadWifiInfoDAO loadWifiInfoDAO = gson.fromJson(Obj.toString(), LoadWifiInfoDAO.class);
					        	    	
					        	    	commonDBMSService = new CommonDBMSService();
					        	    	commonDBMSService.open();
					        	    	commonDBMSService.insertWifiInfo(loadWifiInfoDAO);
					        	    	commonDBMSService.close();
					        	    	
					        	    	System.out.println(loadWifiInfoDAO.toString());
									}
	        		        	}
							} catch (Exception e) {
								// TODO: handle exception
			        			System.out.println(e.getMessage());
			        			e.getStackTrace();
							}
	        			}
	        		} else {
	        			System.out.println(apiRsultList.toString());
	        		}
        		}
        		
        	}
        } catch (Exception e) {
			// TODO: handle exception
		}
        
        return nTotCnt;
	}
}

@Setter
@Getter
@ToString
class ApiRsult {
	@SerializedName("CODE")
	String code;

	@SerializedName("MESSAGE")
	String message;
}