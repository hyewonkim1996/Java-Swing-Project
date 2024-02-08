package fnc;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyDefaultTableCellRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// 특정 컬럼 번호를 지정하여 그 컬럼의 폰트 색을 변경
        if (column == 1 || column == 2) {
            setForeground(Color.GRAY); // 컬럼 0, 1의 폰트 색을 회색으로 변경
        } else {
            // 다른 컬럼은 기본 폰트 색으로 설정
            setForeground(table.getForeground());
        }

        // 다른 컬럼은 일반적인 렌더링 수행
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // 특정 컬럼 번호를 지정하여 오른쪽 정렬 적용
        if (column == 0 || column == 1) {
            setHorizontalAlignment(SwingConstants.RIGHT);
        } else {
            setHorizontalAlignment(SwingConstants.LEFT); // 기본은 왼쪽 정렬
        }
        
        return comp;
	}
};
