package fnc;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface SettingFrame extends ImagePath{
	default JPanel mainPanel() {
		JPanel panel = new JPanel(null);
		ImageIcon bg = new ImageIcon(getClass().getResource(getPath("설정화면")));
		JLabel label = new JLabel(bg);
		String[] options = {"개념 퀴즈", "면접 연습"};
		JComboBox<String> comboBox = new JComboBox<>(options);
		comboBox.setBounds(60, 90, 90, 20);
		label.setBounds(0, -20, 380, 425);
		panel.add(label);
		panel.add(comboBox);
		return panel;
	}
}
