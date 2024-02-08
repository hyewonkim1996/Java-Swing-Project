package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import dao.UserInterviewDAO;
import dto.MemberDTO;

public class InterviewAnswerModify extends JFrame implements ActionListener{
	public UserInterviewDAO userinterviewdao = UserInterviewDAO.getInstance();
	private JTextArea answer = new JTextArea();
	private JButton button;
	private int qNum;
	private Font font = new Font("맑은 고딕 Bold", Font.PLAIN, 15);
	String test;
	
	public InterviewAnswerModify(MemberDTO dto, int qNum) {
		this.qNum = qNum;
		this.setTitle("답변 수정");
		modFrame();
		button.addActionListener(this);
	}
	
	//답변 수정 패널
	private void modFrame() {
		//회원이 등록한 답변 조회 메서드 호출
		test = userinterviewdao.showAnswer(MainMenu.user, qNum);
		//회원이 등록한 답변 textarea에 반영
		answer.setText(test);
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("답변을 수정해 주세요.");
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(BorderFactory.createEmptyBorder(10,0,30,0)); //위, 왼, 아래, 오
		panel.add(label,"North");
		answer.setLineWrap(true);
		answer.setWrapStyleWord(true);
		panel.add(answer,"Center");
		button = new JButton("완료");
		button.setFont(font);
		panel.add(button, "South");
		this.getContentPane().add(panel);
		this.setSize(500,500);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//완료 버튼을 눌렀을 때
		if(e.getSource()==button) {
			//튜플 수정 메서드 호출
			userinterviewdao.update(answer.getText(),MainMenu.user,qNum);
			JOptionPane.showMessageDialog(this, "답변이 수정되었습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		}
	}

}
