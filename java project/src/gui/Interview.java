package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.InterviewDAO;
import dto.MemberDTO;
import fnc.ButtonCellRenderer;
import fnc.ImagePath;
import fnc.MyDefaultTableCellRenderer;

public class Interview extends JFrame implements ActionListener,ImagePath{
	public InterviewDAO interviewdao = InterviewDAO.getInstance();
	private JTable table;
	private JLayeredPane layeredPane;
	private JButton button = new JButton("←");
	private ImageIcon bg = new ImageIcon(getClass().getResource(getPath("면접화면")));
	private DefaultTableModel tableModel;
	private ArrayList<ArrayList<Object>> data = interviewdao.selectAll();
	private Vector<String> columnNames = new Vector<>();

	Interview() {
		
	}
	
	void in(MemberDTO user) {
		button.addActionListener(this);
		this.setTitle("면접 연습");
		this.setBounds(250, 250, 721, 427);
		mainFrame();
	}
	
	//JTable 내용물 설정하는 메서드
	private DefaultTableModel setTable() {
		//컬럼 이름 설정
		columnNames.add("질문 번호");
		columnNames.add("상위 카테고리");
		columnNames.add("하위 카테고리");
		columnNames.add("질문");
		//데이터베이스 튜플 저장
		Vector<Vector<Object>> dataVector = new Vector<>();
		for (ArrayList<Object> row : data) {
			dataVector.add(new Vector<>(row));
		}
		DefaultTableModel model = new DefaultTableModel(dataVector, columnNames);
		//JTable에 버튼 추가하기 위해 컬럼 추가
		model.addColumn("");
		return model;
	}
	
	//JTable 형태 설정하는 메서드
	private JTable setTableDesign() {
		table = new JTable(setTable());
		DefaultTableCellRenderer celRight = new DefaultTableCellRenderer();
		celRight.setHorizontalAlignment(JLabel.RIGHT);
		int rowHeight = 30;
		table.setRowHeight(rowHeight);
		table.setShowGrid(true);
		table.setShowVerticalLines(false);
		table.getColumn("상위 카테고리").setCellRenderer(celRight);
		table.getColumn("상위 카테고리").setPreferredWidth(10);
		table.getColumn("질문 번호").setPreferredWidth(5);
		table.getColumn("하위 카테고리").setPreferredWidth(10);
		table.getColumn("질문").setPreferredWidth(400);
		table.getColumn("").setPreferredWidth(40);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15)); // 원하는 글자 크기로 설정
		MyDefaultTableCellRenderer renderer = new MyDefaultTableCellRenderer();
		table.getColumnModel().getColumn(0).setCellRenderer(renderer); 
		table.getColumnModel().getColumn(1).setCellRenderer(renderer); // 1번 컬럼의 폰트를 회색으로 변경
		table.getColumnModel().getColumn(2).setCellRenderer(renderer); // 2번 컬럼의 폰트를 회색으로 변경
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		//테이블에 추가된 버튼 형태 설정하는 메서드
		table.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer(MainMenu.user, table));
		table.getColumnModel().getColumn(4).setCellEditor(new ButtonCellRenderer(MainMenu.user, table));
		return table;
	}
	
	private JPanel backGround() {
		JPanel panel = new JPanel(null);
		JLabel backgroundLabel = new JLabel(bg);

		// 이미지 라벨로 설정
		backgroundLabel.setBounds(0,-20, 735, 444);
		backgroundLabel.setOpaque(false);
		panel.setBounds(-7, 0, 735, 444);
		panel.add(backgroundLabel);
		return panel;
	}
	
	private JPanel qPanel() {
		JPanel panel = new JPanel(null);
		
		table = setTableDesign();
		table.setBackground(new Color(0, 0, 0, 0));
		table.setBounds(0, 0, 675, 300);
		panel.setBackground(new Color(250,205,204));
		 panel.add(table);
		 panel.setBounds(20,75,675, 300);
		return panel;
	}

	private void mainFrame() {
		 layeredPane = new JLayeredPane();

		    // Set the bounds of the layered pane
		    layeredPane.setBounds(0, 0, 735, 444);

		    // Add panels to the layered pane with appropriate layers
		    layeredPane.add(qPanel(), JLayeredPane.PALETTE_LAYER);
		    layeredPane.add(backGround(), JLayeredPane.DEFAULT_LAYER);

		    // Add the layered pane to the content pane
		    this.getContentPane().add(layeredPane);
		this.setBounds(250, 250, 735, 444);
		repaint();
		setVisible(true);
	}
	
	//업데이트된 버튼 테이블에 반영하는 메서드
	private void updateTableModel() {
		 // 각 행에 대한 ButtonCellRenderer 생성
        ButtonCellRenderer buttonRenderer = new ButtonCellRenderer(MainMenu.user, table);
        // 버튼 업데이트를 위한 ButtonCellEditor 생성
        ButtonCellRenderer buttonEditor = new ButtonCellRenderer(MainMenu.user, table);
        // 기존의 행 삭제
        tableModel.setRowCount(0);
        // 데이터베이스에서 다시 데이터 가져와 반영하기
        for (ArrayList<Object> row : data) {
            tableModel.addRow(new Vector<>(row));
        }
        // ButtonCellRenderer를 버튼 열에만 적용
        table.getColumnModel().getColumn(4).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(4).setCellEditor(buttonEditor);
		tableModel.fireTableDataChanged();
	}
	
	 private void refreshTable() {
	        tableModel.fireTableDataChanged();
	        table.setModel(tableModel);
	    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			// 테이블 모델을 새로 생성하여 테이블 갱신
            tableModel = new DefaultTableModel();
            updateTableModel();
            refreshTable();
		}
	}
}
