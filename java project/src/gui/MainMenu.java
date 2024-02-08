package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import dao.MemberDAO;
import dto.MemberDTO;
import fnc.ImagePath;

public class MainMenu extends JFrame implements ActionListener,ImagePath {
	static MemberDTO user;
	public static MainMenu instance = null;
	public SettingMain settingmain = SettingMain.getInstance();
	private JButton quizButton;
	private JButton ivButton;
	private JButton outButton;
	private JButton quitButton;
	private JRadioButton rb1 = new JRadioButton("객관식");
	private JRadioButton rb2 = new JRadioButton("주관식");
	private ImageIcon quiz = new ImageIcon(getClass().getResource(getPath("개념 퀴즈")));
	private ImageIcon iv = new ImageIcon(getClass().getResource(getPath("면접연습")));
	private ImageIcon quit = new ImageIcon(getClass().getResource(getPath("종료")));
	private ImageIcon set = new ImageIcon(getClass().getResource(getPath("자료관리")));
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("메인메뉴")));
	Interview interview = new Interview();
	
	private MainMenu(MemberDTO dto) {	}
	
	public static MainMenu getInstance() {
		if(instance == null) {
			instance = new MainMenu(user);
		}
		return instance;
	}
	
	private JPanel profilePanel() {
		JPanel panel = new JPanel(null);


		// 버튼 패널 추가
		JPanel buttonPanel = menuPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setBounds(0, 0, 720, 445);
		panel.add(buttonPanel);

		// 이미지 라벨로 설정
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setBounds(0, -17, 720, 445);
		panel.add(backgroundLabel);
		return panel;

	}

	private JPanel menuPanel() {
		JPanel panel = new JPanel(null);
		quizButton = new JButton(quiz);
		ivButton = new JButton(iv);
		quitButton = new JButton(quit);
		outButton = new JButton(set);
		// 버튼 패널(로그인+회원가입)
		quizButton.setBounds(395, 165, 150, 28);
		quizButton.setBorderPainted(false);
		ivButton.setBounds(397, 215, 150, 28);
		ivButton.setBorderPainted(false);
		outButton.setBounds(395, 265, 150, 28);
		outButton.setBorderPainted(false);
		quitButton.setBounds(420, 315, 100, 28);
		quitButton.setBorderPainted(false);
		panel.add(ivButton);
		panel.add(quizButton);
		panel.add(quitButton);
		panel.add(outButton);
		
		
		return panel;
	}

	void mainFrame(MemberDTO userData) {
		MainMenu.user = userData;
		this.setTitle("메인 화면");
		this.getContentPane().add(profilePanel());
		quizButton.addActionListener(this);
		ivButton.addActionListener(this);
		outButton.addActionListener(this);
		quitButton.addActionListener(this);
		this.setBounds(250, 250, 720, 445);
		setVisible(true);
	}
	
	private JPanel quizOption() {
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(rb1);
        panel.add(rb2);
        return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//개념 퀴즈 버튼을 눌렀을 때
		if (e.getSource() == quizButton) {
			//퀴즈 유형 선택 팝업 메시지
			int result = JOptionPane.showConfirmDialog(
	                null,
	               quizOption(),
	                "퀴즈 유형 선택",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.PLAIN_MESSAGE
	        );
			 if (result == JOptionPane.OK_OPTION) {
				 //선택한 라디오 버튼에 따라 객관식, 주관식 객체 생성
		            if (rb1.isSelected()) {
		            	new QuizChoice(0);
		            } else if (rb2.isSelected()) {
		            	new Quiz(0);
		            }
		        } else {
		        }
		} else if (e.getSource() == ivButton) {
			interview.in(user);
		} else if (e.getSource() == outButton) {
			settingmain.mainFrame();
		} else if (e.getSource() == quitButton) {
			System.exit(0);
		}
	}

}
