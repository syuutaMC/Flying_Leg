/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * 顧客コントロールクラス
 * @author 19jz0115
 */
public class CustomerControl {
    private final CustomerBoundary   customerBoundary;
    private final CustomerDAO        customerDAO;

    public CustomerControl() {
        customerBoundary = new CustomerBoundary();
        customerDAO   = new CustomerDAO();
    }
    
    public void start() {
        customerBoundary.setControl(this);
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
        customerDAO.dbAddCustomer(customer);
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     */
    public void searchCustomer(String phoneNumber) {
        List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
        if (customer.size() > 0) {
            customerBoundary.showNotFoundErrorMessage(phoneNumber);
        }
        else {
            customerBoundary.showMemberTextField(customer.get(0));
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
}
