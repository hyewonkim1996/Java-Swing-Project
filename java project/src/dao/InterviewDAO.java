package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import dto.InterviewDTO;


public class InterviewDAO extends DB {
	private ArrayList<ArrayList<Object>> data = new ArrayList<>();
	public static InterviewDAO instance = null;

	public InterviewDAO() {
	}

	public static InterviewDAO getInstance() {
		if (instance == null) {
			instance = new InterviewDAO();
		}
		return instance;
	}

	@Override
	public void insert(Object obj) {
		InterviewDTO data = (InterviewDTO) obj;
		if(getCon()) {
			try {
				String query = "insert into interview values(q_num.nextval,?,?,?)";
				p=con.prepareStatement(query);
				p.setString(1, data.getSort());
				p.setString(2, data.getLow_sort());
				p.setString(3, data.getQ());
				int result = p.executeUpdate();
				System.out.println(result+"건 삽입 성공");
			} catch (Exception e) {
				System.out.println("삽입 오류");
				e.printStackTrace();
			}finally {
				close(p, con);
			}
		}
	}
	
	public ArrayList<ArrayList<Object>> selectAll() {
		if (getCon()) {
			try {
				String query = "select num, sort, low_sort, q from interview";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					int num = rs.getInt(1);
					String sort = rs.getString(2);
					String low_sort = rs.getString(3);
					String q = rs.getString(4);
					data.add(new ArrayList<>(Arrays.asList(num, sort, low_sort, q)));
				}
				System.out.println("면접 테이블 조회 성공");
				return data;

			} catch (SQLException e) {
				System.out.println("테이블 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, con, rs);
			}
		}
		return null;
	}
}
