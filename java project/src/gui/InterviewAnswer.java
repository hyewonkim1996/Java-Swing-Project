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
import javax.swing.JTextField;

import dao.InterviewDAO;
import dao.UserInterviewDAO;
import dto.MemberDTO;
import dto.UserInterviewDTO;

public class InterviewAnswer extends JFrame implements ActionListener{
	public UserInterviewDAO userinterviewdao = UserInterviewDAO.getInstance();
	private JTextArea answer = new JTextArea();
	private JButton button;
	private int qNum;
	private Font font = new Font("맑은 고딕 Bold", Font.PLAIN, 15);
	
	public InterviewAnswer(MemberDTO dto, int qNum){
		this.qNum = qNum;
		this.setTitle("답변 작성");
		mainFrame();
		button.addActionListener(this);
	}
	
	private void mainFrame() {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("답변을 입력해 주세요.");
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
	
	//현 유저가 입력한 답변 객체에 저장하는 메서드
	private UserInterviewDTO getAnswer() {
		UserInterviewDTO a = new UserInterviewDTO();
		a.setId(MainMenu.user.getId());
		a.setqNum(qNum);
		a.setMyAnswer(answer.getText());
		return a;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//완료 버튼을 눌렀을 때
		if(e.getSource()==button) {
			//튜플 삽입 메서드 호출
			userinterviewdao.insert(getAnswer());
			JOptionPane.showMessageDialog(this, "답변이 등록되었습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
			//답변 등록창 끔
			setVisible(false);
		}
	}
}
