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

    public ManagerMenuControl control;
    private final CategoryDAO categoryDAO;
    private final Category category;
    private final PaymentBoundary paymentBoundary;
    private final PaymentDAO paymentDAO;

    public PaymentControl() {
        category = new Category();
        categoryDAO = new CategoryDAO();
        paymentDAO = new PaymentDAO();
        paymentBoundary = new PaymentBoundary();
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
    
    public void showPaymentHistoryAll() {
        
        try {
            paymentDAO.dbSearchPaymentAll();
        } catch (SQLException e) {
            paymentBoundary.showDBErrorMessage();
        }
        
    }
    
    public void start(){
        paymentBoundary.setControl(this);
        paymentBoundary.setVisible(true);
    }
}
