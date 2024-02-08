package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.InterviewDAO;
import dao.QuizDAO;
import fnc.ImagePath;

public class Quiz extends JFrame implements ImagePath,ActionListener{
	public QuizDAO quizdao = QuizDAO.getInstance();
	//디비에 저장된 퀴즈 테이블 카테고리+문제 저장
	private ArrayList<String> q = quizdao.showQ("중");
	//디비에 저장된 퀴즈 테이블 주관식 힌트 저장
	private ArrayList<String> userAnswer;
	private JTextArea quiz = new JTextArea();
	private JTextField a = new JTextField(20);
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("퀴즈배경")));
	private ImageIcon button = new ImageIcon(getClass().getResource(getPath("다음")));
	private ImageIcon button2 = new ImageIcon(getClass().getResource(getPath("힌트")));
	private JButton next = new JButton(button);
	private JButton hintB = new JButton(button2);
	private int index;
	private int aIndex;
	private int hintIndex;
	private Font font = new Font("맑은 고딕 Bold", Font.PLAIN, 18);
	
	public Quiz(int num) {
		//문제, 정답, 힌트의 처음 인덱스(0) 넘겨받아 현 객체에 저장
		this.index = num;
		this.aIndex = num;
		this.hintIndex = num;
		mainFrame(num);
		this.setTitle("개념 퀴즈 - 주관식");
		hintB.addActionListener(this);
		next.addActionListener(this);
	}
	
	private JPanel qnaPanel(int qIndex) {
		JPanel panel = new JPanel(null);
		quiz.setFont(font);
		a.setFont(font);
		//리스트에 저장한 퀴즈 문제를 인덱스 입력해 불러와 textArea에 반영
		quiz.setText(q.get(qIndex));
		//문제 반영된 textArea 수정 안되게 설정
		quiz.setEditable(false);
		//textArea 자동 줄바꿈 설정
		quiz.setLineWrap(true);
		//스크롤 패널에 textArea 추가해 긴 문제도 볼 수 있게 설정
		JScrollPane scrollPane = new JScrollPane(quiz);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(35,80,650,80);
		JLabel label = new JLabel(bg);
		label.setBounds(0, -20, 723, 445);
		a.setBounds(115, 200, 570, 30);
		next.setBounds(125,280,480,50);
		next.setBorderPainted(false);
		hintB.setBounds(57,235,50,50);
		panel.add(scrollPane);
		panel.add(a);
		panel.add(next);
		panel.add(hintB);
		panel.add(label);
		panel.setOpaque(true);
		return panel;
		
	}
	private void mainFrame(int num) {
		this.getContentPane().add(qnaPanel(num));
		this.setBounds(250, 250, 738, 444);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//힌트 버튼을 눌렀을 때
		if(e.getSource()==hintB) {
			//디비에 저장된 주관식 문제의 힌트를 리스트에 저장
			userAnswer = quizdao.showA("hint","중");
			//팝업 메시지로 힌트 띄움
			JOptionPane.showMessageDialog(this, "힌트 : "+userAnswer.get(aIndex));
			//인덱스 증가시켜 다음 힌트로 넘어감
			hintIndex += 1;
			//다음 버튼을 눌렀을 때
		}else if(e.getSource()==next) {
			//문제 리스트 인덱스 증가시켜 다음 문제 인덱스로 만들기
			index += 1;
			try {
				//인덱스가 문제 리스트의 길이 이하일 경우
				if(index <= q.size()) {
					//디비에 저장된 주관식 정답을 리스트에 저장
					userAnswer = quizdao.showA("answer","중");
					//입력한 정답이 디비에 저장된 정답과 같다면
					if(a.getText().equals(userAnswer.get(aIndex))) {
						//정답 팝업 메시지
						JOptionPane.showMessageDialog(
								this,
								//정답 출력
								"정답 : "+userAnswer.get(aIndex),
								"맞혔어요!^0^",
								JOptionPane.INFORMATION_MESSAGE
								);					
						
					}else {
						//오답 팝업 메시지
						JOptionPane.showMessageDialog(
								this,
								//정답 출력
								"정답 : "+userAnswer.get(aIndex),
								"틀렸어요..ㅠㅠ",
								JOptionPane.INFORMATION_MESSAGE
								);	
					}
					//정답 리스트 인덱스 증가
					aIndex += 1;
					//다음 문제 출력
					new Quiz(index);
				}
				//인덱스가 문제 리스트의 길이보다 커지면 문제 풀이 완료 팝업 메시지
			} catch (IndexOutOfBoundsException e2) {
				JOptionPane.showMessageDialog(this, "모든 문제를 풀었습니다.");					
			}//현 퀴즈 화면 끄기
			setVisible(false);
		}
	}
	
}
