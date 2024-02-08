package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.InterviewDAO;
import dto.InterviewDTO;
import dto.MemberDTO;
import dto.QuizDTO;
import fnc.ImagePath;
import fnc.SettingFrame;

public class Setting extends JFrame implements SettingFrame, ImagePath, ActionListener {
	public InterviewDAO interviewdao = InterviewDAO.getInstance();
	public static Setting instance = null;
	JComboBox<String> mainBox = new JComboBox<>(boxOption("개념 퀴즈", "면접 연습"));
	JComboBox<String> qSubBox = new JComboBox<>(boxOption("객관식", "주관식"));
	JComboBox<String> qSubBox2 = new JComboBox<>(boxOption("[자바]", "[DB]"));
	JComboBox<String> iSubBox = new JComboBox<>(boxOption("[기술]", "[인성]"));
	JComboBox<String> iSubBox2 = new JComboBox<>(boxOption("[목표]", "[경험]", "[기타]"));
	JPanel panel = new JPanel(null);
	String selectedOption;
	private ImageIcon ok = new ImageIcon(getClass().getResource(getPath("완료")));
	private JButton button = new JButton(ok);
	JTextArea textArea = new JTextArea();

	private Setting() {	}
	
	public static Setting getInstance() {
		if(instance == null) {
			instance = new Setting();
		}
		return instance;
	}
	
	public JPanel mainPanel() {
		ImageIcon bg = new ImageIcon(getClass().getResource(getPath("설정화면")));
		JLabel label = new JLabel(bg);
		textArea.setBackground(new Color(0xC4DDF6));
		textArea.setBounds(60, 160, 245, 130);
		textArea.setLineWrap(true);
		textArea.setText("여기에 면접 질문 혹은 퀴즈 문제를 입력해 주세요.");
		button.setBounds(135, 305, 80, 50);
		button.setBorderPainted(false);
		mainBox.setBounds(60, 90, 90, 20);
		qSubBox.setBounds(165, 90, 70, 20);
		iSubBox.setBounds(165, 90, 70, 20);
		iSubBox2.setBounds(245, 90, 70, 20);
		qSubBox2.setBounds(245, 90, 70, 20);
		//하위 카테고리 콤보박스 안보이게 설정
		qSubBox.setVisible(false);
		iSubBox.setVisible(false);
		iSubBox2.setVisible(false);
		qSubBox2.setVisible(false);
		label.setBounds(0, -20, 380, 425);
		panel.add(mainBox);
		panel.add(qSubBox);
		panel.add(iSubBox);
		panel.add(iSubBox2);
		panel.add(qSubBox2);
		panel.add(textArea);
		panel.add(button);
		panel.add(label);
		panel.setOpaque(false);
		return panel;
	}
	
	//콤보박스 내용 설정하는 메서드
	private String[] boxOption(String c1, String c2) {
		String[] options = { c1, c2 };
		return options;
	}
	private String[] boxOption(String c1, String c2, String c3) {
		String[] options = { c1, c2, c3 };
		return options;
	}

	void mainFrame() {
		this.setTitle("설정");
		mainBox.addActionListener(this);
		qSubBox2.addActionListener(this);
		qSubBox.addActionListener(this);
		iSubBox.addActionListener(this);
		button.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(mainPanel());
		this.setBounds(395, 260, 398, 435);
		setVisible(true);
	}
	
	//회원이 입력한 면접 질문 객체에 저장하는 메서드
	private InterviewDTO inputInterview() {
		//데이터 저장할 새로운 객체 생성
		InterviewDTO data = new InterviewDTO();
		//회원이 선택한 카테고리 문자열에 저장
		String sort = (String) iSubBox.getSelectedItem();
		String lowSort = (String) iSubBox2.getSelectedItem();
		String lowSort2 = (String) qSubBox2.getSelectedItem();
		//'면접 연습' 카테고리 선택한 경우만
		if ("면접 연습".equals(selectedOption)) {
			//선택한 카테고리 내용 객체에 저장
			data.setSort(sort);
			if ("[인성]".equals(sort)) {
				data.setLow_sort(lowSort);
			} else {
				data.setLow_sort(lowSort2);
			}//입력된 데이터가 공백이 아니고 글자수 500자 이하인 경우
			if (!textArea.getText().equals("") && textArea.getText().length() <= 500) {
				data.setQ(textArea.getText());
			} else {
				return null;
			}
			return data;
		}
		return null;
	}
	
	//회원이 선택한 난이도 카테고리 DB 속성에 맞게 수정해 반환하는 메서드
	private String quizDifficulty() {
		String difficulty = (String) qSubBox.getSelectedItem();
		if ("주관식".equals(difficulty)) {
			difficulty = "중";
		} else {
			difficulty = "하";
		}
		return difficulty;
	}
	
	//회원이 입력한 퀴즈 문제 객체에 저장하는 메서드
	private QuizDTO inputQuiz() {
		//데이터 저장할 새로운 객체 생성
		QuizDTO data = new QuizDTO();
		//회원이 선택한 카테고리 문자열에 저장
		String qType = (String) qSubBox2.getSelectedItem();
		//'개념 퀴즈' 카테고리 선택한 경우만
		if ("개념 퀴즈".equals(selectedOption)) {
			String input = textArea.getText();
			data.setDifficulty(quizDifficulty());
			data.setQ_type(qType);
			//입력된 데이터가 공백이 아니고 글자수 500자 이하인 경우
			if (!input.equals("") && input.length() <= 500) {
				//문제 객체에 저장
				data.setQu(input);
			} else {//데이터가 공백이거나 500자 초과인 경우
				return null;
			}
			return data;
		}
		return null;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		//메인 콤보박스 선택시
		if (e.getSource() == mainBox) {
			//선택된 콤보박스 문자열 저장
			selectedOption = (String) mainBox.getSelectedItem();
			// 선택된 옵션에 따라 하위 카테고리 콤보박스의 가시성 변경
			if ("개념 퀴즈".equals(selectedOption)) {
				//'개념 퀴즈' 선택시 퀴즈 관련 콤보박스만 드러내기
				qSubBox.setVisible(true);
				qSubBox2.setVisible(true);
				iSubBox2.setVisible(false);
			} else {//'면접 연습' 선택시 관련 콤보박스만 드러내기
				qSubBox.setVisible(false);
				iSubBox.setVisible(true);
			}
			//면접 연습 하위 카테고리 선택시
		} else if (e.getSource() == iSubBox) {
			String selectedOption2 = (String) iSubBox.getSelectedItem();

			// 선택된 옵션에 따라 패널의 가시성 변경
			if ("[기술]".equals(selectedOption2)) {
				qSubBox2.setVisible(true);
				iSubBox2.setVisible(false);
			} else {
				qSubBox2.setVisible(false);
				iSubBox2.setVisible(true);
			}
		}
		//개념 퀴즈 콤보박스를 선택하고
		if ("개념 퀴즈".equals(selectedOption)) {
			//완료 버튼을 눌렀을 경우
			if (e.getSource() == button) {
				//회원이 입력한 데이터 저장된 객체
				QuizDTO data = inputQuiz();
				//데이터가 잘못 입력된 경우 팝업 메시지
				if (data == null) {
					JOptionPane.showMessageDialog(this, "카테고리가 선택되지 않았거나\n퀴즈가 입력되지 않았거나\n글자수가 500자 초과입니다.", "잘못된 입력",
							JOptionPane.INFORMATION_MESSAGE);
				} else {//데이터가 알맞게 입력된 경우
					 JOptionPane.showMessageDialog(this, "퀴즈가 등록됐습니다.\n다음 단계로 넘어갑니다.", "입력 성공",
					 JOptionPane.INFORMATION_MESSAGE);
					 //현재 창 끔
					 setVisible(false);
					 //퀴즈 보기, 정답, 힌트/해설 설정창 생성
					 //현재 입력된 데이터(카테고리, 문제)를 전달
					 new SettingQuizAnswer(quizDifficulty(), data);
				};
			}
		} else {//면접 연습 콤보박스를 선택하고
			//완료 버튼을 눌렀을 경우
			if (e.getSource() == button) {
				//회원이 입력한 데이터 저장된 객체
				InterviewDTO data = inputInterview();
				//데이터 잘못 입력된 경우 팝업 메시지
				if (data == null) {
					JOptionPane.showMessageDialog(this, "카테고리가 선택되지 않았거나\n질문이 입력되지 않았거나\n글자수가 500자 초과입니다.", "잘못된 입력",
							JOptionPane.INFORMATION_MESSAGE);
				} else {//데이터 알맞게 입력된 경우
					//튜플 삽입 메서드 호출
					interviewdao.insert(data);
					JOptionPane.showMessageDialog(this, "질문이 등록됐습니다.", "입력 성공", JOptionPane.INFORMATION_MESSAGE);
				};
			}
		}
	}

}
