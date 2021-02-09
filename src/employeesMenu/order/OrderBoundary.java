/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import employeesMenu.customer.Customer;
import managerMenu.item.Item;
import managerMenu.item.Category;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 注文画面
 * @author 19jz0115
 */
public class OrderBoundary extends javax.swing.JFrame {
    private static final String CARD_SHIPPING_ADRRESS = "card2";
    private static final String CARD_ORDER_ITEM = "card3";
    private static final String CARD_FINAL_CHECK = "card4";
    public static final int    ORDER_TOTAL_PRICE_UNDER_LIMIT = 1500;
    private final CardLayout cardLayout;
    private OrderControl control;
    private Customer customer;
    private DefaultTableModel orderTableModel;
    private DefaultTableModel orderListTableModel;
    private List<JTable> jtableMenuList;
    private List<DefaultTableModel> menuTableModelList;
    
    private final double TAX = 0.1;
    
    /**
     * Creates new form OrderBoundary
     */
    public OrderBoundary() {
        initComponents();
        initAddAddressPanel();
        initTopPanel();
        initTableModel();
        cardLayout = (CardLayout)jPanelCardBase.getLayout();
    }
    
    /**
     * コントロールの設定
     * @param control 従業員メニューコントロール
     */
    public void setControl(OrderControl control) {
        this.control = control;
        initTabedPane();
    }
    
    /** 初期化 ****************************************************************/
    
    /**
     * 住所入力画面の初期化
     */
    private void initAddAddressPanel(){
        jTextFieldName.setEditable(false);
        jTextFieldAddress.setEditable(false);
        jTextFieldPhoneNumber.setEditable(false);
        jTextFieldDelivaryNote.setEditable(false);
        jTextFieldSearchPhoneNumber.setText("");
        jTextFieldPhoneNumber.setText("");
        jTextFieldAddress.setText("");
        jTextFieldName.setText("");
        jTextFieldDelivaryNote.setText("");
        jCheckBox1.setEnabled(false);
    }
    
    /**
     * 注文商品選択画面の初期化
     */
    private void initOrderItemPanel(){
        jTextFieldItemId.setText("");
        jTableOrder.setRowHeight(0);
        jTableMenu.setRowHeight(0);   
        showOrderTotalPrice(0);
    }
    
    /**
     * TableModelの初期化
     */
    private void initTableModel(){
        String[] orderTableTitle = {"商品番号", "商品名", "数量", "金額(税抜)"};
        
        orderListTableModel = new DefaultTableModel(orderTableTitle, 0);
        orderTableModel = new DefaultTableModel(orderTableTitle, 0);
        
        jTableOrderList.setModel(orderListTableModel);
        jTableOrder.setModel(orderTableModel);
        jTableOrder.setDefaultEditor(Object.class, null);   //エディタにnullを指定し編集不可に
        
    }
    
    /**
     * ダブドペイン初期化
     */
    private void initTabedPane() {
        jTabbedPaneMenu.removeAll();
        
        String[] menuTableTitle = {"商品番号","商品名", "金額"};
        
        List<Category> categoryList = control.getCategory();
        
        ArrayList<JTable> tableList = new ArrayList<>();
        ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
        List<DefaultTableModel> menuTableModelList = new ArrayList<>();
        
        for(int i = 0; i < categoryList.size(); i++){
            DefaultTableModel menuTableModel = new DefaultTableModel(menuTableTitle, 0);
            
            JTable jTable = new JTable(menuTableModel);
            
            jTable.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent mouseEvent) {
                    Point point = mouseEvent.getPoint();
                    int row = jTable.rowAtPoint(point);
                        if (mouseEvent.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
                            menuTableSelectedItem(row);
                        }
                    }
            });
            menuTableModelList.add(menuTableModel);
            tableList.add(jTable);
            scrollPaneList.add(new JScrollPane());
            
            scrollPaneList.get(i).setViewportView(tableList.get(i));
            jTabbedPaneMenu.addTab(categoryList.get(i).getCategoryName(), scrollPaneList.get(i));
        }
        
        initMenuTableModelSetEditableFalse(tableList);
        
        this.menuTableModelList = menuTableModelList;
        this.jtableMenuList = tableList;
        control.showMenu(categoryList);
    }
    
    /**
     * ボタンの初期化
     */
    private void initTopPanel() {
        jButtonSelectedItem.setEnabled(false);
        jButtonFinalCheck.setEnabled(false);
    }
    
    /**
     * メニュー表列並べ替えを不可に設定
     */
    private void initMenuTableModelSetEditableFalse(List<JTable> jTableList) {
        for (JTable jTable : jTableList) {
            jTable.getTableHeader().setReorderingAllowed(false);    //列の並べ替え不可
            jTable.setDefaultEditor(Object.class, null);    //デフォルトセルエディタにnullオブジェクトを指定し編集不可に 
        }
    }
    
    /**************************************************************************/
    
    /** 画面切り替え ***********************************************************/
    
    /**
     * 画面切り替え処理
     */
    public void showCardAShippingAddress(){
        cardLayout.show(jPanelCardBase, CARD_SHIPPING_ADRRESS);
    }
    
    public void showCardOrderItem(){
        cardLayout.show(jPanelCardBase, CARD_ORDER_ITEM);
    }
    
    public void showCardFinalCheck(){
        cardLayout.show(jPanelCardBase, CARD_FINAL_CHECK);
    }
    
    /**************************************************************************/
    
    /** ダイアログ *************************************************************/
    
    /**
     * エラーダイアログ表示
     * @param message エラーメッセージ
     * @param title   タイトル
     */
    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * 確認ダイアログ表示
     * @param message メッセージ
     * @param title 　タイトル
     */
    public void showConfirmMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 顧客情報が見つからなかったときの処理
     * @param param パラメータ
     */
    public void showCustomerNotFoundErrorMessage(String param) {
        int result = JOptionPane.showConfirmDialog(this,  param + "がありません。\n新規登録しますか？", "確認", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            control.showCustomerAddBoundary(jTextFieldSearchPhoneNumber.getText());
        }
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
    
    public void showItemNoIsNothingErrorMessage(String itemNo){
        showErrorMessage("該当する商品番号がsありません", "エラー");
    }
    
    /**
     * 商品検索で商品が見つからなかったときのエラー
     */
    public void showItemNotFoundErrorMessage() {
        showErrorMessage("商品が見つかりませんでした", "エラー");
    }
    
    /**
     * 合計金額が1500円以下のときのメッセージ
     */
    public void showTotalPriceErrorMessage() {
        showErrorMessage("注文は1500円以上から受け付けています", "エラー");
    }
    
    /**
     * 注文確定確認メッセージ
     */
    public void showOrderFixingSuccessMessage() {
        showConfirmMessage("注文を確定しました", "確認");
    }
    
    /**************************************************************************/
    
    /** 顧客確認画面処理 *******************************************************/
    
    /**
     * 顧客情報テキストフィールド編集可否設定
     * @param b true 編集可 | false 編集不可
     */
    public void setEditableCustomerTextFielde(boolean b) {
        jTextFieldName.setEditable(b);
        jTextFieldAddress.setEditable(b);
        jTextFieldPhoneNumber.setEditable(b);
    }

    /**
     * テキストフィールドに顧客情報を表示
     * @param customer 顧客情報
     */
    public void showCustomerTextField(Customer customer) {
        jLabelCustomerNumber.setText(Integer.toString(customer.getCustomerNumber()));
        jTextFieldName.setText(customer.getName());
        jTextFieldPhoneNumber.setText(customer.getPhoneNumber());
        jTextFieldAddress.setText(customer.getAddress());
        jTextFieldDelivaryNote.setText(customer.getDeliveryNote());
        jCheckBox1.setEnabled(true);
    }
    
    /**
     * 顧客情報が入力されてるか確認
     */
    public void checkAddress(){
        if(jTextFieldName.getText().equals("")){
            
        }
        
        if(jTextFieldPhoneNumber.getText().equals("")){  
            
        }
        
        if(jTextFieldAddress.getText().equals("")){
            
        }
    }
    
    /**************************************************************************/
    
    /** 注文商品選択画面 *******************************************************/
    
    /**
     * メニュー表にメニューを表示
     * @param itemList 商品リスト
     * @param tabIndex タブ番号
     */
    public void showMenuTable(List<Item> itemList, int tabIndex) {
        
        NumberFormat nf = NumberFormat.getNumberInstance();
        
        menuTableModelList.get(tabIndex).setRowCount(0);
        String[] row = new String[3];
        
        for (Item item : itemList) {
            row[0] = item.getItemNumber();
            row[1] = item.getItemName();
            row[2] = nf.format(item.getUnitPrice());
            menuTableModelList.get(tabIndex).addRow(row);
        }
    }
    
    /**
     * 注文商品検索
     * @param itemNumber 商品番号
     * @return row found >= 0 | not found -1
     */
    public int searchOrderItem(String itemNumber) {
        
        for (int i = 0; i < jTableOrder.getRowCount(); i++) {
            if (itemNumber.equals(jTableOrder.getValueAt(i, 0).toString())) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 指定した行の注文商品の個数を一つ足す
     * @param row 行番号
     */
    public void incrementOrderItem(int row) {
        if (row > -1) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            int quantity = Integer.parseInt(jTableOrder.getValueAt(row, 2).toString());
            int price    = Integer.parseInt(jTableOrder.getValueAt(row, 3).toString().replace(",", ""));
            
            price = price / quantity;
            jTableOrder.setValueAt(nf.format(++quantity), row, 2);
            jTableOrder.setValueAt(nf.format(price * quantity) , row, 3);
            
        }
    }
    
    /**
     * 指定した行の注文商品の個数を一つ減らす
     * @param row 
     */
    public void decrementOrderItem(int row) {
        if (row > -1) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            int quantity = Integer.parseInt(jTableOrder.getValueAt(row, 2).toString());
            if (quantity > 1) {
                int price    = Integer.parseInt(jTableOrder.getValueAt(row, 3).toString().replace(",", ""));
                price = price / quantity;
                jTableOrder.setValueAt(nf.format(--quantity), row, 2);
                jTableOrder.setValueAt(nf.format(price * quantity) , row, 3);
            }
        }
    }
    
    /**
     * 注文商品を追加
     * @param item 商品情報
     */
    public void addOrderTable(Item item) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        
        String[] row = new String[4];
        
        item.setQuantity(1);
        
        row[0] = item.getItemNumber();
        row[1] = item.getItemName();
        row[2] = Integer.toString(item.getQuantity());
        row[3] = nf.format(item.getUnitPrice() * item.getQuantity());
        orderTableModel.addRow(row);
    }
    
    /**
     * 注文表から商品を削除
     * @param row 行番号
     */
    public void removeOrderItem(int row) {
        orderTableModel.removeRow(row);
    }
    
    /**
     * 税込み合計金額を計算する
     * @return 合計金額
     */
    public int calcTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < jTableOrder.getRowCount(); i++) {
            totalPrice += Integer.parseInt(jTableOrder.getValueAt(i, 3).toString().replace(",", ""));
        }
        totalPrice += taxCalculation(totalPrice);
        return totalPrice;
    }
    
    /**
     * 商品選択画面に合計金額を表示
     * 注文最低金額未満なら警告表示
     * @param totalPrice 合計金額
     */
    public void showOrderTotalPrice(int totalPrice) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        jLabelTax.setText(nf.format(taxCalculation(totalPrice)) + "円");
        jLabelOrderTotalPrice.setText(nf.format(taxCalculation(totalPrice) + totalPrice) + "円");
        jLabelTotalPriceWarn.setVisible(taxCalculation(totalPrice) + totalPrice < ORDER_TOTAL_PRICE_UNDER_LIMIT);
    }
    
    /**************************************************************************/
    
    /** 注文確認画面 ***********************************************************/
    
    /**
     * 注文確認画面に情報を表示
     */
    public void showFinalCheckPanel() {
        jLabelName.setText(jTextFieldName.getText());
        jLabelPhoneNumber.setText(jTextFieldPhoneNumber.getText());
        jLabelAddress.setText(jTextFieldAddress.getText());
        jLabelDelivaryNote.setText(jTextFieldDelivaryNote.getText());
        jLabelTotalPrice.setText(jLabelOrderTotalPrice.getText());
        showCardFinalCheck();
    } 
    
    /**
     * 注文確認画面を有効化
     * @param b true 有効化（切り替え可） | false 無効化(切り替え不可)
     */
    public void setEnabledFinalCheck(boolean b) {
        jButtonFinalCheck.setEnabled(true);
    }
    
    /**
     * 注文画面終了処理
     */
    public void orderExit() {
        control.exit();
    }
    
    /**
     * 注文確定後の初期化処理　または 中止処理
     */
    public void allReset(){
        initAddAddressPanel();
        initOrderItemPanel();
        initTopPanel();
    }
    
    /**
     * 消費税計算
     * @param price 金額
     * @return 消費税金額
     */
    private int taxCalculation(int price) {
        return (int)(price * TAX);
    }
    
    /**************************************************************************/
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneMenu = new javax.swing.JScrollPane();
        jTableMenu = new javax.swing.JTable();
        jPanelHeadder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelTop = new javax.swing.JPanel();
        jButtonFinalCheck = new javax.swing.JButton();
        jButtonShippingAddress = new javax.swing.JButton();
        jButtonSelectedItem = new javax.swing.JButton();
        jPanelCardBase = new javax.swing.JPanel();
        jPanelAddAddress = new javax.swing.JPanel();
        jTextFieldSearchPhoneNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonCustomerCheck = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jTextFieldAddress = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldPhoneNumber = new javax.swing.JTextField();
        jButtonSelectItem = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldDelivaryNote = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabelCustomerNumber = new javax.swing.JLabel();
        jPanelOrderItem = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableOrder = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldItemId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButtonSearchItemId = new javax.swing.JButton();
        jPanelMenu = new javax.swing.JPanel();
        jTabbedPaneMenu = new javax.swing.JTabbedPane();
        jLabel17 = new javax.swing.JLabel();
        jButtonRemoveOrderItem = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabelOrderTotalPrice = new javax.swing.JLabel();
        jButtonIncrementOrderItem = new javax.swing.JButton();
        jButtonDecrementItem = new javax.swing.JButton();
        jLabelTotalPriceWarn = new javax.swing.JLabel();
        jPanelFinakCheck = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableOrderList = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jLabelPhoneNumber = new javax.swing.JLabel();
        jLabelAddress = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabelTotalPrice = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelDelivaryNote = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelTax = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jTableMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "商品番号", "商品名", "金額"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneMenu.setViewportView(jTableMenu);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("注文画面");
        setMinimumSize(new java.awt.Dimension(983, 624));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelHeadder.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel1.setText("注文受付");

        javax.swing.GroupLayout jPanelHeadderLayout = new javax.swing.GroupLayout(jPanelHeadder);
        jPanelHeadder.setLayout(jPanelHeadderLayout);
        jPanelHeadderLayout.setHorizontalGroup(
            jPanelHeadderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeadderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelHeadderLayout.setVerticalGroup(
            jPanelHeadderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHeadderLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanelTop.setBackground(new java.awt.Color(204, 204, 255));

        jButtonFinalCheck.setText("伝票確認");
        jButtonFinalCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFinalCheckActionPerformed(evt);
            }
        });

        jButtonShippingAddress.setText("配送先登録");
        jButtonShippingAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShippingAddressActionPerformed(evt);
            }
        });

        jButtonSelectedItem.setText("商品選択");
        jButtonSelectedItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectedItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jButtonShippingAddress)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSelectedItem)
                .addGap(287, 287, 287)
                .addComponent(jButtonFinalCheck)
                .addGap(59, 59, 59))
        );

        jPanelTopLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonFinalCheck, jButtonSelectedItem, jButtonShippingAddress});

        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonShippingAddress)
                    .addComponent(jButtonSelectedItem)
                    .addComponent(jButtonFinalCheck))
                .addContainerGap())
        );

        jPanelCardBase.setLayout(new java.awt.CardLayout());

        jPanelAddAddress.setBackground(new java.awt.Color(255, 255, 255));
        jPanelAddAddress.setMinimumSize(new java.awt.Dimension(983, 518));

        jLabel2.setText("電話番号");

        jButtonCustomerCheck.setText("顧客確認");
        jButtonCustomerCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCustomerCheckActionPerformed(evt);
            }
        });

        jLabel3.setText("住所");

        jLabel4.setText("名前");

        jCheckBox1.setText("他配達先");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel5.setText("電話番号");

        jButtonSelectItem.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        jButtonSelectItem.setText("商品選択へ");
        jButtonSelectItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectItemActionPerformed(evt);
            }
        });

        jLabel12.setText("配達備考");

        jButton3.setText("新規登録");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel18.setText("顧客番号");

        jLabelCustomerNumber.setText(" ");

        javax.swing.GroupLayout jPanelAddAddressLayout = new javax.swing.GroupLayout(jPanelAddAddress);
        jPanelAddAddress.setLayout(jPanelAddAddressLayout);
        jPanelAddAddressLayout.setHorizontalGroup(
            jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddAddressLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                        .addGap(75, 75, 75))
                    .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                        .addGroup(jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                                .addComponent(jLabelCustomerNumber)
                                .addGap(364, 364, 364)
                                .addComponent(jCheckBox1))
                            .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldSearchPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCustomerCheck)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addComponent(jLabel4)
                            .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5))
                            .addComponent(jLabel12)
                            .addComponent(jTextFieldDelivaryNote, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanelAddAddressLayout.setVerticalGroup(
            jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSearchPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCustomerCheck)
                    .addComponent(jButton3))
                .addGroup(jPanelAddAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelAddAddressLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jCheckBox1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddAddressLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelCustomerNumber)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldDelivaryNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jButtonSelectItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelCardBase.add(jPanelAddAddress, "card2");

        jPanelOrderItem.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jButton1.setText("確認画面へ");
        jButton1.setMaximumSize(new java.awt.Dimension(95, 21));
        jButton1.setMinimumSize(new java.awt.Dimension(95, 21));
        jButton1.setPreferredSize(new java.awt.Dimension(95, 21));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "商品番号", "商品名", "数量", "金額"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableOrder.setToolTipText("");
        jTableOrder.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableOrder);
        if (jTableOrder.getColumnModel().getColumnCount() > 0) {
            jTableOrder.getColumnModel().getColumn(0).setResizable(false);
            jTableOrder.getColumnModel().getColumn(1).setResizable(false);
            jTableOrder.getColumnModel().getColumn(2).setResizable(false);
            jTableOrder.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel9.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel9.setText("商品検索");

        jLabel10.setText("商品番号");

        jButtonSearchItemId.setText("商品番号検索");
        jButtonSearchItemId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchItemIdActionPerformed(evt);
            }
        });

        jPanelMenu.setBackground(new java.awt.Color(204, 255, 255));
        jPanelMenu.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "メニュー\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS UI Gothic", 1, 18))); // NOI18N

        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("ダブルクリックで商品選択");

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPaneMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMenuLayout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneMenu))
        );

        jButtonRemoveOrderItem.setText("商品を取り除く");
        jButtonRemoveOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveOrderItemActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel15.setText("合計金額(税込み) : ");

        jLabelOrderTotalPrice.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabelOrderTotalPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelOrderTotalPrice.setText("0円");

        jButtonIncrementOrderItem.setText("1つ足す");
        jButtonIncrementOrderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncrementOrderItemActionPerformed(evt);
            }
        });

        jButtonDecrementItem.setText("1つ減らす");
        jButtonDecrementItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDecrementItemActionPerformed(evt);
            }
        });

        jLabelTotalPriceWarn.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabelTotalPriceWarn.setForeground(new java.awt.Color(255, 0, 0));
        jLabelTotalPriceWarn.setText("注文は1500円以上から受け付けています");

        javax.swing.GroupLayout jPanelOrderItemLayout = new javax.swing.GroupLayout(jPanelOrderItem);
        jPanelOrderItem.setLayout(jPanelOrderItemLayout);
        jPanelOrderItemLayout.setHorizontalGroup(
            jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                        .addContainerGap(1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jTextFieldItemId, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(jButtonSearchItemId))
                            .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(25, 25, 25))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOrderItemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonIncrementOrderItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDecrementItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveOrderItem)))
                .addGap(18, 18, 18)
                .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelOrderItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelOrderTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelTotalPriceWarn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanelOrderItemLayout.setVerticalGroup(
            jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldItemId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSearchItemId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRemoveOrderItem)
                            .addComponent(jButtonIncrementOrderItem)
                            .addComponent(jButtonDecrementItem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOrderItemLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelOrderTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTotalPriceWarn)))
                    .addGroup(jPanelOrderItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addContainerGap())
        );

        jLabelTotalPriceWarn.getAccessibleContext().setAccessibleName("jLabel16");

        jPanelCardBase.add(jPanelOrderItem, "card3");

        jPanelFinakCheck.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jButton2.setText("注文確定");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTableOrderList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableOrderList);

        jLabel7.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("名前");

        jLabel8.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("連絡先");

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("配送先");

        jLabelName.setText("a");

        jLabelPhoneNumber.setText("a");

        jLabelAddress.setText("a");

        jLabel13.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("支払総金額");

        jLabelTotalPrice.setText("allPrice");

        jLabel14.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel14.setText("配達備考");

        jLabelDelivaryNote.setText("a");

        jLabel16.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel16.setText("消費税額");

        jLabelTax.setText("jLabel17");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(jLabelPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(340, 340, 340))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelDelivaryNote, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTax)
                            .addComponent(jLabelTotalPrice))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel7, jLabel8, jLabelTotalPrice});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabelPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabelDelivaryNote, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabelTax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTotalPrice))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jLabel6, jLabel7, jLabel8, jLabelAddress, jLabelName, jLabelPhoneNumber, jLabelTotalPrice});

        jLabel11.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel11.setText("伝票確認");

        javax.swing.GroupLayout jPanelFinakCheckLayout = new javax.swing.GroupLayout(jPanelFinakCheck);
        jPanelFinakCheck.setLayout(jPanelFinakCheckLayout);
        jPanelFinakCheckLayout.setHorizontalGroup(
            jPanelFinakCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFinakCheckLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFinakCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFinakCheckLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelFinakCheckLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(42, 42, 42)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelFinakCheckLayout.setVerticalGroup(
            jPanelFinakCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFinakCheckLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelFinakCheckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelFinakCheckLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanelCardBase.add(jPanelFinakCheck, "card4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCardBase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelHeadder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelHeadder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelCardBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if(jCheckBox1.isSelected()){
            jTextFieldName.setEditable(true);
            jTextFieldAddress.setEditable(true);
            jTextFieldPhoneNumber.setEditable(true);
            jTextFieldDelivaryNote.setEditable(true);
        }else{
            jTextFieldName.setEditable(false);
            jTextFieldAddress.setEditable(false);
            jTextFieldPhoneNumber.setEditable(false);
            jTextFieldDelivaryNote.setEditable(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButtonSelectItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectItemActionPerformed
        showCardOrderItem();
        control.checkAddress();
        jButtonSelectedItem.setEnabled(true);
        jLabelName.setText(jTextFieldName.getText());
        jLabelPhoneNumber.setText(jTextFieldPhoneNumber.getText());
        jLabelAddress.setText(jTextFieldAddress.getText());
    }//GEN-LAST:event_jButtonSelectItemActionPerformed

    private void jButtonShippingAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShippingAddressActionPerformed
        showCardAShippingAddress();
    }//GEN-LAST:event_jButtonShippingAddressActionPerformed

    private void jButtonSelectedItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectedItemActionPerformed
        showCardOrderItem();
    }//GEN-LAST:event_jButtonSelectedItemActionPerformed

    private void jButtonFinalCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFinalCheckActionPerformed
        control.showCardFinalCheck();
    }//GEN-LAST:event_jButtonFinalCheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        control.showCardFinalCheck();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonCustomerCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCustomerCheckActionPerformed
        if(jTextFieldSearchPhoneNumber.getText().equals("")){
            showErrorMessage("電話番号を入力してください", "エラー");
        }else{
            control.searchCustomer(jTextFieldSearchPhoneNumber.getText());
        }
    }//GEN-LAST:event_jButtonCustomerCheckActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        control.exit();
    }//GEN-LAST:event_formWindowClosing

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        control.showCustomerAddBoundary();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButtonSearchItemIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchItemIdActionPerformed
        control.searchItemItemNumber(jTextFieldItemId.getText());
        jTextFieldItemId.setText("");
    }//GEN-LAST:event_jButtonSearchItemIdActionPerformed

    private void jButtonRemoveOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveOrderItemActionPerformed
        if (jTableOrder.getSelectedRow() > -1) {
            control.removeOrderItem(jTableOrder.getSelectedRow());
        }
    }//GEN-LAST:event_jButtonRemoveOrderItemActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        List<Item> items = new ArrayList<>();
        
        for (int i = 0; i < jTableOrder.getRowCount(); i++) {
            Item item = new Item();
            item.setItemNumber(jTableOrder.getValueAt(i, 0).toString());
            item.setQuantity(Integer.parseInt(jTableOrder.getValueAt(i, 2).toString()));
            items.add(item);
        }
        
        control.orderFixing( Integer.parseInt(jLabelCustomerNumber.getText()), jTextFieldAddress.getText(), items);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButtonIncrementOrderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncrementOrderItemActionPerformed
        incrementOrderItem(jTableOrder.getSelectedRow());
        showOrderTotalPrice(calcTotalPrice());
    }//GEN-LAST:event_jButtonIncrementOrderItemActionPerformed

    private void jButtonDecrementItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDecrementItemActionPerformed
        decrementOrderItem(jTableOrder.getSelectedRow());
        showOrderTotalPrice(calcTotalPrice());
    }//GEN-LAST:event_jButtonDecrementItemActionPerformed
    
    private void menuTableSelectedItem(int row) {
        int tabNo = jTabbedPaneMenu.getSelectedIndex();
        
        if (jtableMenuList.get(tabNo).getSelectedRow() > -1) {
            control.addOrderItem(jtableMenuList.get(tabNo).getValueAt(row, 0).toString());
        }
    }
    
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
            java.util.logging.Logger.getLogger(OrderBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderBoundary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonCustomerCheck;
    private javax.swing.JButton jButtonDecrementItem;
    private javax.swing.JButton jButtonFinalCheck;
    private javax.swing.JButton jButtonIncrementOrderItem;
    private javax.swing.JButton jButtonRemoveOrderItem;
    private javax.swing.JButton jButtonSearchItemId;
    private javax.swing.JButton jButtonSelectItem;
    private javax.swing.JButton jButtonSelectedItem;
    private javax.swing.JButton jButtonShippingAddress;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCustomerNumber;
    private javax.swing.JLabel jLabelDelivaryNote;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelOrderTotalPrice;
    private javax.swing.JLabel jLabelPhoneNumber;
    private javax.swing.JLabel jLabelTax;
    private javax.swing.JLabel jLabelTotalPrice;
    private javax.swing.JLabel jLabelTotalPriceWarn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAddAddress;
    private javax.swing.JPanel jPanelCardBase;
    private javax.swing.JPanel jPanelFinakCheck;
    private javax.swing.JPanel jPanelHeadder;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelOrderItem;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneMenu;
    private javax.swing.JTabbedPane jTabbedPaneMenu;
    private javax.swing.JTable jTableMenu;
    private javax.swing.JTable jTableOrder;
    private javax.swing.JTable jTableOrderList;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldDelivaryNote;
    private javax.swing.JTextField jTextFieldItemId;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldPhoneNumber;
    private javax.swing.JTextField jTextFieldSearchPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
