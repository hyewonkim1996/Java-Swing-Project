package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dto.MemberDTO;
import dto.UserInterviewDTO;

public class UserInterviewDAO extends DB{
	public static UserInterviewDAO instance = null;

	public UserInterviewDAO() {
	}

	public static UserInterviewDAO getInstance() {
		if (instance == null) {
			instance = new UserInterviewDAO();
		}
		return instance;
	}
	
	@Override
	public void insert(Object obj) {
		//현 회원 아이디, 클릭한 질문 번호, 답변이 저장된 객체를 넘겨받음
		UserInterviewDTO dto = (UserInterviewDTO) obj;
		p = null;
		if(getCon()) {
			try {
				String query = "insert into user_interview values(?,?,?)";
				p=con.prepareStatement(query);
				p.setString(1, dto.getId());
				p.setInt(2, dto.getqNum());
				p.setString(3, dto.getMyAnswer());
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
	
	
	public String showAnswer(MemberDTO dto, int qNum) {
		p=null;
		String my_a;
		if (getCon()) {
			try {
				String query = "select my_a from user_interview where id=? and num=?";
				p=con.prepareStatement(query);
				p.setString(1,dto.getId());
				p.setInt(2, qNum);
				rs = p.executeQuery();
				while (rs.next()) {
					my_a = rs.getString(1);
					return my_a;
				}
			} catch (SQLException e) {
				System.out.println("유저 인터뷰 테이블 답변 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, con, rs);
			}
		}
		return null;
	}
	
	//답변 수정 메서드
	public void update(String answer, MemberDTO dto, int qNum) {
		p = null;
		if(getCon()) {
			try {
				String query = "update user_interview set my_a=? where id=? and num=?";
				p=con.prepareStatement(query);
				p.setString(1, answer);
				p.setString(2, dto.getId());
				p.setInt(3, qNum);
				int result = p.executeUpdate();
				System.out.println(result+"건 수정 성공");
			} catch (Exception e) {
				System.out.println("수정 오류");
				e.printStackTrace();
			}finally {
				close(p, con);
			}
		}
	}
	
	//답변 등록 여부 확인 메서드
	public HashMap<String, ArrayList<Integer>> answerCheck(MemberDTO user) {
		HashMap<String, ArrayList<Integer>> userAnswerList = new HashMap<>();
		p=null;
		rs=null;
		if (getCon()) {
			try {
				String query = "select num from user_interview where id=?";
				p=con.prepareStatement(query);
				p.setString(1,user.getId());
				rs = p.executeQuery();
				ArrayList<Integer> qNum = new ArrayList<>();
				while (rs.next()) {
					qNum.add(rs.getInt("num"));
				}
				userAnswerList.put(user.getId(),qNum);
				System.out.println("유저가 답변 등록한 질문 컬럼 조회 성공");
				return userAnswerList;

			} catch (SQLException e) {
				System.out.println("테이블 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, null, rs);
			}
		}
		return null;
	}

}
