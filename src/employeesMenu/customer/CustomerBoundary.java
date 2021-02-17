/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * 顧客確認バウンダリー
 * @author 19jz0115
 */
public class CustomerBoundary extends javax.swing.JFrame {
    private CustomerControl control;
    private Customer customer;
    
    private DefaultTableModel customerTableModel;
    /**
     * Creates new form OrderBoundary
     */
    public CustomerBoundary() {
        initComponents();
        initTextBox();
        setTitle("顧客確認画面");
        initTableModel();
        jCheckBox1.setEnabled(false);
    }
    
    public void initTextBox(){
        jTextFieldName.setEditable(false);
        jTextFieldAddress.setEditable(false);
        jTextFieldDelivaryNote.setEditable(false);
        jTextFieldPhoneNumber.setEditable(false);
    }

    public void initTableModel() {
        String[] customerTableTitle = {"顧客番号","顧客名","電話番号","住所", "配達備考"};
        
        customerTableModel = new DefaultTableModel(customerTableTitle, 0);
        
        jTable1.setModel(customerTableModel);
        jTable1.setDefaultEditor(Object.class, null);
        
        jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    Point point = mouseEvent.getPoint();
                    int row = jTable1.rowAtPoint(point);
                        if (mouseEvent.getClickCount() == 2 && jTable1.getSelectedRow() != -1) {
                            customerTableSelectedItem(row);
                        }
                    }
            });
    }
    
    /**
     * コントロールを設定
     * @param control 顧客コントロールクラス
     */
    public void setControl(CustomerControl control){
        this.control = control;
        control.showAllCustomerTable();
    }

    /**
     * 顧客情報を設定
     * @param customer 顧客情報
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * 顧客情報を取得
     * @return 顧客情報
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * テキストフィールドに顧客情報を表示
     * @param customer 顧客情報
     */
    public void showMemberTextField(Customer customer) {
        setCustomer(customer);
        jTextFieldPhoneNumber2.setText(getCustomer().getPhoneNumber());
        jTextFieldName.setText(getCustomer().getName());
        jTextFieldPhoneNumber.setText(getCustomer().getPhoneNumber());
        jTextFieldAddress.setText(getCustomer().getAddress());
        jTextFieldDelivaryNote.setText(getCustomer().getDeliveryNote());
        jCheckBox1.setEnabled(true);
    }
    
    /**
     * 顧客表に顧客情報を表示
     * @param customerList 顧客情報
     */
    public void showCustomerTable(List<Customer> customerList) {
        
        String[] culumn = new String[5];
        
        customerTableModel.setRowCount(0);
        
        for (Customer customer : customerList) {
            culumn[0] = Integer.toString(customer.getCustomerNumber());
            culumn[1] = customer.getName();
            culumn[2] = customer.getPhoneNumber();
            culumn[3] = customer.getAddress();
            culumn[4] = customer.getDeliveryNote();
            
            customerTableModel.addRow(culumn);
        }
    }
    
    public void clearMemberTextField() {
        jTextFieldPhoneNumber.setText("");
        jTextFieldName.setText("");
        jTextFieldPhoneNumber2.setText("");
        jTextFieldAddress.setText("");
        jTextFieldDelivaryNote.setText("");
        control.showAllCustomerTable();
    }
    
    /**
     * 顧客情報表ダブルクリック時の処理
     * @param row 行番号
     */
    public void customerTableSelectedItem(int row) {
        control.searchCustomer((String)jTable1.getValueAt(row, 2));
    }
    
    /**
     * エラーダイアログ表示
     * @param message エラーメッセージ
     * @param title   タイトル
     */
    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * 顧客情報が見つからなかったときのエラーダイアログ表示
     * @param param パラメータ
     */
    public void showNotFoundErrorMessage(String param) {
        showErrorMessage("[" + param + "]が見つかりませんでした", "入力エラー");
    }
    
    /**
     * 顧客情報が無効のときのエラーダイアログ表示
     */
    public void showInvalidCustomerErrorMessage() {
        showErrorMessage("顧客情報が無効です", "エラー");
    }
    
    /**
     * 顧客情報を設定していなかったときのメッセージ
     */
    public void showCustomerNothingErrorMessage() {
        showErrorMessage("顧客情報を設定してください", "エラー");
    }
    
    /**
     * データベースエラー発生時のエラーダイアログ表示
     */
    public void showDBErrorMessage() {
        showErrorMessage("データベースエラーが発生しました", "エラー");
    }
    
    /**
     * 確認ダイアログ表示
     * @param message メッセージ
     * @param title   タイトル
     */
    public void showConfirmMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonCustomerSerarch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldPhoneNumber = new javax.swing.JTextField();
        jButtonCustomerUpdate = new javax.swing.JButton();
        jTextFieldPhoneNumber2 = new javax.swing.JTextField();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldDelivaryNote = new javax.swing.JTextField();
        jButtonClose = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jButtonDeleteCustomer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel1.setText("顧客確認");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jButtonCustomerSerarch.setText("顧客を検索する");
        jButtonCustomerSerarch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCustomerSerarchActionPerformed(evt);
            }
        });

        jLabel2.setText("顧客名");

        jLabel3.setText("電話番号");

        jLabel4.setText("住所");

        jLabel5.setText("配達備考");

        jTextFieldName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldNameFocusGained(evt);
            }
        });

        jTextFieldPhoneNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldPhoneNumberFocusGained(evt);
            }
        });

        jButtonCustomerUpdate.setText("顧客情報を更新する");
        jButtonCustomerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCustomerUpdateActionPerformed(evt);
            }
        });

        jTextFieldPhoneNumber2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldPhoneNumber2FocusGained(evt);
            }
        });

        jTextFieldAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldAddressFocusGained(evt);
            }
        });

        jTextFieldDelivaryNote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldDelivaryNoteFocusGained(evt);
            }
        });

        jButtonClose.setText("閉じる");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jCheckBox1.setText("編集");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel6.setText("電話番号");

        jButtonDeleteCustomer.setText("顧客情報を削除する");
        jButtonDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jCheckBox1)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTextFieldPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButtonCustomerSerarch)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonCustomerUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonDeleteCustomer))
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldDelivaryNote)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldAddress)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(221, 221, 221))
                                .addComponent(jTextFieldName)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonClose))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextFieldAddress, jTextFieldDelivaryNote, jTextFieldPhoneNumber});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCustomerSerarch)
                            .addComponent(jTextFieldPhoneNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDelivaryNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonCustomerUpdate)
                            .addComponent(jButtonDeleteCustomer)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButtonClose)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel5});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPhoneNumber2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPhoneNumber2FocusGained
        jTextFieldPhoneNumber.selectAll();
    }//GEN-LAST:event_jTextFieldPhoneNumber2FocusGained

    private void jTextFieldNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldNameFocusGained
        jTextFieldName.selectAll();
    }//GEN-LAST:event_jTextFieldNameFocusGained

    private void jTextFieldPhoneNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldPhoneNumberFocusGained
        jTextFieldPhoneNumber2.selectAll();
    }//GEN-LAST:event_jTextFieldPhoneNumberFocusGained

    private void jTextFieldAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldAddressFocusGained
        jTextFieldAddress.selectAll();
    }//GEN-LAST:event_jTextFieldAddressFocusGained

    private void jTextFieldDelivaryNoteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldDelivaryNoteFocusGained
        jTextFieldDelivaryNote.selectAll();
    }//GEN-LAST:event_jTextFieldDelivaryNoteFocusGained

    private void jButtonCustomerSerarchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCustomerSerarchActionPerformed
        control.searchCustomer(jTextFieldPhoneNumber2.getText());
    }//GEN-LAST:event_jButtonCustomerSerarchActionPerformed

    private void jButtonCustomerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCustomerUpdateActionPerformed
        if (customer != null) {
            control.updateCustomer(customer);
        }
        else {
            //エラーダイアログ表示
            JOptionPane.showMessageDialog(this, "顧客情報を設定してください", "エラー", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButtonCustomerUpdateActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        control.exit();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(jCheckBox1.isSelected()){
            jTextFieldName.setEditable(true);
            jTextFieldAddress.setEditable(true);
            jTextFieldDelivaryNote.setEditable(true);
            jTextFieldPhoneNumber.setEditable(true);
        }else{
            jTextFieldName.setEditable(false);
            jTextFieldAddress.setEditable(false);
            jTextFieldDelivaryNote.setEditable(false);
            jTextFieldPhoneNumber.setEditable(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        control.exit();
    }//GEN-LAST:event_formWindowClosing

    private void jButtonDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteCustomerActionPerformed
        if (JOptionPane.showConfirmDialog(this,"顧客情報を削除しますか？", "確認", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            control.deleteCustomer(getCustomer().getPhoneNumber());
            control.showAllCustomerTable();
        }
        
    }//GEN-LAST:event_jButtonDeleteCustomerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerBoundary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCustomerSerarch;
    private javax.swing.JButton jButtonCustomerUpdate;
    private javax.swing.JButton jButtonDeleteCustomer;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldDelivaryNote;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhoneNumber;
    private javax.swing.JTextField jTextFieldPhoneNumber2;
    // End of variables declaration//GEN-END:variables
}
