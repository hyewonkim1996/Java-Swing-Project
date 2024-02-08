package main;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.Login;
import gui.MainMenu;

public class Main {

	public static void main(String[] args) {
		Login login = Login.getInstance();
		login.mainFrame();
	}

}
