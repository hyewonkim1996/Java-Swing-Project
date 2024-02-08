package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.MemberDTO;

public class MemberDAO extends DB{
	private ArrayList<MemberDTO> memList = new ArrayList<>();
	
	private MemberDAO() {}
	
	public static MemberDAO instance = null;
	public static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	public ArrayList<MemberDTO> getMemList(){
		return memList;
	}
	
	//회원 튜플 삽입 메서드
	@Override
	public void insert(Object obj) {//회원가입때 입력받은 데이터 객체를 매개변수로 전달받음
		MemberDTO dto = (MemberDTO) obj;
		if (dto == null) {
	        System.out.println("입력된 데이터가 유효하지 않습니다.");
	        return;
	    }
		p = null;
		if(getCon()) {
			try {
				//튜플 삽입시 무결성 제약조건 주의(중복된 아이디 입력되면 오류)
				String query = "insert into member2 values(?,?)";
				p=con.prepareStatement(query);
				p.setString(1, dto.getId());
				p.setString(2, dto.getPw());
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

	public void selectAll() {
		if(getCon()) {
			try {
				String query = "select * from member2";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				while(rs.next()) {
					MemberDTO dto = new MemberDTO();
					dto.setId(rs.getString(1));
					dto.setPw(rs.getString(2));
					memList.add(dto);
				}
				System.out.println("테이블 조회 및 디비 데이터 객체 저장 성공");
				
			} catch (SQLException e) {
				System.out.println("테이블 조회 오류");
				e.printStackTrace();
			}finally {
				close(p, con, rs);
			}
		}
	}
	
}
