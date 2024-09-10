package net.gwanghwa.study.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.gwanghwa.study.servlet.dao.LoadWifiInfoDAO;

public class CommonDBMSService {

	Connection conn = null;
	Statement stmt = null;

	public boolean open() {
		boolean isConn = true;
		
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			if (conn == null) {
				conn = DriverManager.getConnection("jdbc:sqlite:wifiInfo.db");
			}
			
			if(stmt == null) {
				stmt = conn.createStatement();
			}
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			isConn = false;
		} catch (SQLException e) {
			e.printStackTrace();
			isConn = false;
		}
		
		return isConn;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			
			if(stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet seleteTB(String sql) throws Exception {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[commonDBMSService::seleteTB] ==> " + e.getMessage());
			e.getStackTrace();
			
			throw e;
		} finally {
			//if(pstmt != null) pstmt.close();
		}
		
		return rs;	
	}
	
	public int createWifiInfoTB() throws Exception {
		String sql = "";
		sql += "CREATE TABLE \"TB_PUBLIC_WIFI_INFO\" (";
		sql += "	\"X_SWIFI_MGR_NO\" TEXT NULL,";
		sql += "	\"X_SWIFI_WRDOFC\" TEXT NULL,";
		sql += "	\"X_SWIFI_MAIN_NM\" TEXT NULL,";
		sql += "	\"X_SWIFI_ADRES1\" TEXT NULL,";
		sql += "	\"X_SWIFI_ADRES2\" TEXT NULL,";
		sql += "	\"X_SWIFI_INSTL_FLOOR\" TEXT NULL,";
		sql += "	\"X_SWIFI_INSTL_TY\" TEXT NULL,";
		sql += "	\"X_SWIFI_INSTL_MBY\" TEXT NULL,";
		sql += "	\"X_SWIFI_SVC_SE\" TEXT NULL,";
		sql += "	\"X_SWIFI_CMCWR\" TEXT NULL,";
		sql += "	\"X_SWIFI_CNSTC_YEAR\" TEXT NULL,";
		sql += "	\"X_SWIFI_INOUT_DOOR\" TEXT NULL,";
		sql += "	\"X_SWIFI_REMARS3\" TEXT NULL,";
		sql += "	\"LAT\" TEXT NULL,";
		sql += "	\"LNT\" TEXT NULL,";
		sql += "	\"WORK_DTTM\" TEXT NULL";
		sql += "	)";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}
	
	public int deleteWifiInfoTB() throws Exception {
		String sql = "";
		sql += "DELETE FROM TB_PUBLIC_WIFI_INFO;";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}
	
	public int insertWifiInfo(LoadWifiInfoDAO loadWifiInfoDAO) throws Exception {
		String sql = "";
		sql += "INSERT INTO TB_PUBLIC_WIFI_INFO VALUES ";
		sql	+= "( ";
		sql += "'" + loadWifiInfoDAO.getXSwifiMgrNo() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiWrdofc() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiMainNm() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiAdres1() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiAdres2() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiInstlFloor() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiInstlTy() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiInstlMby() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiSvcSe() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiCmcwr() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiCnstcYear() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiInoutDoor() + "', ";
		sql += "'" + loadWifiInfoDAO.getXSwifiRemars3() + "', ";
		sql += "'" + loadWifiInfoDAO.getLat() + "', ";
		sql += "'" + loadWifiInfoDAO.getLnt() + "', ";
		sql += "'" + loadWifiInfoDAO.getWorkDttm() + "'";
		sql += ");";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}

	public int createLocationHst() throws Exception {
		String sql = "";
		sql += "CREATE TABLE \"TB_LOCATION_HST\" (";
		sql += "	\"SEQ\" INTEGER NULL,";
		sql += "	\"LAT\" TEXT NULL,";
		sql += "	\"LNT\" TEXT NULL,";
		sql += "	\"INQ_DATE\" DATETIME NOT NULL";
		sql += "	);";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}
	
	public int insertLocationHst(String lat, String lnt) throws Exception {
		String sql = "";
		sql += "INSERT INTO TB_LOCATION_HST VALUES ";
		sql	+= "( ";
		sql += "(SELECT IFNULL(MAX(SEQ), 0)  AS SEQ FROM TB_LOCATION_HST) + 1, ";
		sql += "'" + lat + "', ";
		sql += "'" + lnt + "', ";
		sql += "(SELECT DATETIME('now', 'localtime') AS INQ_DATE)";
		sql += ");";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}
	
	public int deleteLocationHst(int seq) throws Exception {
		String sql = "";
		sql += "DELETE FROM TB_LOCATION_HST ";
		sql	+= " WHERE SEQ = " + seq;
		sql += ";";
		
		int ret = stmt.executeUpdate(sql);
		return ret;
	}
	
	public ResultSet selectWifiInfoByNear(String lat, String lnt) throws Exception {
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "";
			sql += "SELECT\n";
			sql += "    ROUND(6371 * ACOS(\n";
			sql += "        COS((? * PI() / 180)) * COS((LAT * PI() / 180)) *\n";
			sql += "        COS(((LNT - ?) * PI() / 180)) +\n";
			sql += "        SIN((? * PI() / 180)) * SIN((LAT * PI() / 180))\n";
			sql += "    ), 4) AS X_SWIFI_DISTANCES,\n";
			sql += "    PWI.*\n";
			sql += "FROM TB_PUBLIC_WIFI_INFO PWI\n";
			sql += "ORDER BY X_SWIFI_DISTANCES\n";
			sql += "LIMIT 20;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, Double.valueOf(lat));
			pstmt.setDouble(2, Double.valueOf(lnt));
			pstmt.setDouble(3, Double.valueOf(lat));
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("[commonDBMSService::selectWifiInfoByNear] ==> " + e.getMessage());
			e.getStackTrace();
			
			throw e;
		} finally {
			//if(pstmt != null) pstmt.close();
		}
		
		return rs;		
	}

	public void setAutoCommit(boolean bStatus) throws Exception {
		if(conn != null) conn.setAutoCommit(bStatus);
	}
}
