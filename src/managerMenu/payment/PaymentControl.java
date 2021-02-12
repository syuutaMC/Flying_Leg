/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu.payment;

import java.sql.SQLException;
import java.util.List;
import managerMenu.item.Category;
import managerMenu.item.CategoryDAO;
import managerMenu.ManagerMenuControl;
/**
 * 
 * @author syuuta
 */


public class PaymentControl {

    public ManagerMenuControl       control;
    private final CategoryDAO       categoryDAO;
    private final Category          category;
    private final PaymentBoundary   paymentBoundary;
    private final PaymentDAO        paymentDAO;
    private final SalesDAO          salesDAO;

    public PaymentControl() {
        category        = new Category();
        categoryDAO     = new CategoryDAO();
        paymentDAO      = new PaymentDAO();
        paymentBoundary = new PaymentBoundary();
        salesDAO        = new SalesDAO();
    }
    
    
    public void setControl(ManagerMenuControl control){
        this.control = control;
    }
    
    /**
     * カテゴリ取得
     * @return 
     */
    public List<Category> getCategory(){
        List<Category> categorys;

        return categorys = categoryDAO.dbSearchItemCategory();
    }
    
    /**
     * メインメニューの画面切り替え処理
     * @param str CardName
     */
    public void changeCardLayoutMain(String str){
        paymentBoundary.showCardLayoutMain(str);
    }
    
    public void changeCardLayoutSub(String str){
        paymentBoundary.showCardLayoutSub(str);
    }
    
    /**
     * 支払履歴を未払含め全件表示
     */
    public void showPaymentHistoryAll() {
        List<Payment> paymentList;
        try {
            paymentList = paymentDAO.dbSearchPaymentAll();
            
            if(paymentList.size() > 0) {
                paymentBoundary.showPaymentHistory(paymentList);
            } 
        } catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
        
    }
    
    /**
     * 支払済みの履歴を全件表示
     */
    public void showPaymentPaidHistoryAll() {
        List<Payment> paymentList;
        try {
            paymentList = paymentDAO.dbSearchPaymentPaid();
            
            if(paymentList.size() > 0) {
                paymentBoundary.showPaymentHistory(paymentList);
            } 
        } catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 未払の履歴を全件表示
     */
    public void showPaymentUnpaidHistoryAll() {
        List<Payment> paymentList;
        try {
            paymentList = paymentDAO.dbSearchPaymentUnpaid();
            
            if(paymentList.size() > 0) {
                paymentBoundary.showPaymentHistory(paymentList);
            } 
        } catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 注文番号の支払情報を表示する
     * @param orderNumber 注文番号
     */
    public void showPaymentOrderNumberHistory(int orderNumber) {
        List<Payment> paymentList;
        try {
            paymentList = paymentDAO.dbSearchPaymentOrderNumber(orderNumber);
            
            if(paymentList.size() > 0) {
                paymentBoundary.showPaymentHistory(paymentList);
            } 
        } catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 日間の売り上げを表示する
     */
    public void showSalesToday() {
        List<Sales> salesList;
        try {
            salesList = salesDAO.dbSearchSalesToday();
            
            if (salesList.size() > 0) {
                paymentBoundary.showDateSalesTable(salesList);
            }
        }
        catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 週間の売り上げを表示する
     */
    public void showSalesWeek() {
        List<Sales> salesList;
        try {
            salesList = salesDAO.dbSearchSalesThisWeek();
            
            if (salesList.size() > 0) {
                paymentBoundary.showWeekSalesTable(salesList);
            }
        }
        catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 月間の売り上げを表示する
     */
    public void showSalesMonth() {
        List<Sales> salesList;
        try {
            salesList = salesDAO.dbSearchSalesThisMonth();
            
            if (salesList.size() > 0) {
                paymentBoundary.showMonthSalesTable(salesList);
            }
        }
        catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
    }
    
    public void start(){
        paymentBoundary.setControl(this);
        paymentBoundary.setVisible(true);
    }
    
    /**
     * ×ボタン処理
     */
    public void exit() {
       paymentBoundary.setVisible(false);
       control.exitMediaView();
    }
}
