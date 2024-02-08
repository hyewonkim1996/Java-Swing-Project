package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dao.InterviewDAO;
import dao.QuizDAO;
import dto.QuizDTO;
import fnc.ImagePath;

public class SettingQuizAnswer extends JFrame implements ImagePath, ActionListener {
	public QuizDAO quizdao = QuizDAO.getInstance();
	JTextArea textArea = new JTextArea();
	JTextArea textArea2 = new JTextArea();
	JTextArea input = new JTextArea();
	private JButton button = new JButton("보기");
	private JButton answer = new JButton("정답");
	private JButton hint = new JButton("힌트/해설");
	private JButton last = new JButton("최종 확인");
	private QuizDTO data;
	String level;

	SettingQuizAnswer(String level, QuizDTO temp) {
		this.setTitle("설정");
		this.level = level;
		this.data = temp;
		mainFrame();
		button.addActionListener(this);
		answer.addActionListener(this);
		hint.addActionListener(this);
		last.addActionListener(this);
	}

	private void mainFrame() {
		this.getContentPane().add(mainPanel());
		this.setBounds(395, 260, 675, 722);
		setVisible(true);
	}

	public JPanel mainPanel() {
		JPanel panel = new JPanel(null);
		ImageIcon bg = new ImageIcon(getClass().getResource(getPath("퀴즈설정화면")));
		JLabel label = new JLabel(bg);
		label.setBounds(0, -20, 685, 727);
		textArea.setBackground(new Color(0xC4DDF6));
		textArea2.setBackground(new Color(0xC4DDF6));
		button.setBackground(new Color(0xC4DDF6));
		answer.setBackground(new Color(0xC4DDF6));
		hint.setBackground(new Color(0xC4DDF6));
		input.setBackground(new Color(0xFA, 0xCD, 0xCC));
		last.setBackground(new Color(0xFA, 0xCD, 0xCC));
		textArea.setBounds(110, 150, 205, 250);
		textArea2.setBounds(320, 150, 205, 250);
		input.setBounds(110, 410, 410, 120);
		textArea.setText("========객관식 규칙========\r\n" + "<보기>\r\n" + "① 보기1 입력/② 보기2 입력/③ 보기3 입력/④ 보기4 입력\r\n"
				+ "보기는 줄바꿈 없이 한줄로 입력하고 /를 지우지 마세요.\r\n" + "\r\n" + "<정답>\r\n" + "보기 숫자로 입력하세요.\r\n" + "단일정답 예시 : 1\r\n"
				+ "복수정답 예시(오름차순 입력) : 2,3,4\r\n" + "\r\n" + "<해설>\r\n" + "정답에 대한 자세한 설명을 적어 주세요.(형식 제약 없음)\r\n"
				+ "=======================");
		textArea2.setText("========주관식 규칙========\r\n" + "<정답>\r\n" + "한글로 띄어쓰기 없이 입력하세요.\r\n"
				+ "예시 : 인터페이스(o), interface(x)  / 멤버변수(o), 멤버 변수(x) \r\n" + "\r\n" + "<힌트>\r\n"
				+ "정답에 대한 힌트를 적어 주세요.(형식 제약 없음)\r\n" + "=======================");
		input.setText("여기에 보기/정답/힌트를 입력 후 알맞은 등록 버튼을 누르세요.");
		textArea.setLineWrap(true);
		textArea2.setLineWrap(true);
		input.setLineWrap(true);
		textArea.setEditable(false);
		textArea2.setEditable(false);
		button.setBounds(105, 550, 80, 50);
		answer.setBounds(185, 550, 80, 50);
		hint.setBounds(265, 550, 130, 50);
		last.setBounds(395, 550, 130, 50);
		panel.add(textArea);
		panel.add(textArea2);
		panel.add(input);
		panel.add(button);
		panel.add(answer);
		panel.add(hint);
		panel.add(last);
		panel.add(label);
		panel.setOpaque(false);
		return panel;
	}
	
	//회원이 입력한 퀴즈 데이터 객체에 저장해 반환하는 메서드
	private QuizDTO inputQuiz(QuizDTO temp, String column) {
		//회원이 입력한 데이터 문자열에 저장
		String input2 = input.getText();
		//문제가 등록된 객체 전달받음
		data = temp;
		//입력된 데이터가 공백이 아닐 경우만
		if (!input2.equals("")) {
			//입력된 데이터 알맞은 변수에 저장 
			if (column.equals("보기")) {
				data.setChoice(input2);
			} else if (column.equals("정답")) {
				data.setAnswer(input2);
			} else if (column.equals("힌트/해설")) {
				data.setHint(input2);
			}
			return data;
		}else {//입력된 데이터가 공백이라면
			return null;
		}
	}
	
	//공백 데이터 혹은 컬럼명에 알맞은 팝업 메시지 띄우는 메서드
	private void buttonLogic(QuizDTO temp, String column) {
		if (temp == null) {//데이터가 공백이라면
			JOptionPane.showMessageDialog(this, "데이터가 입력되지 않았습니다.", "잘못된 입력",
					JOptionPane.INFORMATION_MESSAGE);
		} else {//데이터가 알맞게 입력됐다면
			 JOptionPane.showMessageDialog(this, column+" 입력됐습니다.", "입력 성공",
			 JOptionPane.INFORMATION_MESSAGE);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//보기 버튼을 눌렀을 때
		if (e.getSource() == button) {
			//주관식 카테고리 선택했다면
			if (level.equals("중")) {
				JOptionPane.showMessageDialog(this, "객관식 전용 버튼입니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
			} else {//객관식 카테고리 선택했다면
				//임시 그릇에 회원이 입력한 데이터 저장
				QuizDTO bowl = inputQuiz(data, "보기");
				//팝업 메시지 메서드 호출
				buttonLogic(bowl, "보기가");
				//임시 그릇에 저장된 객체가 공백 데이터가 아니라면
				if(bowl != null) {
					//해당 데이터 객체에 저장
					data = bowl;
				}
			}
			//정답 버튼을 눌렀을때
		} else if (e.getSource() == answer) {
			QuizDTO bowl = inputQuiz(data, "정답");
			buttonLogic(bowl, "정답이");
			System.out.println("정답:"+data.getAnswer());
			if(bowl != null) {
				data = bowl;
			}//힌트/해설 버튼을 눌렀을때
		}else if(e.getSource() == hint){
			QuizDTO bowl = inputQuiz(data, "힌트/해설");
			buttonLogic(bowl, "힌트/해설이");
			System.out.println("힌트/해설:"+data.getHint());
			if(bowl != null) {
				data = bowl;
			}
			//최종 확인 버튼을 눌렀을때
		} else if(e.getSource()==last) {
			//주관식 카테고리 선택한 경우
			if(level.equals("중")) {
				//정답, 힌트에 공백 데이터가 없는지 다시 확인
				if(data.getAnswer() != null && data.getHint() != null) {
					//튜플 삽입 메서드 호출
					quizdao.insert(data);
					JOptionPane.showMessageDialog(this, "퀴즈가 최종 등록됐습니다.", "등록 성공",
							 JOptionPane.INFORMATION_MESSAGE);
				}else {//정답, 힌트에 공백 데이터가 있다면
					JOptionPane.showMessageDialog(this, "입력되지 않은 데이터가 있습니다.", "등록 실패",
							 JOptionPane.INFORMATION_MESSAGE);
				}
			}else {//객관식 카테고리 선택한 경우
				//보기, 정답, 해설에 공백 데이터가 없는지 다시 확인
				if(data.getAnswer() != null && data.getHint() != null && data.getChoice() != null) {
					//튜플 삽입 메서드 호출
					quizdao.insert(data);
					JOptionPane.showMessageDialog(this, "퀴즈가 최종 등록됐습니다.", "등록 성공",
							 JOptionPane.INFORMATION_MESSAGE);
				}else {//보기, 정답, 해설에 공백 데이터가 있다면
					JOptionPane.showMessageDialog(this, "입력되지 않은 데이터가 있습니다.", "등록 실패",
							 JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
