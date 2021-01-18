/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.order;

import employeesMenu.customer.Customer;
import employeesMenu.customer.CustomerControl;
import employeesMenu.customer.CustomerDAO;
import employeesMenu.EmployeesMenuControl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import managerMenu.item.Item;

/**
 *
 * @author 19jz0115
 */
public class OrderControl {
    private final OrderBoundary orderBoundary;
    private EmployeesMenuControl control;
    private final ItemDAO      orderDAO;
    private final CustomerDAO   customerDAO;
    
    public OrderControl() {
        orderBoundary   = new OrderBoundary();
        orderDAO        = new ItemDAO();
        customerDAO     = new CustomerDAO();
        setControl(control);
    }
    
    /**
     * コントロールを設定
     * @param control 従業員メニューコントロール
     */
    public void setControl(EmployeesMenuControl control){
        this.control = control;
    }
    
    public void start() {
        orderBoundary.setControl(this);
        orderBoundary.setVisible(true);
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     */
    public void searchCustomer(String phoneNumber) {
        try {
            List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
            if (customer.size() > 0) {
                orderBoundary.showCustomerTextField(customer.get(0));
            }
            else {
                orderBoundary.showNotFoundErrorMessage(phoneNumber);
            }
        } catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 商品を商品番号で検索する
     * @param itemNumber 商品番号
     */
    public void searchItemItemNumber(String itemNumber) {
        try {
            List<Item> itemList = orderDAO.dbSearchItemItemNumber(itemNumber);
            if (itemList.size() > 0) {
                
            }
            else {
                
            }
            
        }
        catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 商品を商品名で検索する
     * @param itemName 商品名
     */
    public void searchItemItemName(String itemName) {
        try {
            List<Item> itemList = orderDAO.dbSearchItemItemName(itemName);
            if (itemList.size() > 0) {
                
            }
            else {
                
            }
        }
        catch (SQLException e) {
            orderBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 他配達先指定
     * @param b true 他配達先指定 | false 顧客情報の配達先
     */
    public void setOtherDeliveryDestination(boolean b) {
        OrderBoundary.setDefaultLookAndFeelDecorated(b);
    }
    
    public void showCustomerAddBoundary(){
        control.showCustomerAddBoundary();
    }
    
    public void showCustomerAddBoundary(String phoneNumber){
        control.showCustomerAddBoundary(phoneNumber);
    }
    
    /**
     * ×ボタン処理
     */
    public void exit(){
        orderBoundary.setVisible(false);
        control.exitMediaView();
    }
}
