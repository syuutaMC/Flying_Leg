/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import java.util.List;

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
     * @return            顧客情報 見つからなければnull
     */
    public Customer searchCustomer(String phoneNumber) {
        List<Customer> customer = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
        if (customer.size() > 0) {
            return customer.get(0);
        }
        else {
            return null;
        }
    }
    
    /**
     * 顧客情報更新
     * @param customerNumber 顧客番号
     * @param name           名前
     * @param phoneNumber    住所
     * @param address        電話番号
     * @param deliveryNote   配達備考
     */
    public void updateCustomer(int customerNumber,String name, String phoneNumber, String address, String deliveryNote) {
        Customer customer = new Customer(customerNumber, name, phoneNumber, address, deliveryNote);
        customerDAO.dbUpdateCustomer(customer);
    }
}
