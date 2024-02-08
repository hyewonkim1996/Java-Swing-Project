package fnc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import dao.InterviewDAO;
import dao.UserInterviewDAO;
import dto.MemberDTO;
import gui.InterviewAnswer;
import gui.InterviewAnswerModify;

public class ButtonCellRenderer extends AbstractCellEditor implements ImagePath,TableCellRenderer, TableCellEditor {
	private MemberDTO dto;
	private final JPanel panel;
	private JButton button;
	private int firstColumnValue;
	private JTable table;
	private ImageIcon x = new ImageIcon(getClass().getResource(getPath("답변등록")));
	private ImageIcon v = new ImageIcon(getClass().getResource(getPath("답변수정")));
	public UserInterviewDAO userinterviewdao = UserInterviewDAO.getInstance();
	private HashMap<String, ArrayList<Integer>> userAnswerList;

	public ButtonCellRenderer(MemberDTO dto, JTable table) {
		this.dto = dto;
		this.table = table;
		panel = new JPanel();
		button = new JButton(x);
		button.setMargin(new Insets(-20, 5, 5, 5));
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 handleButtonClick();
				fireEditingStopped();
			}
		});
		panel.setOpaque(false);
		panel.add(button);
	}
	
	// 현재 접속한 회원의 답변 여부에 따라 버튼 상태 업데이트
    private void updateButtonState(int row) {
    	//데이터베이스 회원별 면접 테이블에서 현 회원의 아이디가 있는 튜플 맵에 저장
    	userAnswerList = userinterviewdao.answerCheck(dto);
    	//현 회원의 아이디가 있다면
		if (userAnswerList != null && userAnswerList.containsKey(dto.getId())) {
            //맵의 키(아이디)를 이용해 value(회원이 답변한 질문 번호) 리스트에 저장
			ArrayList<Integer> a = userAnswerList.get(dto.getId());
            //맵의 value와 JTable의 질문 번호 비교
			if (a.contains(row + 1)) {//해당 질문에 등록한 답변이 있다면
				//버튼 이미지 '수정'으로 설정
                button.setIcon(v);
                button.setMargin(new Insets(-30, 5, 5, 5));
            } else {//해당 질문에 등록한 답변이 없다면
            	//버튼 이미지 '등록'으로 설정
            	button.setIcon(x);
            	button.setMargin(new Insets(-20, 5, 5, 5));
            }
        }
    }
    

    // 클릭한 버튼에 따라 답변 등록 혹은 수정 화면 띄우는 메서드
    private void handleButtonClick() {
    	//현재 버튼 이미지 저장
    	Icon currentIcon = button.getIcon();
    	//현재 버튼이 '수정'이라면
        if (currentIcon == v) {
        	//답변 수정(튜플 수정) 객체 생성
            new InterviewAnswerModify(dto, firstColumnValue);
        } else {//현재 버튼이 '등록'이라면
        	//답변 등록(튜플 삽입) 객체 생성
            new InterviewAnswer(dto, firstColumnValue);
        }
    }
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		firstColumnValue = (int) table.getValueAt(row, 0);
		 updateButtonState(row);
		return panel;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		firstColumnValue = (int) table.getValueAt(row, 0);
		 updateButtonState(row);
		return panel;
	}

}
