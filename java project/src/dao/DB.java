package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dto.MemberDTO;

public abstract class DB {
	private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String username = "system";
	private static final String pw = "1111";
	Connection con;
	PreparedStatement p;
	Statement stmt;
	ResultSet rs;
	
	public DB(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
		} catch (Exception e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}
	}
	
	public boolean getCon() {
		try {
			con = DriverManager.getConnection(url, username, pw);
			System.out.println("연결 성공");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("연결 실패");
		}
		return false;
	}
	
	public void close(PreparedStatement p, Connection con) {
		try {
			if(p != null) {
				p.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(PreparedStatement p, Connection con, ResultSet rs) {
		try {
			if(p != null) {
				p.close();
			}
			if(con != null) {
				con.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void insert(Object obj);
}
