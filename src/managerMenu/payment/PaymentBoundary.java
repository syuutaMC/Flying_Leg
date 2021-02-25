/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import managerMenu.item.Category;

/**
 * ここで表示されるデータは当日分のみ
 * @author syuuta
 */
public class PaymentBoundary extends javax.swing.JFrame {
    
    private DefaultTableModel paymentHistoryTableModel;
    
    private DefaultTableModel dateSalesTableModel;
    private List<DefaultTableModel>  dateSalesAtCategoryModelList;
    
    private DefaultTableModel weekSalesTableModel;
    private DefaultTableModel weekAtDaySalesTableModel;
    private List<DefaultTableModel> weekSalesAtCategoryModelList;
    
    private DefaultTableModel monthSalesTableModel;
    private DefaultTableModel monthAtWeekSalesTableModel;
    private List<DefaultTableModel> monthSalesAtCategoryModelList;
    
    private List<JTable> dateSalesAtCategoryTableList;
    private List<JTable> weekSalesAtCategoryTableList;
    private List<JTable> monthSalesAtCategoryTableList;
    
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
        setTitle("売上管理画面");
        initjTextField();
        cardLayout = (CardLayout)jPanelCardBase.getLayout();
        cardLayout2 = (CardLayout)jPanelSalesCardBase.getLayout();
    }
    
    public void setControl(PaymentControl control){
        this.control = control;
        initTabedPane();
        initTableModel();
        
        control.showPaymentHistoryAll();
        control.showAllSalesCategoryTable(control.getCategory());
        
        control.showSalesMonth();
        control.showSalesMonthEveryWeek();
        
        control.showSalesWeek();
        control.showSalesWeekEveryDay();
        
        control.showSalesToday();
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
        setCellHorizontalAlignmentRight(jTablePaymentHistory, 5);
        //ダブルクリック選択リスナー設定
        jTablePaymentHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Point point = mouseEvent.getPoint();
                int row = jTablePaymentHistory.rowAtPoint(point);
                    if (mouseEvent.getClickCount() == 2 && jTablePaymentHistory.getSelectedRow() != -1) {
                        setPaidPayment(row);
                    }
                }
        });
        
        ////////////////////////////////////////////////////////////////////////
        
        //月間、週間、日間表の列見出し
        String[] salesTitle = {"注文日", "店舗番号", "注文回数", "売上金額"};
        
        //週間の曜日表の列見出し
        String[] salesWeekAtDayalesTitle = { "日", "月", "火", "水", "木", "金", "土"};
        
        //月間の週表の見出し
        String[] salesMonthAtWeek = {"週番号", "日", "月", "火", "水", "木", "金", "土"};
        
        //日間売上画面表初期化///////////////////////////////////////////////////
        
        dateSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableDateSales.setModel(dateSalesTableModel);
        jTableDateSales.setDefaultEditor(Object.class, null);
        setCellHorizontalAlignmentRight(jTableDateSales, 3);
        setCellHorizontalAlignmentRight(jTableDateSales, 2);
        
        jLabelDateSalesAmount.setText("0円");
        
        ////////////////////////////////////////////////////////////////////////
        
        //週間売上画面表初期化///////////////////////////////////////////////////
        
        //週ごとの売り上げ表
        weekSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableWeekSales.setModel(weekSalesTableModel);
        jTableWeekSales.setDefaultEditor(Object.class, null);
        setCellHorizontalAlignmentRight(jTableWeekSales, 3);
        setCellHorizontalAlignmentRight(jTableWeekSales, 2);
        
        jLabelWeekSalesAmount.setText("0円");
        
        //曜日ごとの売り上げ表
        weekAtDaySalesTableModel = new DefaultTableModel(salesWeekAtDayalesTitle, 0);
        jTableWeekEveryDaySales.setModel(weekAtDaySalesTableModel);
        jTableWeekEveryDaySales.setDefaultEditor(Object.class, null);
        
        for (int i = 0; i < 7; i++) {
            setCellHorizontalAlignmentRight(jTableWeekEveryDaySales, i);
        }
        
        ////////////////////////////////////////////////////////////////////////
        
        //月間売上画面表初期化///////////////////////////////////////////////////
        
        //月ごとの売上表
        monthSalesTableModel = new DefaultTableModel(salesTitle, 0);
        jTableMonthSales.setModel(monthSalesTableModel);
        jTableMonthSales.setDefaultEditor(Object.class, null);
        setCellHorizontalAlignmentRight(jTableMonthSales, 3);
        setCellHorizontalAlignmentRight(jTableMonthSales, 2);
        
        jLabelMonthSalesAmount.setText("0円");
        
        //週ごとの売上表
        monthAtWeekSalesTableModel = new DefaultTableModel(salesMonthAtWeek, 0);
        jTableMonthEveryWeekSales.setModel(monthAtWeekSalesTableModel);
        jTableMonthEveryWeekSales.setDefaultEditor(Object.class, null);
        
        for (int i = 1; i < 8; i++) {
            setCellHorizontalAlignmentRight(jTableMonthEveryWeekSales, i);
        }
        
        ////////////////////////////////////////////////////////////////////////
    }
    
    /**
     * タブパネル初期化 
     */
    private void initTabedPane(){
        jTabbedCategoryDate.removeAll();
        jTabbedCategoryWeek.removeAll();
        jTabbedCategoryMonth.removeAll();
        
        String[] menuTableTitle = { "店舗番号", "商品番号","商品名", "売上個数", "売上"};
        
        List<Category> categoryList = control.getCategory();
        
        int cnt = categoryList.size();
        
        JScrollPane[] scrollPane = new JScrollPane[cnt];
        JTable[] table = new JTable[cnt];
        
        List<DefaultTableModel> dateMenuTableModelList = new ArrayList<>();
        List<DefaultTableModel> weekMenuTableModelList = new ArrayList<>();
        List<DefaultTableModel> monthMenuTableModelList = new ArrayList<>();
        dateMenuTableModelList.clear();
        weekMenuTableModelList.clear();
        monthMenuTableModelList.clear();
        
        List<JTable> dateTableList = new ArrayList<>();
        List<JTable> weekTableList = new ArrayList<>();
        List<JTable> monthTableList = new ArrayList<>();
        dateTableList.clear();
        weekTableList.clear();
        monthTableList.clear();
        
        List<JScrollPane> scrollPaneList = new ArrayList<>();
        List<JScrollPane> scrollPaneList2 = new ArrayList<>();
        List<JScrollPane> scrollPaneList3 = new ArrayList<>();
        
        for(int i = 0; i < categoryList.size(); i++){
            DefaultTableModel dateMenuTableModel = new DefaultTableModel(menuTableTitle, 0);
            DefaultTableModel weekMenuTableModel = new DefaultTableModel(menuTableTitle, 0);
            DefaultTableModel monthMenuTableModel = new DefaultTableModel(menuTableTitle, 0);
            
            dateMenuTableModelList.add(dateMenuTableModel);
            weekMenuTableModelList.add(weekMenuTableModel);
            monthMenuTableModelList.add(monthMenuTableModel);
            
            dateTableList.add(new JTable(dateMenuTableModel));
            weekTableList.add(new JTable(weekMenuTableModel));
            monthTableList.add(new JTable(monthMenuTableModel));
            
            scrollPaneList.add(new JScrollPane());
            scrollPaneList2.add(new JScrollPane());
            scrollPaneList3.add(new JScrollPane());
                     
            scrollPaneList.get(i).setViewportView(dateTableList.get(i));
            scrollPaneList2.get(i).setViewportView(weekTableList.get(i));
            scrollPaneList3.get(i).setViewportView(monthTableList.get(i));
            
            jTabbedCategoryDate.addTab(categoryList.get(i).getCategoryName(), scrollPaneList.get(i));
            jTabbedCategoryWeek.addTab(categoryList.get(i).getCategoryName(), scrollPaneList2.get(i));
            jTabbedCategoryMonth.addTab(categoryList.get(i).getCategoryName(), scrollPaneList3.get(i));
        }
        initMenuTableModelSetEditableFalse(dateTableList);
        initMenuTableModelSetEditableFalse(weekTableList);
        initMenuTableModelSetEditableFalse(monthTableList);
        
        initMenuTableModelSetCellHorizontalAlignmentRight(dateTableList);
        initMenuTableModelSetCellHorizontalAlignmentRight(weekTableList);
        initMenuTableModelSetCellHorizontalAlignmentRight(monthTableList);
        
        dateSalesAtCategoryTableList = dateTableList;
        weekSalesAtCategoryTableList = weekTableList;
        monthSalesAtCategoryTableList = monthTableList;
        
        dateSalesAtCategoryModelList = dateMenuTableModelList;
        weekSalesAtCategoryModelList = weekMenuTableModelList;
        monthSalesAtCategoryModelList = monthMenuTableModelList;
        
    }

    /**
     * テーブルの指定した列を右寄せ表示にする
     * @param jTable       設定するテーブル
     * @param culumnNumber 設定する列
     */
    private void setCellHorizontalAlignmentRight(JTable jTable, int culumnNumber) {
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        jTable.getColumnModel().getColumn(culumnNumber).setCellRenderer(rightCellRenderer);
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
    
    
    /**
     * メニュー表の右寄せ初期化
     * @param jTableList 設定するテーブル
     */
    private void initMenuTableModelSetCellHorizontalAlignmentRight(List<JTable> jTableList) {
        for (JTable jTable : jTableList) {
            setCellHorizontalAlignmentRight(jTable, 3);
            setCellHorizontalAlignmentRight(jTable, 4);
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
        
        switch(card) {
            case DAY:
                
                break;
                
            case WEEK:
                
                break;
                
            case MONTH:
                
                break;
        }
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
    
    /**
     * コンファームダイアログ表示
     * @param message メッセージ
     * @param title   タイトル
     * @return 結果
     */
    public int showConfirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION);
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
            column[3] = payment.getOrderDate("yyyy年 MM月 dd日");
            column[4] = payment.getPaymentDay("yyyy年 MM月 dd日");
            column[5] = nf.format(payment.getAmount()) + "円";
            paymentHistoryTableModel.addRow(column);
        }
    }
    
    /**
     * ダブルクリックされた行を支払済みにする
     * @param row 行番号
     */
    public void setPaidPayment(int row) {
        if ("未入金".equals(jTablePaymentHistory.getValueAt(row, 4).toString())) {
            control.setPaidPayment(Integer.parseInt(jTablePaymentHistory.getValueAt(row, 0).toString()));
            control.showPaymentHistoryAll();
        }
    }
    
    /**************************************************************************/
    
    /** 日間売上画面 ***********************************************************/
    
        /**
         * 日間売上表に売上情報を表示
         * @param salesList 売上情報
         */
        public void showDateSalesTable(List<Sales> salesList) {
            
            //金額カンマ区切りフォーマット
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            String[] column = new String[4];
            int total = 0;
            
            dateSalesTableModel.setRowCount(0);
            
            for (Sales sales : salesList) {
                column[0] = sales.getSalesDate("yyyy年 MM月 dd日");
                column[1] = Integer.toString(sales.getStoreNumber());
                column[2] = Integer.toString(sales.getOrderQuantity()) + "回";
                column[3] = nf.format(sales.getSalesAmount()) + "円";
                
                total += sales.getSalesAmount();
                
                dateSalesTableModel.addRow(column);
            }
            
            jLabelDateSalesAmount.setText(nf.format(total) + "円");
        }
        
        /**
         * 日間の商品カテゴリ別売り上げ
         * @param salesAtCategoryList 商品カテゴリ別売り上げ
         */
        public void showDateSalesAtCategory(List<SalesAtCategory> salesAtCategoryList, int tabIndex) {
            
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            dateSalesAtCategoryModelList.get(tabIndex).setRowCount(0);
            String[] row = new String[5];
            
            for (SalesAtCategory salesAtCategory : salesAtCategoryList) {
                row[0] = Integer.toString(salesAtCategory.getStoreNumber());
                row[1] = salesAtCategory.getItemNumber();
                row[2] = salesAtCategory.getItemName();
                row[3] = Integer.toString(salesAtCategory.getOrderQuantity()) + "個";
                row[4] = nf.format(salesAtCategory.getSalesAmount()) + "円";
                
                dateSalesAtCategoryModelList.get(tabIndex).addRow(row);
            }
        }
        
    /**************************************************************************/
        
    /** 週間売上画面 ***********************************************************/
        
        /**
         * 週間売上表に売上情報を表示
         * @param salesList 売上情報
         */
        public void showWeekSalesTable(List<Sales> salesList) {
            
            //金額カンマ区切りフォーマット
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            String[] column = new String[4];
            int total = 0;
            
            weekSalesTableModel.setRowCount(0);
            
            for (Sales sales : salesList) {
                column[0] = sales.getSalesDate("yyyy年 MM月 第W週");
                column[1] = Integer.toString(sales.getStoreNumber());
                column[2] = Integer.toString(sales.getOrderQuantity()) + "回";
                column[3] = nf.format(sales.getSalesAmount()) + "円";
                
                total += sales.getSalesAmount();
                
                weekSalesTableModel.addRow(column);
            }
            
            jLabelWeekSalesAmount.setText(nf.format(total) + "円");
        }
        
        /**
         * 週間の各曜日を表示
         * @param salesAtWeekList 週間売上情報
         */
        public void showWeekEveryDaySalesTable(List<SalesAtWeek> salesAtWeekList) {
            
            //金額カンマ区切りフォーマット
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            String[] column = new String[7];
            
            weekAtDaySalesTableModel.setRowCount(0);
            
            for (SalesAtWeek salesAtWeek : salesAtWeekList) {
                column[0] = nf.format(salesAtWeek.getSun()) + "円";
                column[1] = nf.format(salesAtWeek.getMon()) + "円";
                column[2] = nf.format(salesAtWeek.getTue()) + "円";
                column[3] = nf.format(salesAtWeek.getWed()) + "円";
                column[4] = nf.format(salesAtWeek.getThu()) + "円";
                column[5] = nf.format(salesAtWeek.getFry()) + "円";
                column[6] = nf.format(salesAtWeek.getSat()) + "円";
                
                weekAtDaySalesTableModel.addRow(column);
            }
        }
    
        /**
         * 週間の商品カテゴリ別売り上げ
         * @param salesAtCategoryList 商品カテゴリ別売り上げ
         */
        public void showWeekSalesAtCategory(List<SalesAtCategory> salesAtCategoryList, int tabIndex) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            weekSalesAtCategoryModelList.get(tabIndex).setRowCount(0);
            String[] row = new String[5];
            
            for (SalesAtCategory salesAtCategory : salesAtCategoryList) {
                row[0] = Integer.toString(salesAtCategory.getStoreNumber());
                row[1] = salesAtCategory.getItemNumber();
                row[2] = salesAtCategory.getItemName();
                row[3] = Integer.toString(salesAtCategory.getOrderQuantity()) + "個";
                row[4] = nf.format(salesAtCategory.getSalesAmount()) + "円";
                
                weekSalesAtCategoryModelList.get(tabIndex).addRow(row);
            }
        }
        
    /**************************************************************************/
    
    /** 月間売上画面 ***********************************************************/
        
        /**
         * 月間売上表に売上情報を表示
         * @param salesList 売上情報
         */
        public void showMonthSalesTable(List<Sales> salesList) {
            
            //金額カンマ区切りフォーマット
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            String[] column = new String[4];
            int total = 0;
            monthSalesTableModel.setRowCount(0);
            
            for (Sales sales : salesList) {
                column[0] = sales.getSalesDate("yyyy年 MM月");
                column[1] = Integer.toString(sales.getStoreNumber());
                column[2] = Integer.toString(sales.getOrderQuantity()) + "回";
                column[3] = nf.format(sales.getSalesAmount()) + "円";
                
                total += sales.getSalesAmount();
                
                monthSalesTableModel.addRow(column);
            }
            
            jLabelMonthSalesAmount.setText(nf.format(total) + "円");
        }
        
        /**
         * 月の各週の売り上げを確認
         * @param salesAtWeekList 週間売上情報 
         */
        public void showMonthEveryWeekSalesTable(List<SalesAtWeek> salesAtWeekList) {
            
            //金額カンマ区切りフォーマット
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            String[] column = new String[8];
            
            monthAtWeekSalesTableModel.setRowCount(0);
            
            for (SalesAtWeek salesAtWeek : salesAtWeekList) {
                column[0] = "第" + Integer.toHexString(salesAtWeek.getWeekNumber()) + "週";
                column[1] = nf.format(salesAtWeek.getSun()) + "円";
                column[2] = nf.format(salesAtWeek.getMon()) + "円";
                column[3] = nf.format(salesAtWeek.getTue()) + "円";
                column[4] = nf.format(salesAtWeek.getWed()) + "円";
                column[5] = nf.format(salesAtWeek.getThu()) + "円";
                column[6] = nf.format(salesAtWeek.getFry()) + "円";
                column[7] = nf.format(salesAtWeek.getSat()) + "円";
                
                monthAtWeekSalesTableModel.addRow(column);
            }
        }
        
        /**
         * 月間の商品カテゴリ別売り上げ
         * @param salesAtCategoryList 商品カテゴリ別売り上げ
         */
        public void showMonthSalesAtCategory(List<SalesAtCategory> salesAtCategoryList, int tabIndex) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            
            monthSalesAtCategoryModelList.get(tabIndex).setRowCount(0);
            String[] row = new String[5];
            
            for (SalesAtCategory salesAtCategory : salesAtCategoryList) {
                row[0] = Integer.toString(salesAtCategory.getStoreNumber());
                row[1] = salesAtCategory.getItemNumber();
                row[2] = salesAtCategory.getItemName();
                row[3] = Integer.toString(salesAtCategory.getOrderQuantity()) + "個";
                row[4] = nf.format(salesAtCategory.getSalesAmount()) + "円";
                
                monthSalesAtCategoryModelList.get(tabIndex).addRow(row);
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
        jLabel7 = new javax.swing.JLabel();
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
        jButtonShowDateSalesCard = new javax.swing.JButton();
        jButtonShowWeekSalesCard = new javax.swing.JButton();
        jButtonShowMonthSalesCard = new javax.swing.JButton();
        jPanelMenu = new javax.swing.JPanel();
        jButtonShowSalesCard = new javax.swing.JButton();
        jButtonShowPaymentCard = new javax.swing.JButton();

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

        jLabel7.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel7.setText("入金確認");

        javax.swing.GroupLayout jPanelCheckPaymentLayout = new javax.swing.GroupLayout(jPanelCheckPayment);
        jPanelCheckPayment.setLayout(jPanelCheckPaymentLayout);
        jPanelCheckPaymentLayout.setHorizontalGroup(
            jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCheckPaymentLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanelCheckPaymentLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addGap(17, 17, 17)
                .addGroup(jPanelCheckPaymentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAllSerarch)
                    .addComponent(jButtonNoCheckSearch)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearchOrderNumber)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPanePaymentHistory, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        jPanelCardBase.add(jPanelCheckPayment, "card2");

        jPanelSales.setBackground(new java.awt.Color(255, 255, 255));

        jPanelSalesCardBase.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSalesCardBase.setLayout(new java.awt.CardLayout());

        jPanelDateSales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel6.setText("日間金額");

        jLabelDateSalesAmount.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
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
                        .addComponent(jScrollPaneDateSales, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jTabbedCategoryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDateSalesLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDateSalesAmount)))
                .addContainerGap(40, Short.MAX_VALUE))
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

        jLabelWeekSalesAmount.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
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
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(174, 174, 174)
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelWeekSalesLayout.createSequentialGroup()
                        .addComponent(jScrollPaneWeekSales, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedCategoryWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(jLabel14))
                    .addGroup(jPanelWeekSalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTabbedCategoryWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 23, Short.MAX_VALUE)
                .addComponent(jScrollPaneWeekEveryDaySales, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelSalesCardBase.add(jPanelWeekSales, "card3");

        jPanelMonthSales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel10.setText("月間売上金額");

        jLabelMonthSalesAmount.setFont(new java.awt.Font("MS UI Gothic", 0, 18)); // NOI18N
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
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addComponent(jScrollPaneMonthEveryWeekSales)
                        .addContainerGap())
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)
                                .addComponent(jLabelMonthSalesAmount))
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jScrollPaneMonthSales, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelMonthSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMonthSalesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                                .addComponent(jTabbedCategoryMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))))))
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
                        .addComponent(jLabel17))
                    .addGroup(jPanelMonthSalesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedCategoryMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 23, Short.MAX_VALUE)
                .addComponent(jScrollPaneMonthEveryWeekSales, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelSalesCardBase.add(jPanelMonthSales, "card4");

        jPanelSubMenu.setBackground(new java.awt.Color(204, 204, 204));

        jButtonShowDateSalesCard.setText("当日分売上");
        jButtonShowDateSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowDateSalesCardActionPerformed(evt);
            }
        });

        jButtonShowWeekSalesCard.setText("週間売上");
        jButtonShowWeekSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowWeekSalesCardActionPerformed(evt);
            }
        });

        jButtonShowMonthSalesCard.setText("月間売上");
        jButtonShowMonthSalesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowMonthSalesCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSubMenuLayout = new javax.swing.GroupLayout(jPanelSubMenu);
        jPanelSubMenu.setLayout(jPanelSubMenuLayout);
        jPanelSubMenuLayout.setHorizontalGroup(
            jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonShowDateSalesCard)
                .addGap(18, 18, 18)
                .addComponent(jButtonShowWeekSalesCard)
                .addGap(18, 18, 18)
                .addComponent(jButtonShowMonthSalesCard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSubMenuLayout.setVerticalGroup(
            jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSubMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSubMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonShowDateSalesCard)
                    .addComponent(jButtonShowWeekSalesCard)
                    .addComponent(jButtonShowMonthSalesCard))
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

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonShowPaymentCard)
                    .addComponent(jButtonShowSalesCard))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButtonShowSalesCard)
                .addGap(57, 57, 57)
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
        control.showAllSalesTable();
        control.showAllSalesCategoryTable(control.getCategory());
    }//GEN-LAST:event_jButtonShowSalesCardActionPerformed

    private void jButtonShowDateSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowDateSalesCardActionPerformed
        control.changeCardLayoutSub(DAY);
    }//GEN-LAST:event_jButtonShowDateSalesCardActionPerformed

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

    private void jButtonShowMonthSalesCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowMonthSalesCardActionPerformed
        control.changeCardLayoutSub(MONTH);
    }//GEN-LAST:event_jButtonShowMonthSalesCardActionPerformed

    private void jButtonSearchOrderNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchOrderNumberActionPerformed
        if (jTextField1.getText().equals("")) {
            control.showPaymentHistoryAll();
        }
        else {
            if (jTextField1.getText().matches("[0-9]*")) {
                control.showPaymentOrderNumberHistory(Integer.parseInt(jTextField1.getText()));
            }
            else {
                showErrorMessage("入力は数字のみです", "入力エラー");
            }
        }
        
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
    private javax.swing.JLabel jLabel7;
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
