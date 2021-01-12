/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import employeesMenu.EmployeesMenuControl;
import employeesMenu.order.OrderControl;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * 顧客コントロールクラス
 * @author 19jz0115
 */
public class CustomerControl {
    private final CustomerBoundary   customerBoundary;
    private final CustomerAddBoundary customerAddBoundary;
    private final CustomerDAO        customerDAO;
    private EmployeesMenuControl control;

    public CustomerControl() {
        customerBoundary = new CustomerBoundary();
        customerAddBoundary = new CustomerAddBoundary();
        customerDAO   = new CustomerDAO();
    }
    
    /**
     * コントロールを設定
     * @param control 従業員メニューコントロール
     */
    public void setControl(EmployeesMenuControl control){
        this.control = control;
    }
    
    /**
     * 各バウンダリーのコントロールをセット
     */
    public void initControl() {
        customerBoundary.setControl(this);
        customerAddBoundary.setControl(this);
    }
    
    public void showCustomerAddBoundary(){
        customerAddBoundary.setVisible(true);
    }
    
    public void showCustomerCheckBoundary(){
        customerBoundary.setVisible(true);
    }
    
    /**
     * 顧客を追加する
     * @param name          顧客名
     * @param address        住所
     * @param phoneNumber   電話番号
     */
    public void addCustomer(String name, String address, String phoneNumber) {
        Customer customer = new Customer(name, phoneNumber, address, name);
        
        try {
            customerDAO.dbAddCustomer(customer);
        }
        catch(SQLIntegrityConstraintViolationException e) {
            customerBoundary.showDBErrorMessage();
        }
        catch(SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
        
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     */
    public void searchCustomer(String phoneNumber) {
        try {
            List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
            if (customer.size() > 0) {
                customerBoundary.showMemberTextField(customer.get(0));
            }
            else {
                customerBoundary.showNotFoundErrorMessage(phoneNumber);
            }
        } catch (SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
        
    }
    
    /**
     * 顧客情報更新
     * @param customer 顧客情報
     */
    public void updateCustomer(Customer customer) {
        try {
            if (Objects.equals(customer, null)) {
                customerBoundary.showCustomerNothingErrorMessage();
            }
            else if (customer.isValid()) {
                customerDAO.dbUpdateCustomer(customer);
            } else {
                customerBoundary.showInvalidCustomerErrorMessage();
            }
        } catch (SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
        
    }
    
    /**
     * ×ボタン処理
     */
    public void exit(){
        customerBoundary.setVisible(false);
        customerAddBoundary.setVisible(false);
        control.exitMediaView();
    }
}
