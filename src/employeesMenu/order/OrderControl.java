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
import java.util.List;

/**
 *
 * @author 19jz0115
 */
public class OrderControl {
    private final OrderBoundary orderBoundary;
    private EmployeesMenuControl control;
    private final OrderDAO      orderDAO;
    private final CustomerDAO   customerDAO;
    
    public OrderControl() {
        orderBoundary   = new OrderBoundary();
        orderDAO        = new OrderDAO();
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
     * 他配達先指定
     * @param b true 他配達先指定 | false 顧客情報の配達先
     */
    public void setOtherDeliveryDestination(boolean b) {
        OrderBoundary.setDefaultLookAndFeelDecorated(b);
    }
    
    public void showCustomerAddBoundary(){
        control.showCustomerAddBoundary();
    }
    
    /**
     * ×ボタン処理
     */
    public void exit(){
        orderBoundary.setVisible(false);
        control.exitMediaView();
    }
}
