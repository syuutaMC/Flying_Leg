/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.awt.CardLayout;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import managerMenu.item.Category;

/**
 * ここで表示されるデータは当日分のみ
 * @author syuuta
 */
public class PaymentBoundary extends javax.swing.JFrame {
    
    private DefaultTableModel menuTableModel;
    private DefaultTableModel paymentHistoryTableModel;
    
    private DefaultTableModel dateSalesTableModel;
    private DefaultTableModel weekSalesTableModel;
    private DefaultTableModel monthSalesTableModel;
    
    private static final String CARD_SALES = "card3";
    private static final String CARD_PAYMENT = "card2";
    private static final String DAY = "card2";
    private static final String WEEK = "card3";
    private static final String MONTH = "card4";
    
    private final CardLayout cardLayout;
    private final CardLayout cardLayout2;
    
    public PaymentControl control;
    
    public PaymentBoundary() {
        initComponents();
        initjTextField();
        cardLayout = (CardLayout)jPanelCardBase.getLayout();
        cardLayout2 = (CardLayout)jPanelSalesCardBase.getLayout();
    }
    
    public void setControl(PaymentControl control){
        this.control = control;
        initTabedPane();
        initTableModel();
    }
    
    /** 初期化 ****************************************************************/
    
    private void initjTextField(){
        jLabelEmployeeName.setText("");
        jLabelEmplyeeNumber.setText("");
    }
    
    private void initTableModel() {
        
        //支払い確認画面表初期化/////////////////////////////////////////////////
        
        String[] paymentHistoryTitle = {"注文番号", "顧客名", "顧客電話番号", "注文日", "支払日", "金額"};
        
        paymentHistoryTableModel = new DefaultTableModel(paymentHistoryTitle, 0);
        jTablePaymentHistory.setModel(paymentHistoryTableModel);
        jTablePaymentHistory.setDefaultEditor(Object.class, null);  //エディタにnullを指定し編集不可に
        
        ////////////////////////////////////////////////////////////////////////
        
        //月間、週間、日間表の列見出し
        String[] salesTitle = {"注文日", "店舗番号", "注文回数", "売上金額"};
        
        //日間売上画面表初期化///////////////////////////////////////////////////
        
        dateSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableDateSales.setModel(dateSalesTableModel);
        jTableDateSales.setDefaultEditor(Object.class, null);
        
        ////////////////////////////////////////////////////////////////////////
        
        //週間売上画面表初期化///////////////////////////////////////////////////
        
        weekSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableWeekSales.setModel(weekSalesTableModel);
        jTableWeekSales.setDefaultEditor(Object.class, null);
        
        ////////////////////////////////////////////////////////////////////////
        
        //月間売上画面表初期化///////////////////////////////////////////////////
        
        monthSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableMonthSales.setModel(monthSalesTableModel);
        jTableMonthSales.setDefaultEditor(Object.class, null);
        
        ////////////////////////////////////////////////////////////////////////
    }
    
    private void initTabedPane(){
        jTabbedCategoryDate.removeAll();
        
        String[] menuTableTitle = {"商品番号","商品名", "注文数"};
        menuTableModel = new DefaultTableModel(menuTableTitle, 0);
        
        List<Category> categoryList = control.getCategory();
        
        int cnt = categoryList.size();
        
        JScrollPane[] scrollPane = new JScrollPane[cnt];
        JTable[] table = new JTable[cnt];
        
        ArrayList<JTable> TableList = new ArrayList<>();
        ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
        ArrayList<JScrollPane> scrollPaneList2 = new ArrayList<>();
        ArrayList<JScrollPane> scrollPaneList3 = new ArrayList<>();
        
        for(int i = 0; i < categoryList.size(); i++){
            TableList.add(new JTable(menuTableModel));
            scrollPaneList.add(new JScrollPane());
            scrollPaneList2.add(new JScrollPane());
            scrollPaneList3.add(new JScrollPane());
                     
            scrollPaneList.get(i).setViewportView(TableList.get(i));
            jTabbedCategoryDate.addTab(categoryList.get(i).getCategoryName(), scrollPaneList.get(i));
            jTabbedCategoryWeek.addTab(categoryList.get(i).getCategoryName(), scrollPaneList2.get(i));
            jTabbedCategoryMonth.addTab(categoryList.get(i).getCategoryName(), scrollPaneList3.get(i));
        }
    }

    /**************************************************************************/
    
    
    /** 画面切り替え ***********************************************************/
    
    /**
     * メインのカードパネル切り替え
     * @param card 
     */
    public void showCardLayoutMain(String card){
        cardLayout.show(jPanelCardBase, card);
    }
    
    /**
     * 売上管理画面の切り替え
     * @param card 
     */
    public void showCardLayoutSub(String card){
        cardLayout2.show(jPanelSalesCardBase, card);
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
     * データベースエラー発生時のエラーダイアログ表示
     */
    public void showDBErrorMessage() {
        showErrorMessage("データベースエラーが発生しました", "エラー");
    }
    
    /**************************************************************************/
    
    /** 支払履歴画面 ***********************************************************/
    
    /**
     * 支払履歴表示
     * @param paymentList 支払情報 
     */
    public void showPaymentHistory(List<Payment> paymentList) {
        
        //金額カンマ区切りフォーマット
        NumberFormat nf = NumberFormat.getNumberInstance();
        
        String[] column = new String[6];
        //行をクリア
        paymentHistoryTableModel.setRowCount(0);

        for (Payment payment : paymentList) {
            column[0] = Integer.toString(payment.getOrderNumber());
            column[1] = payment.getName();
            column[2] = payment.getPhoneNumber();
            column[3] = payment.getOrderDate();
            column[4] = payment.getPaymentDay();
            column[5] = nf.format(payment.getAmount());
            paymentHistoryTableModel.addRow(column);
        }
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanelTop = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelEmployeeName = new javax.swing.JLabel();
        jLabelEmplyeeNumber = new javax.swing.JLabel();
        jPanelCardBase = new javax.swing.JPanel();
        jPanelCheckPayment = new javax.swing.JPanel();
        jScrollPanePaymentHistory = new javax.swing.JScrollPane();
        jTablePaymentHistory = new javax.swing.JTable();
        jButtonAllSerarch = new javax.swing.JButton();
        jButtonNoCheckSearch = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButtonSearchOrderNumber = new javax.swing.JButton();
        jPanelSales = new javax.swing.JPanel();
        jPanelSalesCardBase = new javax.swing.JPanel();
        jPanelDateSales = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelDateSalesAmount = new javax.swing.JLabel();
        jScrollPaneDateSales = new javax.swing.JScrollPane();
        jTableDateSales = new javax.swing.JTable();
        jTabbedCategoryDate = new javax.swing.JTabbedPane();
        jPanelWeekSales = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabelWeekSalesAmount = new javax.swing.JLabel();
        jScrollPaneWeekEveryDaySales = new javax.swing.JScrollPane();
        jTableWeekEveryDaySales = new javax.swing.JTable();
        jTabbedCategoryWeek = new javax.swing.JTabbedPane();
        jLabel12 = new javax.swing.JLabel();
        jScrollPaneWeekSales = new javax.swing.JScrollPane();
        jTableWeekSales = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanelMonthSales = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabelMonthSalesAmount = new javax.swing.JLabel();
        jScrollPaneMonthEveryWeekSales = new javax.swing.JScrollPane();
        jTableMonthEveryWeekSales = new javax.swing.JTable();
        jTabbedCategoryMonth = new javax.swing.JTabbedPane();
        jLabel15 = new javax.swing.JLabel();
        jScrollPaneMonthSales = new javax.swing.JScrollPane();
        jTableMonthSales = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanelSubMenu = new javax.swing.JPanel();
        jButtonShowMonthSalesCard = new javax.swing.JButton();
        jButtonShowWeekSalesCard = new javax.swing.JButton();
        jButtonShowDateSalesCard = new javax.swing.JButton();
        jPanelMenu = new javax.swing.JPanel();
        jButtonShowSalesCard = new javax.swing.JButton();
        jButtonShowPaymentCard = new javax.swing.JButton();
        jButtonShowSalesManagementCard = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel2.setText("売上金額");

        jLabel3.setText("------円");

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel5.setText("注文数(１時間ごと)");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable5);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelTop.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel1.setText("売上管理");

        jLabelEmployeeName.setText("名前");

        jLabelEmplyeeNumber.setText("従業員番号");

        javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
        jPanelTop.setLayout(jPanelTopLayout);
        jPanelTopLayout.setHorizontalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelEmplyeeNumber)
                .addGap(31, 31, 31)
                .addComponent(jLabelEmployeeName)
                .addGap(67, 67, 67))
        );
        jPanelTopLayout.setVerticalGroup(
            jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmployeeName)
                    .addComponent(jLabelEmplyeeNumber))
                .addContainerGap())
        );

        jPanelCardBase.setLayout(new java.awt.CardLayout());

        jPanelCheckPayment.setBackground(new java.awt.Color(255, 255, 255));

        jTablePaymentHistory.setModel(new javax.swing.table.DefaultTableModel(
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
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePaymentHistory.getTableHeader().setReorderingAllowed(false);
        jScrollPanePaymentHistory.setViewportView(jTablePaymentHistory);
        if (jTablePaymentHistory.getColumnModel().getColumnCount() > 0) {
            jTablePaymentHistory.getColumnModel().getColumn(0).setResizable(false);
            jTablePaymentHistory.getColumnModel().getColumn(1).setResizable(false);
            jTablePaymentHistory.getColumnModel().getColumn(2).setResizable(false);
        }

        jButtonAllSerarch.setText("全件表示");
        jButtonAllSerarch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAllSerarchActionPerformed(evt);
            }
        });

        jButtonNoCheckSearch.setText("未確認分表示");
        jButtonNoCheckSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNoCheckSearchActionPerformed(evt);
            }
        });

        jLabel4.setText("注文番号");

        jButtonSearchOrderNumber.setText("検索");
        jButtonSearchOrderNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchOrderNumberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCheckPaymentLayout = new javax.swing.GroupLayout(jPanelCheckPayment);
        jPanelCheckPayment.setLayout(jPanelCheckPaymentLayout);
        jPanelCheckPaymentLayout.setHorizontalGroup(
            jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCheckPaymentLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelCheckPaymentLayout.createSequentialGroup()
                        .addComponent(jButtonAllSerarch)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonNoCheckSearch)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearchOrderNumber))
                    .addComponent(jScrollPanePaymentHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 932, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanelCheckPaymentLayout.setVerticalGroup(
            jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCheckPaymentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAllSerarch)
                    .addComponent(jButtonNoCheckSearch)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearchOrderNumber))
                .addGap(18, 18, 18)
                .addComponent(jScrollPanePaymentHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        jPanelCardBase.add(jPanelCheckPayment, "card2");

        jPanelSales.setBackground(new java.awt.Color(255, 255, 255));

        jPanelSalesCardBase.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSalesCardBase.setLayout(new java.awt.CardLayout());

        jPanelDateSales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel6.setText("売上金額");

        jLabelDateSalesAmount.setText("------円");

        jTableDateSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "タイトル 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDateSales.getTableHeader().setReorderingAllowed(false);
        jScrollPaneDateSales.setViewportView(jTableDateSales);
        if (jTableDateSales.getColumnModel().getColumnCount() > 0) {
            jTableDateSales.getColumnModel().getColumn(0).setResizable(false);
            jTableDateSales.getColumnModel().getColumn(1).setResizable(false);
            jTableDateSales.getColumnModel().getColumn(2).setResizable(false);
            jTableDateSales.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanelDateSalesLayout = new javax.swing.GroupLayout(jPanelDateSales);
        jPanelDateSales.setLayout(jPanelDateSalesLayout);
        jPanelDateSalesLayout.setHorizontalGroup(
            jPanelDateSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateSalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDateSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDateSalesLayout.createSequentialGroup()
                        .addComponent(jScrollPaneDateSales, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jTabbedCategoryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDateSalesLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDateSalesAmount)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanelDateSalesLayout.setVerticalGroup(
            jPanelDateSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDateSalesLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelDateSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDateSalesAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelDateSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedCategoryDate)
                    .addComponent(jScrollPaneDateSales, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelSalesCardBase.add(jPanelDateSales, "card2");

        jPanelWeekSales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel8.setText("週間売上金額");

        jLabelWeekSalesAmount.setText("-------円");

        jTableWeekEveryDaySales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "-月-日", "Title 2", "Title 3", "Title 4", "タイトル 5", "タイトル 6", "タイトル 7"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableWeekEveryDaySales.getTableHeader().setReorderingAllowed(false);
        jScrollPaneWeekEveryDaySales.setViewportView(jTableWeekEveryDaySales);
        if (jTableWeekEveryDaySales.getColumnModel().getColumnCount() > 0) {
            jTableWeekEveryDaySales.getColumnModel().getColumn(0).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(1).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(2).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(3).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(4).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(5).setResizable(false);
            jTableWeekEveryDaySales.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel12.setText("カテゴリごとの注文数");

        jTableWeekSales.setModel(new javax.swing.table.DefaultTableModel(
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
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableWeekSales.getTableHeader().setReorderingAllowed(false);
        jScrollPaneWeekSales.setViewportView(jTableWeekSales);
        if (jTableWeekSales.getColumnModel().getColumnCount() > 0) {
            jTableWeekSales.getColumnModel().getColumn(0).setResizable(false);
            jTableWeekSales.getColumnModel().getColumn(1).setResizable(false);
            jTableWeekSales.getColumnModel().getColumn(2).setResizable(false);
            jTableWeekSales.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel13.setText("１週間全体の売上");

        jLabel14.setText("各曜日の売上金額");

        javax.swing.GroupLayout jPanelWeekSalesLayout = new javax.swing.GroupLayout(jPanelWeekSales);
        jPanelWeekSales.setLayout(jPanelWeekSalesLayout);
        jPanelWeekSalesLayout.setHorizontalGroup(
            jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneWeekEveryDaySales)
                    .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                        .addGroup(jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabelWeekSalesAmount))
                            .addComponent(jScrollPaneWeekSales, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTabbedCategoryWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelWeekSalesLayout.setVerticalGroup(
            jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                .addGroup(jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanelWeekSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelWeekSalesAmount))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneWeekSales, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedCategoryWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneWeekEveryDaySales, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelSalesCardBase.add(jPanelWeekSales, "card3");

        jPanelMonthSales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel10.setText("月間売上金額");

        jLabelMonthSalesAmount.setText("-------円");

        jTableMonthEveryWeekSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "-月-日", "Title 2", "Title 3", "Title 4", "タイトル 5", "タイトル 6", "タイトル 7"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableMonthEveryWeekSales.getTableHeader().setReorderingAllowed(false);
        jScrollPaneMonthEveryWeekSales.setViewportView(jTableMonthEveryWeekSales);
        if (jTableMonthEveryWeekSales.getColumnModel().getColumnCount() > 0) {
            jTableMonthEveryWeekSales.getColumnModel().getColumn(0).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(1).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(2).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(3).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(4).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(5).setResizable(false);
            jTableMonthEveryWeekSales.getColumnModel().getColumn(6).setResizable(false);
        }

        jLabel15.setText("カテゴリごとの注文数");

        jTableMonthSales.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableMonthSales.getTableHeader().setReorderingAllowed(false);
        jScrollPaneMonthSales.setViewportView(jTableMonthSales);
        if (jTableMonthSales.getColumnModel().getColumnCount() > 0) {
            jTableMonthSales.getColumnModel().getColumn(0).setResizable(false);
            jTableMonthSales.getColumnModel().getColumn(1).setResizable(false);
            jTableMonthSales.getColumnModel().getColumn(2).setResizable(false);
            jTableMonthSales.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel16.setText("月間全体の売上");

        jLabel17.setText("各週の売上金額");

        javax.swing.GroupLayout jPanelMonthSalesLayout = new javax.swing.GroupLayout(jPanelMonthSales);
        jPanelMonthSales.setLayout(jPanelMonthSalesLayout);
        jPanelMonthSalesLayout.setHorizontalGroup(
            jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneMonthEveryWeekSales)
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabelMonthSalesAmount))
                            .addComponent(jScrollPaneMonthSales, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jTabbedCategoryMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMonthSalesLayout.setVerticalGroup(
            jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelMonthSalesAmount))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneMonthSales, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedCategoryMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneMonthEveryWeekSales, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelSalesCardBase.add(jPanelMonthSales, "card4");

        jPanelSubMenu.setBackground(new java.awt.Color(204, 204, 204));

        jButtonShowMonthSalesCard.setText("当日分売上");
        jButtonShowMonthSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowMonthSalesCardActionPerformed(evt);
            }
        });

        jButtonShowWeekSalesCard.setText("週間売上");
        jButtonShowWeekSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowWeekSalesCardActionPerformed(evt);
            }
        });

        jButtonShowDateSalesCard.setText("月間売上");
        jButtonShowDateSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowDateSalesCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSubMenuLayout = new javax.swing.GroupLayout(jPanelSubMenu);
        jPanelSubMenu.setLayout(jPanelSubMenuLayout);
        jPanelSubMenuLayout.setHorizontalGroup(
            jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonShowMonthSalesCard)
                .addGap(18, 18, 18)
                .addComponent(jButtonShowWeekSalesCard)
                .addGap(18, 18, 18)
                .addComponent(jButtonShowDateSalesCard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSubMenuLayout.setVerticalGroup(
            jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonShowMonthSalesCard)
                    .addComponent(jButtonShowWeekSalesCard)
                    .addComponent(jButtonShowDateSalesCard))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelSalesLayout = new javax.swing.GroupLayout(jPanelSales);
        jPanelSales.setLayout(jPanelSalesLayout);
        jPanelSalesLayout.setHorizontalGroup(
            jPanelSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSalesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSalesCardBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelSubMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanelSalesLayout.setVerticalGroup(
            jPanelSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSalesLayout.createSequentialGroup()
                .addComponent(jPanelSubMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelSalesCardBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelCardBase.add(jPanelSales, "card3");

        jButtonShowSalesCard.setText("売上確認");
        jButtonShowSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowSalesCardActionPerformed(evt);
            }
        });

        jButtonShowPaymentCard.setText("入金確認");
        jButtonShowPaymentCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowPaymentCardActionPerformed(evt);
            }
        });

        jButtonShowSalesManagementCard.setText("売上管理");

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonShowSalesManagementCard)
                    .addComponent(jButtonShowPaymentCard)
                    .addComponent(jButtonShowSalesCard))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButtonShowSalesCard)
                .addGap(18, 18, 18)
                .addComponent(jButtonShowSalesManagementCard)
                .addGap(49, 49, 49)
                .addComponent(jButtonShowPaymentCard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanelCardBase, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCardBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonShowSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowSalesCardActionPerformed
        control.changeCardLayoutMain(CARD_SALES);
    }//GEN-LAST:event_jButtonShowSalesCardActionPerformed

    private void jButtonShowMonthSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowMonthSalesCardActionPerformed
        control.changeCardLayoutSub(DAY);
    }//GEN-LAST:event_jButtonShowMonthSalesCardActionPerformed

    private void jButtonShowWeekSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowWeekSalesCardActionPerformed
        control.changeCardLayoutSub(WEEK);
    }//GEN-LAST:event_jButtonShowWeekSalesCardActionPerformed

    private void jButtonAllSerarchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAllSerarchActionPerformed
        control.showPaymentHistoryAll();
    }//GEN-LAST:event_jButtonAllSerarchActionPerformed

    private void jButtonNoCheckSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNoCheckSearchActionPerformed
        control.showPaymentUnpaidHistoryAll();
    }//GEN-LAST:event_jButtonNoCheckSearchActionPerformed

    private void jButtonShowPaymentCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowPaymentCardActionPerformed
        control.changeCardLayoutMain(CARD_PAYMENT);
    }//GEN-LAST:event_jButtonShowPaymentCardActionPerformed

    private void jButtonShowDateSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowDateSalesCardActionPerformed
        control.changeCardLayoutSub(MONTH);
    }//GEN-LAST:event_jButtonShowDateSalesCardActionPerformed

    private void jButtonSearchOrderNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchOrderNumberActionPerformed
        control.showPaymentOrderNumberHistory(Integer.parseInt(jTextField1.getText()));
    }//GEN-LAST:event_jButtonSearchOrderNumberActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        control.exit();
    }//GEN-LAST:event_formWindowClosing
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaymentBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentBoundary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentBoundary().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAllSerarch;
    private javax.swing.JButton jButtonNoCheckSearch;
    private javax.swing.JButton jButtonSearchOrderNumber;
    private javax.swing.JButton jButtonShowDateSalesCard;
    private javax.swing.JButton jButtonShowMonthSalesCard;
    private javax.swing.JButton jButtonShowPaymentCard;
    private javax.swing.JButton jButtonShowSalesCard;
    private javax.swing.JButton jButtonShowSalesManagementCard;
    private javax.swing.JButton jButtonShowWeekSalesCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelDateSalesAmount;
    private javax.swing.JLabel jLabelEmployeeName;
    private javax.swing.JLabel jLabelEmplyeeNumber;
    private javax.swing.JLabel jLabelMonthSalesAmount;
    private javax.swing.JLabel jLabelWeekSalesAmount;
    private javax.swing.JPanel jPanelCardBase;
    private javax.swing.JPanel jPanelCheckPayment;
    private javax.swing.JPanel jPanelDateSales;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelMonthSales;
    private javax.swing.JPanel jPanelSales;
    private javax.swing.JPanel jPanelSalesCardBase;
    private javax.swing.JPanel jPanelSubMenu;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JPanel jPanelWeekSales;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPaneDateSales;
    private javax.swing.JScrollPane jScrollPaneMonthEveryWeekSales;
    private javax.swing.JScrollPane jScrollPaneMonthSales;
    private javax.swing.JScrollPane jScrollPanePaymentHistory;
    private javax.swing.JScrollPane jScrollPaneWeekEveryDaySales;
    private javax.swing.JScrollPane jScrollPaneWeekSales;
    private javax.swing.JTabbedPane jTabbedCategoryDate;
    private javax.swing.JTabbedPane jTabbedCategoryMonth;
    private javax.swing.JTabbedPane jTabbedCategoryWeek;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTableDateSales;
    private javax.swing.JTable jTableMonthEveryWeekSales;
    private javax.swing.JTable jTableMonthSales;
    private javax.swing.JTable jTablePaymentHistory;
    private javax.swing.JTable jTableWeekEveryDaySales;
    private javax.swing.JTable jTableWeekSales;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
