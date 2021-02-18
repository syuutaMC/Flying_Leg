/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import employeesMenu.EmployeesControl;
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
    private EmployeesControl control;

    public CustomerControl() {
        customerBoundary = new CustomerBoundary();
        customerAddBoundary = new CustomerAddBoundary();
        customerDAO   = new CustomerDAO();
    }
    
    /**
     * コントロールを設定
     * @param control 従業員メニューコントロール
     */
    public void setControl(EmployeesControl control){
        this.control = control;
    }
    
    /**
     * 各バウンダリーのコントロールをセット
     */
    public void initControl() {
        customerBoundary.setControl(this);
        customerAddBoundary.setControl(this);
    }
    
    /**
     * 顧客登録画面を表示 
     */
    public void showCustomerAddBoundary(){
        customerAddBoundary.initTextField();
        customerAddBoundary.setVisible(true);
    }
    
    public void showCustomerAddBoundary(String phoneNumber){
        customerAddBoundary.setVisible(true);
        customerAddBoundary.setPhoneNumber(phoneNumber);
    }
    
    public void showCustomerCheckBoundary(){
        customerBoundary.setVisible(true);
    }
    
    /**
     * 顧客を追加する
     * @param name          顧客名
     * @param address        住所
     * @param phoneNumber   電話番号
     * @param delivaryNote  配達備考
     */
    public void addCustomer(String name, String address, String phoneNumber, String delivaryNote) {
        Customer customer = new Customer(name, phoneNumber, address, delivaryNote);
        
        try {
            customerDAO.dbAddCustomer(customer);
            customerAddBoundary.showRegistrationSuccessMessage();
            
        }
        catch(SQLIntegrityConstraintViolationException e) {
            customerAddBoundary.showRegisteredErrorMeessage(phoneNumber);
        }
        catch(SQLException e) {
            customerAddBoundary.showDBErrorMessage();
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
                customerBoundary.setCustomer(customer.get(0));
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
                showAllCustomerTable();
                customerBoundary.showConfirmMessage("顧客情報を変更しました", "確認");
            } else {
                customerBoundary.showInvalidCustomerErrorMessage();
            }
        } catch (SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
        
    }
    
    /**
     * 顧客情報全件表示処理
     */
    public void showAllCustomerTable() {
        List<Customer> customerList;
        try {
            customerList = customerDAO.dbSearchCustomerAll();
            
            if (customerList.size() > 0) {
                customerBoundary.showCustomerTable(customerList);
            }
        } catch (SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * 顧客情報を削除する
     * @param phoneNumber 電話番号 
     */
    void deleteCustomer(String phoneNumber) {
        List<Customer> customerList;
        try {
            customerList = customerDAO.dbSearchCustomerPhoneNumber(phoneNumber);
            
            if (customerList.size() > 0) {
                customerDAO.dbDeleteCustomer(phoneNumber);
                customerBoundary.clearMemberTextField();
            }
        }
        catch (SQLException e) {
            customerBoundary.showDBErrorMessage();
        }
    }
    
    /**
     * ×ボタン処理
     */
    public void exit(){
        customerBoundary.setVisible(false);
        control.exitMediaView();
    }
    
    public void exitCustomerAddBoundary(){
        customerAddBoundary.setVisible(false);
    }
}
