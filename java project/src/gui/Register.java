package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.MemberDAO;
import dto.MemberDTO;
import fnc.ImagePath;

public class Register extends JFrame implements ActionListener, ImagePath {

	public static Register instance = null;
	public MemberDAO memberdao = MemberDAO.getInstance();
	private ImageIcon reg = new ImageIcon(getClass().getResource(getPath("등록")));
	private ImageIcon can = new ImageIcon(getClass().getResource(getPath("취소")));
	private ImageIcon ch = new ImageIcon(getClass().getResource(getPath("중복확인")));
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("로그인 배경")));
	private JTextField idField = new JTextField(20);
	private JPasswordField pwField = new JPasswordField(20);
	private JButton complete = new JButton(reg);
	private JButton cancel = new JButton(can);
	private JButton check = new JButton(ch);

	private Register() {}

	public static Register getInstance() {
		if (instance == null) {
			instance = new Register();
		}
		return instance;
	}

	// 최종 패널(이미지+로그인)
	JPanel createPanel() {
		JLayeredPane layeredPane = new JLayeredPane();

		// 레이어 패널을 합칠 패널
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());


		// 로그인 패널 추가
		JPanel loginPanel = createLoginFormPanel();
		loginPanel.setOpaque(false); // 패널을 투명하게 설정
		loginPanel.setBounds(0, 0, 717, 445);
		layeredPane.add(loginPanel, JLayeredPane.PALETTE_LAYER); // 로그인 패널 위 패널로 설정
		// 이미지 라벨로 설정
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setBounds(0, 0, backgroundLabel.getPreferredSize().width,
				backgroundLabel.getPreferredSize().height); // 라벨 위치, 크기 설정
		layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // 라벨 맨 밑 레이어로 설정

		// 한 패널에 레이어 패널 합치고 반환
		mainPanel.add(layeredPane);
		return mainPanel;

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
		complete.setBounds(250, 310, 75, 40);
		complete.setBorderPainted(false);
		cancel.setBounds(375, 312, 80, 30);
		cancel.setBorderPainted(false);
		check.setBounds(470, 170, 100, 30);
		panel.add(check);
		panel.add(complete);
		panel.add(cancel);
		panel.setBounds(360, 405, 75, 50);

		return panel;
	}

	void mainFrame() {
		this.setTitle("회원가입");
		this.getContentPane().add(createPanel());
		this.setBounds(250,250,720,445);
		complete.addActionListener(this);
		cancel.addActionListener(this);
		check.addActionListener(this);
		this.setVisible(true);
	}

	// 유저가 회원가입창에 입력한값 객체에 저장해 반환하는 메서드
	private MemberDTO inputJoin() {
		MemberDTO dto = new MemberDTO();
		String id = idField.getText();
		char[] pw = pwField.getPassword();
		String pwStr = new String(pw);

		// 아이디나 비밀번호를 입력하지 않으면 null 객체 반환
		if (id.equals("") || pw.length == 0) {
			return null;
		} else {
			//아이디, 비번 입력하면 dto 객체에 저장 후 반환
			dto.setId(id);
			dto.setPw(pwStr);
			return dto;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//등록 버튼을 눌렀을 때
		if (e.getSource() == complete) {
			MemberDTO dto = inputJoin(); // 유저 입력값이 담긴 객체
			// 유저가 아이디, 비번 둘다 입력했다면
			if (dto != null) {
				// 데이터베이스에 해당 데이터 저장
				memberdao.insert(dto);
				// 리스트에 디비 데이터 객체 저장
				JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.", "안내", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "아이디 혹은 비밀번호를 입력해 주세요.", "안내", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (e.getSource() == cancel) {
			setVisible(false);
		}
		//중복 확인 버튼을 눌렀을 때
		if(e.getSource()==check) {
			//유저가 입력한 아이디 저장
			String id = idField.getText();
			//디비 회원 테이블 조회
			memberdao.selectAll();
			boolean idFlag = false;
			//디비 회원 테이블의 아이디와 유저가 입력한 아이디 비교 
			for (MemberDTO memList : memberdao.getMemList()) {
				//일치하는 아이디가 있으면 true
				if (memList.getId().equals(id)) {
					idFlag = true;
					break;
				}
			}
			if(idFlag) {
				JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.", "아이디 중복",
						JOptionPane.INFORMATION_MESSAGE);
				
			}else {
				JOptionPane.showMessageDialog(this, "존재하지 않는 아이디입니다.", "아이디 사용 가능",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
