package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.QuizDAO;
import fnc.ImagePath;

public class QuizChoice extends JFrame implements ImagePath,ActionListener{
	public QuizDAO quizdao = QuizDAO.getInstance();
	//디비 퀴즈 테이블에서 불러온 객관식 문제와 보기 리스트에 저장 
	private ArrayList<String> q = quizdao.showQ("하");
	private ArrayList<String> c = quizdao.showChoice();
	private ArrayList<String> userAnswer;
	//선택된 체크박스(보기)의 번호 저장
	private ArrayList<Integer> selectedIndices = new ArrayList<>();
	//모든 보기들에 체크박스, 스크롤 패널에 설정 후 저장
	private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
	private ArrayList<JScrollPane> checkBoxScrollPanes = new ArrayList<>();
	private JTextArea quiz = new JTextArea();
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("객관식배경")));
	private ImageIcon button = new ImageIcon(getClass().getResource(getPath("다음")));
	private JButton next = new JButton(button);
	private int index;
	private int aIndex;
	private Font font = new Font("맑은 고딕 Bold", Font.PLAIN, 18);
	
	public QuizChoice(int num) {
		//문제, 정답 인덱스 0으로 입력받아 현 객체에 저장
		this.index = num;
		this.aIndex = num;
		this.setTitle("개념 퀴즈 - 객관식");
		mainFrame(num);
		next.addActionListener(this);
	}
	
	private JPanel qnaPanel(int qIndex) {
		JPanel panel = new JPanel(null);
		quiz.setFont(font);
		quiz.setText(q.get(qIndex));
		quiz.setLineWrap(true);
		quiz.setWrapStyleWord(true);
		quiz.setEditable(false);
		//해당 문제의 보기들 문자열에 저장
		String a = c.get(qIndex);
		//개행문자 \n을 기준으로 나눠 배열에 저장
		String[] choices = a.split("\n");
		int checkBoxX = 35;
	    int checkBoxY = 150;
	    //모든 보기에 반복
		for (int i = 0; i < choices.length; i++) {
			//보기 체크박스에 담기
            JCheckBox checkBox = new JCheckBox(choices[i]);
            //보기가 길어질 경우를 위해 체크박스를 스크롤 패널에 담기
            JScrollPane scrollPane = new JScrollPane(checkBox);
            //체크박스, 스크롤패널 설정
            checkBox.setOpaque(false);
            checkBox.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            checkBox.setBounds(checkBoxX, checkBoxY, 650, 20);  // 체크박스의 위치 설정
            checkBoxY += 30;
            scrollPane.setBounds(checkBoxX, checkBoxY, 650, 30);
            scrollPane.setOpaque(false);
            //보기가 여러개이므로 체크박스 리스트->스크롤 패널 리스트에 저장
            checkBoxes.add(checkBox);
            checkBoxScrollPanes.add(scrollPane);
         // 체크박스 체크 여부 판별 후 체크박스 번호 저장
            int finalI = i;
            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                	// 체크박스가 체크됐다면
                    if (checkBox.isSelected()) {
                    	//체크박스 번호 리스트에 번호 저장
                        selectedIndices.add(finalI);
                    } else { //체크박스가 해제됐다면
                    	//저장된 번호 리스트에서 삭제
                        selectedIndices.remove((Integer) finalI);
                    }
                }
            });
            panel.add(scrollPane);
        }
		JScrollPane scrollPane = new JScrollPane(quiz);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(35,80,650,60);
		JLabel label = new JLabel(bg);
		label.setBounds(0, -20, 723, 445);
		next.setBounds(125,320,480,50);
		next.setBorderPainted(false);
		panel.add(scrollPane);
		panel.add(next);
		panel.add(label);
		panel.setOpaque(false);
		return panel;
	}
	
	private void mainFrame(int num) {
		this.getContentPane().add(qnaPanel(num));
		this.setBounds(250, 250, 738, 444);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//다음 버튼을 눌렀을 때
		if(e.getSource()==next) {
			StringBuilder selectedOrder = new StringBuilder();
			//선택된 체크박스 번호 리스트의 번호들을 오름차순으로 정렬하는 메서드
            Collections.sort(selectedIndices);
            //정렬된 번호들에 쉼표 붙이기
            for (int index : selectedIndices) {
                selectedOrder.append(index + 1).append(",");
            }
            //마지막 번호에 붙는 쉼표 제거
            if (selectedOrder.length() > 0) {
                selectedOrder.deleteCharAt(selectedOrder.length() - 1);
            }
            //다음 버튼 클릭 시 문제 리스트 인덱스 증가시켜 다음 문제 인덱스로 만들기
			index += 1;
			try {
				//인덱스가 문제 리스트의 길이 이하일 경우
				if(index <= q.size()) {
					//디비에 저장된 객관식 정답을 리스트에 저장
					userAnswer = quizdao.showA("answer","하");
					//입력한 정답이 내가 선택한 보기와 같다면
					if(userAnswer.get(aIndex).equals(selectedOrder.toString())) {
						//현 문제 정답 문자열에 저장
						String temp = userAnswer.get(aIndex);
						//디비에 저장된 객관식 해설을 리스트에 저장
						userAnswer = quizdao.showA("hint", "하");
						//정답 팝업 메시지
						JOptionPane.showMessageDialog(
								this,
								//정답과 해설 보이기
								"정답 : "+temp+"번\n"+userAnswer.get(aIndex),
								"맞혔어요!^0^",
								JOptionPane.INFORMATION_MESSAGE
								);					
						
					}else {//오답 팝업 메시지
						String temp = userAnswer.get(aIndex);
						userAnswer = quizdao.showA("hint", "하");
						JOptionPane.showMessageDialog(
								this,
								"정답 : "+temp+"번\n"+userAnswer.get(aIndex),
								"틀렸어요..ㅠㅠ",
								JOptionPane.INFORMATION_MESSAGE
								);	
					}
					//다음 문제 출력
					new QuizChoice(index);
				}//인덱스가 리스트 길이보다 커지면 모든 문제 풀이 완료
			} catch (IndexOutOfBoundsException e2) {
				JOptionPane.showMessageDialog(this, "모든 문제를 풀었습니다.");					
			}//퀴즈 화면 끔
			setVisible(false);
		}
		
	}

}
