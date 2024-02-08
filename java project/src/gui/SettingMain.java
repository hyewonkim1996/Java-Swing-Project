package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.MemberDAO;
import dto.MemberDTO;
import fnc.ImagePath;
import fnc.SettingFrame;

public class SettingMain extends JFrame implements SettingFrame, ImagePath, ActionListener{
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("설정화면")));
	private ImageIcon in = new ImageIcon(getClass().getResource(getPath("자료등록")));
	private JButton input = new JButton(in);
	public static SettingMain instance = null;
	public Setting setting = Setting.getInstance();
	
	private SettingMain(){}
	
	public static SettingMain getInstance() {
		if(instance == null) {
			instance = new SettingMain();
		}
		return instance;
	}
	public JPanel mainPanel() {
		JPanel panel = new JPanel(null);
		JLabel label = new JLabel(bg);
		label.setBounds(0, -20, 380, 425);
		input.setBounds(55, 100, 260, 75);
		input.setBorderPainted(false);
		panel.add(label);
		panel.add(input);
		return panel;
	}
	
	void mainFrame() {
		this.setTitle("설정");
		this.getContentPane().add(mainPanel());
		this.setBounds(395, 260, 398, 435);
		input.addActionListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == input) {
			setting.mainFrame();
		}
	}
	
}
