/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeesMenu.customer;

import employeesMenu.order.OrderBoundary;

/**
 * 顧客コントロールクラス
 * @author 19jz0115
 */
public class CustomerControl {
    private OrderBoundary   orderBoundary;

    public CustomerControl() {
    }
    
    public void start() {
        //orderBoundary.setControl()
        //etc
    }
    
    /**
     * 顧客を追加する
     * @param name          顧客名
     * @param addres        住所
     * @param phoneNumber   電話番号
     */
    public void addCustomer(String name, String addres, String phoneNumber) {
        
    }
    
    /**
     * 顧客を検索する
     * @param phoneNumber 電話番号
     * @return            顧客情報
     */
    public Customer searchCustomer(String phoneNumber) {
        
    }
    
    /**
     * 顧客情報更新
     * @param name          名前
     * @param phoneNumber   住所
     * @param addres        電話番号
     * @param deliveryNote  配達備考
     */
    public void updateCustomer(String name, String phoneNumber, String addres, String deliveryNote) {
        
    }
}
