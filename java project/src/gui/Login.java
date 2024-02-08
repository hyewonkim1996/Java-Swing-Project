package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import dao.MemberDAO;
import dto.MemberDTO;
import fnc.ImagePath;

public class Login extends JFrame implements ActionListener, ImagePath {
	public Register register = Register.getInstance();
	public MemberDAO memberdao = MemberDAO.getInstance();
	public MainMenu main = MainMenu.getInstance();
	public static Login instance = null;
	private ImageIcon log = new ImageIcon(getClass().getResource(getPath("로그인")));
	private ImageIcon join = new ImageIcon(getClass().getResource(getPath("회원가입")));
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("로그인 배경")));
	private JTextField idField = new JTextField(20);
	private JPasswordField pwField = new JPasswordField(20);
	private JButton loginButton = new JButton(log);
	private JButton joinButton = new JButton(join);

	private Login() {
	}
	
	public static Login getInstance() {
		if(instance == null) {
			instance = new Login();
		}
		return instance;
	}
	
	// 최종 패널(이미지+로그인)
	private JPanel createPanel() {
		JPanel panel = new JPanel(null);


		// 로그인 패널 추가
		JPanel loginPanel = createLoginFormPanel();
		loginPanel.setOpaque(false);
		loginPanel.setBounds(0, 0, 717, 445);
		panel.add(loginPanel);
		// 이미지 라벨로 설정
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setBounds(0, -20, 720, 445);
		panel.add(backgroundLabel);
		return panel;

	}

	private JPanel createLoginFormPanel() {
		// 각 패널을 합칠 완성본 패널
		JPanel panel = new JPanel(null);

		// 아이디 패널(아이디 라벨+텍스트필드)
		idField.setBounds(300, 170, 150, 30);
		panel.add(idField);

		// 비밀번호 패널(비밀번호 라벨+패스워드필드)
		pwField.setBounds(300, 225, 150, 30);
		panel.add(pwField);

		// 버튼 패널(로그인+회원가입)
		loginButton.setBounds(230, 310, 105, 40);
		loginButton.setBorderPainted(false);
		joinButton.setBounds(355, 312, 140, 37);
		joinButton.setBorderPainted(false);
		panel.add(loginButton);
		panel.add(joinButton);
		panel.setBounds(360, 405, 100, 50);

		return panel;
	}

	public void mainFrame() {
		this.setTitle("로그인");
		this.getContentPane().add(createPanel());
		this.setBounds(250, 250, 720, 445);
		this.repaint();
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginButton.addActionListener(this);
		joinButton.addActionListener(this);
	}

	// 유저가 로그인창에 입력한값 객체에 저장해 반환하는 메서드
	private MemberDTO inputLogin() {
		MemberDTO dto = new MemberDTO();
		String id = idField.getText();
		char[] pw = pwField.getPassword();
		String pwStr = new String(pw);
		// 유저가 아이디, 비번 공백 입력하면 null 반환
		if (id.equals("") || pw.length == 0) {
			return null;
		} else {// 아이디, 비번 둘다 입력하면 객체에 저장 후 반환
			dto.setId(id);
			dto.setPw(pwStr);
			return dto;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//로그인 버튼을 눌렀을 때
		if (e.getSource() == loginButton) {
			MemberDTO dto = inputLogin(); // 유저 입력값이 담긴 객체
			boolean loginFlag = false;
			// 유저가 아이디, 비번 둘다 입력했다면
			if (dto != null) {
				// 디비에 저장된 회원 테이블 조회
				memberdao.selectAll();
				// 유저 입력값을 회원 테이블 튜플과 비교
				for (MemberDTO memList : memberdao.getMemList()) {
					//유저 입력값과 회원 테이블의 아이디, 비번이 같으면
					if (memList.getId().equals(dto.getId()) && memList.getPw().equals(dto.getPw())) {
						loginFlag = true;
						MemberDTO userData = memList;
						//로그인한 회원 정보를 가지고 메인 메뉴 객체 생성
						main.mainFrame(userData);
						//로그인 화면 끔
						setVisible(false);
						break;
					}
				}
				// 유저가 아이디, 비번을 틀렸으면
				if (loginFlag == false) {
					JOptionPane.showMessageDialog(Login.this, "아이디 혹은 비밀번호가 틀렸습니다.", "안내",
							JOptionPane.INFORMATION_MESSAGE);
				}
				// 유저가 아이디, 비번을 공백으로 입력했다면
			} else {
				JOptionPane.showMessageDialog(Login.this, "아이디 혹은 비밀번호를 입력하세요.", "안내", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (e.getSource() == joinButton) {
			register.mainFrame();
		}
	}
}
