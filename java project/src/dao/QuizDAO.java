package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.InterviewDTO;
import dto.MemberDTO;
import dto.QuizDTO;

public class QuizDAO extends DB{
	
	public static QuizDAO instance = null;

	public QuizDAO() {
	}

	public static QuizDAO getInstance() {
		if (instance == null) {
			instance = new QuizDAO();
		}
		return instance;
	}
	
	public ArrayList<String> showQ(String level) {
		ArrayList<String> qu = new ArrayList<>();
		p=null;
		String q_type;
		String q;
		if (getCon()) {
			try {
				String query = "select q_type, qu from quiz where difficulty='"+level+"'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					q_type = rs.getString(1);
					q = rs.getString(2);
					qu.add(q_type+" "+q);
				}
				return qu;
			} catch (SQLException e) {
				System.out.println("퀴즈 테이블 질문 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, con, rs);
			}
		}
		return null;
	}
	
	public ArrayList<String> showA(String column, String level) {
		ArrayList<String> answer = new ArrayList<>();
		p=null;
		String a;
		if (getCon()) {
			try {
				String query = "select "+column+" from quiz where difficulty='"+level+"'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					a = rs.getString(1);
						answer.add(a);
				}
				return answer;
			} catch (SQLException e) {
				System.out.println("퀴즈 테이블 질문 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, con, rs);
			}
		}
		return null;
	}
	
	public ArrayList<String> showChoice() {
		//보기를 저장할 리스트
		ArrayList<String> choice = new ArrayList<>();
		p=null;
		String q;
		if (getCon()) {
			try {
				String query = "select choice from quiz where difficulty='하'";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					q = rs.getString(1);
					// '/'기준으로 끊어 배열에 저장
					String[] choices = q.split("/");
					//스트링빌더 활용해 문자열 객체 여러개 생성되지 않음
	                StringBuilder modifiedChoice = new StringBuilder();
	                //보기마다 줄바꿈 개행문자 추가
	                for (String choicePart : choices) {
	                    modifiedChoice.append(choicePart).append("\n");
	                }
	                //다시 문자열로 변환해 리스트에 저장
	                choice.add(modifiedChoice.toString().trim());
				}
				return choice;
			} catch (SQLException e) {
				System.out.println("퀴즈 테이블 질문 조회 오류");
				e.printStackTrace();
			} finally {
				close(p, con, rs);
			}
		}
		return null;
	}
	
	@Override
	public void insert(Object obj) {
		QuizDTO data = (QuizDTO) obj;
		if(getCon()) {
			try {
				//튜플 삽입시 무결성 제약조건 주의(중복된 아이디 입력되면 오류)
				String query = "insert into quiz values(?,?,?,?,?,?)";
				p=con.prepareStatement(query);
				p.setString(1, data.getQ_type());
				p.setString(2, data.getDifficulty());
				p.setString(3, data.getQu());
				p.setString(4, data.getChoice());
				p.setString(5, data.getAnswer());
				p.setString(6, data.getHint());
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

}
