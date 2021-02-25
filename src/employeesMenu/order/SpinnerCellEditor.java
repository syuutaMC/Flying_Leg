/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * スピナーセルエディタークラス
 * @author 19jz0137
 */
public class SpinnerCellEditor extends DefaultCellEditor {
    private JSpinner spinner = new JSpinner();

    public SpinnerCellEditor() {
        super(new JTextField());
        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinner.setBorder(null);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue( value );
        return spinner;
    }
    
    @Override
    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            return ((MouseEvent) evt).getClickCount() >= 2;
        }
        return true;
    }
    
    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }
}
